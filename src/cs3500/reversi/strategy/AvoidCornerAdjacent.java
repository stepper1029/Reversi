package cs3500.reversi.strategy;

import java.util.List;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class AvoidCornerAdjacent extends SimpleBreakTiesPassStrategy {

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return null;
  }

  // checks if a cell is adjacent to a corner
  private boolean isAdjacentToCorner(ReversiCell cell, int maxBoardIndex) {
    if (cell.containsAllCoords(new int[] {0, maxBoardIndex - 1, (maxBoardIndex - 1) * -1})) {
      return true;
    }
    return false;
  }
}
