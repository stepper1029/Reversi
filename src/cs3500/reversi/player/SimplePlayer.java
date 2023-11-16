package cs3500.reversi.player;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.strategy.InfallibleReversiStrategy;

/**
 * Represents a simple player with a strategy all the functionality of the player interface.
 */
public class SimplePlayer implements Player {
  // represents the color of the disks corresponding to this player
  private final DiskColor color;
  // the strategy that this player is using
  InfallibleReversiStrategy strategy;

  /**
   * Constructor for a player.
   * @param color the color to assign to this player
   * @param strategy the strategy this player is using
   */
  public SimplePlayer(DiskColor color, InfallibleReversiStrategy strategy) {
    this.color = color;
    this.strategy = strategy;
  }

  @Override
  public Optional<ReversiCell> play(MutableModel model) {
    if (this.strategy.shouldPass(model, this.color)) {
      return Optional.empty();
    }
    else {
      return Optional.ofNullable(this.strategy.chooseMove(model, this.color));
    }
  }

  @Override
  public DiskColor getColor() {
    return this.color;
  }
}