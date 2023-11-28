package cs3500.reversi;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MostPieces;
import cs3500.reversi.view.GraphicalView;
import cs3500.reversi.view.ReversiView;

public class Reversi2 {
  public static void main(String[] args) {
    MutableModel model = ReversiCreator.create(4);
    ReversiView viewPlayer1 = new GraphicalView(model, DiskColor.Black);
    ReversiView viewPlayer2 = new GraphicalView(model, DiskColor.White);
    Player player1 = new HumanPlayer(DiskColor.Black);
    Player player2 = new AIPlayer(DiskColor.White, new InfallibleReversiStrategy(new MostPieces()),
            model);
    Controller controller1 = new Controller(model, viewPlayer1, player1);
    Controller controller2 = new Controller(model, viewPlayer2, player2);
    model.startGame();
  }
}
