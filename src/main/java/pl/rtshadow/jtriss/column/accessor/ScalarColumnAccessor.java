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
 
package pl.rtshadow.jtriss.column.accessor;

import org.apache.commons.lang3.tuple.Pair;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;

public class ScalarColumnAccessor<T extends Comparable<? super T>> extends AbstractColumnAccessor<T> {
  private ScalarColumnAccessor(Class<T> type, SortedColumn<T> column) {
    this.column = column;
    this.type = type;
  }

  @Override
  public ReconstructedObject<T> reconstruct(ColumnElement<T> firstElement) {
    if (column.contains(firstElement)) {
      return new ReconstructedObject<T>(firstElement.getValue(), firstElement.getNextElementInTheRow());
    }
    return null;
  }

  @Override
  public ColumnAccessor<T> subColumn(ValueRange<T> range) {
    return new ScalarColumnAccessor<T>(type, column.getSubColumn(range));
  }

  public static <T extends Comparable<? super T>> ColumnAccessorGenerator<T> generator(Class<T> type, ColumnConstructor<T> constructor) {
    return new ScalarColumnAccessorGenerator<T>(type, constructor);
  }

  private static class ScalarColumnAccessorGenerator<T extends Comparable<? super T>> extends AbstractColumnAccessorGenerator<T> {
    public ScalarColumnAccessorGenerator(Class<T> type, ColumnConstructor<T> constructor) {
      this.constructor = constructor;
      this.type = type;
    }

    @Override
    public Pair<ModifiableColumnElement<T>, ModifiableColumnElement<T>>
        insert(Object value, ColumnElement<T> nextElement) {
      ModifiableColumnElement<T> element = factory.createElement(type, value);
      element.setNextElement(nextElement);
      constructor.add(element);

      return Pair.of(element, element);
    }

    @Override
    public ColumnAccessor<T> prepareColumnAccessor() {
      return new ScalarColumnAccessor<T>(type, constructor.generate());
    }
  }

  @Override
  public void prepareMainColumnForReconstruction() {
  }

  @Override
  public void finishReconstruction() {
  }
}
