package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReversiCell;



public class MostPieces implements FallibleReversiStrategy {
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves) {
    ArrayList<ReversiCell> goodMoves = new ArrayList<>();
    int highestScore = 0;
    for (ReversiCell cell : possibleMoves) {
      MutableModel modelCopy = model.copy();
      modelCopy.place(cell, player);
      if (modelCopy.getScore(player) > highestScore) {
        highestScore = modelCopy.getScore(player);
        goodMoves.add(cell);
      }
      else if (modelCopy.getScore(player) == highestScore) {
        goodMoves.add(cell);
      }
    }
    int finalHighestScore = highestScore;
    return goodMoves.stream()
            .filter(cell -> {
              MutableModel modelCopy = model.copy();
              modelCopy.place(cell, player);
              return modelCopy.getScore(player) == finalHighestScore;
            })
            .collect(Collectors.toList());
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    List<ReversiCell> sortedMoves = this.allGoodMoves(model, player, possibleMoves);
    sortedMoves.sort(new TopLeftComparator());
    if (sortedMoves.isEmpty()) {
      return Optional.empty();
    }
    else {
      return Optional.ofNullable(sortedMoves.get(0));
    }
  }
}
