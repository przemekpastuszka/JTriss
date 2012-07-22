package pl.rtshadow.jtriss.column.element;

public class ColumnElementFactory {
  public <T extends Comparable<? super T>> ColumnElement<T> createElement(T value) {
    return new StandardColumnElement<T>(value);
  }
}
