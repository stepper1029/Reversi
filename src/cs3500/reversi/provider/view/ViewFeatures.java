package cs3500.reversi.provider.view;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;

/**
 * <h3>ViewFeatures Interface</h3>
 * This interface represents all of the callbacks that can happen to the {@link ReversiView}.
 *     In this case, there are only two we need to notify listeners about - when a player chooses
 *     to move or decides to pass their turn.
 */
public interface ViewFeatures {

  /**
   * Called if the player has indicated they want to move into the selected tile.
   * @param coordinate the {@link Coordinate} the player has selected
   */
  void moveTurn(Coordinate coordinate);

  /**
   * Called if the player has indicated they want to pass their turn.
   * @param playerNumber the {@code int} value of the current player's number. This corresponds
   *                     with the player's {@link GamePieceColor}.
   */
  void passTurn(int playerNumber);
}
