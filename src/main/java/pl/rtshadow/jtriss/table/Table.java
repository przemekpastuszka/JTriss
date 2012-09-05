package pl.rtshadow.jtriss.table;

import java.util.Collection;

import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

public interface Table {
  Collection<Row> select(Query query);
}
