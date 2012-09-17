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

package pl.rtshadow.jtriss.factory;

import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.accessor.ListColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ScalarColumnAccessor;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor;
import pl.rtshadow.jtriss.table.ColumnSet;
import pl.rtshadow.jtriss.table.StandardTable;
import pl.rtshadow.jtriss.table.Table;

public class StandardFactory implements TrissFactory {
  @Override
  public <T extends Comparable<? super T>> ModifiableColumnElement<T>
      createElement(Class<T> type, Object value) {

    if (value != null) {
      T castedValue = type.cast(value);
      if (castedValue == null) {
        throw new IllegalArgumentException("Expected instance of " + type + ", got: " +
            value.getClass());
      }
      return new StandardColumnElement<T>(castedValue);
    }

    return new StandardColumnElement<T>(null);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor) {
    return ScalarColumnAccessor.generator(type, constructor);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(
      Class<T> type, ColumnConstructor<T> constructor) {
    return ListColumnAccessor.generator(type, constructor);
  }

  @Override
  public <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id) {
    return UnmodifiableColumnConstructor.<T> constructor(id);
  }

  @Override
  public ColumnSet createColumnSet(List<ColumnAccessor> columns) {
    return new ColumnSet(columns);
  }

  @Override
  public Table prepareTable(List<ColumnAccessor> columns) {
    return new StandardTable(columns);
  }
}
