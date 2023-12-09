package cs3500.reversi.view.text;

import java.util.Objects;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * HexTextView gives a text-based view of a game of Reversi where black disks are represented by
 * "X", white disks by "O", and empty cells as "_".
 */
public class HexTextView extends AbstractTextView {


  /**
   * Constructor for the text view initializes a ReadOnlyModel
   * which only has observable permissions.
   *
   * @param model the current, in play game of Reversi
   */
  public HexTextView(ReadOnlyModel model) {
    super();
    this.model = Objects.requireNonNull(model);
  }

  /**
   * Makes a text-based rendering of the state of the game, where black is represented by "X",
   * white is represented by "O" and empty cells are represented by "_".
   *
   * @return a string that represented the game state.
   */
  public String toString() {
    String output = "";
    int boardSize = model.getBoardSize();
    int maxWidth = (((boardSize * 2) - 1) * 2) - 1;
    int numSpaces = (maxWidth - (boardSize * 2) - 1) / 2;
    for (int numRow = 0; numRow < model.getNumRows(); numRow++) {
      int widthCount = 0;
      while (widthCount <= numSpaces) {
        output += " ";
        widthCount++;
      }
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
      if (numRow < boardSize - 1) {
        numSpaces--;
      } else {
        numSpaces++;
      }
    }
    return output;
  }
}
