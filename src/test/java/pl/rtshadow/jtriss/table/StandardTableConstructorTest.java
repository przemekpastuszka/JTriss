package pl.rtshadow.jtriss.table;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.rtshadow.jtriss.schema.ColumnType.LIST;
import static pl.rtshadow.jtriss.schema.ColumnType.SCALAR;
import static pl.rtshadow.jtriss.test.TestColumnElement.element;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.Schema;

@RunWith(MockitoJUnitRunner.class)
public class StandardTableConstructorTest {
  @Mock
  private TrissFactory factory;
  @Mock
  private ColumnAccessorGenerator firstColumnAccessorGen, secondColumnAccessorGen;
  @Mock
  private ColumnAccessor firstColumnAccessor, secondColumnAccessor;
  @Mock
  private ColumnConstructor firstColumnConstructor, secondColumnConstructor;
  @Mock
  private Table table;

  @InjectMocks
  private StandardTableConstructor constructor = new StandardTableConstructor(
      new Schema().
          addColumn(Integer.class, SCALAR).
          addColumn(Integer.class, LIST));

  @Before
  public void setUp() {
    when(factory.createColumnConstructor(0)).thenReturn(firstColumnConstructor);
    when(factory.createColumnConstructor(1)).thenReturn(secondColumnConstructor);

    when(factory.createScalarColumnAccessorGenerator(Integer.class, firstColumnConstructor))
        .thenReturn(firstColumnAccessorGen);
    when(factory.createListColumnAccessorGenerator(Integer.class, secondColumnConstructor))
        .thenReturn(secondColumnAccessorGen);

    when(firstColumnAccessorGen.prepareColumnAccessor()).thenReturn(firstColumnAccessor);
    when(secondColumnAccessorGen.prepareColumnAccessor()).thenReturn(secondColumnAccessor);

    when(factory.prepareTable(asList(firstColumnAccessor, secondColumnAccessor))).thenReturn(table);
  }

  @Test
  public void createsEmptyTable() {
    assertThat(constructor.prepare()).isEqualTo(table);
  }

  @Test
  public void addsRow() {
    ModifiableColumnElement A1 = element(11), B1 = element(21), B2 = element(22);

    when(secondColumnAccessorGen.insert(asList(21, 22), null)).thenReturn(Pair.of(B1, B2));
    when(firstColumnAccessorGen.insert(11, B1)).thenReturn(Pair.of(A1, A1));

    constructor.add(new Row(asList(11, asList(21, 22))));

    assertThat(B2.getNextElementInTheRow()).isEqualTo(A1);
  }
}
