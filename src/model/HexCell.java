package model;

import java.util.Objects;

class HexCell implements ReversiCell {
  private final int q;
  private final int r;
  private final int s;

  public HexCell(int q, int r, int s) {
    if (q + r + s != 0) {
      throw new IllegalArgumentException("q, r, and s must sum to 0");
    }
    this.q = q;
    this.r = r;
    this.s = s;
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HexCell cell = (HexCell) o;
    return q == cell.q && r == cell.r && s == cell.s;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
  }
}