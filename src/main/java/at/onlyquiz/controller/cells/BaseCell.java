package at.onlyquiz.controller.cells;

import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;

public class BaseCell<T> extends ListCell<T> {
  protected final Region blank = new Region();
  public BaseCell() { super(); }

  protected void listViewUpdate() {

  }

  protected boolean isEmptyItem(Object item, boolean empty) {
    if (empty || item == null) {
      setText(null);
      setGraphic(null);
      return true;
    }
    return false;
  }
}
