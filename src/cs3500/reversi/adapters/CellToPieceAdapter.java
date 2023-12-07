package cs3500.reversi.adapters;

import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;

public class CellToPieceAdapter implements Piece {
  private ReversiCell adaptee;
  private ReadOnlyModel model;

  public CellToPieceAdapter(ReversiCell adaptee, ReadOnlyModel model) {
    this.adaptee = adaptee;
    this.model = model;
  }

  @Override
  public void setColor(GamePieceColor color) throws IllegalArgumentException {
    throw new UnsupportedOperationException("Should not set the color of a piece in an adapter.");
  }

  @Override
  public GamePieceColor getColor() {
    if (this.model.isEmpty(this.adaptee)) {
      return GamePieceColor.Empty;
    }
    else {
      return ValueClassAdapters.dcToGPC(this.model.getColorAt(this.adaptee));
    }
  }

  @Override
  public Coordinate getCoordinate() {
    return ValueClassAdapters.cellToCoordinate(this.adaptee);
  }
}
