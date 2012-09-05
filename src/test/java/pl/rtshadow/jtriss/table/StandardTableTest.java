package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    when(subcolumnOfA.getSize()).thenReturn(0);
    when(subcolumnOfA.iterator()).thenReturn(emptyList().iterator());

    assertThat(table.select(query)).isEmpty();
  }

  @Test
  public void returnsEmptyCollectionWhenOneColumn() {
    when(subcolumnOfA.getSize()).thenReturn(2);
    when(subcolumnOfA.iterator()).thenReturn(asList(element(7), element(8)).iterator());
    when(subcolumnOfA.reconstruct(element(7))).thenReturn(reconstructed(7));
    when(subcolumnOfA.reconstruct(element(8))).thenReturn(reconstructed(8));

    assertThat(table.select(query)).containsOnly(row(7), row(8));
  }

  private ReconstructedObject<Integer> reconstructed(Integer value) {
    return new ReconstructedObject<Integer>(value, null);
  }

  private Row row(Integer... values) {
    return new Row(asList(values));
  }
}
