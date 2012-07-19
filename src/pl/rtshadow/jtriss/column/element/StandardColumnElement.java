package pl.rtshadow.jtriss.column.element;

public class StandardColumnElement<T extends Comparable<T>> extends
		ModifiableColumnElement<T> {

	private T value;

	public StandardColumnElement(T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

}
