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

package pl.rtshadow.jtriss.column.utils;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.utils.BinarySearch.lowerBound;
import static pl.rtshadow.jtriss.utils.BinarySearch.upperBound;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class BinarySearchTest {
  private final List<Integer> input = asList(1, 5, 5, 7, 8, 12, 12, 19);

  @Test
  public void findsAppropriateIndexesForNonOpenRanges() {
    assertThatRangeEquals(5, 12, /**/1, 6, /**/false);
    assertThatRangeEquals(6, 11, /**/3, 4, /**/false);
    assertThatRangeEquals(13, 27, /**/7, 7, /**/false);
    assertThatRangeEquals(-10, 5, /**/0, 2, /**/false);
    assertThatRangeEquals(7, 7, /**/3, 3, /**/false);
  }

  @Test
  public void findsAppropriateIndexesForOpenRanges() {
    assertThatRangeEquals(5, 12, /**/3, 4, /**/true);
    assertThatRangeEquals(6, 11, /**/3, 4, /**/true);
    assertThatRangeEquals(13, 27, /**/7, 7, /**/true);
    assertThatRangeEquals(-10, 5, /**/0, 0, /**/true);
  }

  @Test
  public void isAwareOfEmptyRanges() {
    assertThatRangeIsEmpty(10, 10, /**/false);
    assertThatRangeIsEmpty(-14, -4, /**/false);
    assertThatRangeIsEmpty(24, 28, /**/false);
    assertThatRangeIsEmpty(7, 8, true);
  }

  private void assertThatRangeIsEmpty(int leftValue, int rightValue, boolean openRange) {
    int leftIndex = lowerBound(input, leftValue, openRange, INTEGER_CMP);
    int rightIndex = upperBound(input, rightValue, openRange, INTEGER_CMP);

    assertThat(leftIndex).isGreaterThan(rightIndex);
  }

  private void assertThatRangeEquals(int leftValue, int rightValue, int leftIndex, int rightIndex, boolean openRange) {
    assertThat(lowerBound(input, leftValue, openRange, INTEGER_CMP)).isEqualTo(leftIndex);
    assertThat(upperBound(input, rightValue, openRange, INTEGER_CMP)).isEqualTo(rightIndex);
  }

  private static final Comparator<Integer> INTEGER_CMP = new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  };
}
