package cs3500.reversi.provider.strategy;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

/**
 * Represents a {@link ReversiStrategy} that tries two Strategies. The first strategy is
 *     tried first, while the second is used as a fallback in case the first strategy fails.
 */
public class TryTwoStrategies extends ReversiStrategyExceptions {

  private final ReversiStrategy strategy1;
  private final ReversiStrategy strategy2;

  /**
   * Constructs a new TryTwoStrategies with two parameters representing each strategy.
   * @param strategy1 the first {@link ReversiStrategy to try}
   * @param strategy2 the second ReversiStrategy that acts as a fallback
   */
  public TryTwoStrategies(ReversiStrategy strategy1, ReversiStrategy strategy2) {
    this.strategy1 = strategy1;
    this.strategy2 = strategy2;
  }

  @Override
  public Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException {
    super.emptyColorException(gpc);
    super.nullModelException(model);

    // Tries the first strategy, which has the potential to throw an IllegalArgumentException
    // if no moves are found.
    try {
      return strategy1.findBestTurn(model, gpc);
    }
    catch (IllegalArgumentException e) {
      // second strategy is returned if the first fails.
      return strategy2.findBestTurn(model, gpc);
    }
  }
}
