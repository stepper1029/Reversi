package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

class HexBoard implements Board {
  int boardSize;
  ReversiCell[][] cells;
  List<ReversiCell> blackCells;
  List<ReversiCell> whiteCells;

  HexBoard(int boardSize) {
    if (boardSize < 3) {
      throw new IllegalArgumentException("Board size must be at least 3");
    }
    this.blackCells = new ArrayList<>();
    this.whiteCells = new ArrayList<>();
    this.boardSize = boardSize;
    this.cells = this.getBoard();
  }

  // todo TEST THIS!!!!!!!
  ReversiCell[][] getBoard() {
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
          width --;
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

  @Override
  public ReversiCell[] getRow(int numRow) {
    return this.cells[numRow];
  }

  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  @Override
  public List<ReversiCell> getCells(DiscColor color) {
    switch (color) {
      case Black :
        return Collections.unmodifiableList(this.blackCells);
      case White:
        return Collections.unmodifiableList(this.whiteCells);
      default: throw new IllegalArgumentException("Invalid color");
    }
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return !this.whiteCells.contains(c) && !this.blackCells.contains(c);
  }

  @Override
  public List<ReversiCell> getCellsBetween (ReversiCell cell1, ReversiCell cell2) {
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
    for(ReversiCell[] row : this.cells) {
      cellNum += row.length;
    }
    return cellNum;
  }

  // determines which of the given two cells is on the left
  private ReversiCell getLeftCell(ReversiCell cell1, ReversiCell cell2) {
    if (cell1.getCoord('q') < cell2.getCoord('q')) {
      return cell1;
    }
    else if (cell1.getCoord('q') == cell2.getCoord('q')) {
      if (cell1.getCoord('r') < cell2.getCoord('r')) {
        return cell1;
      }
      else {
        return cell2;
      }
    }
    else {
      return cell2;
    }
  }

  // determines which of the given two cells is on the right
  private ReversiCell getRightCell(ReversiCell cell1, ReversiCell cell2) {
    if (this.getLeftCell(cell1, cell2).equals(cell1)) {
      return cell2;
    }
    else {
      return cell1;
    }
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

  @Override
  public void flipDisc(ReversiCell c) {
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
