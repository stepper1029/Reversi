package cs3500.reversi.strategy;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class ChooseCorners extends SimpleBreakTiesPassStrategy {
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return possibleMoves.stream().filter(c ->
            this.isCorner(c, model.getBoardSize() - 1)).collect(Collectors.toList());
  }

  // determines if the given cell is at the corner
  private boolean isCorner(ReversiCell cell, int maxBoardIndex) {
    return cell.containsAllCoords(new int[]{0, maxBoardIndex, maxBoardIndex * -1});
  }
}
