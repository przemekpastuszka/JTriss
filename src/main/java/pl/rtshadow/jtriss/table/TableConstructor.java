package pl.rtshadow.jtriss.table;

import pl.rtshadow.jtriss.row.Row;

public interface TableConstructor {
  void add(Row row);

  Table prepare();
}
