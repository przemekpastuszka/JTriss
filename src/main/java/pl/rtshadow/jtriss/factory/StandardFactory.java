package pl.rtshadow.jtriss.factory;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.accessor.ListColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ScalarColumnAccessor;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor;

public class StandardFactory implements TrissFactory {
  public static <T extends Comparable<? super T>> ModifiableColumnElement<T>
      createElement(Class<T> type, Object value) {

    T castedValue = type.cast(value);
    if (castedValue == null) {
      throw new IllegalArgumentException("Expected instance of " + type + ", got: " + value.getClass());
    }

    return new StandardColumnElement<T>(castedValue);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor) {
    return ScalarColumnAccessor.generator(type, constructor);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor) {
    return ListColumnAccessor.generator(type, constructor);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id) {
    return UnmodifiableColumnConstructor.constructor(id);
  }
}
