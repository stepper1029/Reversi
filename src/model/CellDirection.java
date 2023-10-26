package model;

enum CellDirection {
  Left (new int[]{-1, 0, 1}), Right (new int[]{1, 0, -1}),
  UpperLeft (new int[]{0, -1, 1}), UpperRight (new int[]{1, -1, 0}),
  BottomLeft (new int[]{-1, 1, 0}), BottomRight (new int[]{0, 1, -1});

  private final int[] directionCoordinates;

  CellDirection(int[] directionCoordinates) {
    this.directionCoordinates = directionCoordinates;
  }

  int[] getDirection() {
    return this.directionCoordinates;
  }
}