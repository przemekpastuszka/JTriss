package pl.rtshadow.jtriss.column.element;

public abstract class ModifiableColumnElement<T extends Comparable<T>>
		implements ColumnElement<T> {

	ColumnElement<T> nextElement;
	int position;

	@Override
	public ColumnElement<T> getNextElementInTheRow() {
		return nextElement;
	}

	@Override
	public int getPositionInColumn() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setNextElement(ColumnElement<T> nextElement) {
		this.nextElement = nextElement;
	}

	@Override
	public int compareTo(ColumnElement<T> other) {
		return getValue().compareTo(other.getValue());
	}
}
