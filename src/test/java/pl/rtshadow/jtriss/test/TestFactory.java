package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.table.ColumnSet;

public class TestFactory implements TrissFactory {

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ModifiableColumnElement<T> createElement(Class<T> type, Object value) {
    return (ModifiableColumnElement) element((Integer) value);
  }

  @Override
  public ColumnSet createColumnSet(List<ColumnAccessor> columns) {
    throw new UnsupportedOperationException();
  }
}
