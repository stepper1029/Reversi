package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SquareBoard extends AbstractBoard {

  public SquareBoard(int boardSize) {
    super(boardSize);
    if (boardSize < 4) {
      throw new IllegalArgumentException("Board size must be at least 4");
    }
  }

  // assumes and preserves field invariant boardSize > 3
  // generates the cells within the board by position, starting at the top left of the board
  // and working left to right, top to bottom. private because no other class needs to generate
  // a board of cells.
  protected ReversiCell[][] getBoard() {
    ReversiCell[][] cells = new ReversiCell[boardSize][];

    for(int y = 0; y < this.boardSize; y ++) {
      ReversiCell[] row = new ReversiCell[boardSize];
      for(int x = 0; x < this.boardSize; x ++) {
        row[x] = new SquareCell(x, y);
      }
      cells[y] = row;
    }
    return cells;
  }

  @Override
  public List<ReversiCell> getInitPositions() {
    int topLeftX = (boardSize / 2) - 1;
    int topLeftY = (boardSize / 2) - 1;
    return new ArrayList<>(Arrays.asList(new SquareCell(topLeftX, topLeftY),
            new SquareCell(topLeftX, topLeftY + 1),
            new SquareCell(topLeftX + 1, topLeftY + 1),
            new SquareCell(topLeftX + 1, topLeftY)));
  }

  @Override
  public List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2) {
    if(cell1.getCoord('x') == cell2.getCoord('x')) {
      return null;
    }
    return null;
  }

  // assumes and preserves invariant boardSize > 3
  @Override
  public ReversiCell[] getRow(int numRow) {
    if (numRow < this.boardSize && numRow >= 0) {
      return this.cells[numRow];
    } else {
      throw new IllegalArgumentException("Invalid numRow: " + numRow);
    }
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return !whiteCells.contains(c) && !blackCells.contains(c);
  }

  protected void invalidCellException(ReversiCell c) {
    if ((c.getCoord('x') < 0)
            || c.getCoord('x') >= this.boardSize
            || c.getCoord('y') < 0
            || c.getCoord('y') >= this.boardSize) {
      throw new IllegalArgumentException("Cell coordinates must be between 0 and " +
              this.boardSize);
    }
  }
}