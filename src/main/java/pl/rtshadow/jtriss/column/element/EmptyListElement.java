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

import java.util.Comparator;

public class EmptyListElement<T extends Comparable<? super T>> extends ModifiableColumnElement<T> {
  @Override
  public T getValue() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasValue() {
    return false;
  }

  private static class EmptyListAwareComparator implements Comparator<ColumnElement> {
    @Override
    public int compare(ColumnElement o1, ColumnElement o2) {
      if (o1 instanceof EmptyListElement) {
        return -1;
      }
      if (o2 instanceof EmptyListElement) {
        return 1;
      }
      return o1.getValue().compareTo(o2.getValue());
    }
  }

  public static Comparator<ColumnElement> EMPTY_LIST_AWARE_CMP = new EmptyListAwareComparator();
}
