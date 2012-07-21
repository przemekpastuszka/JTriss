package pl.rtshadow.jtriss.column.unmodifiable;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class ColumnConstructor<T extends Comparable<T>> {
	private List<ModifiableColumnElement<T>> elements = new ArrayList<ModifiableColumnElement<T>>();
	private boolean hasBeenGenerated = false;

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
