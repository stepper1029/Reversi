package cs3500.reversi.controller;

import cs3500.reversi.model.ReversiCell;

/**
 * Actions a player can take during the game, currently limited to placing and passing.
 */
public interface PlayerActions {
  /**
   * Notifies the listener that the player wants to place a disk at the given cell location.
   *
   * @param cell the cell to place the disk in.
   */
  void receivePlace(ReversiCell cell);

  /**
   * Notifies the listener that the given player wants to pass.
   */
  void receivePass();
}
