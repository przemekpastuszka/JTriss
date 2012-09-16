package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.finiteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class EqualsConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private final ValueRange<T> range;

  public static <T extends Comparable<? super T>> Constraint<T> equalsTo(T value) {
    return new EqualsConstraint<T>(value);
  }

  public EqualsConstraint(T value) {
    range = finiteRange(value, value);
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
