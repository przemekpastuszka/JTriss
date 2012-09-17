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
 
package pl.rtshadow.jtriss.column.unmodifiable;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.common.ValueRange.INFINITE;
import static pl.rtshadow.jtriss.common.ValueRange.finiteRange;
import static pl.rtshadow.jtriss.common.ValueRange.leftFiniteRange;
import static pl.rtshadow.jtriss.common.ValueRange.rightFiniteRange;
import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;
import static pl.rtshadow.jtriss.test.TestObjects.toElementList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.common.ValueRange;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableSortedColumnTest {
  private SortedColumn<Integer> column = generateSortedColumnFrom(0, 1, 2, 3, 4);

  @Test
  public void hasValidId() {
    assertThat(column.getId()).isEqualTo(TEST_COLUMN_ID);
  }

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
  public void worksIfInitialListIsEmpty() {
    column = generateSortedColumnFrom();

    assertSubColumnContainsOnly(finiteRange(2, 5));
  }

  @Test
  public void createsValidSubColumn() {
    assertSubColumnContainsOnly(INFINITE, 0, 1, 2, 3, 4);
    assertSubColumnContainsOnly(finiteRange(2, 5), 2, 3, 4);
    assertSubColumnContainsOnly(finiteRange(5, 5));
    assertSubColumnContainsOnly(leftFiniteRange(3), 3, 4);
    assertSubColumnContainsOnly(leftFiniteRange(3).openOnTheLeft(), 4);
    assertSubColumnContainsOnly(rightFiniteRange(2), 0, 1, 2);
    assertSubColumnContainsOnly(rightFiniteRange(2).openOnTheRight(), 0, 1);
  }

  private void assertSubColumnContainsOnly(ValueRange<Integer> valueRange, Integer... values) {
    assertColumnContainsOnly(column.getSubColumn(valueRange), values);
  }

  private void assertColumnContainsOnly(SortedColumn<Integer> column, Integer... values) {
    assertTheSameCollection(column.iterator(), toElementList(values).iterator());

    assertThat(column.getSize()).isEqualTo(values.length);

    for (Integer i : values) {
      columnContains(column, i, true);
    }
    if (values.length > 0) {
      columnContains(column, values[0] - 1, false);
      columnContains(column, values[values.length - 1] + 1, false);
    }
    else {
      columnContains(column, 1, false);
    }
  }

  private void columnContains(SortedColumn<Integer> column, Integer i, boolean expected) {
    assertThat(column.contains(element(i).atPosition(i))).isEqualTo(expected);
  }
}
