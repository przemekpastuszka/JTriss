package pl.rtshadow.jtriss.column.accessor;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractColumnAccessorTest {
  ColumnAccessor<Integer> accessor;

  @Mock
  SortedColumn<Integer> column;
  @Mock
  ColumnConstructor<Integer> constructor;

  @Before
  public void hasIntegerType() {
    when(constructor.generate()).thenReturn(column);
  }
}
