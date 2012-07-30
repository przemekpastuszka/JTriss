package pl.rtshadow.jtriss.column.accessor;

import static org.apache.commons.lang3.BooleanUtils.negate;
import static pl.rtshadow.jtriss.column.element.ColumnElementFactory.createElement;

import java.util.ArrayList;
import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public class ListColumnAccessor<T extends Comparable<? super T>> extends AbstractColumnAccessor<T> {
  private ListColumnAccessor(Class<T> type, SortedColumn<T> column) {
    this.column = column;
    this.type = type;
  }

  @Override
  public ReconstructedObject<T> reconstruct(ColumnElement<T> element) {
    boolean hasAnyElementInsideColumn = false;
    int originalColumnId = element.getColumnId();

    ArrayList<T> valuesList = new ArrayList<T>();
    while (element.getColumnId() == originalColumnId) {
      hasAnyElementInsideColumn |= column.contains(element);
      valuesList.add(element.getValue());
      element = element.getNextElementInTheRow();
    }

    if (hasAnyElementInsideColumn) {
      return new ReconstructedObject<T>(valuesList, element);
    }
    return null;
  }

  @Override
  public ColumnAccessor<T> subColumn(ValueRange<T> range) {
    return new ListColumnAccessor<T>(type, column.getSubColumn(range));
  }

  public static <T extends Comparable<? super T>> ColumnAccessorGenerator<T> generator(Class<T> type, ColumnConstructor<T> constructor) {
    return new ListColumnAccessorGenerator<T>(type, constructor);
  }

  private static class ListColumnAccessorGenerator<T extends Comparable<? super T>> extends AbstractColumnAccessorGenerator<T> {

    public ListColumnAccessorGenerator(Class<T> type, ColumnConstructor<T> constructor) {
      this.constructor = constructor;
      this.type = type;
    }

    @Override
    public ModifiableColumnElement<T> insert(Object object, ColumnElement<T> nextElement) {
      List<T> valuesList = retrieveNonEmptyListFrom(object);
      List<ModifiableColumnElement<T>> columnElements = mapValuesToColumnElements(valuesList);

      for (int i = 0; i < columnElements.size() - 1; ++i) {
        setNextElementAndAddToConstructor(columnElements.get(i), columnElements.get(i + 1));
      }

      ModifiableColumnElement<T> lastElement = columnElements.get(columnElements.size() - 1);
      setNextElementAndAddToConstructor(lastElement, nextElement);

      return lastElement;
    }

    private void setNextElementAndAddToConstructor(ModifiableColumnElement<T> currentElement, ColumnElement<T> next) {
      currentElement.setNextElement(next);
      constructor.add(currentElement);
    }

    private List<ModifiableColumnElement<T>> mapValuesToColumnElements(List<T> valuesList) {
      List<ModifiableColumnElement<T>> columnElements = new ArrayList<ModifiableColumnElement<T>>(valuesList.size());
      for (T singleValue : valuesList) {
        columnElements.add(createElement(type, singleValue));
      }
      return columnElements;
    }

    private List<T> retrieveNonEmptyListFrom(Object value) {
      if (negate(value instanceof List)) {
        throw new IllegalArgumentException("Expected instance of java.util.List, got: " + value.getClass());
      }

      @SuppressWarnings("unchecked")
      List<T> valuesList = (List<T>) value;

      if (valuesList.isEmpty()) {
        throw new UnsupportedOperationException("Empty lists are not yet supported");
      }

      return valuesList;
    }

    @Override
    public ColumnAccessor<T> prepareColumnAccessor() {
      return new ListColumnAccessor<T>(type, constructor.generate());
    }
  }
}
