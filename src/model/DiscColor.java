package model;

/**
 * Represents the color of a disc.
 */
public enum DiscColor {
  /**
   * A disc can either be black or white.
   */
  Black, White;

  /**
   * Returns the next DiscColor in the order of colors within this enum
   * @param color the current color
   * @return the next DiscColor in the order of colors
   */
  public static DiscColor getnextColor(DiscColor color) {
    int ordinal = color.ordinal();
    DiscColor[] values = DiscColor.values();
    if (ordinal == values.length - 1) {
      return values[0];
    }
    else {
      return values[ordinal + 1];
    }
  }
}
