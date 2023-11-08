package cs3500.reversi.strategy;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Interface to represent which cell should be played next for the given player.
 */

public interface CellStrategy {
  /**
   * Chooses a cell to place a disk in next based on
   * @param model
   * @param color
   * @returnf
   */
  ReversiCell chooseCell(ReadOnlyModel model, DiskColor color);
}