package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HexagonalReversi {

  private int boardSize;
  private List<List<Cell>> cells;
  private List<Cell> blackDiscs;
  private List<Cell> whiteDiscs;

  public HexagonalReversi(int boardSize) {
    if (boardSize <= 0) {
      throw new IllegalArgumentException("Board size must be at least one.");
    }
    this.boardSize = boardSize;
    this.cells = getCells();
    this.blackDiscs = new ArrayList<>(Arrays.asList());
    this.whiteDiscs = new ArrayList<>(Arrays.asList());
  }

  public List<List<Cell>> getCells() {
    List<List<Cell>> board = new ArrayList<List<Cell>>(Arrays.asList());

    for (int r = (this.boardSize - 1) * -1; r < this.boardSize; r++) {
      List<Cell> row = new ArrayList<Cell>(Arrays.asList());
      for(int q = (this.boardSize - 1) * -1; q < this.boardSize; q++) {
        for (int s = boardSize - 1; s > boardSize * -1; s --) {
          if (q + r + s == 0) {
            row.add(new Cell(q, r, s));
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

  public List<Cell> getRow(int numRow) {
    return this.cells.get(numRow);
  }

  private void setBoard() {
    if (this.boardSize > 2) {

      this.blackDiscs.add(new Cell(0, -1, 1));
      this.blackDiscs.add(new Cell(1, 0, -1));
      this.blackDiscs.add(new Cell(-1, 1, 0));
      this.whiteDiscs.add(new Cell(1, -1, 0));
      this.whiteDiscs.add(new Cell(0, 1, -1));
      this.whiteDiscs.add(new Cell(-1, 0, 1));
    }
  }

  public void startGame() {
    this.setBoard();
  }

  List<Cell> getDiscsBetweenCells(Cell cell1, Cell cell2) {
    Cell leftCell;
    Cell rightCell;
    List<Cell> betweenCells = new ArrayList<>();
    if (cell1.getQ() <= cell1.getQ()) {
      leftCell = cell1;
      rightCell = cell2;
    }
    else {
      leftCell = cell2;
      rightCell = cell1;
    }
    if (leftCell.getQ() == rightCell.getQ()) {
      int q = leftCell.getQ();
      for (int r = leftCell.getR() + 1, s  = leftCell.getS() - 1;
           r < rightCell.getR() && s > rightCell.getS(); r++, s--) {
        betweenCells.add(new Cell(q, r, s));
      }
    }
    else if (leftCell.getR() == rightCell.getR()) {
      int r = leftCell.getR();
      for (int q = leftCell.getQ() + 1, s = leftCell.getS() - 1;
           q < rightCell.getQ() && s > rightCell.getS(); q++, s--) {
        betweenCells.add(new Cell(q, r, s));
      }
    }
    else if (leftCell.getS() == rightCell.getS()) {
      int s = leftCell.getS();
      for (int q = leftCell.getQ() + 1, r = leftCell.getR() - 1;
           q < rightCell.getQ() && r > rightCell.getR(); q++, r--) {
          betweenCells.add(new Cell(q, r, s));
      }
    }
    else {
      throw new IllegalArgumentException("Given cells are not in a row and do not have " +
              "cells in between");
    }
    return betweenCells;
  }

  public boolean isBlack(Cell c) {
    return this.blackDiscs.contains(c);
  }

  public boolean isWhite(Cell c) {
    return this.whiteDiscs.contains(c);
  }

  public boolean isEmpty(Cell c) {
    return !this.isWhite(c) && !this.isBlack(c);
  }
}
