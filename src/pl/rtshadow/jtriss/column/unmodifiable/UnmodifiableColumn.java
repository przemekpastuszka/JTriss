package pl.rtshadow.jtriss.column.unmodifiable;

import static pl.rtshadow.jtriss.utils.BinarySearch.lowerBound;
import static pl.rtshadow.jtriss.utils.BinarySearch.upperBound;

import java.util.Iterator;
import java.util.List;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

public class UnmodifiableColumn<T extends Comparable<? super T>> implements SortedColumn<T> {
  private List<ColumnElement<T>> elements;

  UnmodifiableColumn(List<ColumnElement<T>> elements) {
    this.elements = elements;
  }

  @Override
  public SortedColumn<T> getSubColumn(T left, T right) {
    int leftIndex = lowerBound(elements, new StandardColumnElement<T>(left));
    int rightIndex = upperBound(elements, new StandardColumnElement<T>(right));

    return new UnmodifiableColumn<T>(elements.subList(leftIndex, rightIndex + 1));
  }

  @Override
  public boolean contains(ColumnElement<T> element) {
    if (elements.isEmpty()) {
      return false;
    }

    return positionOf(0) <= element.getPositionInColumn()
        && element.getPositionInColumn() <= positionOf(elements.size() - 1);
  }

  private int positionOf(int index) {
    return elements.get(index).getPositionInColumn();
  }

  @Override
  public int getSize() {
    return elements.size();
  }

  @Override
  public Iterator<ColumnElement<T>> iterator() {
    return elements.iterator();
  }
}
