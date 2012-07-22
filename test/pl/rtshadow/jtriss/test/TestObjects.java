package pl.rtshadow.jtriss.test;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.column.unmodifiable.ColumnConstructor;

public class TestObjects {
  public static SortedColumn<Integer> generateSortedColumnFrom(Integer... integers) {
    ColumnConstructor<Integer> constructor = new ColumnConstructor<Integer>();

    for (Integer integer : integers) {
      constructor.add(new StandardColumnElement<Integer>(integer));
    }

    return constructor.generate();
  }
}
