package pl.rtshadow.jtriss.column;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.column.UnmodifiableColumn.construct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.element.StandardColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class ColumnConstructorTest {

	UnmodifiableColumn.ColumnConstructor<Integer> constructor = construct();

	@Test
	public void shouldGenerateAppropriateColumn() {
		addIntegers(1, 2, 3);
		SortedColumn<Integer> column = constructor.generate();

		assertThat(column.getSize()).isEqualTo(3);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowExceptionIfGenerateIsCalledMultipleTimes() {
		constructor.generate();
		constructor.generate();
	}

	public void addIntegers(Integer... integers) {
		for (Integer integer : integers) {
			constructor.add(new StandardColumnElement<Integer>(integer));
		}
	}

}
