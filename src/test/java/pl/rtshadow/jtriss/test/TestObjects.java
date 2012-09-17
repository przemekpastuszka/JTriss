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

import static java.util.Arrays.asList;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import java.util.ArrayList;
import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;
import pl.rtshadow.jtriss.column.unmodifiable.UnmodifiableColumnConstructor;
import pl.rtshadow.jtriss.row.Row;

public class TestObjects {
  public static final int TEST_COLUMN_ID = 0;

  public static Row row(Integer... values) {
    return new Row(asList(values));
  }

  public static SortedColumn<Integer> generateSortedColumnFrom(Integer... values) {
    ColumnConstructor<Integer> constructor = UnmodifiableColumnConstructor.<Integer> constructor(TEST_COLUMN_ID);

    for (Integer value : values) {
      constructor.add(element(value));
    }

    return constructor.generate();
  }

  public static List<StandardColumnElement<Integer>> toElementList(Integer... values) {
    return toElementList(false, values);
  }

  public static List<StandardColumnElement<Integer>> toElementListWithOrdinalPositions(Integer... values) {
    return toElementList(true, values);
  }

  private static List<StandardColumnElement<Integer>> toElementList(boolean setOrdinalPositions, Integer... values) {
    List<StandardColumnElement<Integer>> elements = new ArrayList<StandardColumnElement<Integer>>();

    int currentPosition = 0;
    for (Integer value : values) {
      TestColumnElement newElement = element(value);
      if (setOrdinalPositions) {
        newElement = newElement.atPosition(currentPosition);
      }

      elements.add(newElement);
      currentPosition++;
    }
    return elements;
  }

}
