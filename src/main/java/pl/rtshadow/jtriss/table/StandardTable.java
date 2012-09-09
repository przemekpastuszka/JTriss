package pl.rtshadow.jtriss.table;

import static pl.rtshadow.jtriss.common.ValueRange.INFINITE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.factory.StandardFactory;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

public class StandardTable implements Table {
  TrissFactory factory = new StandardFactory();

  private final List<ColumnAccessor> accessors;
  private final int size;

  public StandardTable(List<ColumnAccessor> accessors) {
    this.accessors = accessors;
    this.size = accessors.size();
  }

  @Override
  public Collection<Row> select(Query query) {
    ColumnSet columnSet = factory.createColumnSet(calculateQueryAccessors(query));
    return columnSet.select(query.getLimit());
  }

  private List<ColumnAccessor> calculateQueryAccessors(Query query) {
    List<ColumnAccessor> queryAccessors = new ArrayList<ColumnAccessor>(accessors.size());

    for (int i = 0; i < size; ++i) {
      ValueRange range = query.getRangeForColumn(i);
      ColumnAccessor<?> accessor = accessors.get(i);

      if (range != null) {
        queryAccessors.add(accessor.subColumn(range));
      } else {
        queryAccessors.add(accessor.subColumn(INFINITE));
      }
    }
    return queryAccessors;
  }

}
