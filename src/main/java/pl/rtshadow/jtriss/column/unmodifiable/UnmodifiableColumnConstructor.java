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

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class UnmodifiableColumnConstructor<T extends Comparable<? super T>> implements ColumnConstructor<T> {
  private final List<ModifiableColumnElement<T>> elements = new ArrayList<ModifiableColumnElement<T>>();
  private boolean hasBeenGenerated = false;
  private final int id;

  private UnmodifiableColumnConstructor(int id) {
    this.id = id;
  }

  public static <T extends Comparable<? super T>> UnmodifiableColumnConstructor<T>
      constructor(int id) {
    return new UnmodifiableColumnConstructor<T>(id);
  }

  @Override
  public void add(ModifiableColumnElement<T> element) {
    elements.add(element);
  }

  @Override
  public SortedColumn<T> generate(Comparator<ColumnElement> elementComparator) {
    assureFirstGeneration();

    sort(elements, elementComparator);
    setElementsPositionsAndColumnId(id);

    return new UnmodifiableSortedColumn<T>(
        unmodifiableList(new ArrayList<ColumnElement<T>>(elements)), id, elementComparator);
  }

  private void setElementsPositionsAndColumnId(int columnId) {
    int i = 0;
    for (ModifiableColumnElement<T> element : elements) {
      element.setPosition(i++);
      element.setColumnId(columnId);
    }
  }

  private void assureFirstGeneration() {
    if (hasBeenGenerated) {
      throw new IllegalStateException();
    }
    hasBeenGenerated = true;
  }
}
