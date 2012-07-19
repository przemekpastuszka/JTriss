package pl.rtshadow.jtriss.column;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class UnmodifiableColumn<T extends Comparable<T>> implements
		SortedColumn<T> {

	private List<ColumnElement<T>> elements;

	private UnmodifiableColumn(List<ColumnElement<T>> elements) {
		this.elements = elements;
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public SortedColumn<T> getSubColumn(T left, T right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ColumnElement<T> element) {
		// TODO Auto-generated method stub
		return false;
	}

	public static <T extends Comparable<T>> ColumnConstructor<T> construct() {
		return new ColumnConstructor<T>();
	}

	public static class ColumnConstructor<T extends Comparable<T>> {
		private List<ModifiableColumnElement<T>> elements = new ArrayList<ModifiableColumnElement<T>>();
		private boolean hasBeenGenerated = false;

		private ColumnConstructor() {
		}

		public void add(ModifiableColumnElement<T> element) {
			elements.add(element);
		}

		public SortedColumn<T> generate() {
			assureFirstGeneration();

			sort(elements);
			setElementsPositions();

			return new UnmodifiableColumn<T>(
					unmodifiableList(new ArrayList<ColumnElement<T>>(elements)));
		}

		private void setElementsPositions() {
			int i = 0;
			for (ModifiableColumnElement<T> element : elements) {
				element.setPosition(i++);
			}
		}

		private void assureFirstGeneration() {
			if (hasBeenGenerated) {
				throw new IllegalStateException();
			}
			hasBeenGenerated = true;
		}
	}
}
