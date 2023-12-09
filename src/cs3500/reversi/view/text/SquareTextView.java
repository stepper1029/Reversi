package cs3500.reversi.view.text;

import java.util.Objects;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class SquareTextView extends AbstractTextView {

  public SquareTextView(ReadOnlyModel model) {
    super();
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public String toString() {
    String output = "";
    for (int numRow = 0; numRow < model.getNumRows(); numRow++) {
      for (int numCell = 0; numCell < model.getRowSize(numRow); numCell++) {
        ReversiCell currCell = model.getCellAt(numRow, numCell);
        if (model.isEmpty(currCell)) {
          output += emptyStringHelper(numRow, numCell);
        } else if (model.getColorAt(currCell).equals(DiskColor.Black)) {
          output += blackStringHelper(numRow, numCell);
        } else {
          output += whiteStringHelper(numRow, numCell);
        }
      }
    }
    return output;
  }
}
