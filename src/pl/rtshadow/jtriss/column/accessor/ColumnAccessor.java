package pl.rtshadow.jtriss.column.accessor;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public interface ColumnAccessor {
  <T extends Comparable<? super T>> ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement, SortedColumn<T> column);

  <T extends Comparable<? super T>> ModifiableColumnElement<T> insert(Object value, ColumnElement<T> nextElement, ColumnConstructor<T> constructor);
}
