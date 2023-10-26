package model;

public interface ReversiCell {
  boolean equals (Object o);
  public int hashCode();
  public int getCoord(char plane);
  public ReversiCell addVector(int[] directions);
}
