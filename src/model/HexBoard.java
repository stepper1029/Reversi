package model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class HexBoard implements Board {
  int boardSize;
  ReversiCell[][] cells;
  List<ReversiCell> blackCells;
  List<ReversiCell> whiteCells;

  public HexBoard(int boardSize) {
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
    this.blackCells = new ArrayList<>(Arrays.asList());
    this.whiteCells = new ArrayList<>(Arrays.asList());
    this.boardSize = boardSize;
    this.cells = new ReversiCell[(boardSize * 2) - 1][];
  }

  // todo TEST THIS!!!!!!!
  public ReversiCell[][] getBoard() {
    for (int outerArrayIndex = 0; outerArrayIndex < 2 * boardSize - 1; outerArrayIndex++) {
      int width = boardSize - 1;
      ReversiCell[] currRow;
      if (outerArrayIndex <= boardSize) {
        width++;
        currRow = new ReversiCell[width];
      } else {
        currRow = new ReversiCell[width];
        width--;
      }
      int innerArrayIndex = 0;

      for (int r = (this.boardSize - 1) * -1; r < this.boardSize; r++) {
        for (int q = (this.boardSize - 1) * -1; q < this.boardSize; q++) {
          for (int s = this.boardSize - 1; s > (this.boardSize - 1) * -1; s--) {
            if (q + r + s == 0 && innerArrayIndex < width) {
              currRow[innerArrayIndex] = new HexCell(q, r, s);
            }
          }
        }
      }
      cells[outerArrayIndex] = currRow;
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
    return cell.addVector(direction.getHexDirectionCoordinates());
  }

  @Override
  public ReversiCell[] getRow(int numRow) {
    return this.cells[numRow];
  }

  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  @Override
  public int getNumTotalCells() {
    return this.cells.length;
  }

  public DiscColor getColorAt(ReversiCell c) {
    if (this.isEmpty(c)) {
      throw new IllegalArgumentException("No color, is empty");
    } else if (this.blackCells.contains(c)) {
      return DiscColor.Black;
    } else {
      return DiscColor.White;
    }
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return !this.whiteCells.contains(c) && !this.blackCells.contains(c);
  }

  @Override
  public boolean sameColor(ReversiCell c1, ReversiCell c2) {
    return (this.blackCells.contains(c1) && this.blackCells.contains(c2)) ||
            (this.whiteCells.contains(c1) && this.whiteCells.contains(c2));
  }

  @Override
  public int getNumColor(DiscColor color) {
    if (color.equals(DiscColor.Black)) {
      return this.blackCells.size();
    } else {
      return this.whiteCells.size();
    }
  }

  /**
   * Todo separate into helper methods.
   */
  private List<ReversiCell> getDiscsBetweenCells(ReversiCell cell1, ReversiCell cell2) {
    ReversiCell leftCell;
    ReversiCell rightCell;
    List<ReversiCell> betweenCells = new ArrayList<>();
    if (cell1.getCoord('q') <= cell1.getCoord('q')) {
      leftCell = cell1;
      rightCell = cell2;
    } else {
      leftCell = cell2;
      rightCell = cell1;
    }
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
  public void placeDisc(ReversiCell c, DiscColor color) {
    this.invalidCellException(c);
    if (!this.isEmpty(c)) {
      throw new IllegalStateException("You can only place discs in empty cells.");
    } else if (color.equals(DiscColor.Black)) {
      this.blackCells.add(c);
    } else {
      this.whiteCells.add(c);
    }
  }

  public int getScore(DiscColor color) {
    return 0;
  }

  private void flipDisc(ReversiCell c) {
    invalidCellException(c);

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

  private void invalidCellException(ReversiCell cell) {
    if (cell.getCoord('q') > this.boardSize - 1
            || cell.getCoord('q') < this.boardSize * -1
            || cell.getCoord('r') > this.boardSize
            || cell.getCoord('r') < this.boardSize * -1
            || cell.getCoord('s') > this.boardSize
            || cell.getCoord('s') < this.boardSize * -1) {
      throw new IllegalArgumentException("All coordinates within the cell must be between -" +
              this.boardSize + " and " + this.boardSize);
    }
  }
}
