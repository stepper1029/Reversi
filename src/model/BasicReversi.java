package model;

import java.util.ArrayList;
import java.util.List;

public class BasicReversi implements ReversiModel {

  DiscColor currColor;
  private int numPasses;
  Board board;

  public BasicReversi(Board board) {
    this.numPasses = 0;
    this.board = board;
    this.setBoard(board);
  }

  private void setBoard(Board board) {
      DiscColor[] colors = DiscColor.values();
      List<ReversiCell> cells = board.getInitPositions();
      for (int i = 0; i < cells.size(); i ++) {
        ReversiCell cell = cells.get(i);
        DiscColor color = colors[i % colors.length];
        board.placeDisc(cell, color);
      }
    }

  @Override
  public boolean validMoveInOneD(CellDirection direction, ReversiCell startingCell) {
    return false;
  }

  @Override
  public List<ReversiCell> validMovesInAllDirections(ReversiCell startingCell) {
    return null;
  }

  @Override
  public List<ReversiCell> allPosibleMoves(DiscColor color) {
    return null;
  }

  @Override
  public int getScore(DiscColor color) {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    if (numPasses > DiscColor.values().length) {
      return true;
    }
    return false;
  }

  // will also delegate to the player interface to switch players.
  @Override
  public void pass() {
    this.numPasses += 1;
  }

  @Override
  public void place(ReversiCell cell) {
    board.placeDisc(cell, currColor);
  }

  public int getNumRows() {
    return (this.board.getBoardSize() * 2) - 1;
  }

  public int getRowSize(int row) {
    return this.board.getRow(row).length;
  }

  public int getBoardSize() {
    return this.board.getBoardSize();
  }

  public DiscColor getColorAt(ReversiCell cell) {
    return this.board.getColorAt(cell);
  }

  public boolean isEmpty(ReversiCell cell) {
    return this.board.isEmpty(cell);
  }

  public ReversiCell getCellAt(int numRow, int numCell) {
    return this.board.getRow(numRow)[numCell];
  }

  // for a valid move, determines all possible
  private List<ReversiCell> getConnections(ReversiCell c) {
    ArrayList<ReversiCell> connections = new ArrayList<>();
    ReversiCell currCell = c;
    for (CellDirection direction : CellDirection.values()) {
      currCell = this.board.getNeighborCell(currCell, direction);
      while (!this.board.isEmpty(currCell) && !this.board.sameColor(c, currCell)) {
        try {
          currCell = this.board.getNeighborCell(currCell, direction);
          if (this.board.sameColor(currCell, c)) {
            connections.add(currCell);
            break;
          }
        } catch (IllegalArgumentException e) {
          // do nothing because we want to continue through the for loop to
          // check the other directions
        }
      }
    }
    return connections;
  }

  // determines if there is a valid move in the given direction with the given starting cell
  public boolean validMoveInOneD(CellDirection direction, ReversiCell startingCell) {
    ReversiCell currCell= this.board.getNeighborCell(startingCell, direction);
    // returns false if the cell directly next to this one is empty
    if (this.board.isEmpty(currCell)) {
      return false;
    }
    while (!this.board.isEmpty(currCell) && !this.board.sameColor(startingCell, currCell)) {
      currCell = this.board.getNeighborCell(startingCell, direction);
    }
    return !this.board.sameColor(startingCell, currCell);
  }

  public List<ReversiCell> validMovesInAllDirections(ReversiCell startingCell) {
    List<ReversiCell> validMoves = new ArrayList<>();
    ReversiCell currCell;
    for (CellDirection direction : CellDirection.values()) {
      currCell = this.board.getNeighborCell(startingCell, direction);
      if (this.validMoveInOneD(direction, startingCell)) {
        while (!this.board.isEmpty(currCell)) {
          currCell = this.board.getNeighborCell(startingCell, direction);
        }
      }
      validMoves.add(currCell);
    }
    return validMoves;
  }

  @Override
  public List<ReversiCell> allPosibleMoves(DiscColor color) {
    return null;
  }
}
