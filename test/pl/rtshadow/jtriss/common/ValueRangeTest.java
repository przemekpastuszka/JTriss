package pl.rtshadow.jtriss.common;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.common.ValueRange.finiteRange;
import static pl.rtshadow.jtriss.common.ValueRange.leftFiniteRange;
import static pl.rtshadow.jtriss.common.ValueRange.rightFiniteRange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValueRangeTest {
  private static int INFINITY = 1000;

  @Test
  public void createsValidRangesInitially() {
    assertRangeEquality(rightFiniteRange(17), INFINITY, 17, false, false);
    assertRangeEquality(leftFiniteRange(17), 17, INFINITY, false, false);
    assertRangeEquality(finiteRange(17, 17), 17, 17, false, false);
    assertRangeEquality(finiteRange(10, 17).openOnTheLeft(), 10, 17, true, false);
    assertRangeEquality(finiteRange(10, 17).openOnTheRight(), 10, 17, false, true);
  }

  @Test
  public void intersectsRangesAppropriately() {
    assertRangeEquality(
        rightFiniteRange(17).intersect(rightFiniteRange(10)),
        INFINITY, 10, false, false);

    assertRangeEquality(
        rightFiniteRange(17).intersect(leftFiniteRange(10)),
        10, 17, false, false);

    assertRangeEquality(
        leftFiniteRange(10).intersect(rightFiniteRange(17)),
        10, 17, false, false);

    assertRangeEquality(
        rightFiniteRange(17).intersect(finiteRange(10, 10)),
        10, 10, false, false);

    assertThat(rightFiniteRange(17).intersect(finiteRange(21, 21)).isEmpty()).isTrue();

    assertRangeEquality(
        rightFiniteRange(17).intersect(rightFiniteRange(10).openOnTheRight()),
        INFINITY, 10, false, true);

    assertRangeEquality(
        rightFiniteRange(17).intersect(leftFiniteRange(10).openOnTheLeft()),
        10, 17, true, false);

    assertRangeEquality(
        leftFiniteRange(10).intersect(rightFiniteRange(17).openOnTheRight()),
        10, 17, false, true);

    assertRangeEquality(
        rightFiniteRange(17).openOnTheRight().intersect(finiteRange(10, 10)),
        10, 10, false, false);
  }

  public void assertRangeEquality(ValueRange<Integer> range,
      int left, int right,
      boolean leftOpen, boolean rightOpen) {

    if (left == INFINITY) {
      assertThat(range.isFiniteOnTheLeft()).isFalse();
    } else {
      assertThat(range.isFiniteOnTheLeft()).isTrue();
      assertThat(range.getLeft()).isEqualTo(left);
      assertThat(range.isOpenOnTheLeft()).isEqualTo(leftOpen);
    }

    if (right == INFINITY) {
      assertThat(range.isFiniteOnTheRight()).isFalse();
    } else {
      assertThat(range.isFiniteOnTheRight()).isTrue();
      assertThat(range.getRight()).isEqualTo(right);
      assertThat(range.isOpenOnTheRight()).isEqualTo(rightOpen);
    }

    assertThat(range.isEmpty()).isFalse();
  }
}
