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
