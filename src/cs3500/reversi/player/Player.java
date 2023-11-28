package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Represents a player in the game of Reversi: can be a person or an AI player.
 */
public interface Player {
  /**
   * Returns the color of the disk corresponding to this player.
   *
   * @return DiskColor
   */
  DiskColor getColor();

  /**
   * Sets this object's listener to the given PlayerActions listener.
   *
   * @param listener PlayerActions listener to initialize the field within this object
   */
  void setListener(PlayerActions listener);

  /**
   * Called by the controller to notify the player that it's their turn.
   */
  void play();
}
