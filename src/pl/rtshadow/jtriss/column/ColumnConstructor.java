package pl.rtshadow.jtriss.column;

import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public interface ColumnConstructor<T extends Comparable<? super T>> {
  public abstract void add(ModifiableColumnElement<T> element);

  public abstract SortedColumn<T> generate(int id);
}