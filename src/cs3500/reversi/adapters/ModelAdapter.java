package cs3500.reversi.adapters;

import java.util.List;

import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReversiModel;

public class ModelAdapter implements ReversiModel {
  @Override
  public void startGame() throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void flipRun(List<Piece> run, GamePieceColor color) {

  }

  @Override
  public void flipDisc(Piece gp) {

  }

  @Override
  public void turn(Coordinate destCoordinate, GamePieceColor color) {

  }

  @Override
  public void setIsGameOver(boolean b) {

  }

  @Override
  public GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException {
    return null;
  }

  @Override
  public List<Piece> getBoardCopy() {
    return null;
  }
}
