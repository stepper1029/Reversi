package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.strategy.InfallibleReversiStrategy;

/**
 * Represents an AI player in a game of Reversi.
 */
public class AIPlayer implements Player {
  // represents the color of the disks corresponding to this player
  private final DiskColor color;
  // the strategy that this player is using
  InfallibleReversiStrategy strategy;

  /**
   * Constructor for a player.
   * @param color the color to assign to this player
   * @param strategy the strategy this player is using
   */
  public AIPlayer(DiskColor color, InfallibleReversiStrategy strategy) {
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