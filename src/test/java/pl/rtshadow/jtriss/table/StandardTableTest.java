/*
   Copyright 2012 Przemysław Pastuszka

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
 
package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.common.ValueRange.INFINITE;
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
  private ColumnAccessor columnA, subColumnA, columnB, subColumnB;
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
  public void choosesAppropriateAccessorsWhenQueryHasRange() {
    when(query.getRangeForColumn(0)).thenReturn(rangeA);
    when(columnA.subColumn(rangeA)).thenReturn(subColumnA);
    when(columnB.subColumn(INFINITE)).thenReturn(subColumnB);
    when(factory.createColumnSet(asList(subColumnA, subColumnB))).thenReturn(columnSet);

    assertThat(table.select(query)).containsOnly(row(1), row(2));
  }
}
