package pl.rtshadow.jtriss.column.accessor;

import static pl.rtshadow.jtriss.column.element.ColumnElementFactory.createElement;
import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public class ScalarColumnAccessor<T extends Comparable<? super T>> extends AbstractColumnAccessor<T> {
  public ScalarColumnAccessor(Class<T> type, ColumnConstructor<T> constructor) {
    this.constructor = constructor;
    this.type = type;
  }

  private ScalarColumnAccessor(Class<T> type, SortedColumn<T> column) {
    this.column = column;
    this.type = type;
  }

  @Override
  public ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement) {
    if (column.contains(firstElement)) {
      return new ReconstructedObject<T>(firstElement.getValue(), firstElement.getNextElementInTheRow());
    }
    return null;
  }

  @Override
  public ModifiableColumnElement<T> insert(Object value, ColumnElement<T> nextElement) {
    ModifiableColumnElement<T> element = createElement(type, value);
    element.setNextElement(nextElement);
    constructor.add(element);

    return element;
  }

  @Override
  public ColumnAccessor<T> subColumn(ValueRange<T> range) {
    return new ScalarColumnAccessor<T>(type, column.getSubColumn(range));
  }
}
