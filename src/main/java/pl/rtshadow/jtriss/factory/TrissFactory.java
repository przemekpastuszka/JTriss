package pl.rtshadow.jtriss.factory;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;

public interface TrissFactory {

  <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor);

  <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor);

  <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id);
}