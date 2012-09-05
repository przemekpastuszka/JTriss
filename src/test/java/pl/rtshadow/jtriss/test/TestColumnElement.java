package pl.rtshadow.jtriss.test;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.StandardColumnElement;

public class TestColumnElement extends StandardColumnElement<Integer> {

  public TestColumnElement(Integer value) {
    super(value);
    setColumnId(TEST_COLUMN_ID);
    setPosition(value);
  }

  public static TestColumnElement element(Integer value) {
    return new TestColumnElement(value);
  }

  public TestColumnElement atPosition(int index) {
    setPosition(index);
    return this;
  }

  public TestColumnElement inColumn(int columnId) {
    setColumnId(columnId);
    return this;
  }

  public TestColumnElement withNext(ColumnElement<Integer> next) {
    setNextElement(next);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    return reflectionEquals(this, obj, true);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this, true);
  }
}
