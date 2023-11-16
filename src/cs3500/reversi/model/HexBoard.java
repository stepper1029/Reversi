package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class HexBoard implements Board and represents a Reversi game board with Hexagonal shape. The
 * class is package private because the board itself should only be accessible within the package.
 * Anything outside the model package should use the Model itself to mutate or observe the game.
 */
class HexBoard implements Board {

  // represents the number of cells that make up one side length of the board. Private field
  // because any observations on the board should be made through the model. Final because the
  // reference should not be changed.
  // INVARIANT: boardSize > 2
  private final int boardSize;

  // Holds all the cells that are a part of the board. The outer array holds the horizontal rows
  // of the board, where the 0th index is the top most row. The inner array hold the cells within
  // a row, where the 0th index is the leftmost cell in the row. Arrays were used because once the
  // board is created, its size must stay constant. Private field because any observations or
  // mutations on the board should be made through the model. Final because the
  // reference should not be changed. This field serves the purpose of holding information about
  // the structure of the board, and makes it easier to both display the board and
  // find cells at specific locations within the board.

  private final ReversiCell[][] cells;

  // Holds all the cells that have been captured by the black player, or cells that
  // have a black game disk on them. Used a list because the number of black cells will
  // inevitably change throughout the game, therefore the length should be flexible. Final
  // because the reference should not be changed.
  private final List<ReversiCell> blackCells;

  // Holds all the cells that have been captured by the white player, or cells that
  // have a white game disk on them. Used a list because the number of white cells will
  // inevitably change throughout the game, therefore the length should be flexible.
  // Final because the reference should not be changed.
  private final List<ReversiCell> whiteCells;

  /**
   * Constructor for the HexBoard class, takes in a board size and initializes the fields.
   * boardSize invariant is ensured by the constructor, you cannot pass in a boardSize less than
   * 3. blackCells and whiteCells are initialized to be empty lists. When the board is made
   * initially all the cells are empty. cells field is initialized to hold the valid coordinates
   * for a board of the given size. Package private because no class outside the model
   * interface should have permission to construct an instance of a specific board.
   * Initializes the board using HexCells and assumes the invariant in the HexCell class to be
   * true (q + r + s = 0).
   *
   * @param boardSize side length in cells
   */
  HexBoard(int boardSize) {
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
    this.blackCells = new ArrayList<>();
    this.whiteCells = new ArrayList<>();
    this.boardSize = boardSize;
    this.cells = this.getBoard();
  }

  /**
   * Constructor to initialize fields to the given values. Package private because no class
   * outside the model interface should have permission to construct an instance of a specific
   * board. This is necessary for the copy method. This is necessary for the copy method.
   *
   * @param boardSize  the size of the board
   * @param cells      a list of all cells in this board
   * @param blackCells a list of all cells with black pieces
   * @param whiteCells a list of all cells with white pieces
   */
  public HexBoard(int boardSize, ReversiCell[][] cells, List<ReversiCell> blackCells,
                  List<ReversiCell> whiteCells) {
    this.boardSize = boardSize;
    this.cells = cells;
    this.blackCells = blackCells;
    this.whiteCells = whiteCells;
  }

  // assumes and preserves field invariant boardSize > 2
  // generates the cells within the board by position, starting at the top left of the board
  // and working left to right, top to bottom. private because no other class needs to generate
  // a board of cells.
  private ReversiCell[][] getBoard() {
    ReversiCell[][] cells = new ReversiCell[(boardSize * 2) - 1][];
    int width = boardSize;

    for (int r = (this.boardSize - 1) * -1; r < this.boardSize; r++) {
      ReversiCell[] currRow;
      int cellIndex = 0;
      int rowIndex = r + boardSize - 1;
      if (rowIndex < boardSize) {
        currRow = new ReversiCell[width];
        width++;
        if (rowIndex == boardSize - 1) {
          width--;
        }
      } else {
        width--;
        currRow = new ReversiCell[width];
      }
      for (int q = (this.boardSize - 1) * -1; q < this.boardSize; q++) {
        for (int s = this.boardSize - 1; s > (this.boardSize) * -1; s--) {
          if (q + r + s == 0 && cellIndex < width) {
            currRow[cellIndex] = new HexCell(q, r, s);
            cellIndex++;
          }
        }
      }
      cells[rowIndex] = currRow;
    }
    return cells;
  }

  @Override
  public List<ReversiCell> getInitPositions() {
    return new ArrayList<>(Arrays.asList(new HexCell(0, -1, 1),
            new HexCell(1, -1, 0), new HexCell(1, 0, -1),
            new HexCell(0, 1, -1), new HexCell(-1, 1, 0),
            new HexCell(-1, 0, 1)));
  }

  @Override
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    ReversiCell neighbor = cell.addVector(direction.getHexDirectionCoordinates());
    this.invalidCellException(neighbor);
    return neighbor;
  }

  // assumes and preserves invariant boardSize > 2
  @Override
  public ReversiCell[] getRow(int numRow) {
    if (numRow < (this.boardSize * 2) - 1 && numRow >= 0) {
      return this.cells[numRow];
    } else {
      throw new IllegalArgumentException("Invalid numRow: " + numRow);
    }
  }

  // assumes and preserves invariant boardSize > 2
  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

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
  public List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2) {
    ReversiCell leftCell;
    ReversiCell rightCell;
    List<ReversiCell> betweenCells = new ArrayList<>();
    leftCell = this.getLeftCell(cell1, cell2);
    rightCell = this.getRightCell(cell1, cell2);
    if (leftCell.getCoord('q') == rightCell.getCoord('q')) {
      int q = leftCell.getCoord('q');
      for (int r = leftCell.getCoord('r') + 1, s = leftCell.getCoord('s') - 1;
           r < rightCell.getCoord('r') && s > rightCell.getCoord('s'); r++, s--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    } else if (leftCell.getCoord('r') == rightCell.getCoord('r')) {
      int r = leftCell.getCoord('r');
      for (int q = leftCell.getCoord('q') + 1, s = leftCell.getCoord('s') - 1;
           q < rightCell.getCoord('q') && s > rightCell.getCoord('s'); q++, s--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    } else if (leftCell.getCoord('s') == rightCell.getCoord('s')) {
      int s = leftCell.getCoord('s');
      for (int q = leftCell.getCoord('q') + 1, r = leftCell.getCoord('r') - 1;
           q < rightCell.getCoord('q') && r > rightCell.getCoord('r'); q++, r--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    } else {
      throw new IllegalArgumentException("Given cells are not in a row and do not have " +
              "cells in between");
    }
    return betweenCells;
  }

  @Override
  public int getTotalNumCells() {
    int cellNum = 0;
    for (ReversiCell[] row : this.cells) {
      cellNum += row.length;
    }
    return cellNum;
  }

  @Override
  public Board copy() {
    ReversiCell[][] cellsCopy = new ReversiCell[this.cells.length][];
    for (int row = 0; row < this.cells.length; row++) {
      cellsCopy[row] = this.cells[row].clone();
    }
    return new HexBoard(this.boardSize, cellsCopy,
            new ArrayList<>(this.blackCells), new ArrayList<>(this.whiteCells));
  }

  // determines which of the given two cells is on the left. private because this functionality
  // is only relevant to this class. Just a helper that does not need to be in the interface.
  private ReversiCell getLeftCell(ReversiCell cell1, ReversiCell cell2) {
    if (cell1.getCoord('q') < cell2.getCoord('q')) {
      return cell1;
    } else if (cell1.getCoord('q') == cell2.getCoord('q')) {
      if (cell1.getCoord('r') < cell2.getCoord('r')) {
        return cell1;
      } else {
        return cell2;
      }
    } else {
      return cell2;
    }
  }

  // determines which of the given two cells is on the right. private because this functionality
  // is only relevant to this class. Just a helper that does not need to be in the interface.
  private ReversiCell getRightCell(ReversiCell cell1, ReversiCell cell2) {
    if (this.getLeftCell(cell1, cell2).equals(cell1)) {
      return cell2;
    } else {
      return cell1;
    }
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

  // checks that the given cell has valid coordinates within the board. Otherwise, throws an
  // exception. assumes and preserves invariant boardSize > 2. Private because this is only
  // relevant inside the board class.
  private void invalidCellException(ReversiCell cell) {
    if (cell.getCoord('q') > this.boardSize - 1
            || cell.getCoord('q') < (this.boardSize * -1) + 1
            || cell.getCoord('r') > this.boardSize - 1
            || cell.getCoord('r') < (this.boardSize * -1) + 1
            || cell.getCoord('s') > this.boardSize - 1
            || cell.getCoord('s') < (this.boardSize * -1) + 1) {
      throw new IllegalArgumentException("All coordinates within the cell must be between -" +
              this.boardSize + " and " + this.boardSize);
    }
  }
}
