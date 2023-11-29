package cs3500.reversi.controller;

import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.player.Player;

public class ListenerMock implements PlayerActions, ModelFeatures {
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
