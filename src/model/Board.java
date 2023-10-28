package model;

import java.util.List;

public interface Board {
  ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);
  ReversiCell[] getRow(int numRow);
  int getBoardSize();
  DiscColor getColorAt(ReversiCell c);
  boolean isEmpty(ReversiCell c);
  void placeDisc(ReversiCell c, DiscColor color);
  boolean sameColor(ReversiCell c1, ReversiCell c2);
  int getNumColor(DiscColor color);
  int getScore(DiscColor color);
  List<ReversiCell> getInitPositions();
}
