package cs3500.reversi.model;

import java.util.Objects;


/**
 * Class HexCell implements ReversiCell to represents a hexagonal cell. The class is package
 * private because it only needs to be accessed within the model.
 */
class HexCell implements ReversiCell {

  // Cubic coordinates (r, q, s) are used. See below for more details about each coordinate.
  // Each coordinate starts at the origin, which is (0,0,0) at the very center of a hexagonal
  // board. The first and last rows of a hexagonal boards are full sides of the hexagon (as
  // opposed to corners)

  // plane q runs from NW to SE, where the +q direction is NE. private final because any
  // observations should be made through observable methods and the reference should never be
  // changed.
  private final int q;

  // plane r runs from E to W, where the +r direction is S. private final because any
  // observations should be made through observable methods and the reference should never be
  // changed.
  private final int r;

  // plane s runs from NE to SW where the +s direction is NW. private final because any
  // observations should be made through observable methods and the reference should never be
  // changed.
  private final int s;

  // Constructor for the class, initialize q, r, and s to their passed in values.
  // Package private because anything outside the model should use the ReversiCell interface.
  // INVARIANT: q + r + s = 0 ensured by the constructor.
  HexCell(int q, int r, int s) {
    if (q + r + s != 0) {
      throw new IllegalArgumentException("q, r, and s must sum to 0");
    }
    this.q = q;
    this.r = r;
    this.s = s;
  }

  // assumes and preserves the class invariant r + q + s = 0
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

  // assumes and preserves class invariant r + q + s = 0
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

  // assumes and preserves class invariant r + q + s = 0
  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
  }

  // assumes and preserves class invariant r + q + s = 0
  @Override
  public ReversiCell addVector(int[] directions)
          throws IllegalArgumentException {
    if (directions.length != 3) {
      throw new IllegalArgumentException("Directional array length should be 3");
    }
    return new HexCell(this.q + directions[0], this.r
            + directions[1], this.s + directions[2]);
  }

  /**
   * toString method to make testing and debugging easier and so that the mock can add the r q s
   * value to the cell.
   *
   * @return string representing the q, r, s value of this hexCell
   */
  @Override
  public String toString() {
    return "Cell: q: " + this.q + " r: " + this.r + " s: " + this.s;
  }
 }