package pl.rtshadow.jtriss.test;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.EqualsConstraint.equalsTo;
import static pl.rtshadow.jtriss.query.constraint.GreaterOrEqualConstraint.greaterOrEqual;
import static pl.rtshadow.jtriss.query.constraint.LessOrEqualConstraint.lessOrEqual;

import org.junit.Before;
import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;

public class SimpleQueriesTest extends AbstractTableTest {
  @Before
  public void setUp() {
    inputRows.add(new Row(7.0, asList(1, 2), "belial"));
    inputRows.add(new Row(1.0, asList(7), "merihem"));
    inputRows.add(new Row(8.0, asList(3), "astaroth"));
    inputRows.add(new Row(3.0, asList(0, 10, 9), "belphegor"));

    prepareTable();
  }

  @Test
  public void returnsEmptyResultForDisjointConstraints() {
    select(query().
        and(0, lessOrEqual(10.0)).
        and(0, greaterOrEqual(11.0)));

    assertThat(result).isEmpty();
  }

  @Test
  public void returnsEmptyResultWhenComputedRangesOnEachColumnDoNotIntersect() {
    select(query().
        and(0, lessOrEqual(7.0)).
        and(2, equalsTo("astaroth")));

    assertThat(result).isEmpty();
  }

  @Test
  public void returnsLastRowForContainsConstraint() {
    select(query().and(1, equalsTo(10)));

    assertResultContainsAllOf(3);
  }

  @Test
  public void returnsSecondRowForContainsConstraint() {
    select(query().and(1, equalsTo(7)));

    assertResultContainsAllOf(1);
  }

  @Test
  public void returnsOneRowWithLimit() {
    select(query().and(0, greaterOrEqual(7.0)).limit(1));

    assertResultContainsAnyOf(0, 2);
    assertThat(result).hasSize(1);
  }

  @Test
  public void returnsOneRowWithTwoConstraintsOnOneColumn() {
    select(query().
        and(0, greaterOrEqual(7.0)).
        and(0, lessOrEqual(7.0)));

    assertResultContainsAllOf(0);
  }

  @Test
  public void returnsTwoRows() {
    select(query().and(0, greaterOrEqual(7.0)));

    assertResultContainsAllOf(0, 2);
  }
}
