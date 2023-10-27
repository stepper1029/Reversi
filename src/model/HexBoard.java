package model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class HexBoard implements Board {
  int boardSize;
  List<List<ReversiCell>> cells;
  List<ReversiCell> blackCells;
  List<ReversiCell> whiteCells;

  public HexBoard(int boardSize) {
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
    this.boardSize = boardSize;
    this.cells = new ArrayList<>(Arrays.asList());
  }

  public List<List<ReversiCell>> getBoard() {
    for (int r = (this.boardSize - 1) * -1; r < this.boardSize; r++) {
      List<ReversiCell> row = new ArrayList<>(Arrays.asList());
      for (int q = (this.boardSize - 1) * -1; q <  this.boardSize; q++) {
        for(int s = this.boardSize - 1; s > (this.boardSize - 1) * -1; s--) {
          if (q + r + s == 0) {
            row.add(new HexCell(q, r, s));
          }
        }
      }
      cells.add(row);
    }
    return cells;
  }

  @Override
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    return cell.addVector(direction.getHexDirectionCoordinates());
  }

  @Override
  public List<ReversiCell> getRow(int numRow) {
    return this.cells.get(numRow);
  }

  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  @Override
  public int getNumTotalCells() {
      return this.cells.size();
  }

  @Override
  public boolean isBlack(ReversiCell c) {
    return this.blackCells.contains(c);
  }

  @Override
  public boolean isWhite(ReversiCell c) {
    return this.whiteCells.contains(c);
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return !this.isWhite(c) && !this.isBlack(c);
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
    }
    else {
      leftCell = cell2;
      rightCell = cell1;
    }
    if (leftCell.getCoord('q') == rightCell.getCoord('q')) {
      int q = leftCell.getCoord('q');
      for (int r = leftCell.getCoord('r') + 1, s  = leftCell.getCoord('s') - 1;
           r < rightCell.getCoord('r') && s > rightCell.getCoord('s'); r++, s--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    }
    else if (leftCell.getCoord('r') == rightCell.getCoord('r')) {
      int r = leftCell.getCoord('r');
      for (int q = leftCell.getCoord('q') + 1, s = leftCell.getCoord('s') - 1;
           q < rightCell.getCoord('q') && s > rightCell.getCoord('s'); q++, s--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    }
    else if (leftCell.getCoord('s') == rightCell.getCoord('s')) {
      int s = leftCell.getCoord('s');
      for (int q = leftCell.getCoord('q') + 1, r = leftCell.getCoord('r') - 1;
           q < rightCell.getCoord('q') && r > rightCell.getCoord('r'); q++, r--) {
        betweenCells.add(new HexCell(q, r, s));
      }
    }
    else {
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



  /**
   * Todo implement this method
   */
  private List<ReversiCell> getConnections(ReversiCell c) {
    return null;
  }

  private void invalidCellException(ReversiCell cell) {
    if (cell.getCoord('q') > this.boardSize -1
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
