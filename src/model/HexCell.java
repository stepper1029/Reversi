package model;
import java.util.Objects;

public class HexCell implements ReversiCell {
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

  @Override
  public int getCoord(char plane) {
    switch (plane) {
      case 'q':
        return this.q;
      case 'r':
        return this.r;
      case 's':
        return this.s;
      default:
        throw new IllegalArgumentException("plane must be q r or s for a hexagonal grid");
    }
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

  @Override
  public ReversiCell addVector(int[] directions)
          throws IllegalArgumentException {
    if (directions.length != 3) {
      throw new IllegalArgumentException("Directional array length should be 3");
    }
    return new HexCell(this.q + directions[0], this.r +
            directions[1], this.s + directions[2]);
  }
}