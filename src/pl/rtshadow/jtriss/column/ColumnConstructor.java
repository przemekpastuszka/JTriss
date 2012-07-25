package pl.rtshadow.jtriss.column;

import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public interface ColumnConstructor<T extends Comparable<? super T>> {
  void add(ModifiableColumnElement<T> element);

  SortedColumn<T> generate();

  Class<T> getElementsType();
}