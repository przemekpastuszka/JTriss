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
 
package pl.rtshadow.jtriss.utils;

import java.util.Comparator;

class EqualAlteredComparator<T extends Comparable<? super T>> implements Comparator<T> {
  private final int valueForEqual;

  private EqualAlteredComparator(int valueForEqual) {
    this.valueForEqual = valueForEqual;
  }

  public final static EqualAlteredComparator LEFT_SKEWED_CMP = new EqualAlteredComparator(1);
  public final static EqualAlteredComparator RIGHT_SKEWED_CMP = new EqualAlteredComparator(-1);
  public final static EqualAlteredComparator IDENTITY_CMP = new EqualAlteredComparator(0);

  @Override
  public int compare(T o1, T o2) {
    int comparison = o1.compareTo(o2);
    if (comparison == 0) {
      return valueForEqual;
    }
    return comparison;
  }
}
