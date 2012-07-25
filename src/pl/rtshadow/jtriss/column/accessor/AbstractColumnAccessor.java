package pl.rtshadow.jtriss.column.accessor;

import java.util.Iterator;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;

public abstract class AbstractColumnAccessor<T extends Comparable<? super T>>
    implements ColumnAccessor<T> {

  protected SortedColumn<T> column;
  protected ColumnConstructor<T> constructor;

  @Override
  public Iterator<ColumnElement<T>> iterator() {
    return column.iterator();
  }

  @Override
  public void prepareStructure() {
    column = constructor.generate();
    constructor = null;
  }
}