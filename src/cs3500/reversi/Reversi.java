package cs3500.reversi;

import cs3500.reversi.adapters.StrategyAdapter;
import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.provider.strategy.AvoidAdjacentCornerSpaces;
import cs3500.reversi.provider.strategy.MaximumCaptures;
import cs3500.reversi.provider.strategy.MinMaxStrategy;
import cs3500.reversi.provider.strategy.PlayCorner;
import cs3500.reversi.strategy.AvoidCornerAdjacent;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.CombineStrategies;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.MostPieces;
import cs3500.reversi.view.gui.GraphicalView;
import cs3500.reversi.view.gui.HexGUI;
import cs3500.reversi.view.gui.ReversiView;
import cs3500.reversi.view.gui.SquareGUI;

/**
 * Class Reversi to run the game.
 */
public class Reversi {

  /**
   * Main method for our Reversi game. Instantiates a game with a boardSize of 4 by default
   * with two human players. Creates two players, two views, and two controllers which all
   * are connected by one model. Board size can be specified using the first arg, args[1] and
   * args[2] can be used to specify the player types, either "human" or "strategy1", "strategy2",
   * "strategy3", "strategy4", to pick increasingly difficult and chained strategies. The default,
   * with no inputs to main is a board of size 4 with two human players.
   *
   * @param args input
   */
  public static void main(String[] args) {
    MutableModel model;
    ReversiView view1;
    ReversiView view2;
    //cs3500.reversi.provider.view.ReversiView view2;
    Player p1;
    Player p2;
    Controller controller1;
    Controller controller2;

    if (args.length == 0) {
      model = ReversiCreator.createSquare(6);
      view1 = new GraphicalView(model, DiskColor.Black, new SquareGUI(model, DiskColor.Black));
      view2 = new GraphicalView(model, DiskColor.White, new SquareGUI(model, DiskColor.White));
      p1 = new HumanPlayer(DiskColor.Black);
      p2 = new HumanPlayer(DiskColor.White);
    }
    else if (args.length == 1) {
      try {
        int boardSize = Integer.parseInt(args[0]);
        model = ReversiCreator.createHex(boardSize);
        view1 = new GraphicalView(model, DiskColor.Black, new HexGUI(model, DiskColor.Black));
        view2 = new GraphicalView(model, DiskColor.White, new HexGUI(model, DiskColor.White));
        p1 = new HumanPlayer(DiskColor.Black);
        p2 = new HumanPlayer(DiskColor.White);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("If there is only one argument, it must be the desired "
                + "board size. Board size must be at least 3.");
      }
    } else if (args.length == 3) {
      try {
        int boardSize = Integer.parseInt(args[0]);
        model = ReversiCreator.createHex(boardSize);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("First argument must be the desired board size. Board "
                + "size must be at least 3.");
      }
      view1 = new GraphicalView(model, DiskColor.Black, new HexGUI(model, DiskColor.Black));
      view2 = new GraphicalView(model, DiskColor.White, new HexGUI(model, DiskColor.Black));

      p1 = chooseStrategy(args[1], DiskColor.Black, model);
      if (args[2].contains("provider")) {
        p2 = chooseProviderStrategy(args[2], DiskColor.White, model);
      } else {
        p2 = chooseStrategy(args[2], DiskColor.White, model);
      }
    }
    else {
      throw new IllegalArgumentException("Invalid number of inputs.");
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

  private static Player chooseProviderStrategy(String arg, DiskColor color, MutableModel model) {
    Player player;
    switch (arg) {
      case "human":
        player = new HumanPlayer(color);
        break;
      case "providerStrategy1":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new StrategyAdapter(
                new MaximumCaptures())), model);
        break;
      case "providerStrategy2":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new StrategyAdapter(
                new AvoidAdjacentCornerSpaces(new MaximumCaptures()))), model);
        break;
      case "providerStrategy3":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new StrategyAdapter(
                new PlayCorner(new AvoidAdjacentCornerSpaces(new MaximumCaptures())))), model);
        break;
      case "providerStrategy4":
        player = new AIPlayer(color, new InfallibleReversiStrategy(new StrategyAdapter(
                new MinMaxStrategy(new PlayCorner(new AvoidAdjacentCornerSpaces(
                        new MaximumCaptures()))))), model);
        break;
      default:
        throw new IllegalArgumentException("invalid player type");
    }
    return player;
  }
}
