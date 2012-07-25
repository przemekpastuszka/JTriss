package pl.rtshadow.jtriss.column.unmodifiable;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import pl.rtshadow.jtriss.column.ColumnConstructor;
import pl.rtshadow.jtriss.column.SortedColumn;
import pl.rtshadow.jtriss.column.element.ColumnElement;
import pl.rtshadow.jtriss.column.element.ModifiableColumnElement;

public class UnmodifiableColumnConstructor<T extends Comparable<? super T>> implements ColumnConstructor<T> {
  private List<ModifiableColumnElement<T>> elements = new ArrayList<ModifiableColumnElement<T>>();
  private boolean hasBeenGenerated = false;
  private int id;
  private Class<T> type;

  private UnmodifiableColumnConstructor(int id, Class<T> type) {
    this.id = id;
    this.type = type;
  }

  public static <T extends Comparable<? super T>> UnmodifiableColumnConstructor<T> constructor(int id, Class<T> type) {
    return new UnmodifiableColumnConstructor<T>(id, type);
  }

  @Override
  public void add(ModifiableColumnElement<T> element) {
    elements.add(element);
  }

  @Override
  public SortedColumn<T> generate() {
    assureFirstGeneration();

    sort(elements);
    setElementsPositionsAndColumnId(id);

    return new UnmodifiableSortedColumn<T>(
        unmodifiableList(new ArrayList<ColumnElement<T>>(elements)), type, id);
  }

  private void setElementsPositionsAndColumnId(int columnId) {
    int i = 0;
    for (ModifiableColumnElement<T> element : elements) {
      element.setPosition(i++);
      element.setColumnId(columnId);
    }
  }

  private void assureFirstGeneration() {
    if (hasBeenGenerated) {
      throw new IllegalStateException();
    }
    hasBeenGenerated = true;
  }

  @Override
  public Class<T> getElementsType() {
    return type;
  }
}
