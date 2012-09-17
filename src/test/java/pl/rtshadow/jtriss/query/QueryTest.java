/*
   Copyright 2012 Przemysław Pastuszka

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

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
        .and(1, constraint)
        .and(2, constraint);

    verify(rangeA, never()).intersect(any(ValueRange.class));
    verify(rangeB, never()).intersect(any(ValueRange.class));
    assertThat(q.columnRange).hasSize(2);
  }

  @Test
  public void intersectRangesWhenConstraintsAreOnTheSameColumn() {
    Query q = query()
        .and(1, constraint)
        .and(1, constraint);

    verify(rangeA).intersect(rangeB);
    verify(rangeB, never()).intersect(any(ValueRange.class));
    assertThat(q.columnRange).hasSize(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void throwsExceptionIfLimitIsNotPositive() {
    query().limit(0);
  }
}
