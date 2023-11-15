package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.view.GraphicalView;
import cs3500.reversi.view.ReversiView;

public class Reversi {
  public static void main(String[] args) {
    MutableModel model = ReversiCreator.create(3);
    ReversiView view = new GraphicalView(model);
    Controller controller = new Controller(model, view);
  }
}
