package cs3500.reversi.adapters;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.provider.view.Message;
import cs3500.reversi.model.ReadOnlyModel;

/**
 * Adapter for the view from our provider's view to ours.
 */
public class ViewAdapter implements cs3500.reversi.view.ReversiView {

  // from theirs to ours
  // because their view needs to fit into our controller

  private cs3500.reversi.provider.view.ReversiView adaptee;
  private ReadOnlyModel model;

  /**
   * Constructor to initialize the adaptee and model fields.
   * @param adaptee provider's view
   * @param model a copy of our model
   */
  public ViewAdapter(cs3500.reversi.provider.view.ReversiView adaptee, ReadOnlyModel model) {
    this.adaptee = adaptee;
    this.model = model;

  }

  @Override
  public void makeVisible() {
    this.adaptee.render();
  }

  @Override
  public void popUpError(String message) {
    if (message.contains("Invalid move")) {
      this.adaptee.displayPanel(Message.InvalidMove);
    }
  }

  @Override
  public void gameOver() {
    this.adaptee.displayPanel(Message.GameWon);
  }

  @Override
  public void addFeatures(PlayerActions playerActions) {
    this.adaptee.addFeatures(new PlayerActionsAdapter(playerActions, model));
  }

  @Override
  public void update() {
    this.adaptee.render();
  }

  @Override
  public void place(DiskColor color) {
    //
  }

  @Override
  public Optional<Integer> getSelectedX() {
    throw new UnsupportedOperationException("Not necessary for their view");
  }

  @Override
  public Optional<Integer> getSelectedY() {
    throw new UnsupportedOperationException("Not necessary for their view");
  }
}
