package pl.rtshadow.jtriss.test;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.query.Query.query;
import static pl.rtshadow.jtriss.query.constraint.LessThanConstraint.lessThan;

import org.junit.Test;

public class EmptyTableTest extends AbstractTableTest {
  @Test
  public void returnsEmptyResultForAQuery() {
    prepareTable();
    select(query().and(0, lessThan(10), Integer.class));

    assertThat(result).isEmpty();
  }
}
