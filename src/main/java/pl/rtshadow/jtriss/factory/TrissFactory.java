package pl.rtshadow.jtriss.factory;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public interface TrissFactory {

  <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor);

  <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor);

  <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id);

  <T extends Comparable<? super T>> ModifiableColumnElement<T>
      createElement(Class<T> type, Object value);
}