package model;

import java.util.List;

public interface Board {
  public ReversiCell[][] getBoard();
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);
  public ReversiCell[] getRow(int numRow);
  public int getBoardSize();
  public int getNumTotalCells();
  public DiscColor getColorAt(ReversiCell c);
  public boolean isEmpty(ReversiCell c);
  public void placeDisc(ReversiCell c, DiscColor color);
  public boolean sameColor(ReversiCell c1, ReversiCell c2);
  public int getNumColor(DiscColor color);
  public int getScore(DiscColor color);
  public List<ReversiCell> getInitPositions();
}
