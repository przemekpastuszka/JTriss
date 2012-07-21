package pl.rtshadow.jtriss.column.utils;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.utils.BinarySearch.lowerBound;
import static pl.rtshadow.jtriss.utils.BinarySearch.upperBound;

import java.util.List;

import org.junit.Test;

public class BinarySearchTest {
	List<Integer> input = asList(1, 5, 5, 7, 8, 12, 12, 19);

	@Test
	public void shouldFindApropriateIndexes() {
		assertThatRangeEquals(5, 12, /**/1, 6);
		assertThatRangeEquals(6, 11, /**/3, 4);
		assertThatRangeEquals(13, 27, /**/7, 7);
		assertThatRangeEquals(-10, 5, /**/0, 2);
		assertThatRangeEquals(7, 7, /**/3, 3);
	}

	@Test
	public void shouldBeAwareOfEmptyRanges() {
		assertThatRangeIsEmpty(10, 10);
		assertThatRangeIsEmpty(-14, -4);
		assertThatRangeIsEmpty(24, 28);
	}

	private void assertThatRangeIsEmpty(int leftValue, int rightValue) {
		int leftIndex = lowerBound(input, leftValue);
		int rightIndex = upperBound(input, leftValue);

		assertThat(leftIndex).isGreaterThan(rightIndex);
	}

	private void assertThatRangeEquals(int leftValue, int rightValue,
			int leftIndex, int rightIndex) {
		assertThat(lowerBound(input, leftValue)).isEqualTo(leftIndex);
		assertThat(upperBound(input, rightValue)).isEqualTo(rightIndex);
	}
}
