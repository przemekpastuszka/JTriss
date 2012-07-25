package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor.constructor;
import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

public class TestObjects {
  public static final int TEST_COLUMN_ID = 1;

  public static <T extends Comparable<? super T>> SortedColumn<T> generateSortedColumnFrom(Class<T> type, T... elements) {
    ColumnConstructor<T> constructor = constructor(TEST_COLUMN_ID, type);

    for (T element : elements) {
      constructor.add(new StandardColumnElement<T>(element));
    }

    return constructor.generate();
  }
}
