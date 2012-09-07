package pl.rtshadow.jtriss.query;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.query.Query.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.constraint.Constraint;

@RunWith(MockitoJUnitRunner.class)
public class QueryTest {
  @Mock
  private ValueRange<Integer> rangeA;
  @Mock
  private ValueRange<Integer> rangeB;
  @Mock
  private Constraint<Integer> constraint;

  @Mock
  private Constraint<String> constraintWithStringType;
  @Mock
  private ValueRange<String> rangeWithStringType;

  @Before
  public void setUp() {
    when(rangeA.getType()).thenReturn(Integer.class);
    when(rangeB.getType()).thenReturn(Integer.class);
    when(rangeWithStringType.getType()).thenReturn(String.class);

    when(constraint.reduceToRange()).thenReturn(rangeA, rangeB);
    when(constraintWithStringType.reduceToRange()).thenReturn(rangeWithStringType);
  }

  @Test
  public void doesNotIntersectRangesWhenConstraintsAreOnDifferentColumns() {
    Query q = query()
        .and(1, constraint, Integer.class)
        .and(2, constraint, Integer.class);

    verify(rangeA, never()).intersect(any(ValueRange.class));
    verify(rangeB, never()).intersect(any(ValueRange.class));
    assertThat(q.columnRange).hasSize(2);
  }

  @Test
  public void intersectRangesWhenConstraintsAreOnTheSameColumn() {
    Query q = query()
        .and(1, constraint, Integer.class)
        .and(1, constraint, Integer.class);

    verify(rangeA).intersect(rangeB);
    verify(rangeB, never()).intersect(any(ValueRange.class));
    assertThat(q.columnRange).hasSize(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throwsExceptionWhenDifferentConstraintTypesGivenOnOneColumn() {
    query()
        .and(1, constraint, Integer.class)
        .and(1, constraintWithStringType, String.class);
  }
}
