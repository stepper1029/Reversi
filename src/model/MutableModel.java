package model;

public interface MutableModel extends ReadOnlyModel {
  public void pass();
  public void place(ReversiCell cell);
  public void setNextColor();
}
