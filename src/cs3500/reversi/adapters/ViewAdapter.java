package cs3500.reversi.adapters;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.provider.view.Message;
import cs3500.reversi.model.ReadOnlyModel;

public class ViewAdapter implements cs3500.reversi.view.ReversiView {

  // from theirs to ours
  // because their view needs to fit into our controller

  private cs3500.reversi.provider.view.ReversiView adaptee;
  private ReadOnlyModel model;

  public ViewAdapter(cs3500.reversi.provider.view.ReversiView adaptee) {
    this.adaptee = adaptee;

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
    throw new UnsupportedOperationException("Handled in the class");
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
