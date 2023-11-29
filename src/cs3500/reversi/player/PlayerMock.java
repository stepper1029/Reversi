package cs3500.reversi.player;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;

/**
 * Mock for keeping track of methods called on a player.
 */
public class PlayerMock implements Player {
  // color of this player
  private StringBuilder log;
  private Player player;

  public PlayerMock(StringBuilder log, Player player) {
    this.log = log;
    this.player = player;
  }

  @Override
  public DiskColor getColor() {
    return this.player.getColor();
  }

  @Override
  public void setListener(PlayerActions listener) {
    this.log.append("Adding a PlayerActions listener for the " + this.player.getColor() +
            " class\n");
    this.player.setListener(listener);
  }

  @Override
  public void play() {
    this.log.append("Making a move for " + this.player.getColor() + " player");
    this.player.play();
  }
}
