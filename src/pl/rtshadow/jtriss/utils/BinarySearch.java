package pl.rtshadow.jtriss.utils;

import java.util.List;

public class BinarySearch {

	private enum SearchType {
		LEFTMOST, RIGHTMOST
	}

	public static <T extends Comparable<T>> int lowerBound(List<T> ls, T value) {
		return search(ls, value, SearchType.LEFTMOST);
	}

	public static <T extends Comparable<T>> int upperBound(List<T> ls, T value) {
		return search(ls, value, SearchType.RIGHTMOST);
	}

	private static <T extends Comparable<T>> int search(List<T> ls, T value,
			SearchType type) {

		int p = 0, k = ls.size() - 1;

		while (p < k) {
			int q = (p + k) / 2;
			T midValue = ls.get(q);

			int comparision = value.compareTo(midValue);

			if (comparision < 0) {
				k = q - 1;
			} else if (comparision > 0) {
				p = q + 1;
			} else if (type.equals(SearchType.LEFTMOST)) {
				k = q;
			} else if (p == q) {
				break;
			} else {
				p = q;
			}
		}

		// TODO above loop guarantees, that the result is one of:
		// p - 1, p or p + 1
		// need to add the final check

		return p;
	}
}
