package cs3500.reversi.strategy;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Strategy that tries to avoid moves adjacent to corners (either on an edge or in the middle of
 * the board).
 */
public class AvoidCornerAdjacent extends SimpleBreakTiesPassStrategy {

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return possibleMoves.stream().filter(c ->
            !this.isAdjacentToCorner(c,
                    model.getBoardSize() - 1)).collect(Collectors.toList());
  }

  // checks if a cell is adjacent to a corner
  private boolean isAdjacentToCorner(ReversiCell cell, int maxBoardIndex) {
    return cell.containsAllCoords(new int[] {0, maxBoardIndex - 1, (maxBoardIndex - 1) * -1}) ||
            (cell.contains(maxBoardIndex) && !cell.contains(0)) ||
            (cell.contains(maxBoardIndex * -1) && !cell.contains(0));
  }
}
