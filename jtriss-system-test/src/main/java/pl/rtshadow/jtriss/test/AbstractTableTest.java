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

package pl.rtshadow.jtriss.test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static pl.rtshadow.jtriss.schema.ColumnType.LIST;
import static pl.rtshadow.jtriss.schema.ColumnType.SCALAR;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.Schema;
import pl.rtshadow.jtriss.table.StandardTableConstructor;
import pl.rtshadow.jtriss.table.Table;
import pl.rtshadow.jtriss.table.TableConstructor;

public class AbstractTableTest {
  private TableConstructor constructor = new StandardTableConstructor(
      new Schema().
          addColumn(Double.class, SCALAR).
          addColumn(Integer.class, LIST).
          addColumn(String.class, SCALAR));

  protected Table table;
  protected List<Row> inputRows = new LinkedList<Row>();

  protected Collection<Row> result;

  protected void prepareTable() {
    for (Row row : inputRows) {
      constructor.add(row);
    }

    table = constructor.prepare();
  }

  protected void select(Query query) {
    result = table.select(query);
  }

  protected void assertResultContainsAllOf(Integer... rowIds) {
    assertThat(result).hasSize(rowIds.length);
    for (int rowId : rowIds) {
      assertThat(result).contains(inputRows.get(rowId));
    }
  }

  protected void assertResultContainsAnyOf(Integer... rowIds) {
    for (int rowId : rowIds) {
      if (result.contains(inputRows.get(rowId))) {
        return;
      }
    }
    fail("None of specified rows found in result");
  }
}