package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.TestColumnElement.chain;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.test.TestObjects.row;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ReconstructedObject;
import pl.rtshadow.jtriss.column.element.ColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class ColumnSetTest {
  private ColumnSet columnSet;
  private List<ColumnAccessor> columns;

  @Test
  public void returnsEmptyCollectionWhenNoElements() {
    createColumns(1);

    assertThat(columnSet.select()).isEmpty();
  }

  @Test
  public void returnsAppropriateRowsInSimpleCase() {
    createColumns(1);
    putInColumn(columns.get(0), chain(1, 1), chain(2, 2));

    assertThat(columnSet.select()).containsOnly(row(1), row(2));
  }

  @Test
  public void asksTwoTimesForValueInFirstColumn() {
    createColumns(2);
    putInColumn(columns.get(0), chain(1, 10));
    putInColumn(columns.get(1), chain(10, 1), chain(20, 2));

    when(columns.get(0).reconstruct(hasSameValueAs(element(1)))).thenReturn(
        reconstructed(chain(1, 10)), reconstructed(element(8)));

    assertThat(columnSet.select()).containsOnly(row(8, 10));
  }

  @Test
  public void returnsAppropriateRowsInSimpleTwoColumnCase() {
    createColumns(2);
    putInColumn(columns.get(0), chain(1, 10), chain(2, 20));
    putInColumn(columns.get(1), chain(10, 1), chain(30, 3), chain(40, 4));

    assertThat(columnSet.select()).containsOnly(row(1, 10));
  }

  @Test
  public void neverIteratesBigColumn() {
    createColumns(2);
    putInColumn(columns.get(0), chain(1, 10), chain(2, 20));
    putInColumn(columns.get(1), chain(3, 30));

    assertThat(columnSet.select()).isEmpty();
    verify(columns.get(0), never()).iterator();
  }

  private void createColumns(int number) {
    columns = new ArrayList<ColumnAccessor>(number);

    for (int i = 0; i < number; ++i) {
      ColumnAccessor column = createColumn(i, columns);
    }

    columnSet = new ColumnSet(columns);
  }

  private ColumnAccessor createColumn(int i, List<ColumnAccessor> target) {
    ColumnAccessor column = mock(ColumnAccessor.class);
    when(column.getId()).thenReturn(i);
    putInColumn(column);
    target.add(column);
    return column;
  }

  private void putInColumn(ColumnAccessor<Integer> column, ColumnElement<Integer>... elements) {
    when(column.getSize()).thenReturn(elements.length);
    when(column.iterator()).thenReturn(asList(elements).iterator());

    for (ColumnElement<Integer> element : elements) {
      when(column.reconstruct(hasSameValueAs(element))).thenReturn(reconstructed(element));
    }
  }

  private ColumnElement<Integer> hasSameValueAs(final ColumnElement<Integer> element) {
    return Mockito.argThat(new BaseMatcher<ColumnElement<Integer>>() {

      @Override
      public boolean matches(Object item) {
        if (item == null) {
          return false;
        }
        return ((ColumnElement<Integer>) item).getValue().equals(element.getValue());
      }

      @Override
      public void describeTo(Description description) {
      }
    });
  }

  private ReconstructedObject<Integer> reconstructed(ColumnElement<Integer> element) {
    return new ReconstructedObject<Integer>(element.getValue(), element.getNextElementInTheRow());
  }
}
