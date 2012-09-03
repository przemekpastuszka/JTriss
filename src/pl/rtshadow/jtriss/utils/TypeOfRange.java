package pl.rtshadow.jtriss.utils;

import java.util.Comparator;

public enum TypeOfRange {
  OPEN(new EqualAlteredComparator(1), new EqualAlteredComparator(-1)),
  CLOSED(new EqualAlteredComparator(0), new EqualAlteredComparator(0));

  private final EqualAlteredComparator comparatorForLeftRange;
  private final EqualAlteredComparator comparatorForRightRange;

  private TypeOfRange(EqualAlteredComparator comparatorForLeftRange, EqualAlteredComparator comparatorForRightRange) {
    this.comparatorForLeftRange = comparatorForLeftRange;
    this.comparatorForRightRange = comparatorForRightRange;
  }

  EqualAlteredComparator getComparatorForLeftRange() {
    return comparatorForLeftRange;
  }

  EqualAlteredComparator getComparatorForRightRange() {
    return comparatorForRightRange;
  }

  private static class EqualAlteredComparator<T extends Comparable<? super T>> implements Comparator<T> {
    private final int valueForEqual;

    private EqualAlteredComparator(int valueForEqual) {
      this.valueForEqual = valueForEqual;
    }

    @Override
    public int compare(T o1, T o2) {
      int comparison = o1.compareTo(o2);
      if (comparison == 0) {
        return valueForEqual;
      }
      return comparison;
    }
  }
}
