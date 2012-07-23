package pl.rtshadow.jtriss.column.accessor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.ColumnElementGenerator.element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class AbstractColumnAccessorTest {
  ColumnAccessor accessor;

  @Mock
  SortedColumn<Integer> column;
  @Mock
  ColumnConstructor<Integer> constructor;

  @SuppressWarnings("unchecked")
  @Test
  public void returnsNullWhenElementNotInColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(false);

    assertThat(accessor.reconstruct(element(7).get(), column)).isNull();
  }
}
