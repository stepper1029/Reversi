package cs3500.reversi.controller;

import cs3500.reversi.model.ReversiCell;

/**
 * A mock to test that the PlayActions and ModelFeatures interfaces work properly and the control
 * flow with the model, view, and players is correct.
 */
public class ListenerMock implements PlayerActions, ModelFeatures {

  public ListenerMock()

  @Override
  public void receiveTurnNotif() {

  }

  @Override
  public void receiveGameOverNotif() {

  }

  @Override
  public void receivePlace(ReversiCell cell) {

  }

  @Override
  public void receivePass() {

  }
}
