package cs3500.reversi.model;

import java.util.Objects;

/**
 * Class SquareCell implements ReversiCell and represents a cell on a square game board.
 */
class SquareCell implements ReversiCell {
  final int x;
  final int y;

  /**
   * Constructor take in the coordinates, x and y.
   * @param x horizontal coordinate, +x to the right.
   * @param y vertical coordinate, +y down.
   */
  public SquareCell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int getCoord(char plane) {
    if (plane == ('x')) {
      return this.x;
    } else if (plane == ('y')) {
      return this.y;
    } else {
      throw new IllegalArgumentException("not a valid coordinate for a square cell");
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
    SquareCell cell = (SquareCell) o;
    return x == cell.x && y == cell.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public ReversiCell addVector(int[] directions)
          throws IllegalArgumentException {
    if (directions.length != 2) {
      throw new IllegalArgumentException("Directional array length should be 2");
    }
    return new SquareCell(this.x + directions[0], this.y + directions[1]);
  }

  @Override
  public boolean contains(int coord) {
    return this.x == coord || this.y == coord;
  }

  @Override
  public boolean containsAllCoords(int[] coords) {
    if (coords.length != 2) {
      throw new IllegalArgumentException("A SquareCell contains 2 coordinates, only given " +
              coords.length);
    }
    for (int coord : coords) {
      if (!this.contains(coord)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public char getHorizontalComparisonCoord() {
    return 'x';
  }

  @Override
  public char getVerticalComparisionCoord() {
    return 'y';
  }

  @Override
  public String toString() {
    return String.format("SquareCell: %d, %d", this.x, this.y);
  }
}
