package cs3500.reversi.model;

/**
 * Interface to represent a single cell in a Reversi board.
 */
public interface ReversiCell {

  /**
   * Checks if two cells are equal, if they have the same q, r, and s coordinates.
   * @param o that object to compare to.
   * @return if the two objects have the same coordinates.
   */
  boolean equals (Object o);

  /**
   * Returns a hash value for the object, must consistently
   * return the same value for the same object.
   * @return int representing hash code for the object.
   */
  int hashCode();

  /**
   * Observes the specified coordinate of this cell.
   * @param plane char which coordinate needs to be observed, ie 'r' for the horizontal plane in
   *              a hexagonal cell in a hexagonal board.
   * @throws IllegalArgumentException if the given char does not match the coordinate system of
   * the cell.
   * @return int coordinate of this cell on a specified plane
   */
  int getCoord(char plane);

  /**
   * Adds a vector to the cells coordinates to return a new cell that has moved one unit in
   * the given direction. Used to traverse through the board in various directions based on
   * a given starting cell. For a grid using q, r, s, the vector {-1, 0, 1} would return the
   * cell immediately to the left.
   * @param directions the array that holds the vector
   * @throws IllegalArgumentException if the direction vector is not the same length as the number
   *                                  of coordinates.
   * @return the cell immediately next to this cell in the given direction.
   */
  ReversiCell addVector(int[] directions);
}
