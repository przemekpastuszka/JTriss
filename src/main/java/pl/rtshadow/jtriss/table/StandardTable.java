package pl.rtshadow.jtriss.table;

import static java.util.Collections.nCopies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import pl.rtshadow.jtriss.column.accessor.ColumnAccessor;
import pl.rtshadow.jtriss.column.accessor.ReconstructedObject;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.Query;
import pl.rtshadow.jtriss.row.Row;

public class StandardTable implements Table {
  private final List<ColumnAccessor> accessors;

  public StandardTable(List<ColumnAccessor> accessors) {
    this.accessors = accessors;
  }

  @Override
  public Collection<Row> select(Query query) {
    List<ColumnAccessor<?>> queryAccessors = calculateQueryAccessors(query);
    ColumnAccessor<?> minimalColumn = findMinimalColumn(queryAccessors);

    Collection<Row> results = new LinkedList<Row>();
    for (ColumnElement<?> element : minimalColumn) {
      Row nextRow = retrieveNextRowFrom(element, minimalColumn, queryAccessors);
      if (nextRow != null) {
        results.add(nextRow);
      }
    }

    return results;
  }

  private Row retrieveNextRowFrom(ColumnElement<?> element, ColumnAccessor<?> minimalColumn, List<ColumnAccessor<?>> queryAccessors) {
    Row nextRow = new Row(nCopies(accessors.size(), null));
    for (int i = 0; i < accessors.size(); ++i) {
      ColumnAccessor currentColumn = queryAccessors.get((minimalColumn.getId() + i) % accessors.size());
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

  private ColumnAccessor<?> findMinimalColumn(List<ColumnAccessor<?>> queryAccessors) {
    return Collections.min(queryAccessors, new Comparator<ColumnAccessor<?>>() {
      @Override
      public int compare(ColumnAccessor<?> o1, ColumnAccessor<?> o2) {
        return ObjectUtils.compare(o1.getSize(), o2.getSize());
      }
    });
  }

  private List<ColumnAccessor<?>> calculateQueryAccessors(Query query) {
    Map<Integer, ValueRange> ranges = query.getColumnRange();
    List<ColumnAccessor<?>> queryAccessors = new ArrayList<ColumnAccessor<?>>(accessors.size());

    for (int i = 0; i < accessors.size(); ++i) {
      ValueRange range = ranges.get(i);
      ColumnAccessor<?> accessor = accessors.get(i);

      if (range != null) {
        queryAccessors.add(accessor.subColumn(range));
      } else {
        queryAccessors.add(accessor);
      }
    }
    return queryAccessors;
  }
}
