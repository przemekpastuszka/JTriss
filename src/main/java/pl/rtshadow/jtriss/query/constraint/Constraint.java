package pl.rtshadow.jtriss.query.constraint;

import pl.rtshadow.jtriss.common.ValueRange;

public interface Constraint<T extends Comparable<? super T>> {
  ValueRange<T> reduceToRange();
}
