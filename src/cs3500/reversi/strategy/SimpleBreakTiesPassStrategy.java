package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Represents simple strategies that choose the best potential move by breaking ties using the
 * TopLeftComparator and passes only if the model does not have any valid moves for the given
 * player. This is an abstract class because many strategies use the same logic for these two
 * functionalities.
 */
public abstract class SimpleBreakTiesPassStrategy implements FallibleReversiStrategy {
  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    List<ReversiCell> sortedMoves = this.allGoodMoves(model, player, possibleMoves);

    if (sortedMoves.isEmpty()) {
      return Optional.empty();
    } else if (sortedMoves.size() == 1) {
      return Optional.ofNullable(sortedMoves.get(0));
    } else {
      sortedMoves.sort(new TopLeftComparator());
      return Optional.ofNullable(sortedMoves.get(0));
    }
  }

  @Override
  public boolean shouldPass(ReadOnlyModel model, DiskColor player) {
    return model.allPossibleMoves(player).isEmpty();
  }
}
