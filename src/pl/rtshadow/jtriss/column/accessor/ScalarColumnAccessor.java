package pl.rtshadow.jtriss.column.accessor;

import static pl.rtshadow.jtriss.column.element.ColumnElementFactory.createElement;
import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class ScalarColumnAccessor implements ColumnAccessor {
  public static ScalarColumnAccessor INSTANCE = new ScalarColumnAccessor();

  private ScalarColumnAccessor() {
  }

  @Override
  public <T extends Comparable<? super T>> ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement, SortedColumn<T> column) {
    if (column.contains(firstElement)) {
      return new ReconstructedObject<T>(firstElement.getValue(), firstElement.getNextElementInTheRow());
    }
    return null;
  }

  @Override
  public <T extends Comparable<? super T>>
      ModifiableColumnElement<T> insert(Object value, ColumnElement<T> nextElement, ColumnConstructor<T> constructor) {

    ModifiableColumnElement<T> element = createElement(value);
    element.setNextElement(nextElement);
    constructor.add(element);

    return element;
  }

}
