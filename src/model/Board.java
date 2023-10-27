package model;

import java.util.List;

public interface Board {
  public List<List<ReversiCell>> getBoard();
  public ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);
  public List<ReversiCell> getRow(int numRow);
  public int getBoardSize();
  public int getNumTotalCells();
  public boolean isBlack(ReversiCell c);
  public boolean isWhite(ReversiCell c);
  public boolean isEmpty(ReversiCell c);
  public void placeDisc(ReversiCell c, DiscColor color);
}
