package pl.rtshadow.jtriss.test;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor;

public class TestObjects {
  public static final int TEST_COLUMN_ID = 1;

  public static <T extends Comparable<? super T>> SortedColumn<T> generateSortedColumnFrom(T... elements) {
    ColumnConstructor<T> constructor = new UnmodifiableColumnConstructor<T>();

    for (T element : elements) {
      constructor.add(new StandardColumnElement<T>(element));
    }

    return constructor.generate(TEST_COLUMN_ID);
  }
}
