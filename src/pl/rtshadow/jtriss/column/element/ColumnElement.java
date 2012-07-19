package pl.rtshadow.jtriss.column.element;

public interface ColumnElement<T extends Comparable<T>> extends
		Comparable<ColumnElement<T>> {

	T getValue();

	ColumnElement<T> getNextElementInTheRow();

	int getPositionInColumn();

}
