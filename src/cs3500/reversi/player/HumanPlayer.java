package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;

public class HumanPlayer implements Player{
  @Override
  public Optional<ReversiCell> play(MutableModel model) {
    return Optional.empty();
  }

  @Override
  public DiskColor getColor() {
    return null;
  }
}
