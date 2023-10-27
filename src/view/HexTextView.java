package view;

import java.util.Objects;

import model.BasicReversi;
import model.Board;
import model.DiscColor;
import model.ReversiCell;
import model.ReversiModel;

public class HexTextView implements ReversiView {
  private final ReversiModel model;

  public HexTextView(BasicReversi model) {

    this.model = Objects.requireNonNull(model);
  }

  public String toString() {
    String output = "";
    int cells = board.getNumTotalCells();
    int boardSize = board.getBoardSize();
    int maxWidth = (((boardSize * 2) - 1) * 2) - 1;
    int numSpacesOnEachSide = (maxWidth - (boardSize * 2) - 1) / 2;

    for(int row = 0; row < cells; row ++) {
      int widthCount = 0;
      while (widthCount <= numSpacesOnEachSide) {
        output += " ";
        widthCount++;
      }
      for (ReversiCell c : board.getRow(row)) {
        if (board.getColorAt(c).equals(DiscColor.Black)) {
          output += "X ";
          widthCount += 2;
        } else if (board.getColorAt(c).equals(DiscColor.White)) {
          output += "O ";
          widthCount += 2;
        } else {
          output += "_ ";
          widthCount += 2;
        }
      }
      if (row < cells/2) {
        numSpacesOnEachSide--;
      } else {
        numSpacesOnEachSide++;
      }
      output += "\n";
    }
    return output;
  }
}
