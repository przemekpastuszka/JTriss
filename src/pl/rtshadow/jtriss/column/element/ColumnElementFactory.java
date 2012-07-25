package pl.rtshadow.jtriss.column.element;


public class ColumnElementFactory {

  public static <T extends Comparable<? super T>> ModifiableColumnElement<T>
      createElement(Class<T> type, Object value) {

    T castedValue = type.cast(value);
    if (castedValue == null) {
      throw new IllegalArgumentException("Expected instance of " + type + ", got: " + value.getClass());
    }

    ModifiableColumnElement<T> element = new StandardColumnElement<T>(castedValue);
    return element;
  }
}
