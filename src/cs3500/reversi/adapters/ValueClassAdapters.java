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

  public static Coordinate cellToCoordinate(ReversiCell cell) {
    int q = cell.getCoord('q');
    int r = cell.getCoord('r');
    int s = cell.getCoord('s');

    int x = -1 * s + q;
    int y = r;

    Coordinate coord = new Coordinate(x, y);
    return coord;
  }



  public static ReversiCell coordinateToCell(Coordinate coord, ReadOnlyModel model) {
    int y = coord.getY();
    int x = coord.getX();
    int row = y + ((model.getNumRows() - 1) / 2);
    int col;
    int i;
    int holder = (model.getRowSize(row) * -1) + 1;

    if (x == (model.getRowSize(row) * -1) + 1) {
      col = 0;
      return model.getCellAt(row, col);
    } else if (x == (model.getRowSize(row)) - 1) {
      col = x;
      return model.getCellAt(row, col);
    } else {
      if (x % 2 == 0) {
        if (x < 0) {
          i = (x * -1) / 2;
        } else if (x == 0) {
          i = (model.getRowSize(row) - 1) / 2;
        } else {
          i = x / 2;
        }
      } else {
        if (x < 0) {
          i = ((x + 1) * -1) / 2;
        } else {
          i = (x - 1) / 2;

        }
      }
    }
      if (x >= 0) {
        col = (-1 * holder) - i;
      } else {
        col = holder + i;
      }
    return model.getCellAt(row, col);
  }
}
