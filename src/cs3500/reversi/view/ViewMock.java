package cs3500.reversi.view;

import java.awt.event.KeyListener;
import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;

public class ViewMock implements ReversiView {

  private final DiskColor color;
  private final StringBuilder log;

  public ViewMock(DiskColor color, StringBuilder log) {
    this.color = color;
    this.log = log;
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void popUpError(String message) {
    log.append(" New message: \"").append(message).append("\" on ").append(this.color)
            .append("'s view.");
  }

  @Override
  public void gameOver() {
    log.append("Display game over.\n");
  }

  @Override
  public void addFeatures(PlayerActions playerActions) {}

  @Override
  public void update() {}

  @Override
  public void place(DiskColor color) {
    log.append("placed");
  }

  @Override
  public Optional<Integer> getSelectedX() {
    return Optional.empty();
  }

  @Override
  public Optional<Integer> getSelectedY() {
    return Optional.empty();
  }
}
