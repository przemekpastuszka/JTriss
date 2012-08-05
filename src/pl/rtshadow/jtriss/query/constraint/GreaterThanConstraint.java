package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.leftFiniteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class GreaterThanConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private final ValueRange<T> range;

  public GreaterThanConstraint(T value) {
    range = leftFiniteRange(value).openOnTheLeft();
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
