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

package pl.rtshadow.jtriss.column.element;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public abstract class ModifiableColumnElement<T extends Comparable<? super T>> implements ColumnElement<T> {
  private ColumnElement<T> nextElement;
  private int position;
  private int columnId;

  @Override
  public ColumnElement<T> getNextElementInTheRow() {
    return nextElement;
  }

  @Override
  public int getColumnId() {
    return columnId;
  }

  @Override
  public int getPositionInColumn() {
    return position;
  }

  public void setColumnId(int columnId) {
    this.columnId = columnId;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public void setNextElement(ColumnElement<T> nextElement) {
    this.nextElement = nextElement;
  }

  @Override
  public String toString() {
    return reflectionToString(this);
  }
}
