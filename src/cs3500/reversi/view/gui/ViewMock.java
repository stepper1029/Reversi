package cs3500.reversi.view.gui;

import java.awt.event.KeyListener;
import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.view.gui.BoardPanelMock;
import cs3500.reversi.view.gui.ReversiView;

/**
 * Mock for the view for testing purposes.
 */

public class ViewMock implements ReversiView {

  private final DiskColor color;
  private final StringBuilder log;
  private final ReadOnlyModel model;

  /**
   * Constructor with a log to create an instance of the mock view.
   * @param color disk color ( player who this view belongs to )
   * @param log stringbuilder to test interactions
   */
  public ViewMock(ReadOnlyModel model, DiskColor color, StringBuilder log) {
    this.color = color;
    this.log = log;
    this.model = model;
    new BoardPanelMock(model, color, log);
  }

  @Override
  public void makeVisible() {
    // no necessary to implement for the mock
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
  public void addFeatures(PlayerActions playerActions) {
    this.log.append("View for ").append(this.color).append(" is adding a new action listener\n");
    playerActions.receivePlace(this.model.getCellAt(2, 2));
    playerActions.receivePass();
  }

  private void addKeyListener(KeyListener keyListener) {
    // no necessary to implement for the mock
  }

  @Override
  public void update() {
    // no necessary to implement for the mock
  }

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
