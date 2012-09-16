package pl.rtshadow.jtriss.row;

import java.util.ArrayList;
import java.util.Collection;

public class Row extends ArrayList {
  public Row() {
  }

  public Row(Object... elements) {
    super(elements.length);
    for (Object element : elements) {
      add(element);
    }
  }

  public Row(Collection c) {
    super(c);
  }

  public Row(int size) {
    super(size);
    for (int i = 0; i < size; ++i) {
      add(null);
    }
  }
}
