package cs3500.reversi.model;

/**
 * Enum CellDirection holds all the directions in which a single cell can be connected to
 * neighboring cells. Package-private because the constructor and other methods only need to be
 * accessed by the board.
 */
enum CellDirection {
  // Enum values for all directions of cells and their direction vectors for a hexagonal cell
  // to move one cell over in the corresponding direction
  Left(new int[]{-1, 0, 1}, new int[]{-1, 0}), Right(new int[]{1, 0, -1}, new int[]{1, 0}),
  UpperLeft(new int[]{0, -1, 1}, new int[]{-1, -1}),
  UpperRight(new int[]{1, -1, 0}, new int[]{1, -1}),
  BottomLeft(new int[]{-1, 1, 0}, new int[]{-1, 1}),
  BottomRight(new int[]{0, 1, -1}, new int[]{1, 1}),
  Up(new int[]{0, 0, 0}, new int[]{0, -1}), Down(new int[]{0, 0, 0}, new int[]{0, 1});

  // field to represent the direction vectors of hexagonal coordinates (these values
  // can be added to a cell to receive the directly adjacent neighbor cell in the
  // corresponding direction) represented as an array of length 3, where the first value
  // is the q vector, the second value is the r vector, and the third value is the s vector
  private final int[] hexDirectionCoordinates;

  // field to represent the direction vectors of square coordinates and work the same way as
  // the hexDirectionCoordinates described above. Represented as an array of length 2, where the
  // first value is the x vector and the second value is the y vector
  private final int[] squareDirectionCoordinates;

  /**
   * Constructor to initialize hexDirectionCoordinates.
   *
   * @param hexDirectionCoordinates direction vector for hexagonal cells
   */
  CellDirection(int[] hexDirectionCoordinates, int[] squareDirectionCoordinates) {
    this.hexDirectionCoordinates = hexDirectionCoordinates;
    this.squareDirectionCoordinates = squareDirectionCoordinates;
  }

  /**
   * Gets the hexDirectionCoordinates of this CellDirection.
   *
   * @return array of three ints that represent the direction vector to move one cell over
   */
  int[] getHexDirectionCoordinates() {
    return this.hexDirectionCoordinates;
  }

  /**
   * Gets the squareDirectionCoordinates of this CellDirection.
   *
   * @return array of 2 ints that represent the direction vector to move one cell over
   */
  int[] getSquareDirectionCoordinates() {
    return this.squareDirectionCoordinates;
  }
}