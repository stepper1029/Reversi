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
  SquareBoard(int boardSize, ReversiCell[][] cells, List<ReversiCell> blackCells,
           List<ReversiCell> whiteCells) {
    super(boardSize, cells, blackCells, whiteCells);
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
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    ReversiCell neighbor = cell.addVector(direction.getSquareDirectionCoordinates());
    this.invalidCellException(neighbor);
    return neighbor;
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
    if (cell1.getCoord('x') == cell2.getCoord('x')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.Down);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.Down);
      }
    }
    else if (cell1.getCoord('y') == cell2.getCoord('y')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.Right);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.Right);
      }
    }
    else if (cell1.getCoord('x') - cell2.getCoord('x')
            == cell1.getCoord('y') - cell2.getCoord('y')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.BottomRight);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.BottomRight);
      }
    }
    else if ((cell1.getCoord('x') - cell2.getCoord('x')) * -1
            == cell1.getCoord('y') - cell2.getCoord('y')) {
      currCell = this.getNeighborCell(leftCell, CellDirection.UpperRight);
      while (!currCell.equals(rightCell)) {
        betweenCells.add(currCell);
        currCell = this.getNeighborCell(currCell, CellDirection.UpperRight);
      }
    }
    else {
      throw new IllegalArgumentException("Given cells are not in a row and do not have " +
              "cells in between");
    }
    return betweenCells;
  }

  /**
   * Works the same as described in the abstract class, but if the x coordinates are the same,
   * the upper cell is returned (cell with a lower y value).
   */
  @Override
  protected ReversiCell getLeftCell(ReversiCell cell1, ReversiCell cell2) {
    if (cell1.getCoord('x') == cell2.getCoord('x')) {
      if (cell1.getCoord('y') <= cell2.getCoord('y')) {
        return cell1;
      }
      else {
        return cell2;
      }
    }
    else {
      if (cell1.getCoord('x') < cell2.getCoord('x')) {
        return cell1;
      }
      else {
        return cell2;
      }
    }
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

  @Override
  public Board copy() {
    ReversiCell[][] cellsCopy = new ReversiCell[this.cells.length][];
    for (int row = 0; row < this.cells.length; row++) {
      cellsCopy[row] = this.cells[row].clone();
    }
    return new SquareBoard(this.boardSize, cellsCopy,
            new ArrayList<>(this.blackCells), new ArrayList<>(this.whiteCells));
  }
}