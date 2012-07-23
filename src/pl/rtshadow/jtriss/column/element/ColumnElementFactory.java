package pl.rtshadow.jtriss.column.element;

import static org.apache.commons.lang3.BooleanUtils.negate;

public class ColumnElementFactory {

  public static <T extends Comparable<? super T>> ModifiableColumnElement<T> createElement(Object value) {
    if (negate(value instanceof Comparable)) {
      throw new IllegalStateException("Argument passed as value is not comparable");
    }

    @SuppressWarnings("unchecked")
    ModifiableColumnElement<T> element = new StandardColumnElement<T>((T) value);
    return element;
  }
}
