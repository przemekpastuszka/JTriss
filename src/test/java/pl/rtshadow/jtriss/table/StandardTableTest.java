package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.TestObjects.row;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.query.Query;

@RunWith(MockitoJUnitRunner.class)
public class StandardTableTest {
  @Mock
  private Query query;
  @Mock
  private ColumnAccessor columnA, subColumnA, columnB;
  @Mock
  private TrissFactory factory;
  @Mock
  private ColumnSet columnSet;
  @Mock
  private ValueRange rangeA;

  private StandardTable table;

  @Before
  public void setUp() {
    table = new StandardTable(asList(columnA, columnB));
    table.factory = factory;
    when(query.getLimit()).thenReturn(7);
    when(columnSet.select(7)).thenReturn(asList(row(1), row(2)));
  }

  @Test
  public void choosesAppropriateAccessorInSimpleCase() {
    when(factory.createColumnSet(asList(columnA, columnB))).thenReturn(columnSet);

    assertThat(table.select(query)).containsOnly(row(1), row(2));
  }

  @Test
  public void choosesAppropriateAccessorsWhenQueryHasRange() {
    when(query.getRangeForColumn(0)).thenReturn(rangeA);
    when(columnA.subColumn(rangeA)).thenReturn(subColumnA);
    when(factory.createColumnSet(asList(subColumnA, columnB))).thenReturn(columnSet);

    assertThat(table.select(query)).containsOnly(row(1), row(2));
  }
}
