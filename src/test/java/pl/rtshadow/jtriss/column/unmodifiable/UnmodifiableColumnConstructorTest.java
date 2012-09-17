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
 
package pl.rtshadow.jtriss.column.unmodifiable;

import static pl.rtshadow.jtriss.test.CommonAssertions.assertTheSameCollection;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;
import static pl.rtshadow.jtriss.test.TestObjects.TEST_COLUMN_ID;
import static pl.rtshadow.jtriss.test.TestObjects.generateSortedColumnFrom;
import static pl.rtshadow.jtriss.test.TestObjects.toElementListWithOrdinalPositions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;

@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableColumnConstructorTest {
  @Test
  public void sortsElements() {
    SortedColumn<Integer> column = generateSortedColumnFrom(3, 1, 2, 4);

    assertTheSameCollection(column.iterator(), toElementListWithOrdinalPositions(1, 2, 3, 4).iterator());
  }

  @Test(expected = IllegalStateException.class)
  public void cannotGenerateColumnMultipleTimes() {
    ColumnConstructor<Integer> constructor = UnmodifiableColumnConstructor.<Integer> constructor(TEST_COLUMN_ID);
    constructor.add(element(5));

    constructor.generate();
    constructor.generate();
  }
}
