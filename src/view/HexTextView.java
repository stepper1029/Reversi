package view;

import java.util.Objects;

import model.Board;
import model.ReversiCell;

public class HexTextView implements ReversiView {
  private final Board board;

  public HexTextView(Board board) {
    this.board = Objects.requireNonNull(board);
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
        if (board.isBlack(c)) {
          output += "X ";
          widthCount += 2;
        } else if (board.isWhite(c)) {
          output += "O ";
          widthCount += 2;
        } else {
          output += "_ ";
          widthCount += 2;
        }
      }
      for (int i = 0; i < numSpacesOnEachSide; i++) {
        output += " ";
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
