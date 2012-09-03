package pl.rtshadow.jtriss.schema;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.factory.TrissFactory;

public enum ColumnType {
  SCALAR {
    @Override
    public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createColumnAccessorGenerator(
        TrissFactory factory, Class<T> type, ColumnConstructor<T> constructor) {
      return factory.createScalarColumnAccessorGenerator(type, constructor);
    }
  },
  LIST {
    @Override
    public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createColumnAccessorGenerator(
        TrissFactory factory, Class<T> type, ColumnConstructor<T> constructor) {
      return factory.createListColumnAccessorGenerator(type, constructor);
    }
  };

  public abstract <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createColumnAccessorGenerator(
      TrissFactory factory, Class<T> type, ColumnConstructor<T> constructor);
}
