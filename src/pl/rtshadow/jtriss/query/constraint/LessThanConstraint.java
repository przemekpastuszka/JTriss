package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.rightFiniteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class LessThanConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private ValueRange<T> range;

  public LessThanConstraint(T value) {
    range = rightFiniteRange(value).openOnTheRight();
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
