package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

public class ColumnElementGenerator<T extends Comparable<? super T>> {
  private ModifiableColumnElement<T> element;

  private ColumnElementGenerator(ModifiableColumnElement<T> element) {
    this.element = element;
    this.element.setColumnId(TEST_COLUMN_ID);
  }

  public static <T extends Comparable<? super T>> ColumnElementGenerator<T> element(T value) {
    return new ColumnElementGenerator<T>(new StandardColumnElement<T>(value));
  }

  public ColumnElementGenerator<T> atPosition(int index) {
    element.setPosition(index);
    return this;
  }

  public ColumnElementGenerator<T> inColumn(int columnId) {
    element.setColumnId(columnId);
    return this;
  }

  public ColumnElementGenerator<T> withNext(ColumnElement<T> next) {
    element.setNextElement(next);
    return this;
  }

  public ModifiableColumnElement<T> get() {
    return element;
  }
}