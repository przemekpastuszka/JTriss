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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ReconstructedObject;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.row.Row;

public class ColumnSet {
  private final List<ColumnAccessor> accessors;
  private final int size;

  public ColumnSet(List<ColumnAccessor> accessors) {
    this.accessors = accessors;
    this.size = accessors.size();
  }

  public Collection<Row> select(int limit) {
    ColumnAccessor minimalColumn = findMinimalColumn();
    Iterator<ColumnElement> candidates = minimalColumn.iterator();

    Collection<Row> results = new LinkedList<Row>();
    for (int i = 0; i < limit && candidates.hasNext(); ++i) {
      minimalColumn.prepareMainColumnForReconstruction();

      Row nextRow = retrieveNextRowFrom(candidates.next(), minimalColumn);
      if (nextRow != null) {
        results.add(nextRow);
      }

      minimalColumn.finishReconstruction();
    }

    return results;
  }

  private Row retrieveNextRowFrom(ColumnElement<?> element, ColumnAccessor<?> minimalColumn) {
    Row nextRow = new Row(size);
    for (int i = 0; i < size + 1; ++i) {
      ColumnAccessor currentColumn = accessors.get((minimalColumn.getId() + i) % size);
      ReconstructedObject reconstructed = currentColumn.reconstruct(element);
      if (reconstructed == null) {
        nextRow = null;
        break;
      }

      nextRow.set(currentColumn.getId(), reconstructed.getObject());
      element = reconstructed.getNextElementInRow();
    }
    return nextRow;
  }

  private ColumnAccessor<?> findMinimalColumn() {
    return Collections.min(accessors, new Comparator<ColumnAccessor>() {
      @Override
      public int compare(ColumnAccessor o1, ColumnAccessor o2) {
        return ObjectUtils.compare(o1.getSize(), o2.getSize());
      }
    });
  }

}
