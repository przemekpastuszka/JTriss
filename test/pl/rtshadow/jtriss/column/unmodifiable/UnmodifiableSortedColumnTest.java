package pl.rtshadow.jtriss.column.unmodifiable;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.common.ValueRange.finiteRange;
import static pl.rtshadow.jtriss.common.ValueRange.leftFiniteRange;
import static pl.rtshadow.jtriss.common.ValueRange.rightFiniteRange;
import static pl.rtshadow.jtriss.test.ColumnElementGenerator.element;
import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.common.ValueRange;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableSortedColumnTest {
  private final SortedColumn<Integer> column = generateSortedColumnFrom(0, 1, 2, 3, 4);

  @Test(expected = UnsupportedOperationException.class)
  public void cannotModifyColumnViaIterator() {
    column.iterator().remove();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void cannotModifySubColumnViaIterator() {
    column.getSubColumn(finiteRange(1, 3)).iterator().remove();
  }

  @Test
  public void containsElementsAtAppropriatePositions() {
    assertColumnContainsOnly(column, 0, 1, 2, 3, 4);
  }

  @Test
  public void createsValidSubColumn() {
    assertSubColumnContainsOnly(finiteRange(2, 5), 2, 3, 4);
    assertSubColumnContainsOnly(finiteRange(5, 5));
    assertSubColumnContainsOnly(leftFiniteRange(3), 3, 4);
    assertSubColumnContainsOnly(leftFiniteRange(3).openOnTheLeft(), 4);
    assertSubColumnContainsOnly(rightFiniteRange(2), 0, 1, 2);
    assertSubColumnContainsOnly(rightFiniteRange(2).openOnTheRight(), 0, 1);
  }

  private void assertSubColumnContainsOnly(ValueRange<Integer> valueRange, Integer... elements) {
    assertColumnContainsOnly(column.getSubColumn(valueRange), elements);
  }

  private void assertColumnContainsOnly(SortedColumn<Integer> column, Integer... elements) {
    assertTheSameCollection(column.iterator(), generateSortedColumnFrom(elements).iterator());

    for (Integer i : elements) {
      columnContains(column, i, true);
    }
    if (elements.length > 0) {
      columnContains(column, elements[0] - 1, false);
      columnContains(column, elements[elements.length - 1] + 1, false);
    }
    else {
      columnContains(column, 1, false);
    }
  }

  private void columnContains(SortedColumn<Integer> column, Integer i, boolean expected) {
    assertThat(column.contains(element(i).atPosition(i).get())).isEqualTo(expected);
  }
}
