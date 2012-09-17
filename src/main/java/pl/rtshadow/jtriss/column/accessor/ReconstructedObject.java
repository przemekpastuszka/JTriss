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

import pl.rtshadow.jtriss.column.element.ColumnElement;

public class ReconstructedObject<T extends Comparable<? super T>> {
  private final Object object;
  private ColumnElement<T> nextElementInRow;

  public ReconstructedObject(Object object, ColumnElement<T> nextElementInRow) {
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
