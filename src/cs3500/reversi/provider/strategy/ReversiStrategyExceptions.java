package strategies;

import model.GamePieceColor;
import model.ReadonlyReversiModel;

/**
 * <h3>ReversiStrategyExceptions Abstract Class</h3>
 * This Abstract Class is in charge of some the methods that could cause a strategy to crash.
 *     This would include checking to make sure the model isn't empty, as well as making sure
 *     that the passed in {@link GamePieceColor} isn't Empty.
 */
abstract class ReversiStrategyExceptions implements ReversiStrategy {

  /**
   * Checks if the passed in {@link GamePieceColor} is Empty. Empty colors are not valid for the
   *     findBestTurn() method above.
   * @param gpc the GamePieceColor to check.
   * @throws IllegalArgumentException if the given GamePieceColor is Empty.
   */
  protected void emptyColorException(GamePieceColor gpc) throws IllegalArgumentException {
    if (gpc.equals(GamePieceColor.Empty)) {
      throw new IllegalArgumentException("Game piece color cannot be empty");
    }
  }

  /**
   * Checks if the passed in model is {@code null} and throws an IllegalArgumentException if it is.
   * @param model the passed-in model to check
   * @throws IllegalArgumentException if the given model is {@code null}.
   */
  protected void nullModelException(ReadonlyReversiModel model) throws IllegalStateException {
    if (model == null) {
      throw new IllegalStateException("Model cannot be null");
    }
  }
}
