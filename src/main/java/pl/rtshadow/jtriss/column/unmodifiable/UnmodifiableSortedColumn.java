/*
   Copyright 2012 Przemysław Pastuszka

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package pl.rtshadow.jtriss.column.unmodifiable;

import static pl.rtshadow.jtriss.utils.BinarySearch.lowerBound;
import static pl.rtshadow.jtriss.utils.BinarySearch.upperBound;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public class UnmodifiableSortedColumn<T extends Comparable<? super T>> implements SortedColumn<T> {
  private final List<ColumnElement<T>> elements;
  private final int id;
  private final Comparator elementComparator;

  UnmodifiableSortedColumn(List<ColumnElement<T>> elements, int id, Comparator elementComparator) {
    this.elements = elements;
    this.id = id;
    this.elementComparator = elementComparator;
  }

  @Override
  public SortedColumn<T> getSubColumn(ValueRange<T> range) {
    if (elements.isEmpty()) {
      return this;
    }

    int leftIndex = 0;
    int rightIndex = elements.size() - 1;

    if (range.isFiniteOnTheLeft()) {
      leftIndex = lowerBound(elements, new StandardColumnElement<T>(range.getLeft()),
          range.isOpenOnTheLeft(), elementComparator);
    }
    if (range.isFiniteOnTheRight()) {
      rightIndex = upperBound(elements, new StandardColumnElement<T>(range.getRight()),
          range.isOpenOnTheRight(), elementComparator);
    }

    return new UnmodifiableSortedColumn<T>(elements.subList(leftIndex, rightIndex + 1), id, elementComparator);
  }

  @Override
  public Iterator<ColumnElement<T>> iterator() {
    return elements.iterator();
  }

  @Override
  public boolean contains(ColumnElement<T> element) {
    if (elements.isEmpty()) {
      return false;
    }

    return element.getColumnId() == id
        && positionOf(0) <= element.getPositionInColumn()
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
  public int getId() {
    return id;
  }
}
