package cs3500.reversi.model;

/**
 * Interface to represent a single cell in a Reversi board.
 */
public interface ReversiCell {

  /**
   * Checks if two cells are equal, if they have the same q, r, and s coordinates. This is
   * in the interface because all Cell classes must override equals.
   *
   * @param o that object to compare to.
   * @return if the two objects have the same coordinates.
   */
  boolean equals(Object o);

  /**
   * Returns a hash value for the object, must consistently
   * return the same value for the same object. This is in the interface because all Cell classes
   * must override hashCode.
   *
   * @return int representing hash code for the object.
   */
  int hashCode();

  /**
   * Observes the specified coordinate of this cell. This is in the interface because every Cell
   * class should have an access point for the coordinates without having to access the fields
   * directly.
   *
   * @param plane char which coordinate needs to be observed, ie 'r' for the horizontal plane in
   *              a hexagonal cell in a hexagonal board.
   * @return      int coordinate of this cell on a specified plane
   * @throws IllegalArgumentException if the given char does not match the coordinate system of
   *                                  the cell.
   */
  int getCoord(char plane);

  /**
   * Adds a vector to the cells coordinates to return a new cell that has moved one unit in
   * the given direction. Used to traverse through the board in various directions based on
   * a given starting cell. For a grid using q, r, s, the vector {-1, 0, 1} would return the
   * cell immediately to the left. This is in the interface because all Cell classes must allow
   * this functionality in order to make the functionality of the model possible.
   *
   * @param directions the array that holds the vector
   * @return the cell immediately next to this cell in the given direction.
   * @throws IllegalArgumentException if the direction vector is not the same length as the number
   *                                  of coordinates.
   */
  ReversiCell addVector(int[] directions);

  /**
   * Determines whether the cell contains the given coordinate value as either q r or s. This is
   * used in the strategy and in containsAllCoords because the strategy makes moves based on
   * the location of coordinates on the board.
   *
   * @param coord coordinate value to see if it is one of the coordinate values of this cell
   * @return true if this coordinate is one of the coordinate values of this cell
   */
  boolean contains(int coord);

  /**
   * Determines whether the cell contains all coordinate values in the list of coordinates. This
   * is used in the strategy because corners and other positions are determined by the
   * combination of coordinates.
   *
   * @param coords array of coordinates to check if this cell contains
   * @return true if all values match are contained within this cell's coordinates and false if not
   */
  boolean containsAllCoords(int[] coords);
}
