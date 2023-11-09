package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class BackupStrategy implements FallibleReversiStrategy {

  private final FallibleReversiStrategy strategyOne, strategyTwo;

  public BackupStrategy(FallibleReversiStrategy strategyOne, FallibleReversiStrategy strategyTwo) {
    this.strategyOne = strategyOne;
    this.strategyTwo = strategyTwo;
  }

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return null;
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    return Optional.empty();
  }
}
