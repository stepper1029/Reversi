package cs3500.reversi.adapters;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;

public class ViewAdapter implements cs3500.reversi.view.ReversiView {

  // from theirs to ours
  // because their view needs to fit into our controller

  private cs3500.reversi.provider.view.ReversiView adaptee;

  public ViewAdapter(cs3500.reversi.provider.view.ReversiView adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void popUpError(String message) {

  }

  @Override
  public void gameOver() {

  }

  @Override
  public void addFeatures(PlayerActions playerActions) {

  }

  @Override
  public void update() {

  }

  @Override
  public void place(DiskColor color) {

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
