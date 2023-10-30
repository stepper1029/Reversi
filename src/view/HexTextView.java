package view;

import java.util.Objects;

import model.DiscColor;
import model.ReversiCell;
import model.ReadOnlyModel;

public class HexTextView implements ReversiView {
  private final ReadOnlyModel model;

  public HexTextView(ReadOnlyModel model) {

    this.model = Objects.requireNonNull(model);
  }

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
        numSpaces --;
      } else {
        numSpaces ++;
      }
    }
    return output;
  }


  private String emptyStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if(numRow == model.getNumRows() - 1) {
        output += "_";
      } else {
        output += "_\n";
      }
    } else {
      output += "_ ";
    }
    return output;
  }

  private String blackStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if(numRow == model.getNumRows() - 1) {
        output += "X";
      } else {
        output += "X\n";
      }
    } else {
      output += "X ";
    }
    return output;
  }

  private String whiteStringHelper(int numRow, int numCell) {
    String output = "";
    if (numCell == model.getRowSize(numRow) - 1) {
      if(numRow == model.getNumRows() - 1) {
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
