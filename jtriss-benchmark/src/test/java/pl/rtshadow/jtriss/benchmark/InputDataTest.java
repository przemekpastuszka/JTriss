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

package pl.rtshadow.jtriss.benchmark;

import static org.fest.assertions.Assertions.assertThat;
import static pl.rtshadow.jtriss.benchmark.test.TestObjects.SAMPLE_INPUT;
import static pl.rtshadow.jtriss.schema.ColumnType.LIST;
import static pl.rtshadow.jtriss.schema.ColumnType.SCALAR;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.Schema;

public class InputDataTest {

  @Test
  public void parsesDataCorrectly() {
    Iterator<Row> iterator = new InputData(
        new StringReader(SAMPLE_INPUT),
        new Schema().addColumn(Integer.class, LIST).addColumn(String.class, SCALAR))
        .iterator();

    assertRowIsValid(iterator, "Aquarium", 11, 12);
    assertRowIsValid(iterator, "Making Dioramas", 21, 22);
    assertThat(iterator.hasNext()).isFalse();
  }

  private void assertRowIsValid(Iterator<Row> iterator, String expectedScalar, Integer... expectedListElements) {
    assertThat(iterator.hasNext()).isTrue();

    Row row = iterator.next();
    assertThat(row.get(0)).isInstanceOf(List.class);
    assertThat((List<String>) row.get(0)).containsExactly(expectedListElements);
    assertThat(row.get(1)).isEqualTo(expectedScalar);
  }
}
