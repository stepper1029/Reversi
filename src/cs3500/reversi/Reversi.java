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
   * Main method for our Reversi game. Instantiates a game with a boardSize of 4 by default
   * with two human players. Creates two players, two views, and two controllers which all
   * are connected by one model. Board size can be specified using the first arg, args[1] and
   * args[2] can be used to specify the player types, either "human" or "strategy1", "strategy2",
   * "strategy3", "strategy4", to pick increasingly difficult and chained strategies.
   *
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

  private static Player chooseStrategy(String arg, DiskColor color, MutableModel model) {
    Player player;
    switch (arg) {
      case "human":
        player = new HumanPlayer(color);
        break;
      case "strategy1":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new MostPieces()), model);
        break;
      case "strategy2":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new CombineStrategies(
                new AvoidCornerAdjacent(), new MostPieces())), model);
        break;
      case "strategy3":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new CombineStrategies(
                new ChooseCorners(), new CombineStrategies(new AvoidCornerAdjacent(),
                new MostPieces()))), model);
        break;
      case "strategy4":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new CombineStrategies(
                new MiniMax(), new CombineStrategies(new ChooseCorners(), new CombineStrategies(
                new AvoidCornerAdjacent(), new MostPieces())))), model);
        break;
      default:
        throw new IllegalArgumentException("invalid player type");
    }
    return player;
  }
}
