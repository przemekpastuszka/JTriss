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

import static pl.rtshadow.jtriss.query.Query.query;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;

public class NullValuesTableTest extends AbstractTableTest {
  @Test
  public void returnsValidRowWithNulls() {
    List<Integer> listWithNull = new LinkedList<Integer>();
    listWithNull.add(null);

    inputRows.add(new Row(null, listWithNull, null));
    prepareTable();

    select(query());

    assertResultContainsAllOf(0);
  }
}
