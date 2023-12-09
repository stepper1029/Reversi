package cs3500.reversi.strategy;

import java.util.List;
import java.util.stream.Collectors;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Strategy that favors moves that are corners (there are six corners in a hexagon).
 */
public class ChooseCorners extends SimpleBreakTiesPassStrategy {
  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player,
                                        List<ReversiCell> possibleMoves) {
    return possibleMoves.stream().filter(model::isCorner).collect(Collectors.toList());
  }
}
