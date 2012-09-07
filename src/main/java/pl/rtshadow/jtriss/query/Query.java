package pl.rtshadow.jtriss.query;

import static org.apache.commons.lang3.BooleanUtils.negate;

import java.util.HashMap;
import java.util.Map;

import pl.rtshadow.jtriss.common.ValueRange;
import pl.rtshadow.jtriss.query.constraint.Constraint;

public class Query {
  private int limit;
  final Map<Integer, ValueRange> columnRange = new HashMap<Integer, ValueRange>();

  public int getLimit() {
    return limit;
  }

  public ValueRange getRangeForColumn(int id) {
    return columnRange.get(id);
  }

  public Query limit(int limit) {
    this.limit = limit;
    return this;
  }

  public static Query query() {
    return new Query();
  }

  public <T extends Comparable<? super T>>
      Query and(int columnId, Constraint<T> constraint, Class<T> constraintType) {

    if (columnRange.containsKey(columnId)) {
      ValueRange<T> valueRange = retrieveTypedValueRange(columnId, constraintType);
      columnRange.put(columnId, valueRange.intersect(constraint.reduceToRange()));

    } else {
      columnRange.put(columnId, constraint.reduceToRange());
    }

    return this;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <T extends Comparable<? super T>> ValueRange<T> retrieveTypedValueRange(int columnId, Class<T> constraintType) {
    ValueRange valueRange = columnRange.get(columnId);
    if (negate(valueRange.getType().equals(constraintType))) {
      throw new IllegalArgumentException("Wrong constraint type given. Wanted: "
          + valueRange.getType() + ", got: " + constraintType);
    }
    return valueRange;
  }
}
