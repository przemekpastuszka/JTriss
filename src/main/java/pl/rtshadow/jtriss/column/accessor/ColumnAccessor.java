package pl.rtshadow.jtriss.column.accessor;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public interface ColumnAccessor<T extends Comparable<? super T>>
    extends Iterable<ColumnElement<T>> {

  ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement);

  ColumnAccessor<T> subColumn(ValueRange<T> range);

  int getSize();

  int getId();
}
