package pl.rtshadow.jtriss.utils;

import java.util.Comparator;

class EqualAlteredComparator<T extends Comparable<? super T>> implements Comparator<T> {
  private final int valueForEqual;

  private EqualAlteredComparator(int valueForEqual) {
    this.valueForEqual = valueForEqual;
  }

  public final static EqualAlteredComparator LEFT_SKEWED_CMP = new EqualAlteredComparator(1);
  public final static EqualAlteredComparator RIGHT_SKEWED_CMP = new EqualAlteredComparator(-1);
  public final static EqualAlteredComparator IDENTITY_CMP = new EqualAlteredComparator(0);

  @Override
  public int compare(T o1, T o2) {
    int comparison = o1.compareTo(o2);
    if (comparison == 0) {
      return valueForEqual;
    }
    return comparison;
  }
}
