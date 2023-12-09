package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class to represent the board and all the shared fields and methods.
 */
abstract class AbstractBoard implements Board {
  // represents the number of cells that make up one side length of the board. Protected field
  // because any observations on the board should be made through the model.
  // INVARIANT: boardSize > 2 (HexBoard), boardSize > 3 (SquareBoard)
  protected int boardSize;

  // Holds all the cells that are a part of the board. The outer array holds the horizontal rows
  // of the board, where the 0th index is the top most row. The inner array hold the cells within
  // a row, where the 0th index is the leftmost cell in the row. Arrays were used because once the
  // board is created, its size must stay constant. Private field because any observations or
  // mutations on the board should be made through the model. Final because the
  // reference should not be changed. This field serves the purpose of holding information about
  // the structure of the board, and makes it easier to both display the board and
  // find cells at specific locations within the board.

  protected ReversiCell[][] cells;

  // Holds all the cells that have been captured by the black player, or cells that
  // have a black game disk on them. Used a list because the number of black cells will
  // inevitably change throughout the game, therefore the length should be flexible. Final
  // because the reference should not be changed.
  protected List<ReversiCell> blackCells;

  // Holds all the cells that have been captured by the white player, or cells that
  // have a white game disk on them. Used a list because the number of white cells will
  // inevitably change throughout the game, therefore the length should be flexible.
  // Final because the reference should not be changed.
  protected List<ReversiCell> whiteCells;

  /**
   * Constructor for AbstractBoard class.
   *
   * @param boardSize side length in cells
   */
  AbstractBoard(int boardSize) {
    this.blackCells = new ArrayList<>();
    this.whiteCells = new ArrayList<>();
    this.boardSize = boardSize;
  }


  AbstractBoard(int boardSize, ReversiCell[][] cells, List<ReversiCell> blackCells,
                  List<ReversiCell> whiteCells) {
    this.boardSize = boardSize;
    this.cells = cells;
    this.blackCells = blackCells;
    this.whiteCells = whiteCells;
  }

  protected abstract ReversiCell[][] getBoard();

  @Override
  public abstract ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);

  @Override
  public List<ReversiCell> getCells(DiskColor color) {
    switch (color) {
      case Black:
        return Collections.unmodifiableList(this.blackCells);
      case White:
        return Collections.unmodifiableList(this.whiteCells);
      default:
        throw new IllegalArgumentException("Invalid color");
    }
  }

  @Override
  public abstract ReversiCell[] getRow(int numRow);

  // assumes and preserves invariant boardSize > 2
  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  @Override
  public void placeDisk(ReversiCell c, DiskColor color) {
    this.invalidCellException(c);
    if (!this.isEmpty(c)) {
      throw new IllegalStateException("You can only place disks in empty cells.");
    } else if (color.equals(DiskColor.Black)) {
      this.blackCells.add(c);
    } else {
      this.whiteCells.add(c);
    }
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    this.invalidCellException(c);
    return !this.whiteCells.contains(c) && !this.blackCells.contains(c);
  }

  @Override
  public List<ReversiCell> getInitPositions() {
    return null;
  }

  @Override
  public void flipDisk(ReversiCell c) {
    this.invalidCellException(c);

    if (this.isEmpty(c)) {
      throw new IllegalStateException("Empty cell cannot be flipped.");
    } else if (this.whiteCells.contains(c)) {
      this.whiteCells.remove(c);
      this.blackCells.add(c);
    } else {
      this.blackCells.remove(c);
      this.whiteCells.add(c);
    }
  }

  @Override
  public abstract List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2);

  @Override
  public int getTotalNumCells() {
    int cellNum = 0;
    for (ReversiCell[] row : this.cells) {
      cellNum += row.length;
    }
    return cellNum;
  }

  /**
   * determines which of the given two cells is on the left. protected because this functionality
   * is only relevant to subclasses. Just a helper that does not need to be in the interface.
   * @param cell1 cell to compare
   * @param cell2 cell to compare
   * @return whichever cell is farther left
   */
  protected abstract ReversiCell getLeftCell(ReversiCell cell1, ReversiCell cell2);

  @Override
  public abstract Board copy();

  protected abstract void invalidCellException(ReversiCell c);
}
