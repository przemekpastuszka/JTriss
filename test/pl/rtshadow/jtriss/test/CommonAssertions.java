package pl.rtshadow.jtriss.test;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Iterator;

public class CommonAssertions {

	public static <T> void assertTheSameCollection(Iterator<? extends T> first,
			Iterator<? extends T> second) {

		while (first.hasNext() && second.hasNext()) {
			assertThat(first.next()).isEqualTo(second.next());
		}
		assertThat(first.hasNext()).isEqualTo(second.hasNext());
	}
}
