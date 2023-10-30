package model;

/**
 * Represents the color of a disc
 */
public enum DiscColor {
  /**
   * A disc can either be black or white.
   */
  Black, White;

  public static DiscColor getnextColor(DiscColor color) {
    int ordinal = color.ordinal();
    DiscColor[] values = DiscColor.values();
    if (ordinal == values.length - 1) {
      return values[1];
    }
    else {
      return values[ordinal + 1];
    }
  }
}
