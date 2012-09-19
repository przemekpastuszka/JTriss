package pl.rtshadow.jtriss.benchmark;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static pl.rtshadow.jtriss.schema.ColumnType.LIST;
import static pl.rtshadow.jtriss.schema.ColumnType.SCALAR;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.ColumnType;
import pl.rtshadow.jtriss.schema.Schema;
import au.com.bytecode.opencsv.CSVReader;

public class InputData implements Iterable<Row> {
  private static final char ESCAPE = '\\';
  private static final char QUOTECHAR = '\'';
  private static final char FIELD_SEPARATOR = ';';
  private static final char LIST_SEPARATOR = ',';

  private final CSVReader inputReader;
  private final Schema schema;

  public InputData(Reader reader, Schema schema) {
    inputReader = new CSVReader(reader, FIELD_SEPARATOR, QUOTECHAR, ESCAPE);
    this.schema = schema;
  }

  @Override
  public Iterator<Row> iterator() {
    return new RowIterator();
  }

  private String[] readNext(CSVReader reader) {
    try {
      return reader.readNext();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private class RowIterator implements Iterator<Row> {
    String[] nextRow;

    public RowIterator() {
      nextRow = readNext(inputReader);
    }

    @Override
    public boolean hasNext() {
      return nextRow != null;
    }

    @Override
    public Row next() {
      Row row = new Row(schema.size());
      for (int i = 0; i < schema.size(); ++i) {
        setField(i, row);
      }

      nextRow = readNext(inputReader);
      return row;
    }

    private void setField(int i, Row row) {
      Pair<Class, ColumnType> columnInfo = schema.get(i);

      if (columnInfo.getRight() == SCALAR) {
        row.set(i, convert(nextRow[i], columnInfo.getLeft()));
      }

      if (columnInfo.getRight() == LIST) {
        CSVReader listReader = new CSVReader(new StringReader(nextRow[i]), LIST_SEPARATOR, QUOTECHAR, ESCAPE);

        row.set(i, convert(readNext(listReader), columnInfo.getLeft()));
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private List<?> convert(String[] listElements, Class clazz) {
    if (clazz.equals(Integer.class)) {
      List<Integer> result = new ArrayList<Integer>(listElements.length);
      for (String element : listElements) {
        result.add(parseInt(element));
      }

      return result;
    }
    return asList(listElements);
  }

  private Object convert(String txt, Class clazz) {
    if (clazz.equals(Integer.class)) {
      return parseInt(txt);
    }
    return txt;
  }
}
