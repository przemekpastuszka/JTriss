package pl.rtshadow.jtriss.column.accessor;

import pl.rtshadow.jtriss.column.element.ColumnElement;

public class ReconstructedObject<T extends Comparable<? super T>> {
  private Object object;
  private ColumnElement<T> nextElementInRow;

  ReconstructedObject(Object object, ColumnElement<T> nextElementInRow) {
    this.object = object;
    this.nextElementInRow = nextElementInRow;
  }

  void setNextElementInRow(ColumnElement<T> nextElementInRow) {
    this.nextElementInRow = nextElementInRow;
  }

  public Object getObject() {
    return object;
  }

  public ColumnElement<T> getNextElementInRow() {
    return nextElementInRow;
  }
}
