package cs3500.reversi.model;

/**
 * Enum CellDirection holds all the directions in which a single cell can be connected to
 * neighboring cells.
 */
enum CellDirection {
  // Enum values for all directions of cells and their direction vectors for a hexagonal cell
  // to move one cell over in the corresponding direction
  Left(new int[]{-1, 0, 1}), Right(new int[]{1, 0, -1}),
  UpperLeft(new int[]{0, -1, 1}), UpperRight(new int[]{1, -1, 0}),
  BottomLeft(new int[]{-1, 1, 0}), BottomRight(new int[]{0, 1, -1});

  // field to represent the direction vectors of hexagonal coordinates (these values
  // can be added to a cell to receive the directly adjacent neighbor cell in the
  // corresponding direction) represented as an array of length 3, where the first value
  // is the q vector, the second value is the r vector, and the third value is the s vector`
  private final int[] hexDirectionCoordinates;

  /**
   * Constructor to initialize hexDirectionCoordinates.
   *
   * @param hexDirectionCoordinates direction vector for hexagonal cells
   */
  CellDirection(int[] hexDirectionCoordinates) {
    this.hexDirectionCoordinates = hexDirectionCoordinates;
  }

  /**
   * Gets the hexDirectionCoordinates of this CellDirection.
   *
   * @return array of three ints that represent the direction vector to move one cell over
   */
  int[] getHexDirectionCoordinates() {
    return this.hexDirectionCoordinates;
  }
}