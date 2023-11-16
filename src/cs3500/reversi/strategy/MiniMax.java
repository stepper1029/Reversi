package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Minimax strategy that tries to minimize the number of possible moves for the other player.
 */
public class MiniMax extends SimpleBreakTiesPassStrategy {

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    // map representing the number of moves possible for the other player
    Map<ReversiCell, Integer> otherPlayerPossibleMoves = new HashMap<>();
    // ensures that lowestMoves starts greater than the number of moves for the other player
    int lowestMoves = model.getNumRows() * model.getNumRows();
    int currNumMoves = 0;

    // finding the lowest possible moves for the other player
    for (ReversiCell cell : possibleMoves) {
      MutableModel modelCopy = model.copy();
      modelCopy.place(cell, player);
      currNumMoves = modelCopy.allPossibleMoves(DiskColor.getNextColor(DiskColor.Black)).size();
      if (currNumMoves < lowestMoves) {
        lowestMoves = currNumMoves;
      }
      otherPlayerPossibleMoves.put(cell, lowestMoves);
    }
    // must make highestScore final for the lambda
    final int finalLowestMoves = lowestMoves;
    Map<ReversiCell, Integer> goodMoves = otherPlayerPossibleMoves.entrySet().stream()
            .filter(possibleMoveVal -> possibleMoveVal.getValue() == finalLowestMoves)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return new ArrayList<>(goodMoves.keySet());
  }
}
