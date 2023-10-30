package model;

import java.util.List;

interface Board {
  ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);
  List<ReversiCell> getCells(DiscColor color);
  boolean isEmpty(ReversiCell c);
  ReversiCell[] getRow(int numRow);
  int getBoardSize();
  void placeDisc(ReversiCell c, DiscColor color);
  List<ReversiCell> getInitPositions();
  void flipDisc(ReversiCell c);
  List<ReversiCell> getCellsBetween (ReversiCell cell1, ReversiCell cell2);
}
