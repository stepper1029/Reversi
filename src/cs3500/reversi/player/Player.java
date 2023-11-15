package cs3500.reversi.player;

import cs3500.reversi.model.DiskColor;

/**
 * Represents a player in the game of Reversi: can be a person or an AI player.
 */
public interface Player {

  /**
   * Returns the color of this
   * @return
   */
  DiskColor getColor();
}
