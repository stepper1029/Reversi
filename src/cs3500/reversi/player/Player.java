package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Represents a player in the game of Reversi: can be a person or an AI player.
 */
public interface Player {
  /**
   * Returns the move that the player wants to make or empty if the player is passing. The
   * value returned by this method will be handled in the controller.
   *
   * @param model a model to represent the current game
   * @return a ReversiCell representing where the player wants to move or empty if the player
   * wants to pass.
   */
  Optional<ReversiCell> play(MutableModel model);

  /**
   * Returns the color of the disk corresponding to this player.
   * @return DiskColor
   */
  DiskColor getColor();
}
