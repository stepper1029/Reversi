package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Represents a human player in a Reversi game. Takes user input rather than computing a strategy.
 */
public class HumanPlayer implements Player{
  // represents the color of the disks corresponding to this player
  private final DiskColor color;

  public HumanPlayer(DiskColor color) {
    this.color = color;
  }
  @Override
  public Optional<ReversiCell> play(MutableModel model) {
    return Optional.empty();
  }

  @Override
  public DiskColor getColor() {
    return null;
  }
}
