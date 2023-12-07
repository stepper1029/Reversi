package cs3500.reversi.adapters;

import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;

public class CellToPieceAdapter implements Piece {
  private ReversiCell adaptee;

  public CellToPieceAdapter(ReversiCell adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void setColor(GamePieceColor color) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Should not set the color of a piece in an adapter.");
  }

  @Override
  public GamePieceColor getColor() {
    return null;
  }

  @Override
  public Coordinate getCoordinate() {
    return ValueClassAdapters.;
  }
}
