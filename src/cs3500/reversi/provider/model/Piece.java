package cs3500.reversi.provider.model;

/**
 * <h3>Piece Interface</h3>
 * Represents a piece of this Reversi game. Each piece has methods to set the color,
 *     get the color, and get the coordinate.
 */
public interface Piece {

  /**
   * Sets the color of this Piece to a new {@link GamePieceColor}.
   * @param color
   *     the color to be changed
   * @throws IllegalArgumentException if the color is {@code null}
   */
  void setColor(GamePieceColor color) throws IllegalArgumentException;

  /**
   * Gets the color of this Piece.
   * @return the {@link GamePieceColor} of this Piece
   */
  GamePieceColor getColor();

  /**
   * Gets the coordinates of this Piece.
   * @return the {@link Coordinate} of this GamePiece
   */
  Coordinate getCoordinate();
}
