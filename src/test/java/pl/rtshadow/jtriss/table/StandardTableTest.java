package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.utils.Tools.map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ReconstructedObject;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

@RunWith(MockitoJUnitRunner.class)
public class StandardTableTest {
  @Mock
  private ColumnAccessor columnA, subcolumnOfA;
  @Mock
  private ValueRange columnARange;
  @Mock
  private Query query;

  private Table table;

  @Before
  public void setUp() {
    table = new StandardTable(asList(columnA));
    when(columnA.subColumn(columnARange)).thenReturn(subcolumnOfA);
    when(query.getColumnRange()).thenReturn(map(Pair.of(0, columnARange)));
  }

  @Test
  public void returnsEmptyCollectionWhenNoElements() {
    putInColumn(subcolumnOfA);

    assertThat(table.select(query)).isEmpty();
  }

  @Test
  public void returnsEmptyCollectionWhenOneColumn() {
    putInColumn(subcolumnOfA, element(7), element(8));

    assertThat(table.select(query)).containsOnly(row(7), row(8));
  }

  private void putInColumn(ColumnAccessor<Integer> column, ColumnElement<Integer>... elements) {
    when(column.getSize()).thenReturn(elements.length);
    when(column.iterator()).thenReturn(asList(elements).iterator());

    for (ColumnElement<Integer> element : elements) {
      when(column.reconstruct(element)).thenReturn(reconstructed(element));
    }
  }

  private ReconstructedObject<Integer> reconstructed(ColumnElement<Integer> element) {
    return new ReconstructedObject<Integer>(element.getValue(), element.getNextElementInTheRow());
  }

  private Row row(Integer... values) {
    return new Row(asList(values));
  }
}
