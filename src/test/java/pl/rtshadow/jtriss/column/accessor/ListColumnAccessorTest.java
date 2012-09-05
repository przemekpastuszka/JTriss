package pl.rtshadow.jtriss.column.accessor;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.column.accessor.ListColumnAccessor.generator;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;

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
    accessorGenerator = generator(Integer.class, constructor);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void returnsTwoElementsFromTheSameColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(false, true, false);

    ColumnAccessor<Integer> accessor = accessorGenerator.prepareColumnAccessor();
    ReconstructedObject<Integer> reconstructed = accessor.reconstruct(testList());

    assertThat(reconstructed.getObject()).isInstanceOf(List.class);
    List<Integer> objects = (List<Integer>) reconstructed.getObject();

    assertThat(objects).hasSize(2).contains(7, 8);

    assertThat(reconstructed.getNextElementInRow().getValue()).isEqualTo(9);
  }

  private ModifiableColumnElement<Integer> testList() {
    return element(7).withNext(
        element(8).withNext(
            element(9).inColumn(TEST_COLUMN_ID + 1)));
  }

  @Test
  public void returnsNullIfListNotIncludedInColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(false);

    ColumnAccessor<Integer> accessor = accessorGenerator.prepareColumnAccessor();
    ReconstructedObject<Integer> reconstructed = accessor.reconstruct(testList());

    assertThat(reconstructed).isNull();
  }

  @Test
  public void addsEachObjectInGivenList() {
    ModifiableColumnElement<Integer> newElement = accessorGenerator.insert(asList(7, 8), element(9));

    assertThat(newElement).isEqualTo(element(8));
    assertThat(newElement.getNextElementInTheRow()).isEqualTo(element(9));

    verify(constructor).add(element(7));
    verify(constructor).add(element(8));

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
