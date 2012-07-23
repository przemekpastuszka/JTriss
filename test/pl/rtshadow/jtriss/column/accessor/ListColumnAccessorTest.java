package pl.rtshadow.jtriss.column.accessor;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.test.ColumnElementGenerator.element;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

@RunWith(MockitoJUnitRunner.class)
public class ListColumnAccessorTest extends AbstractColumnAccessorTest {
  @Before
  public void setUp() {
    accessor = ListColumnAccessor.INSTANCE;
  }

  @SuppressWarnings("unchecked")
  @Test
  public void returnsTwoElementsFromTheSameColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(true, true, false);

    ReconstructedObject<Integer> reconstructed =
        accessor.reconstruct(
            element(7).withNext(
                element(8).withNext(
                    element(9).get()).get()).get(), column);

    assertThat(reconstructed.getObject()).isInstanceOf(List.class);
    List<Integer> objects = (List<Integer>) reconstructed.getObject();

    assertThat(objects).hasSize(2).contains(7, 8);

    assertThat(reconstructed.getNextElementInRow().getValue()).isEqualTo(9);
  }

  @Test
  public void addsEachObjectInGivenList() {
    ModifiableColumnElement<Integer> newElement = accessor.insert(asList(7, 8), element(9).get(), constructor);

    assertThat(newElement).isEqualTo(element(8).get());
    assertThat(newElement.getNextElementInTheRow()).isEqualTo(element(9).get());

    verify(constructor).add(element(7).get());
    verify(constructor).add(element(8).get());

    assertThat(getElementAddedToConstructorWithValue(7).getNextElementInTheRow().getValue()).isEqualTo(8);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private ModifiableColumnElement<Integer> getElementAddedToConstructorWithValue(int value) {
    ArgumentCaptor<ModifiableColumnElement> captor = forClass(ModifiableColumnElement.class);
    verify(constructor, atLeastOnce()).add(captor.capture());
    for (ModifiableColumnElement element : captor.getAllValues()) {
      if (element.getValue().equals(value)) {
        return element;
      }
    }
    return null;
  }
}
