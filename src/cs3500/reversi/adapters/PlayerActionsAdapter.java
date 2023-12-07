package cs3500.reversi.adapters;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.view.ViewFeatures;

public class PlayerActionsAdapter implements ViewFeatures {
  private PlayerActions adaptee;

  public PlayerActionsAdapter(PlayerActions adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void moveTurn(Coordinate coordinate) {

  }

  @Override
  public void passTurn(int playerNumber) {

  }
}