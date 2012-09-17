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

import java.util.Iterator;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.factory.StandardFactory;
import pl.rtshadow.jtriss.factory.TrissFactory;

abstract class AbstractColumnAccessor<T extends Comparable<? super T>>
    implements ColumnAccessor<T> {

  SortedColumn<T> column;
  Class<T> type;

  @Override
  public Iterator<ColumnElement<T>> iterator() {
    return column.iterator();
  }

  @Override
  public int getSize() {
    return column.getSize();
  }

  @Override
  public int getId() {
    return column.getId();
  }

  static abstract class AbstractColumnAccessorGenerator<T extends Comparable<? super T>>
      implements ColumnAccessorGenerator<T> {

    protected TrissFactory factory = new StandardFactory();

    ColumnConstructor<T> constructor;
    Class<T> type;

    @Override
    public void setFactory(TrissFactory factory) {
      this.factory = factory;
    }
  }
}