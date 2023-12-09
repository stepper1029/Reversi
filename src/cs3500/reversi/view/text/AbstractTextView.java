package cs3500.reversi.view.text;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public abstract class AbstractTextView {

  // model is final because it should not be change in the view. ReadOnlyModel is passed
  // because the view should not have permission to mutate it, only to observe.
  protected ReadOnlyModel model;

  // add the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // a black piece.
  protected String whiteStringHelper(int numRow, int numCell) {
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

  // add the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // a black piece.
  protected String blackStringHelper(int numRow, int numCell) {
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

  // adds the appropriate number of spaces and line breaks based on the index of the cell
  // in the row. returns the string which represents the specified position in the board for
  // an empty cell.
  protected String emptyStringHelper(int numRow, int numCell) {
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

  public abstract String toString();
}
