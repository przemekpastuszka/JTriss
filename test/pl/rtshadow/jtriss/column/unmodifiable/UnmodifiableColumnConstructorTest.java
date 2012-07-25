package pl.rtshadow.jtriss.column.unmodifiable;

import static pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor.constructor;
import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableColumnConstructorTest {
  @Test
  public void sortsElements() {
    SortedColumn<Integer> column = generateSortedColumnFrom(3, 1, 2, 4);

    assertTheSameCollection(column.iterator(), toElementList(1, 2, 3, 4).iterator());
  }

  @Test(expected = IllegalStateException.class)
  public void cannotGenerateColumnMultipleTimes() {
    ColumnConstructor<Integer> constructor = constructor(TEST_COLUMN_ID);
    constructor.add(new StandardColumnElement<Integer>(5));

    constructor.generate();
    constructor.generate();
  }

  private List<StandardColumnElement<Integer>> toElementList(Integer... integers) {
    List<StandardColumnElement<Integer>> elements = new ArrayList<StandardColumnElement<Integer>>();
    for (Integer integer : integers) {
      elements.add(new StandardColumnElement<Integer>(integer));
    }

    return elements;
  }

}
