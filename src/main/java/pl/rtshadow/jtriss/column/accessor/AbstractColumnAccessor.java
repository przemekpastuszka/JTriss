package pl.rtshadow.jtriss.column.accessor;

import java.util.Iterator;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;

abstract class AbstractColumnAccessor<T extends Comparable<? super T>>
    implements ColumnAccessor<T> {

  SortedColumn<T> column;
  Class<T> type;

  @Override
  public Iterator<ColumnElement<T>> iterator() {
    return column.iterator();
  }

  @Override
  public int getSize() {
    return column.getSize();
  }

  @Override
  public int getId() {
    return column.getId();
  }

  static abstract class AbstractColumnAccessorGenerator<T extends Comparable<? super T>>
      implements ColumnAccessorGenerator<T> {

    ColumnConstructor<T> constructor;
    Class<T> type;
  }
}