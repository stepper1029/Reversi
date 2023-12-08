package cs3500.reversi.adapters;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.strategy.ReversiStrategy;
import cs3500.reversi.strategy.FallibleReversiStrategy;


/**
 * Adapter class that adapts from their strategy to ours.
 */
public class StrategyAdapter implements FallibleReversiStrategy {

  // from theirs to ours
  // because their strategy needs to fit into our player

  private ReversiStrategy adaptee;

  public StrategyAdapter(ReversiStrategy adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return null;
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    try {
      Coordinate coord = this.adaptee.findBestTurn(new ReadOnlyModelAdapter(model),
              ValueClassAdapters.dcToGPC(player));
      return Optional.ofNullable(ValueClassAdapters.coordinateToCell(coord, model));
    } catch (IllegalStateException | IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean shouldPass(ReadOnlyModel model, DiskColor player) {
    return model.allPossibleMoves(player).isEmpty();
  }
}