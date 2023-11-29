package cs3500.reversi;

import javax.swing.text.View;

import cs3500.reversi.controller.Controller;
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
import cs3500.reversi.view.GraphicalView;
import cs3500.reversi.view.ReversiView;

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

  // views to use for testing
  protected ReversiView viewBlack;
  protected ReversiView viewWhite;

  // controllers to use for testing
  protected ModelFeatures controllerBlack;
  protected ModelFeatures controllerWhite;

  // players to use for testing
  protected Player playerWhite;
  protected Player playerBlack;

  // initializes the models
  protected void initModels() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
  }

  // initializes strategies
  protected void initStrategies() {
    this.initModels();
    this.mostPieces = new MostPieces();
    this.mostPiecesInfallible = new InfallibleReversiStrategy(this.mostPieces);
    this.chooseCorners = new ChooseCorners();
    this.avoidCornerAdjacent = new AvoidCornerAdjacent();
    this.minimax = new MiniMax();
  }

  // initializes fields for testing the strategy with the mock
  protected void initMockStrategies() {
    this.initStrategies();
    this.initStrategies();
    this.log = new StringBuilder();
    this.mock = new ModelMock(this.model3, false, this.log);
    this.lyingMock = new ModelMock(this.model3, true, this.log);
  }

  protected void initGameComponentsHuman() {
    this.initModels();
    this.playerBlack = new HumanPlayer(DiskColor.Black);
    this.playerWhite = new HumanPlayer(DiskColor.White);
    this.viewBlack = new GraphicalView(this.model4, DiskColor.Black);
    this.viewWhite = new GraphicalView(this.model4, DiskColor.White);
    this.controllerBlack = new Controller(this.model4, this.viewBlack, this.playerBlack);
    this.controllerWhite = new Controller(this.model4, this.viewWhite, this.playerWhite);
  }
}
