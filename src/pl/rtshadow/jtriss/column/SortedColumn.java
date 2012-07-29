package pl.rtshadow.jtriss.column;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public interface SortedColumn<T extends Comparable<? super T>>
    extends Iterable<ColumnElement<T>> {

  SortedColumn<T> getSubColumn(ValueRange<T> valueRange);

  boolean contains(ColumnElement<T> element);
}