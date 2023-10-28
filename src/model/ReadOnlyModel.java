package model;

import java.awt.*;
import java.util.List;

public interface ReadOnlyModel {
  int getBoardSize();
  DiscColor getColorAt(ReversiCell cell);
  int getScore(DiscColor color);
  boolean isGameOver();
  List<ReversiCell> allPosibleMoves(DiscColor color);
  int getNumRows();
  int getRowSize(int row);
  ReversiCell getCellAt(int numRow, int numCell);
  boolean isEmpty(ReversiCell c);
}
