package pl.rtshadow.jtriss.column.accessor;

import org.apache.commons.lang3.tuple.Pair;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.TrissFactory;

public interface ColumnAccessorGenerator<T extends Comparable<? super T>> {

  Pair<ModifiableColumnElement<T>, ModifiableColumnElement<T>> insert(Object value, ColumnElement<T> nextElement);

  ColumnAccessor<T> prepareColumnAccessor();

  void setFactory(TrissFactory factory);
}
