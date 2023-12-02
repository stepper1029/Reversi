package cs3500.reversi.provider.strategy;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.provider.model.ReversiModel;

/**
 * <h3>ReversiStrategy Interface</h3>
 * This Interface encapsulates all the given Reversi strategies that give a player (or AI) a
 *     greater advantage to win. All strategies have the method findBestTurn(), which returns
 *     the {@link Coordinate} of the best turn for each Strategy class. The method takes in a
 *     {@link ReversiModel} and a {@link GamePieceColor}.
 */
public interface ReversiStrategy {

  /**
   * Finds the best turn available for the given strategy. This method will
   *     return the {@link Coordinate} of the tile that has the best move.
   * @param model the {@link ReadonlyReversiModel} of this Reversi game
   * @param gpc the {@link GamePieceColor} of the current user/AI
   * @return the Coordinate of the tile that has the best move given this Strategy
   * @throws IllegalArgumentException if the give GamePieceColor is Empty
   * @throws IllegalStateException if the model is null or the game has not started
   */
  Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException;
}
