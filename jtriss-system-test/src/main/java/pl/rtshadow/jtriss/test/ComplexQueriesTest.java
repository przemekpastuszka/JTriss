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
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.ContainsConstraint.contains;
import static pl.rtshadow.jtriss.query.constraint.GreaterOrEqualConstraint.greaterOrEqual;
import static pl.rtshadow.jtriss.query.constraint.LessOrEqualConstraint.lessOrEqual;

import org.junit.Before;
import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;

public class ComplexQueriesTest extends AbstractTableTest {
  @Before
  public void setUp() {
    inputRows.add(new Row(3.141, asList(7, 9, 9, 10), "luxuria"));
    inputRows.add(new Row(2.718, asList(21, 9), "gula"));
    inputRows.add(new Row(1.618, asList(4, 4, 5), "avaritia"));
    inputRows.add(new Row(1.414, asList(0, -3, 0), "acedia"));
    inputRows.add(new Row(1.732, asList(-1), "ira"));
    inputRows.add(new Row(2.236, asList(1), "invidia"));
    inputRows.add(new Row(0.577, asList(-1000), "superbia"));

    prepareTable();
  }

  @Test
  public void returnsFirstRow() {
    select(query().
        and(0, greaterOrEqual(3.0)).
        and(1, contains(9)));

    assertResultContainsAllOf(0);
  }

  @Test
  public void returnsFirstTwoRows() {
    select(query().and(1, contains(9)));

    assertResultContainsAllOf(0, 1);
  }

  @Test
  public void returnsAnswerForConstraintOnAllColumns() {
    select(query().
        and(0, lessOrEqual(1.7)).
        and(1, contains(0)).
        and(2, lessOrEqual("s")));

    assertResultContainsAllOf(3);
  }

  @Test
  public void returnsAllRowsWhenNoConstraintGiven() {
    select(query());

    assertResultContainsAllOf(0, 1, 2, 3, 4, 5, 6);
  }
}
