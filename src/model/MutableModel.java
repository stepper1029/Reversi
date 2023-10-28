package model;

import java.util.List;

public interface MutableModel {
  public void pass();
  public void place(ReversiCell cell);
  public List<ReversiCell> allPossibleMoves();
}
