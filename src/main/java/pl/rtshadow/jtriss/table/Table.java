package pl.rtshadow.jtriss.table;

import java.util.List;

import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

public interface Table {
  List<Row> select(Query query);
}
