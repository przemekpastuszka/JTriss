package pl.rtshadow.jtriss.column.unmodifiable;

import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;
import static pl.rtshadow.jtriss.test.TestObjects.toElementListWithOrdinalPositions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableColumnConstructorTest {
  @Test
  public void sortsElements() {
    SortedColumn<Integer> column = generateSortedColumnFrom(3, 1, 2, 4);

    assertTheSameCollection(column.iterator(), toElementListWithOrdinalPositions(1, 2, 3, 4).iterator());
  }

  @Test(expected = IllegalStateException.class)
  public void cannotGenerateColumnMultipleTimes() {
    ColumnConstructor<Integer> constructor = UnmodifiableColumnConstructor.<Integer> constructor(TEST_COLUMN_ID);
    constructor.add(element(5));

    constructor.generate();
    constructor.generate();
  }
}
