package pl.rtshadow.jtriss.column.accessor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.ColumnElementGenerator.element;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class ScalarColumnAccessorTest {
  ColumnAccessor accessor = ScalarColumnAccessor.INSTANCE;

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

  @SuppressWarnings("unchecked")
  @Test
  public void returnsOnlyOneElementFromColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(true);

    ReconstructedObject<Integer> reconstructed =
        accessor.reconstruct(element(7).withNext(element(8).get()).get(), column);

    assertThat(reconstructed.getObject()).isEqualTo(7);
    assertThat(reconstructed.getNextElementInRow().getValue()).isEqualTo(8);
  }

  @Test
  public void treatsAnyObjectAsScalar() {
    ModifiableColumnElement<Integer> newElement = accessor.insert(7, element(8).get(), constructor);

    assertThat(newElement).isEqualTo(element(7).get());
    assertThat(newElement.getNextElementInTheRow()).isEqualTo(element(8).get());

    verify(constructor).add(element(7).get());
  }
}
