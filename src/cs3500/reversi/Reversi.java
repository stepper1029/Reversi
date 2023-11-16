package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.view.GraphicalView;
import cs3500.reversi.view.ReversiView;

/**
 * Class Reversi to run the game.
 */
public class Reversi {

  /**
   * main method for our Reversi game. Instantiates a game with a boardSize of 3 by default.
   * @param args input
   */
  public static void main(String[] args) {
    MutableModel model = ReversiCreator.create(3);
    ReversiView view = new GraphicalView(model);
    Controller controller = new Controller(model, view);
  }
}
