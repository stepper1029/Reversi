package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * A Reversi strategy that combines multiple strategies. It attempts to use the first strategy, and
 * if that returns multiple tied moves, it passes the list of good moves into the second strategy
 * and attempts to use that. If there are no good that combine both strategies, the best move
 * from the first strategy is chosen, and if that does not work, the best move from the second
 * strategy is chosen.
 */
public class CombineStrategies extends SimpleBreakTiesPassStrategy {
  // the two strategies combined in this object
  private final FallibleReversiStrategy strategyOne, strategyTwo;

  /**
   * Constructor to initialize the two strategies
   * @param strategyOne the first strategy to be tried
   * @param strategyTwo the second strategy to be tried
   */
  public CombineStrategies(FallibleReversiStrategy strategyOne,
                           FallibleReversiStrategy strategyTwo) {
    this.strategyOne = strategyOne;
    this.strategyTwo = strategyTwo;
  }

  /**
   * Attempts to find and return all possible moves that combine both strategies. If there are no
   * good moves to return that combine both strategies, then the good moves from
   * strategy one are returned. If that is empty, then the good moves from strategy two are
   * returned.
   */
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    List<ReversiCell> goodMoves = this.strategyTwo.allGoodMoves(model, player,
            this.strategyOne.allGoodMoves(model, player, possibleMoves));
    if (!goodMoves.isEmpty()) {
      return goodMoves;
    }
    else {
      goodMoves = this.strategyOne.allGoodMoves(model, player, model.allPossibleMoves(player));
    }

    if (!goodMoves.isEmpty()) {
      return goodMoves;
    }
    else {
      return this.strategyTwo.allGoodMoves(model, player, model.allPossibleMoves(player));
    }
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    List<ReversiCell> combinedMoves = this.strategyTwo.allGoodMoves(model, player,
            this.strategyOne.allGoodMoves(model, player, possibleMoves));

    if(!combinedMoves.isEmpty()) {
      return super.bestPotentialMove(model, player, possibleMoves);
    }
    else if (this.strategyOne.bestPotentialMove(model, player, possibleMoves).isPresent()) {
      return this.strategyOne.bestPotentialMove(model, player, possibleMoves);
    }
    else {
      return this.strategyTwo.bestPotentialMove(model, player, possibleMoves);
    }
  }

  @Override
  public boolean shouldPass(ReadOnlyModel model, DiskColor player) {
    return this.strategyOne.shouldPass(model, player) &&
            this.strategyTwo.shouldPass(model, player);
  }
}
