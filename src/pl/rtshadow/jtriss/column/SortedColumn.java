package pl.rtshadow.jtriss.column;

import pl.rtshadow.jtriss.column.element.ColumnElement;

public interface SortedColumn<T extends Comparable<? super T>> extends Iterable<ColumnElement<T>> {
  int getSize();

  SortedColumn<T> getSubColumn(T left, T right);

  boolean contains(ColumnElement<T> element);
}