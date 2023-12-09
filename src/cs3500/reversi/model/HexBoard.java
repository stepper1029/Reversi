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
class HexBoard extends AbstractBoard {

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
    super(boardSize);
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
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
  HexBoard(int boardSize, ReversiCell[][] cells, List<ReversiCell> blackCells,
                  List<ReversiCell> whiteCells) {
    super(boardSize, cells, blackCells, whiteCells);
  }

  // assumes and preserves field invariant boardSize > 2
  // generates the cells within the board by position, starting at the top left of the board
  // and working left to right, top to bottom. private because no other class needs to generate
  // a board of cells.
  protected ReversiCell[][] getBoard() {
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
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    ReversiCell neighbor = cell.addVector(direction.getHexDirectionCoordinates());
    this.invalidCellException(neighbor);
    return neighbor;
  }

  @Override
  public List<ReversiCell> getInitPositions() {
    return new ArrayList<>(Arrays.asList(new HexCell(0, -1, 1),
            new HexCell(1, -1, 0), new HexCell(1, 0, -1),
            new HexCell(0, 1, -1), new HexCell(-1, 1, 0),
            new HexCell(-1, 0, 1)));
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

  @Override
  public List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2) {
    ReversiCell leftCell;
    ReversiCell rightCell;
    List<ReversiCell> betweenCells = new ArrayList<>();
    leftCell = this.getLeftCell(cell1, cell2);
    if(!leftCell.equals(cell1)) {
      rightCell = cell1;
    }
    else {
      rightCell = cell2;
    }
    ReversiCell currCell;
    if (leftCell.getCoord('q') == rightCell.getCoord('q')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.BottomRight);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.BottomRight);
      }
    } else if (leftCell.getCoord('r') == rightCell.getCoord('r')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.Right);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.Right);
      }
    } else if (leftCell.getCoord('s') == rightCell.getCoord('s')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.UpperRight);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.UpperRight);
      }
    } else {
      throw new IllegalArgumentException("Given cells are not in a row and do not have " +
              "cells in between");
    }
    return betweenCells;
  }

  @Override
  protected ReversiCell getLeftCell(ReversiCell cell1, ReversiCell cell2) {
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

  // checks that the given cell has valid coordinates within the board. Otherwise, throws an
  // exception. assumes and preserves invariant boardSize > 2. Private because this is only
  // relevant inside the board class.
  protected void invalidCellException(ReversiCell cell) {
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
