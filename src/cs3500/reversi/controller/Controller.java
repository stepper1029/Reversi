package cs3500.reversi.controller;

import cs3500.reversi.view.ViewFeatures;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.view.ReversiView;

public class Controller implements ViewFeatures {
  private final MutableModel model;
  private final ReversiView view;

  public Controller(MutableModel model, ReversiView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
  }

  public void go() {
    this.view.makeVisible();
  }

  public void pass() {

  }

  @Override
  public void place() {

  }

}
