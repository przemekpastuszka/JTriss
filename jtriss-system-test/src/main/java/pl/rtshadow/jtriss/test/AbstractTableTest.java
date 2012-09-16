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
          addColumn(Integer.class, SCALAR).
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