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

package pl.rtshadow.jtriss.test;

import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.EmptyListElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.table.ColumnSet;
import pl.rtshadow.jtriss.table.Table;

public class TestFactory implements TrissFactory {

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createScalarColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ColumnAccessorGenerator<T> createListColumnAccessorGenerator(Class<T> type,
      ColumnConstructor<T> constructor) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ColumnConstructor<T> createColumnConstructor(int id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ModifiableColumnElement<T> createElement(Class<T> type, Object value) {
    return (ModifiableColumnElement) element((Integer) value);
  }

  @Override
  public ColumnSet createColumnSet(List<ColumnAccessor> columns) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Table prepareTable(List<ColumnAccessor> columns) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T extends Comparable<? super T>> ModifiableColumnElement<T> createEmptyListElement() {
    return new EmptyListElement<T>();
  }
}
