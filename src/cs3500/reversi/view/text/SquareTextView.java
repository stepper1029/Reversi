package cs3500.reversi.view.text;

import java.util.Objects;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Square Text view shows the current state of the game board as a string of characters.
 * X represents a black piece on the board, O represents a white piece on the board, _ represents
 * and empty square on the board.
 */
public class SquareTextView extends AbstractTextView {

  /**
   * Constructor takes in an observable model.
   * @param model ReadOnlyModel
   */
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
