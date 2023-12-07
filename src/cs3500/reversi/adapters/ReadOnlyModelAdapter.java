package cs3500.reversi.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.DiskColor;

public class ReadOnlyModelAdapter implements ReadonlyReversiModel {

  // from ours to theirs
  // our model needs to fit into their view/strategy

  protected ReadOnlyModel adaptee;

  public ReadOnlyModelAdapter(ReadOnlyModel adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public GamePieceColor getColorAt(Coordinate coordinate) throws IllegalArgumentException {
    ReversiCell cell = ValueClassAdapters.coordinateToCell(coordinate, adaptee);
    DiskColor color = this.adaptee.getColorAt(cell);
    return ValueClassAdapters.dcToGPC(color);
  }

  @Override
  public int getScoreForColor(GamePieceColor gpc) {
//    if (gpc.equals(GamePieceColor.Empty)) {
//      return 0;
//    } else {
//      return this.adaptee.getScore(ValueClassAdapters.gpcToDC(gpc).get());
//    }
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Piece> getPiecesFromColor(GamePieceColor gpc) {
//    List<Piece> color = new ArrayList<>(Arrays.asList());
//    for(Piece p : this.getBoard()) {
//      if (p.getColor().equals(gpc)) {
//        color.add(p);
//      }
//    }
//    return color;
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Piece> getBoard() {
    List<Piece> list = new ArrayList<>(Arrays.asList());
    for (int row = 0; row < this.adaptee.getNumRows(); row++ ) {
      for (int col = 0; col < this.adaptee.getRowSize(row); col++ ) {
        ReversiCell cell = this.adaptee.getCellAt(row, col);
        Piece p = new CellToPieceAdapter(cell, this.adaptee);
        list.add(p);
      }
    }
    return list;
  }

  @Override
  public int getNumRows() {
    return this.adaptee.getNumRows();
  }

  @Override
  public int getMaxSize() {
    return this.adaptee.getRowSize((this.adaptee.getNumRows() - 1) / 2);
  }

  @Override
  public int getBoardSize() {
    int count = 0;
    for (int numRow = 0; numRow < this.getNumRows(); numRow ++) {
      count += this.adaptee.getRowSize(numRow);
    }
    return count;
  }

  @Override
  public boolean isMoveValid(Coordinate coor, GamePieceColor color) {
    ReversiCell cell = ValueClassAdapters.coordinateToCell(coor, this.adaptee);
    if (ValueClassAdapters.gpcToDC(color).isPresent()) {
      List<ReversiCell> list = this.adaptee.allPossibleMoves(
              ValueClassAdapters.gpcToDC(color).get());
      for (ReversiCell c : list) {
        if(c.equals(cell)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public List<List<Piece>> returnRuns() {
    throw new UnsupportedOperationException("Our method is private.");
  }

  @Override
  public boolean isGameOver() {
//    return this.adaptee.isGameOver();
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean didColorWin(GamePieceColor gpc) throws IllegalArgumentException {
//    return this.adaptee.getWinner().equals(ValueClassAdapters.gpcToDC(gpc));
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean noMoves(GamePieceColor color) {
    if (ValueClassAdapters.gpcToDC(color).isPresent()) {
      return this.adaptee.allPossibleMoves(ValueClassAdapters.gpcToDC(color).get()).isEmpty();
    }
    else {
      return true;
    }
  }
}
