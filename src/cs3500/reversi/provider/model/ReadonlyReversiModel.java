package cs3500.reversi.provider.model;

import java.util.List;

/**
 * <h3>ReadonlyReversiModel Interface</h3>
 * Represents a Reversi interface with only the observational methods. This allows the view
 *     to never be able to change the model, even accidentally.
 */
public interface ReadonlyReversiModel {

  /**
   * Gets the disc at the specified coordinate.
   * @return the {@link GamePieceColor} at the given coordinate
   * @throws IllegalArgumentException if the coordinate is invalid
   * @see GamePieceColor
   */
  GamePieceColor getColorAt(Coordinate coordinate)
          throws IllegalArgumentException;

  /**
   * Returns the score for the player of the given {@link GamePieceColor}. Scores are
   *     calculated by finding out how many pieces of that player are currently on
   *     the board.
   * @param gpc the GamePieceColor to check the score of
   * @return the {@code int} value of the score of the player of the given GamePieceColor
   */
  int getScoreForColor(GamePieceColor gpc);

  /**
   * Finds the list of all pieces of the given {@link GamePieceColor} currently in
   *     play on this game board.
   * @param gpc the GamePieceColor to find the list of
   * @return the List of all game pieces of the given GamePieceColor currently in play
   */
  List<Piece> getPiecesFromColor(GamePieceColor gpc);

  /**
   * Returns the game board as a list of all its {@link Piece}s.
   * @return the game board as a list of pieces
   */
  List<Piece> getBoard();

  /**
   * Gets the number of rows of this Reversi game.
   * @return the {@code int} value of the number of rows
   */
  int getNumRows();

  /**
   * Gets the maximum size of a row in this Reversi game.
   * @return the {@code int} value of the maximum row size
   */
  int getMaxSize();

  /**
   * Gets the total size of the game board.
   * @return the size of the game board of this Reversi model
   */
  int getBoardSize();

  /**
   * Checks if the move from the given {@link Coordinate}
   *     is valid with the given {@link GamePieceColor}.
   * @param coor the Coordinate to be checked
   * @param color the color of the current player
   * @return {@code true} if the move with the given parameters is valid
   */

  boolean isMoveValid(Coordinate coor, GamePieceColor color);

  /**
   * Returns the list of a list of all the correct runs from a {@link Coordinate}
   *     as specified by the model.
   * @return the list of all correct runs defined in the model
   */
  List<List<Piece>> returnRuns();


  /**
   * Detects whether this Reversi game is over or not based on if there are any empty pieces
   *     left on the board and if there are any valid turns left.
   * @return {@code true} if this Reversi game is over
   *
   */
  boolean isGameOver();

  /**
   * Checks if the given color has won this Reversi game. This method would be used only
   *     if the game is over, and it checks the size of the list of white pieces and
   *     black pieces to determine who won (larger size = win).
   * @param gpc the {@link GamePieceColor} to check
   * @return {@code true} if the given GamePieceColor won the game
   * @throws IllegalArgumentException if the GamePieceColor is Empty
   * @throws IllegalStateException if the game hasn't started yet.
   */
  boolean didColorWin(GamePieceColor gpc) throws IllegalArgumentException;

  /**
   * Checks if there are anymore moves left.
   * @param color the color to be checked
   * @return {@code true} if there are no more moves present
   */
  boolean noMoves(GamePieceColor color);
}
