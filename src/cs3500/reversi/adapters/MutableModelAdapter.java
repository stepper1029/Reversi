package cs3500.reversi.adapters;

import java.util.List;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.ModelFeatures;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReversiModel;

/**
 * Adapts our mutable model interfaces.
 */
public class MutableModelAdapter extends ReadOnlyModelAdapter implements ReversiModel {
  private MutableModel mutableAdaptee;

  public MutableModelAdapter(MutableModel model) {
    super(model);
    this.mutableAdaptee = model;
  }

  @Override
  public void startGame() throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void flipRun(List<Piece> run, GamePieceColor color) {
    throw new UnsupportedOperationException("Our method is private");
  }

  @Override
  public void flipDisc(Piece gp) {
    throw new UnsupportedOperationException("Our method is private");
  }

  @Override
  public void turn(Coordinate destCoordinate, GamePieceColor color) {
    ReversiCell cell = ValueClassAdapters.coordinateToCell(destCoordinate, this.adaptee);
    if (ValueClassAdapters.gpcToDC(color).isPresent()) {
      DiskColor dc = ValueClassAdapters.gpcToDC(color).get();
      this.mutableAdaptee.place(cell, dc);
    }
  }

  @Override
  public void setIsGameOver(boolean b) {
    throw new UnsupportedOperationException("Used for testing purposes, not relevant");
  }

  @Override
  public GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Piece> getBoardCopy() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addListener(ModelFeatures listener) {
    throw new UnsupportedOperationException();
  }
}
