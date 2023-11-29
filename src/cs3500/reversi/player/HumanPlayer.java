package cs3500.reversi.player;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;

/**
 * Represents a human player in a Reversi game. The implementation for play is just empty because
 * a human player should interact through the view.
 */
public class HumanPlayer implements Player{
  // represents the color of the disks corresponding to this player
  private final DiskColor color;

  // represents the controller for this player
  private PlayerActions controller;

  /**
   * Constructor to initialize the color of this player.
   *
   * @param color DiskColor to set as the color for this player
   */
  public HumanPlayer(DiskColor color) {
    this.color = color;
  }

  @Override
  public DiskColor getColor() {
    return this.color;
  }

  @Override
  public void setListener(PlayerActions listener) {
    this.controller = listener;
  }

  @Override
  public void play() {
  }
}
