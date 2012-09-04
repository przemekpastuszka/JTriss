package pl.rtshadow.jtriss.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public final class Tools {
  public static <K, V> Map<K, V> map(Pair<K, V>... pairs) {
    Map<K, V> map = new HashMap<K, V>();
    for (Pair<K, V> pair : pairs) {
      map.put(pair.getLeft(), pair.getRight());
    }
    return map;
  }

  private Tools() {
  }
}
