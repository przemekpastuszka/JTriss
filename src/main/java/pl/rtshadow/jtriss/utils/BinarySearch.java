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

import static pl.rtshadow.jtriss.utils.EqualAlteredComparator.IDENTITY_CMP;
import static pl.rtshadow.jtriss.utils.EqualAlteredComparator.LEFT_SKEWED_CMP;
import static pl.rtshadow.jtriss.utils.EqualAlteredComparator.RIGHT_SKEWED_CMP;

import java.util.Comparator;
import java.util.List;

public class BinarySearch {
  private enum SearchType {
    LEFTMOST, RIGHTMOST
  }

  public static <T extends Comparable<? super T>> int lowerBound(List<T> ls, T value, boolean withOpenRange) {
    return search(ls, value, SearchType.LEFTMOST, withOpenRange ? LEFT_SKEWED_CMP : IDENTITY_CMP);
  }

  public static <T extends Comparable<? super T>> int upperBound(List<T> ls, T value, boolean withOpenRange) {
    return search(ls, value, SearchType.RIGHTMOST, withOpenRange ? RIGHT_SKEWED_CMP : IDENTITY_CMP);
  }

  private static <T extends Comparable<? super T>> int search(List<T> ls, T value, SearchType type, Comparator<T> cmp) {

    int p = 0, k = ls.size() - 1;

    // loop invariant:
    // if LEFTMOST then result is in [p, k + 1]
    // if RIGHTMOST then result is in [p - 1, k]
    while (p < k) {
      int q = (p + k) / 2;
      T midValue = ls.get(q);

      int comparison = cmp.compare(value, midValue);

      if (comparison < 0) {
        k = q - 1;
      } else if (comparison > 0) {
        p = q + 1;
      } else if (type.equals(SearchType.LEFTMOST)) {
        k = q - 1;
      } else {
        p = q + 1;
      }
    }

    // since p == k, then result is p or p - 1
    if (type.equals(SearchType.RIGHTMOST) && cmp.compare(value, ls.get(p)) < 0) {
      return p - 1;
    }

    // since p == k, then result is p or p + 1
    if (type.equals(SearchType.LEFTMOST) && cmp.compare(value, ls.get(p)) > 0) {
      return p + 1;
    }

    return p;
  }
}
