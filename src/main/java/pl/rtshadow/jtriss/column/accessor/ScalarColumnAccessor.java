package pl.rtshadow.jtriss.column.accessor;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public class ScalarColumnAccessor<T extends Comparable<? super T>> extends AbstractColumnAccessor<T> {
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
  public ColumnAccessor<T> subColumn(ValueRange<T> range) {
    return new ScalarColumnAccessor<T>(type, column.getSubColumn(range));
  }

  public static <T extends Comparable<? super T>> ColumnAccessorGenerator<T> generator(Class<T> type, ColumnConstructor<T> constructor) {
    return new ScalarColumnAccessorGenerator<T>(type, constructor);
  }

  private static class ScalarColumnAccessorGenerator<T extends Comparable<? super T>> extends AbstractColumnAccessorGenerator<T> {
    public ScalarColumnAccessorGenerator(Class<T> type, ColumnConstructor<T> constructor) {
      this.constructor = constructor;
      this.type = type;
    }

    @Override
    public ModifiableColumnElement<T> insert(Object value, ColumnElement<T> nextElement) {
      ModifiableColumnElement<T> element = factory.createElement(type, value);
      element.setNextElement(nextElement);
      constructor.add(element);

      return element;
    }

    @Override
    public ColumnAccessor<T> prepareColumnAccessor() {
      return new ScalarColumnAccessor<T>(type, constructor.generate());
    }
  }

  @Override
  public void prepareMainColumnForReconstruction() {
  }

  @Override
  public void finishReconstruction() {
  }
}
