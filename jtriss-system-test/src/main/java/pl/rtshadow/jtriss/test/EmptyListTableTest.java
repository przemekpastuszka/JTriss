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
import static java.util.Collections.emptyList;
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.ContainsConstraint.contains;

import org.junit.Before;
import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;

public class EmptyListTableTest extends AbstractTableTest {
  @Before
  public void setUp() {
    inputRows.add(new Row(1.0, emptyList(), "s"));
    inputRows.add(new Row(2.0, asList(0), "x"));
    prepareTable();
  }

  @Test
  public void returnsValidRowWithEmptyList() {
    select(query());

    assertResultContainsAllOf(0, 1);
  }

  @Test
  public void returnsOnlyRowWithNonEmptyList() {
    select(query().and(1, contains(0)));

    assertResultContainsAllOf(1);
  }
}
