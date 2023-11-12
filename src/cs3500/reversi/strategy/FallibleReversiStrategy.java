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
  List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
                                      possibleMoves);
  Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves);
}