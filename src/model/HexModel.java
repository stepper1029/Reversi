package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HexModel implements ReversiModel {

  private final int boardSize;
  private List<List<ReversiCell>> cells;
  private List<ReversiCell> blackDiscs;
  private List<ReversiCell> whiteDiscs;
  private int numPasses;

  public HexModel(int boardSize) {
    if (boardSize <= 0) {
      throw new IllegalArgumentException("Board size must be at least one.");
    }
    this.boardSize = boardSize;
    this.cells = getCells();
    this.blackDiscs = new ArrayList<>(Arrays.asList());
    this.whiteDiscs = new ArrayList<>(Arrays.asList());
    this.numPasses = 0;
  }

  public List<List<ReversiCell>> getCells() {
    List<List<ReversiCell>> board = new ArrayList<List<ReversiCell>>(Arrays.asList());

    for (int r = (this.boardSize - 1) * -1; r < this.boardSize; r++) {
      List<ReversiCell> row = new ArrayList<ReversiCell>(Arrays.asList());
      for(int q = (this.boardSize - 1) * -1; q < this.boardSize; q++) {
        for (int s = boardSize - 1; s > boardSize * -1; s --) {
          if (q + r + s == 0) {
            row.add(new HexCell(q, r, s));
          }
        }
      }
      board.add(row);
    }
    return board;
  }

  public int getBoardSize() {
    return this.boardSize;
  }

  public List<ReversiCell> getRow(int numRow) {
    return this.cells.get(numRow);
  }


  private ReversiCell addVector(ReversiCell cell, int[] directions)
          throws IllegalArgumentException {
    if (directions.length != 3) {
      throw new IllegalArgumentException("Directional array length should be 3");
    }
    return new HexCell(cell.getCoord('q') + directions[0], cell.getCoord('r') +
            directions[1], cell.getCoord('s') + directions[2]);
  }

  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    return this.addVector(cell, direction.getHexDirectionCoordinates());
  }

  private void setBoard() {
    if (this.boardSize > 2) {

      this.blackDiscs.add(new HexCell(0, -1, 1));
      this.blackDiscs.add(new HexCell(1, 0, -1));
      this.blackDiscs.add(new HexCell(-1, 1, 0));
      this.whiteDiscs.add(new HexCell(1, -1, 0));
      this.whiteDiscs.add(new HexCell(0, 1, -1));
      this.whiteDiscs.add(new HexCell(-1, 0, 1));
    }
  }

  public void startGame() {
    this.setBoard();
  }

  List<ReversiCell> getDiscsBetweenCells(ReversiCell cell1, ReversiCell cell2) {
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

  public boolean isBlack(ReversiCell c) {
    return this.blackDiscs.contains(c);
  }

  public boolean isWhite(ReversiCell c) {
    return this.whiteDiscs.contains(c);
  }

  public boolean isEmpty(ReversiCell c) {
    return !this.isWhite(c) && !this.isBlack(c);
  }
}
