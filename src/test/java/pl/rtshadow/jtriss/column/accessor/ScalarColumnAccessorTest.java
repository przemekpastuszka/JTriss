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
 
package pl.rtshadow.jtriss.column.accessor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.column.accessor.ScalarColumnAccessor.generator;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.test.TestFactory;

@RunWith(MockitoJUnitRunner.class)
public class ScalarColumnAccessorTest extends AbstractColumnAccessorTest {
  @Before
  public void setUp() {
    accessorGenerator = generator(Integer.class, constructor);
    accessorGenerator.setFactory(new TestFactory());
  }

  @SuppressWarnings("unchecked")
  @Test
  public void returnsOnlyOneElementFromColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(true);

    ColumnAccessor<Integer> accessor = accessorGenerator.prepareColumnAccessor();
    ReconstructedObject<Integer> reconstructed =
        accessor.reconstruct(element(7).withNext(element(8)));

    assertThat(reconstructed.getObject()).isEqualTo(7);
    assertThat(reconstructed.getNextElementInRow().getValue()).isEqualTo(8);
  }

  @Test
  public void treatsAnyObjectAsScalar() {
    Pair<ModifiableColumnElement<Integer>, ModifiableColumnElement<Integer>> newElements =
        accessorGenerator.insert(7, element(8));

    assertThat(newElements.getLeft()).isEqualTo(newElements.getRight());
    assertThat(newElements.getLeft()).isEqualTo(element(7).withNext(element(8)));
    verify(constructor).add(element(7).withNext(element(8)));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void returnsNullWhenElementNotInColumn() {
    when(column.contains(any(ColumnElement.class))).thenReturn(false);

    ColumnAccessor<Integer> accessor = accessorGenerator.prepareColumnAccessor();
    assertThat(accessor.reconstruct(element(7))).isNull();
  }
}
