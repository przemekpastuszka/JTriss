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

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class CsvReaderTest {

  @Test
  public void readsSampleData() throws IOException {
    CSVReader reader = new CSVReader(new StringReader(SAMPLE_INPUT), ';', '\'', '\\');

    assertThat(reader.readNext()).isEqualTo(
        new String[] { "11,12", "Aquarium" });
    assertThat(reader.readNext()).isEqualTo(
        new String[] { "21,22", "Making Dioramas" });
    assertThat(reader.readNext()).isNull();
  }
}
