package cs3500.reversi.adapters;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.view.ViewFeatures;

/**
 * Adapts the player actions interface.
 */
public class PlayerActionsAdapter implements ViewFeatures {
  private PlayerActions adaptee;
  private ReadOnlyModel model;

  /**
   * Constructor for the class.
   * @param adaptee our inter face to be adapted.
   * @param model our model.
   */
  public PlayerActionsAdapter(PlayerActions adaptee, ReadOnlyModel model) {
    this.adaptee = adaptee;
    this.model = model;
  }

  @Override
  public void moveTurn(Coordinate coordinate) {
    this.adaptee.receivePlace(ValueClassAdapters.coordinateToCell(coordinate, model));
  }

  @Override
  public void passTurn(int playerNumber) {
    this.adaptee.receivePass();
  }
}