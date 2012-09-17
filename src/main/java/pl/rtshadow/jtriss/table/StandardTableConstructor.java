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
 
package pl.rtshadow.jtriss.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ColumnAccessorGenerator;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;
import pl.rtshadow.jtriss.factory.StandardFactory;
import pl.rtshadow.jtriss.factory.TrissFactory;
import pl.rtshadow.jtriss.row.Row;
import pl.rtshadow.jtriss.schema.ColumnType;
import pl.rtshadow.jtriss.schema.Schema;

public class StandardTableConstructor implements TableConstructor {
  private TrissFactory factory = new StandardFactory();

  private final Schema schema;
  private List<ColumnAccessorGenerator> columnGenerators;

  public StandardTableConstructor(Schema schema) {
    this.schema = schema;

  }

  @Override
  public void add(Row row) {
    createColumnGenerators(schema);

    ModifiableColumnElement nextElement = null;
    ModifiableColumnElement lastElement = null;
    for (int i = schema.size() - 1; i >= 0; --i) {
      Pair<ModifiableColumnElement, ModifiableColumnElement> newElements =
          columnGenerators.get(i).insert(row.get(i), nextElement);

      nextElement = newElements.getLeft();
      if (lastElement == null) {
        lastElement = newElements.getRight();
      }
    }
    lastElement.setNextElement(nextElement);
  }

  @Override
  public Table prepare() {
    createColumnGenerators(schema);

    List<ColumnAccessor> accessors = new ArrayList<ColumnAccessor>(schema.size());
    for (ColumnAccessorGenerator generator : columnGenerators) {
      accessors.add(generator.prepareColumnAccessor());
    }
    return factory.prepareTable(accessors);
  }

  private void createColumnGenerators(Schema schema) {
    if (columnGenerators != null) {
      return;
    }

    int i = 0;
    columnGenerators = new ArrayList<ColumnAccessorGenerator>(schema.size());
    for (Pair<Class, ColumnType> colInfo : schema) {
      ColumnType columnType = colInfo.getRight();
      columnGenerators.add(
          columnType.createColumnAccessorGenerator(
              factory,
              colInfo.getLeft(),
              factory.createColumnConstructor(i++)));
    }
  }
}
