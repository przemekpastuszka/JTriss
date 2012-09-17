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
 
package pl.rtshadow.jtriss.common;

import static org.apache.commons.lang3.BooleanUtils.negate;

public class ValueRange<T extends Comparable<? super T>> {
  public final static ValueRange INFINITE = new ValueRange(null, null);

  private T left, right;
  private boolean leftOpen = false, rightOpen = false;

  private ValueRange(T left, T right) {
    this.left = left;
    this.right = right;
  }

  public static <T extends Comparable<? super T>> ValueRange<T> finiteRange(T left, T right) {
    return new ValueRange<T>(left, right);
  }

  public static <T extends Comparable<? super T>> ValueRange<T> leftFiniteRange(T left) {
    return new ValueRange<T>(left, null);
  }

  public static <T extends Comparable<? super T>> ValueRange<T> rightFiniteRange(T right) {
    return new ValueRange<T>(null, right);
  }

  public ValueRange<T> openOnTheLeft() {
    leftOpen = true;
    return this;
  }

  public ValueRange<T> openOnTheRight() {
    rightOpen = true;
    return this;
  }

  public ValueRange<T> intersect(ValueRange<T> other) {
    ValueRange<T> result = new ValueRange<T>(left, right);
    result.leftOpen = leftOpen;
    result.rightOpen = rightOpen;

    if (other.isFiniteOnTheLeft()) {
      if (isFiniteOnTheLeft() && left.compareTo(other.left) >= 0) {
        if (left.compareTo(other.left) == 0) {
          result.leftOpen = leftOpen || other.leftOpen;
        }
      } else {
        result.left = other.left;
        result.leftOpen = other.leftOpen;
      }
    }

    if (other.isFiniteOnTheRight()) {
      if (isFiniteOnTheRight() && right.compareTo(other.right) <= 0) {
        if (right.compareTo(other.right) == 0) {
          result.rightOpen = rightOpen || other.rightOpen;
        }
      } else {
        result.right = other.right;
        result.rightOpen = other.rightOpen;
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public Class<T> getType() {
    if (isFiniteOnTheLeft()) {
      return (Class<T>) left.getClass();
    }
    if (isFiniteOnTheRight()) {
      return (Class<T>) right.getClass();
    }
    return null;
  }

  public boolean isFiniteOnTheLeft() {
    return left != null;
  }

  public boolean isFiniteOnTheRight() {
    return right != null;
  }

  public boolean isEmpty() {
    if (negate(isFiniteOnTheLeft() && isFiniteOnTheRight())) {
      return false;
    }
    if (left.compareTo(right) == 0) {
      return isOpenOnTheLeft() || isOpenOnTheRight();
    }
    return left.compareTo(right) > 0;
  }

  public boolean isOpenOnTheLeft() {
    return leftOpen;
  }

  public boolean isOpenOnTheRight() {
    return rightOpen;
  }

  public T getLeft() {
    return left;
  }

  public T getRight() {
    return right;
  }
}
