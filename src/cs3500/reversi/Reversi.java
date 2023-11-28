package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AvoidCornerAdjacent;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.CombineStrategies;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.MostPieces;
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
    MutableModel model = ReversiCreator.create(4);
    ReversiView view1 = new GraphicalView(model, DiskColor.Black);
    ReversiView view2 = new GraphicalView(model, DiskColor.White);
    Player p1 = new HumanPlayer(DiskColor.Black);
    Player p2 = new HumanPlayer(DiskColor.White);
    Controller controller1;
    Controller controller2;

    if (args.length == 1) {
      try {
        int boardSize = Integer.parseInt(args[0]);
        model = ReversiCreator.create(boardSize);
      } catch (NumberFormatException e) {
        System.err.println("If there is only one argument, it must be the desired board size");
      }
    } else if (args.length == 3) {
      try {
        int boardSize = Integer.parseInt(args[0]);
        model = ReversiCreator.create(boardSize);
      } catch (NumberFormatException e) {
        System.err.println("First argument must be the desired board size");
      }

      p1 = chooseStrategy(args[1], DiskColor.Black, model);
      p2 = chooseStrategy(args[2], DiskColor.White, model);
    }

    controller1 = new Controller(model, view1, p1);
    controller2 = new Controller(model, view2, p2);
    model.startGame();
  }
}
