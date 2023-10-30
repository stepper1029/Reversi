package model;

import java.util.ArrayList;
import java.util.List;

public class BasicReversi implements MutableModel {

  DiscColor currColor;
  private int numPasses;
  Board board;

  public BasicReversi(Board board) {
    this.numPasses = 0;
    this.board = board;
    this.setBoard(board);
    this.currColor = DiscColor.Black;
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
  public int getScore(DiscColor color) {
    return this.board.getCells(color).size();
  }

  @Override
  public boolean isGameOver() {
    if (this.numPasses > DiscColor.values().length) {
      return true;
    }
    return false;
  }

  // will also delegate to the player interface to switch players.
  @Override
  public void pass() {
    this.numPasses += 1;
  }

  private void flipAll(List<ReversiCell> cells) {
    for (ReversiCell c : cells) {
      this.board.flipDisc(c);
    }
  }

  @Override
  public void place(ReversiCell cell) {
    if (this.allPosibleMoves().contains(cell)) {
      this.board.placeDisc(cell, this.currColor);
      for (ReversiCell connectingCell : this.getConnections(cell)) {
        this.flipAll(this.board.getCellsBetween(cell, connectingCell));
      }
    }
    else {
      throw new IllegalStateException("Invalid move");
    }
  }

  @Override
  public void setNextColor() {
    this.currColor = DiscColor.getnextColor(this.currColor);
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
    if (this.isEmpty(cell)) {
      throw new IllegalArgumentException("Cell is empty");
    }
    else if (this.board.getCells(DiscColor.Black).contains(cell)) {
      return DiscColor.Black;
    }
    else {
      return DiscColor.White;
    }
  }

  public boolean isEmpty(ReversiCell cell) {
    return this.board.isEmpty(cell);
  }

  public ReversiCell getCellAt(int numRow, int numCell) {
    return this.board.getRow(numRow)[numCell];
  }

  private boolean sameColor(ReversiCell cell1, ReversiCell cell2) {
    return (this.getColorAt(cell1).equals(this.getColorAt(cell2)));
  }

  // for a valid move, determines all connections to this cell that should be flipped
  private List<ReversiCell> getConnections(ReversiCell c) {
    ArrayList<ReversiCell> connections = new ArrayList<>();
    ReversiCell currCell = c;
    for (CellDirection direction : CellDirection.values()) {
      currCell = this.board.getNeighborCell(currCell, direction);
      while (!this.board.isEmpty(currCell) && !this.sameColor(c, currCell)) {
        try {
          currCell = this.board.getNeighborCell(currCell, direction);
          if (this.sameColor(currCell, c)) {
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
  private boolean validMoveInOneD(CellDirection direction, ReversiCell startingCell) {
    ReversiCell currCell= this.board.getNeighborCell(startingCell, direction);
    // returns false if the cell directly next to this one is empty
    if (this.board.isEmpty(currCell)) {
      return false;
    }
    while (!this.board.isEmpty(currCell) && !this.sameColor(startingCell, currCell)) {
      currCell = this.board.getNeighborCell(currCell, direction);
    }
    return !this.sameColor(startingCell, currCell);
  }

  // determines all valid moves from the given starting cell
  private List<ReversiCell> validMovesInAllDirections(ReversiCell startingCell) {
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
  public List<ReversiCell> allPosibleMoves() {
    List<ReversiCell> validMoves = new ArrayList<>();
    for(ReversiCell cell : this.board.getCells(this.currColor)) {
      validMoves.addAll(this.validMovesInAllDirections(cell));
    }
    return validMoves;
  }
}
