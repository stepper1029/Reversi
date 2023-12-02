package cs3500.reversi.provider.model;

import java.util.List;

/**<h3>ReversiModel Interface</h3>
 * Represents an interface for a Reversi model.
 *     This model controls the logic of the Reversi game.
 *     There are various methods that control this logic, such as startGame() and moveDisc().
 * @see GamePieceColor
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Starts this Reversi game with the given parameters.
   * @throws IllegalArgumentException with invalid parameters
   * @throws IllegalStateException if the game has already started
   */
  void startGame()
          throws IllegalArgumentException, IllegalStateException;

  /**
  * Flips a run with the correct color disc after the {@link Piece} is moved.
  * @param run the List of {@link Piece} to flip the colors of
  * @param color the {@link GamePieceColor} to flip the run color to.
  */
  void flipRun(List<Piece> run, GamePieceColor color);

  /**
   * Flips this {@link GamePieceColor} disk's color.
   * @param gp
   *     The GamePiece to be flipped
   * @see GamePieceColor
   */
  void flipDisc(Piece gp);

  /**
   * Represents a player turn. A player turn causes pieces to be captured by the player.
   * @param destCoordinate the intended destination coordinate
   * @param color the color of the tile that is being added
   */
  void turn(Coordinate destCoordinate, GamePieceColor color);

  /**
   * Sets isGameOver to the given boolean. This method is used for testing purposes.
   * @param b {@code boolean} to set the game over state of this Reversi game to
   */
  void setIsGameOver(boolean b);


  /**
   * Passes a turn by returning the opposite color of the given color.
   * @param color the color to be flipped
   * @return the {@link GamePieceColor} of the flipped color
   * @throws IllegalArgumentException if the given GamePieceColor is Empty
   */
  GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException;

  /**
   * Creates a copy of the game board and returns it.
   * @return a copy of the game board as a {@code List<Piece>}
   */
  List<Piece> getBoardCopy();

  /**
   * Adds listeners to this model. The model will notify the listener when something interesting
   *     happens, like for instance if a user turn is complete.
   * @param listener the {@link ModelFeatures} listener to be added to a list of ModelFeatures
   */
  void addListener(ModelFeatures listener);

}
