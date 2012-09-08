package pl.rtshadow.jtriss.table;

import static java.util.Collections.nCopies;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

  public Collection<Row> select() {
    ColumnAccessor<?> minimalColumn = findMinimalColumn();

    Collection<Row> results = new LinkedList<Row>();
    for (ColumnElement<?> element : minimalColumn) {
      Row nextRow = retrieveNextRowFrom(element, minimalColumn);
      if (nextRow != null) {
        results.add(nextRow);
      }
    }

    return results;
  }

  private Row retrieveNextRowFrom(ColumnElement<?> element, ColumnAccessor<?> minimalColumn) {
    Row nextRow = new Row(nCopies(size, null));
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
