package pl.rtshadow.jtriss.column.accessor;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public interface ColumnAccessorGenerator<T extends Comparable<? super T>> {

  ModifiableColumnElement<T> insert(Object value, ColumnElement<T> nextElement);

  ColumnAccessor<T> prepareColumnAccessor();
}
