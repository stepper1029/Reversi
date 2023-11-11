package cs3500.reversi.strategy;

import java.util.Optional;

import javax.swing.text.html.Option;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class InfallibleReversiStrategy {
  FallibleReversiStrategy strategyAttempt;

  public InfallibleReversiStrategy(FallibleReversiStrategy strategyAttempt) {
    this.strategyAttempt = strategyAttempt;
  }
  public ReversiCell chooseMove(ReadOnlyModel model, DiskColor color) {
    Optional<ReversiCell> move = this.strategyAttempt.bestPotentialMove(model, color,
            model.allPossibleMoves(color));
    if (move.isEmpty()) {
      throw new IllegalStateException("Strategy does not return a move");
    }
    return move.get();
  }
}
