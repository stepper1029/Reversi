package model;

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
}
