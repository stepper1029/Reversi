package cs3500.reversi.model;

import java.util.List;

public class AbstractBoard implements Board {


  // represents the number of cells that make up one side length of the board. Private field
  // because any observations on the board should be made through the model. Final because the
  // reference should not be changed.
  // INVARIANT: boardSize > 2
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

  @Override
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction) {
    return null;
  }

  @Override
  public List<ReversiCell> getCells(DiskColor color) {
    return null;
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return false;
  }

  @Override
  public ReversiCell[] getRow(int numRow) {
    return new ReversiCell[0];
  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public void placeDisk(ReversiCell c, DiskColor color) {

  }

  @Override
  public List<ReversiCell> getInitPositions() {
    return null;
  }

  @Override
  public void flipDisk(ReversiCell c) {

  }

  @Override
  public List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2) {
    return null;
  }

  @Override
  public int getTotalNumCells() {
    return 0;
  }

  @Override
  public Board copy() {
    return null;
  }
}
