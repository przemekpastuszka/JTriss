package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

public class TestColumnElement<T extends Comparable<? super T>> extends StandardColumnElement<T> {

  public TestColumnElement(T value) {
    super(value);
    setColumnId(TEST_COLUMN_ID);
  }

  public static <T extends Comparable<? super T>> TestColumnElement<T> element(T value) {
    return new TestColumnElement<T>(value);
  }

  public TestColumnElement<T> atPosition(int index) {
    setPosition(index);
    return this;
  }

  public TestColumnElement<T> inColumn(int columnId) {
    setColumnId(columnId);
    return this;
  }

  public TestColumnElement<T> withNext(ColumnElement<T> next) {
    setNextElement(next);
    return this;
  }
}
