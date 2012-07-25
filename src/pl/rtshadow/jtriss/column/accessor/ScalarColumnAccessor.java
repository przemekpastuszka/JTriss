package pl.rtshadow.jtriss.column.accessor;

import static pl.rtshadow.jtriss.column.element.ColumnElementFactory.createElement;
import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class ScalarColumnAccessor<T extends Comparable<? super T>> extends AbstractColumnAccessor<T> {
  public ScalarColumnAccessor(ColumnConstructor<T> constructor) {
    this.constructor = constructor;
  }

  private ScalarColumnAccessor(SortedColumn<T> column) {
    this.column = column;
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
    ModifiableColumnElement<T> element = createElement(constructor.getElementsType(), value);
    element.setNextElement(nextElement);
    constructor.add(element);

    return element;
  }

  @Override
  public ColumnAccessor<T> subColumn(T left, T right) {
    return new ScalarColumnAccessor<T>(column.getSubColumn(left, right));
  }
}
