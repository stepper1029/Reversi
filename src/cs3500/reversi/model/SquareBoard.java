package cs3500.reversi.model;

import java.util.ArrayList;

public class SquareBoard extends AbstractBoard {

  public SquareBoard(int boardSize) {
    super();
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
    this.blackCells = new ArrayList<>();
    this.whiteCells = new ArrayList<>();
    this.boardSize = boardSize;
    this.cells = this.getBoard();
  }

  // assumes and preserves field invariant boardSize > 2
  // generates the cells within the board by position, starting at the top left of the board
  // and working left to right, top to bottom. private because no other class needs to generate
  // a board of cells.
  private ReversiCell[][] getBoard() {
    ReversiCell[][] cells = new ReversiCell[(boardSize * 2)][];

    for(int x = 0; x < this.boardSize; x ++) {
      ReversiCell[] row = new ReversiCell[boardSize];
      for(int y = 0; y < this.boardSize; y ++) {
        row[y] = new SquareCell(x, y);
      }
      cells[x] = row;
    }
    return cells;
  }
}
