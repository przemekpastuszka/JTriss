package pl.rtshadow.jtriss.test;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.LessThanConstraint.lessThan;
import static pl.rtshadow.jtriss.schema.ColumnType.LIST;
import static pl.rtshadow.jtriss.schema.ColumnType.SCALAR;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.Schema;
import pl.rtshadow.jtriss.table.StandardTableConstructor;
import pl.rtshadow.jtriss.table.Table;
import pl.rtshadow.jtriss.table.TableConstructor;

public class EmptyTableTest {
  TableConstructor constructor = new StandardTableConstructor(
      new Schema().
          addColumn(Integer.class, SCALAR).
          addColumn(Integer.class, LIST).
          addColumn(String.class, SCALAR));

  Table table;

  @Before
  public void setUp() {
    table = constructor.prepare();
  }

  @Test
  public void returnsEmptyResultForAQuery() {
    Collection<Row> result = table.select(query().and(0, lessThan(10), Integer.class));

    assertThat(result).isEmpty();
  }
}
