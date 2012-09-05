package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.utils.Tools.map;

import java.util.ArrayList;
import java.util.List;

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
  private ValueRange columnARange;
  @Mock
  private Query query;

  private Table table;
  private List<ColumnAccessor> subColumns, columns;

  @Before
  public void setUp() {
    when(query.getColumnRange()).thenReturn(map(Pair.of(0, columnARange)));
  }

  @Test
  public void returnsEmptyCollectionWhenNoElements() {
    createColumns(1);

    assertThat(table.select(query)).isEmpty();
  }

  @Test
  public void returnsAppropriateRowsInSimpleCase() {
    createColumns(1);
    putInColumn(subColumns.get(0), element(7), element(8));

    assertThat(table.select(query)).containsOnly(row(7), row(8));
  }

  private void createColumns(int number) {
    subColumns = new ArrayList<ColumnAccessor>(number);
    columns = new ArrayList<ColumnAccessor>(number);

    for (int i = 0; i < number; ++i) {
      ColumnAccessor column = createColumn(i, columns);
      ColumnAccessor subColumn = createColumn(i, subColumns);
      when(column.subColumn(any(ValueRange.class))).thenReturn(subColumn);
    }

    table = new StandardTable(columns);
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
