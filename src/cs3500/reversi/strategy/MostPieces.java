package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReversiCell;

/**
 * Strategy that chooses the move that flips the most disks for the given player. Breaks ties by
 * choosing the top-most left-most choice. Uses the SimpleBreakTiesPassStrategy to choose the
 * best move and to decide when to pass.
 */
public class MostPieces extends SimpleBreakTiesPassStrategy {
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves) {
    // map representing the numerical values of each move
    Map<ReversiCell, Integer> moveVals = new HashMap<>();
    int highestScore = 0;
    int currScore = 0;

    // finding the highest possible score based on the possible moves (therefore the move that
    // flips the most pieces
    for (ReversiCell cell : possibleMoves) {
      MutableModel modelCopy = model.copy();
      modelCopy.place(cell, player);
      currScore = modelCopy.getScore(player);
      if (currScore > highestScore) {
        highestScore = currScore;
      }
      moveVals.put(cell, currScore);
    }
    // must make highestScore final for the lambda
    final int finalHighScore = highestScore;
    Map<ReversiCell, Integer> goodMoves = moveVals.entrySet().stream()
            .filter(moveVal -> moveVal.getValue() == finalHighScore)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return new ArrayList<>(goodMoves.keySet());
  }
}
