package cs3500.reversi.strategy;

import cs3500.reversi.model.DiscColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Interface to represent which cell should be played next for the given player.
 */

public interface CellStrategy {
  ReversiCell chooseCell(MutableModel model, DiscColor color);
}