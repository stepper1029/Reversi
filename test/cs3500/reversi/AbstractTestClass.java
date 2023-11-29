package cs3500.reversi;

import javax.swing.text.View;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.model.ModelMock;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AvoidCornerAdjacent;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.FallibleReversiStrategy;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.MostPieces;

public abstract class AbstractTestClass {
  // model fields for testing
  protected MutableModel model3;
  protected MutableModel model4;

  // strategy fields to test
  protected FallibleReversiStrategy mostPieces;
  protected InfallibleReversiStrategy mostPiecesInfallible;
  protected FallibleReversiStrategy chooseCorners;
  protected FallibleReversiStrategy avoidCornerAdjacent;
  protected FallibleReversiStrategy minimax;

  // fields for testing the strategy with the mock
  protected ModelMock mock;
  protected ModelMock lyingMock;
  protected StringBuilder log;

  // controllers to use for testing
  protected ModelFeatures controllerBlack;
  protected ModelFeatures controllerWhite;

  // views to use for testing
  protected View view1;
  protected View view2;

  // players to use for testing
  protected Player playerWhite;
  protected Player playerBlack;

  // initializes the models
  protected void initModelsPlayersControllers() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
    this.playerBlack = new HumanPlayer(DiskColor.Black);
    this.playerWhite = new HumanPlayer(DiskColor.White);
  }

  // initializes strategies
  protected void initStrategies() {
    this.initModelsPlayersControllers();
    this.mostPieces = new MostPieces();
    this.mostPiecesInfallible = new InfallibleReversiStrategy(this.mostPieces);
    this.chooseCorners = new ChooseCorners();
    this.avoidCornerAdjacent = new AvoidCornerAdjacent();
    this.minimax = new MiniMax();
  }

  // initializes fields for testing the strategy with the mock
  protected void initMockStrategies() {
    this.initModelsPlayersControllers();
    this.initStrategies();
    this.log = new StringBuilder();
    this.mock = new ModelMock(this.model3, false, this.log);
    this.lyingMock = new ModelMock(this.model3, true, this.log);
  }
}
