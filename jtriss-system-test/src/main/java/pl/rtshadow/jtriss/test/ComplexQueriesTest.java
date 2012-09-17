package pl.rtshadow.jtriss.test;

import static java.util.Arrays.asList;
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.EqualsConstraint.equalsTo;
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
        and(1, equalsTo(9)));

    assertResultContainsAllOf(0);
  }

  @Test
  public void returnsThirdRow() {
    select(query().
        and(1, greaterOrEqual(4)).
        and(1, lessOrEqual(5)));

    assertResultContainsAllOf(2);
  }
}
