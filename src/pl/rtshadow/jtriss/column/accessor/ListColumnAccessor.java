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
  public ListColumnAccessor(Class<T> type, ColumnConstructor<T> constructor) {
    this.constructor = constructor;
    this.type = type;
  }

  private ListColumnAccessor(Class<T> type, SortedColumn<T> column) {
    this.column = column;
    this.type = type;
  }

  @Override
  public ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement) {
    ReconstructedObject<T> reconstructedListOfValues = retrieveListOfValues(firstElement);
    if (isEmpty(reconstructedListOfValues)) {
      return null;
    }
    return reconstructedListOfValues;
  }

  private ReconstructedObject<T> retrieveListOfValues(ColumnElement<T> element) {
    ArrayList<T> valuesList = new ArrayList<T>();
    while (column.contains(element)) {
      valuesList.add(element.getValue());
      element = element.getNextElementInTheRow();
    }
    return new ReconstructedObject<T>(valuesList, element);
  }

  @SuppressWarnings("unchecked")
  private boolean isEmpty(ReconstructedObject<T> reconstructedObject) {
    List<T> values = (List<T>) reconstructedObject.getObject();
    return values.isEmpty();
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
  public ColumnAccessor<T> subColumn(ValueRange<T> range) {
    return new ListColumnAccessor<T>(type, column.getSubColumn(range));
  }
}
