package model;

public interface ReadOnlyModel {
  public int getBoardSize();
  public DiscColor getColorAt(ReversiCell cell);
  public int getScore();
  public boolean isGameOver();
}
