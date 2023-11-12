package cs3500.reversi.strategy;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReversiCell;


public class MostPieces implements FallibleReversiStrategy {
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves) {
    int highestScore = 0;
    for (ReversiCell cell : model.allPossibleMoves(player)) {
      MutableModel modelCopy = model.copy();
      modelCopy.place(cell, player);
      if (modelCopy.getScore(player) > highestScore) {

      }
    }
    return null;
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    return null;
  }
}
