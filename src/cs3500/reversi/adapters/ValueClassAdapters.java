package cs3500.reversi.adapters;

import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

public class ValueClassAdapters {
  /**
   * Converts a GamePieceColor from our provider's code to a DiskColor in our code.
   * @return
   */
  public static Optional<DiskColor> gpcToDC(GamePieceColor color) {
    switch (color) {
      case Black:
        return Optional.of(DiskColor.Black);
      case White:
        return Optional.of(DiskColor.White);
      case Empty:
        return Optional.empty();
      default:
        throw new IllegalArgumentException("Illegal GamePieceColor");
    }
  }

  public static GamePieceColor dcToGPC(DiskColor color) {
    switch (color) {
      case Black:
        return GamePieceColor.Black;
      case White:
        return GamePieceColor.White;
      default:
        throw new IllegalArgumentException("Illegal DiskColor");
    }
  }

  public static ReversiCell PieceToCell(Piece piece, ReadOnlyModel model) {
    Coordinate coord = piece.getCoordinate();
    return coordinateToCell(coord, model);
  }

  public static Coordinate

  public static Piece cellToPiece(ReversiCell cell, ReadonlyReversiModel model) {
    // before I passed in their model (ReadonlyReversiModel) and I could return a Piece that way
    // but where im calling the method in ModelAdapter I dont have an instance of their model
    // to pass in
    int q = cell.getCoord('q');
    int r = cell.getCoord('r');
    int s = cell.getCoord('s');

    int x = -1 * s + q;
    int y = r;

    Coordinate coord = new Coordinate(x, y);
    List<Piece> board = model.getBoard();
    for (Piece p : board) {
      if (p.getCoordinate().equals(coord)) {
        return p;
      }
    }
    throw new IllegalArgumentException("no such cell");
  }

  public static ReversiCell coordinateToCell(Coordinate coord, ReadOnlyModel model) {
    int y = coord.getY();
    int x = coord.getX();

    int row = y + ((model.getNumRows() - 1) / 2);
    int col;
    if (x%2 == 0) {
      col = ((-1 * x) / 2) + ((model.getNumRows() - 1) / 2);
    } else {
      col = (((-1 * x) -1) / 2) + ((model.getNumRows() - 1) / 2);
    }

    return model.getCellAt(row, col);
  }
}
