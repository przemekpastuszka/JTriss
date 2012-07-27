package pl.rtshadow.jtriss.common;

public class ValueRange<T extends Comparable<? super T>> {

  public static <T extends Comparable<? super T>> ValueRange<T> finiteRange(T left, T right) {
    return new ValueRange<T>();
  }

  public static <T extends Comparable<? super T>> ValueRange<T> leftFiniteRange(T left) {
    return new ValueRange<T>();
  }

  public static <T extends Comparable<? super T>> ValueRange<T> rightFiniteRange(T right) {
    return new ValueRange<T>();
  }

  public ValueRange<T> openOnTheLeft() {
    return this;
  }

  public ValueRange<T> openOnTheRight() {
    return this;
  }

  public ValueRange<T> intersect(ValueRange<T> other) {
    return null;
  }

  public Class<T> getType() {
    return null;
  }

  public boolean isFiniteOnTheLeft() {
    return false;
  }

  public boolean isFiniteOnTheRight() {
    return false;
  }

  public boolean isEmpty() {
    return false;
  }

  public boolean isOpenOnTheLeft() {
    return false;
  }

  public boolean isOpenOnTheRight() {
    return false;
  }

  public T getLeft() {
    return null;
  }

  public T getRight() {
    return null;
  }
}
