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

package pl.rtshadow.jtriss.query;

import java.util.HashMap;
import java.util.Map;

import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.constraint.Constraint;

public class Query {
  public final static int NO_LIMIT = Integer.MAX_VALUE;

  private int limit;
  final Map<Integer, ValueRange> columnRange = new HashMap<Integer, ValueRange>();

  public int getLimit() {
    return limit;
  }

  public ValueRange getRangeForColumn(int id) {
    return columnRange.get(id);
  }

  public Query limit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be positive value");
    }

    this.limit = limit;
    return this;
  }

  public static Query query() {
    return new Query().limit(NO_LIMIT);
  }

  public <T extends Comparable<? super T>>
      Query and(int columnId, Constraint<T> constraint) {

    if (columnRange.containsKey(columnId)) {
      ValueRange<T> valueRange = columnRange.get(columnId);
      columnRange.put(columnId, valueRange.intersect(constraint.reduceToRange()));

    } else {
      columnRange.put(columnId, constraint.reduceToRange());
    }

    return this;
  }
}
