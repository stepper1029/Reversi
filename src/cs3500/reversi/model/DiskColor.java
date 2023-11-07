package cs3500.reversi.model;

/**
 * Represents the color of a disk. Public enum because the number of players, or colors,
 * present in the game will be needed by the controller and view.
 */
public enum DiskColor {
  /**
   * A disk can either be black or white.
   */
  Black, White;

  /**
   * Returns the next DiskColor in the order of colors within this enum. Package private because
   * the model needs to be able to switch the current color but the view and controller should
   * not have that permission.
   *
   * @param color the current color
   * @return the next DiskColor in the order of colors
   */
  static DiskColor getNextColor(DiskColor color) {
    int ordinal = color.ordinal();
    DiskColor[] values = DiskColor.values();
    if (ordinal == values.length - 1) {
      return values[0];
    } else {
      return values[ordinal + 1];
    }
  }
}
