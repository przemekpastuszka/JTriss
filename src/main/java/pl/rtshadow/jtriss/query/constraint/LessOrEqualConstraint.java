package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.rightFiniteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class LessOrEqualConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private final ValueRange<T> range;

  public static <T extends Comparable<? super T>> Constraint<T> lessOrEqual(T value) {
    return new LessOrEqualConstraint<T>(value);
  }

  public LessOrEqualConstraint(T value) {
    range = rightFiniteRange(value);
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
