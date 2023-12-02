package cs3500.reversi.provider.model;

import cs3500.reversi.provider.view.ViewFeatures;

/**
 * <h3>Player Interface</h3>
 * This Interface represents a Reversi Player. There are various getters and setters in this
 *     interface, as well as a method to run this Player's turn.
 */
public interface Player {

  /**
   * Retrieves the {@link GamePieceColor} of this Player.
   * @return the GamePieceColor of this Player
   */
  GamePieceColor getPlayerColor();

  /**
   * Sets the player's number.
   * This number is used for identification purposes, such as distinguishing
   * players in a multiplayer setting or for display in the user interface.
   *
   * @param playerNumber The player's identification number.
   */
  void setPlayerNumber(int playerNumber);

  /**
   * Gets this player's number.
   * @return the {@code int} value of this player's number
   */
  int getPlayerNumber();

  /**
   * Runs the turn for this Player. This is mostly designed for AI players who cannot depend
   *     on a user to run the turn for them.
   */
  void runTurn();

  /**
   * Adds a new {@link ViewFeatures} listener to this Reversi View if applicable.
   * @param listener the ViewFeatures to add to the list of all listeners
   */
  void addFeatures(ViewFeatures listener);
}
