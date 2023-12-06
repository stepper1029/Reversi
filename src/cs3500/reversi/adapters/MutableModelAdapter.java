package cs3500.reversi.adapters;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.ModelFeatures;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReversiModel;

public class MutableModelAdapter extends ReadOnlyModelAdapter implements ReversiModel {
  private MutableModel mutableAdaptee;

  public MutableModelAdapter(MutableModel model) {
    super(model);
    this.mutableAdaptee = model;
  }

  @Override
  public void startGame() throws IllegalArgumentException, IllegalStateException {
    this.mutableAdaptee.startGame();
  }

  @Override
  public void flipRun(List<Piece> run, GamePieceColor color) {

  }

  @Override
  public void flipDisc(Piece gp) {

  }

  @Override
  public void turn(Coordinate destCoordinate, GamePieceColor color) {
    ReversiCell cell = ValueClassAdapters.CoordinateToCell(destCoordinate, this.adaptee);
    if (ValueClassAdapters.gpcToDC(color).isPresent()) {
      DiskColor dc = ValueClassAdapters.gpcToDC(color).get();
      this.mutableAdaptee.place(cell, dc);
    }
  }

  @Override
  public void setIsGameOver(boolean b) {}

  @Override
  public GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException {
    Optional<DiskColor> dc = ValueClassAdapters.gpcToDC(color);
    if (dc.isPresent()) {
      this.mutableAdaptee.pass(dc.get());
      return ValueClassAdapters.dcToGPC(DiskColor.getNextColor(dc.get()));
    } else {
      throw new IllegalArgumentException("Empty cannot pass.");
    }
  }

  @Override
  public List<Piece> getBoardCopy() {
    return null;
  }

  @Override
  public void addListener(ModelFeatures listener) {

  }
}
