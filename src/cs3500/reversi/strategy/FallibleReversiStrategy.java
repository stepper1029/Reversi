package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Interface to represent which cell should be played next for the given player.
 */

public interface FallibleReversiStrategy {
  /**
   * Determines all the best moves based on this strategy. Represented as a list because there
   * could be ties. Allows strategies to be composed by passing in a list of all possible moves
   * from another strategy.
   *
   * @param model the current model to make moves based on
   * @param player the player to make a move for
   * @param possibleMoves the list of moves to choose from
   * @return a list of all good moves based on this strategy
   */
  List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
                                      possibleMoves);

  /**
   * Determines the best potential move out of the given moves based on this strategy. Breaks ties
   * by using choosing the top-most left-most option.
   *
   * @param model the current model to make moves based on
   * @param player the player to make a move for
   * @param possibleMoves the list of moves to choose from
   * @return the best move based on this strategy and the given possible moves or empty if
   *         there are no good moves with this strategy
   */
  Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves);
}