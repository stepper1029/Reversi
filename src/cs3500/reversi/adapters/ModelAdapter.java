package cs3500.reversi.adapters;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.controller.ModelFeatures;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.provider.model.ReversiModel;


public class ModelAdapter implements ReversiModel {

  MutableModel adaptee;

  public ModelAdapter(MutableModel adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void startGame() {
    this.adaptee.startGame();
  }

  @Override
  public void flipRun(List<Piece> run, GamePieceColor color) { }

  @Override
  public void flipDisc(Piece gp) { }

  @Override
  public void turn(Coordinate destCoordinate, GamePieceColor color) {
    // FIXME : adapt Coordinate to ReversiCell???????
    this.adaptee.place(destCoordinate, color);
  }

  @Override
  public void setIsGameOver(boolean b) {
    this.adaptee.isGameOver();
  }

  @Override
  public GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException {
    if (color == )
    this.adaptee.pass(color);
    if (this.adaptee.getTurn() == DiskColor.Black) {
      return GamePieceColor.White;
    } else {
      return GamePieceColor.Black;
    }
  }

  @Override
  public List<Piece> getBoardCopy() {
    return null;
  }

  @Override
  public void addListener(cs3500.reversi.provider.model.ModelFeatures listener) {

  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public boolean isMoveValid(Coordinate coor, GamePieceColor color) {
    return false;
  }

  @Override
  public List<List<Piece>> returnRuns() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean didColorWin(GamePieceColor gpc) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean noMoves(GamePieceColor color) {
    return false;
  }

  @Override
  public GamePieceColor getColorAt(Coordinate coordinate) throws IllegalArgumentException {
    return null;
  }

  @Override
  public int getScoreForColor(GamePieceColor gpc) {
    return 0;
  }

  @Override
  public List<Piece> getPiecesFromColor(GamePieceColor gpc) {
    return null;
  }

  @Override
  public List<Piece> getBoard() {
    return null;
  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getMaxSize() {
    return 0;
  }
}