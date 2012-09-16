package pl.rtshadow.jtriss.schema;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

public class Schema extends ArrayList<Pair<Class, ColumnType>> {
  private static final long serialVersionUID = 2779832184835370214L;

  public Schema addColumn(Class clazz, ColumnType type) {
    add(Pair.of(clazz, type));
    return this;
  }
}
