package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.utils.Tools.map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.Query;

@RunWith(MockitoJUnitRunner.class)
public abstract class StandardTableTest {
  @Mock
  private ColumnAccessor columnA;
  @Mock
  private ValueRange columnARange;
  @Mock
  private Query query;

  private Table table;

  @Before
  public void setUp() {
    when(columnA.subColumn(columnARange)).thenReturn(columnA);
  }

  @Test
  public void returnsEmptyCollectionWhenNoElements() {
    table = new StandardTable(asList(columnA));
    when(columnA.getSize()).thenReturn(0);
    when(query.getColumnRange()).thenReturn(map(Pair.of(0, columnARange)));

    assertThat(table.select(query)).isEmpty();
  }

}
