package model;

import java.util.List;

public class BasicReversi implements ReversiModel {

  DiscColor currColor;
  private int numPasses;
  Board board;

  public BasicReversi(Board board) {
    this.numPasses = 0;
    this.board = board;
  }

  private void setBoard(Board board) {
    if (board.getBoardSize() > 2) {

      board.placeDisc(new HexCell(0, -1, 1), DiscColor.Black);
      board.placeDisc(new HexCell(1, 0, -1), DiscColor.Black);
      board.placeDisc(new HexCell(-1, 1, 0), DiscColor.Black);
      board.placeDisc(new HexCell(1, -1, 0), DiscColor.White);
      board.placeDisc(new HexCell(0, 1, -1), DiscColor.White);
      board.placeDisc(new HexCell(-1, 0, 1), DiscColor.White);
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
