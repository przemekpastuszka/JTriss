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
 
package pl.rtshadow.jtriss.query.constraint;

import static pl.rtshadow.jtriss.common.ValueRange.finiteRange;
import pl.rtshadow.jtriss.common.ValueRange;

public class EqualsConstraint<T extends Comparable<? super T>> implements Constraint<T> {
  private final ValueRange<T> range;

  public static <T extends Comparable<? super T>> Constraint<T> equalsTo(T value) {
    return new EqualsConstraint<T>(value);
  }

  public EqualsConstraint(T value) {
    range = finiteRange(value, value);
  }

  @Override
  public ValueRange<T> reduceToRange() {
    return range;
  }

}
