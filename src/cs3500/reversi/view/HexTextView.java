package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.model.DiscColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * HexTextView gives a text-based view of a game of Reversi where black discs are represented by
 * "X", white discs by "O", and empty cells as "_".
 */
public class HexTextView implements ReversiView {

  // model is final because it should not be change in the view. ReadOnlyModel is passed
  // because the view should not have permission to mutate it, only to observe.
  private final ReadOnlyModel model;

  /**
   * Constructor for the text view initializes a ReadOnlyModel
   * which only has observable permissions.
   *
   * @param model the current, in play game of Reversi
   */
  public HexTextView(ReadOnlyModel model) {
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
        } else if (model.getColorAt(currCell).equals(DiscColor.Black)) {
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

  // adds the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // an empty cell.
  private String emptyStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if (numRow == model.getNumRows() - 1) {
        output += "_";
      } else {
        output += "_\n";
      }
    } else {
      output += "_ ";
    }
    return output;
  }

  // add the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // a black piece.
  private String blackStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if (numRow == model.getNumRows() - 1) {
        output += "X";
      } else {
        output += "X\n";
      }
    } else {
      output += "X ";
    }
    return output;
  }

  // add the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // a black piece.
  private String whiteStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if (numRow == model.getNumRows() - 1) {
        output += "O";
      } else {
        output += "O\n";
      }
    } else {
      output += "O ";
    }
    return output;
  }
}
