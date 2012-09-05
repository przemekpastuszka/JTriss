package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.TrissFactory;

public class TestFactory implements TrissFactory {

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T extends Comparable<? super T>> ModifiableColumnElement<T> createElement(Class<T> type, Object value) {
    return (ModifiableColumnElement) element((Integer) value);
  }

}
