package cs3500.reversi.strategy;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * A strategy for Reversi that will never fail. If it cannot find a move, it will throw an error.
 */
public class InfallibleReversiStrategy {
  private FallibleReversiStrategy strategyAttempt;

  /**
   * Constructor to initialize the strategy.
   *
   * @param strategyAttempt strategy to try
   */
  public InfallibleReversiStrategy(FallibleReversiStrategy strategyAttempt) {
    this.strategyAttempt = strategyAttempt;
  }

  /**
   * Determines if the player should pass or not.
   *
   * @param model the model to make moves based on
   *    * @param color the player to move for
   * @return true if the player should pass
   */
  public boolean shouldPass(ReadOnlyModel model, DiskColor color) {
    return this.strategyAttempt.shouldPass(model, color);
  }

  /**
   * Returns where the player should move based on the provided strategy.
   *
   * @param model the model to make moves based on
   * @param color the player to move for
   * @return the move to be made
   * @throws IllegalStateException if the strategy does not return a move
   */
  public ReversiCell chooseMove(ReadOnlyModel model, DiskColor color) {
    Optional<ReversiCell> move = this.strategyAttempt.bestPotentialMove(model, color,
            model.allPossibleMoves(color));
    if (move.isEmpty()) {
      throw new IllegalStateException("Strategy does not return a move");
    }
    return move.get();
  }
}