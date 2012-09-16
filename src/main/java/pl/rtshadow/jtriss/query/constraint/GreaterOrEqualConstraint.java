package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.leftFiniteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class GreaterOrEqualConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private final ValueRange<T> range;

  public GreaterOrEqualConstraint(T value) {
    range = leftFiniteRange(value);
  }

  public static <T extends Comparable<? super T>> Constraint<T> greaterOrEqual(T value) {
    return new GreaterOrEqualConstraint<T>(value);
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
