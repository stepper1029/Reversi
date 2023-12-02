package cs3500.reversi.provider.model;

import java.awt.Color;

/**
 * <h3>GamePieceColor Enumeration</h3>
 * Represents a disc of the game with two colors; Black and White. There is an Empty color
 *     as well, which represents an empty piece of the game board. This Enumeration also
 *     has the ability to return the Color representation of each GamePieceColor.
 * @see ReversiModel
 * @see Color
 */
public enum GamePieceColor {
  Black(Color.BLACK),  // Enum constant for Black with color black
  White(Color.WHITE),  // Enum constant for White with color white
  Empty(Color.GRAY); // Representation of an empty game piece

  private final Color color;  // Color associated with the enum constant

  /**
   * Constructs a new GamePieceColor with the given {@link Color} value.
   * @param color the Color that this GamePieceColor will be
   */
  GamePieceColor(Color color) {
    this.color = color;
  }

  /**
   * Gets the color associated with this GamePieceColor.
   * @return the {@link Color} representation of this GamePieceColor
   */
  public Color getColor() {
    return color;
  }
}
