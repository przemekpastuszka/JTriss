package pl.rtshadow.jtriss.column.element;

public class ColumnElementFactory {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ColumnElement createElement(Comparable value) {
		return new StandardColumnElement(value);
	}
}
