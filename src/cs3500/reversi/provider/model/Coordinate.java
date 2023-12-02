package cs3500.reversi.provider.model;

import java.util.Objects;

/**
 * <h3>Coordinate Class</h3>
 * Represents an axial Coordinate for each {@link GamePiece}.
 * Coordinates have an X and Y variable.
 * @implNote (0, 0) represents the center of the game, with +x being coordinates to the right of
 *     the center. +y would be coordinates below the center of the board.
 */
public class Coordinate {

  // represents the x-coordinate of this game piece
  private int x;
  // represents the y-coordinate of this game piece
  private int y;

  /**
   * Constructs a new Coordinate.
   * @param x
   *     The x-coordinate
   * @param y
   *     The y-coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Overriding the toString() method for displaying a Coordinate.
   * @return the String representation of this Coordinate
   */
  @Override
  public String toString() {
    return "Coordinate: (" + this.x + ", " + this.y + ")";
  }

  /**
   * Sets this x-coordinate to the given value.
   * @param x
   *     The x-value to be set
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Sets this y-coordinate to the given value.
   * @param y
   *     The y-value to be set
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Gets this x-coordinate.
   * @return this x-coordinate
   */
  public int getX() {
    return this.x;
  }

  /**
   * Gets this y-coordinate.
   * @return this y-coordinate
   */
  public int getY() {
    return this.y;
  }

  /**
   * Overriding the equals() method for Coordinate.
   * @param other the other Object to be checked
   * @return {@code true} if the given Object equals this Coordinate
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    else if (! (other instanceof Coordinate)) {
      return false;
    }
    else {
      Coordinate that = (Coordinate) other;
      return this.x == that.x && this.y == that.y;
    }
  }

  /**
   * Overriding the hashCode() method for Coordinate.
   * @return the hash value of this Coordinate
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
