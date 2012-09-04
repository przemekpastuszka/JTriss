package pl.rtshadow.jtriss.table;

import java.util.List;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

public class StandardTable implements Table {
  private final List<ColumnAccessor> accessors;

  public StandardTable(List<ColumnAccessor> accessors) {
    this.accessors = accessors;
  }

  @Override
  public List<Row> select(Query query) {
    return null;
  }
}
