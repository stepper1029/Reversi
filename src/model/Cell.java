package model;

import java.util.Objects;

public class Cell {
  private final int q;
  private final int r;
  private final int s;

  public Cell(int q, int r, int s) {
    if(q + r + s != 0) {
      throw new IllegalArgumentException("q, r, and s must sum to 0");
    }
    this.q = q;
    this.r = r;
    this.s = s;
  }

  private Cell addCellDimensions(int[] directions) throws IllegalArgumentException {
    if (directions.length != 3) {
      throw new IllegalArgumentException("Directional array length should be 3");
    }
    return new Cell(this.q + directions[0], this.r + directions[1], this.s + directions[2]);
  }

  private Cell getNeighbor(CellDirection direction) {
    return this.addCellDimensions(direction.getDirection());
  }

 int getQ() {
    return this.q;
 }

  int getR() {
    return this.r;
  }

  int getS() {
    return this.s;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Cell cell = (Cell) o;
    return q == cell.q && r == cell.r && s == cell.s;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
  }
}