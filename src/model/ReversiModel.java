package model;

import java.util.List;

public interface ReversiModel {

  public boolean validMoveInOneD(CellDirection direction, ReversiCell startingCell);

  List<ReversiCell> validMovesInAllDirections(ReversiCell startingCell);

  List<ReversiCell> allPosibleMoves(DiscColor color);

  int getScore(DiscColor color);

  boolean isGameOver();

  void pass();

  void place(ReversiCell cell);
}