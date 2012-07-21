package pl.rtshadow.jtriss.column.unmodifiable;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableColumnTest {
	SortedColumn<Integer> column = generateSortedColumnFrom(0, 1, 2, 3, 4);

	@Test
	public void hasAppropriateSize() {
		assertThat(column.getSize()).isEqualTo(5);
	}

	@Test
	public void containsElementsAtAppropriatePositions() {
		assertThat(column.contains(createElementAt(2, 2))).isTrue();
		assertThat(column.contains(createElementAt(4, 4))).isTrue();

		assertThat(column.contains(createElementAt(6, 6))).isFalse();
	}

	@Test
	public void createsValidNonEmptySubColumn() {
		SortedColumn<Integer> subColumn = column.getSubColumn(2, 5);

		assertTheSameCollection(subColumn.iterator(),
				generateSortedColumnFrom(2, 3, 4).iterator());

		assertThat(subColumn.contains(createElementAt(1, 1))).isFalse();
		assertThat(subColumn.contains(createElementAt(2, 2))).isTrue();
		assertThat(subColumn.contains(createElementAt(4, 4))).isTrue();
	}

	@Test
	public void createsValidEmptySubColumn() {
		SortedColumn<Integer> subColumn = column.getSubColumn(5, 5);

		assertThat(subColumn).isEmpty();

		assertThat(subColumn.contains(createElementAt(1, 1))).isFalse();
		assertThat(subColumn.contains(createElementAt(4, 4))).isFalse();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void cannotModifyColumnViaIterator() {
		column.iterator().remove();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void cannotModifySubColumnViaIterator() {
		column.getSubColumn(1, 3).iterator().remove();
	}

	private ModifiableColumnElement<Integer> createElementAt(int value,
			int position) {

		StandardColumnElement<Integer> element = new StandardColumnElement<Integer>(
				value);
		element.setPosition(position);

		return element;
	}

}
