package cs3500.reversi.adapters;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;

/**
 * Adapter for value classes like colors and cells, essentially a utils method.
 */
public class ValueClassAdapters {
  /**
   * Converts a GamePieceColor from our provider's code to a DiskColor in our code.
   *
   * @return the corresponding DiskColor or empty if the game piece color is empty
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

  /**
   * Converts a DiskColor to a GamePieceColor.
   *
   * @param color the given DiskColor
   * @return the corresponding GamePieceColor
   */
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

  /**
   * Converts our cells to our provider's coordinate.
   *
   * @param cell our cell
   * @return the corresponding coordinate
   */
  public static Coordinate cellToCoordinate(ReversiCell cell) {
    int q = cell.getCoord('q');
    int r = cell.getCoord('r');
    int s = cell.getCoord('s');

    int x = -1 * s + q;
    int y = r;

    Coordinate coord = new Coordinate(x, y);
    return coord;
  }

  /**
   * Translates their coordinate to a Reversi cell.
   *
   * @param coord coordinate
   * @param model our model
   * @return a Reversi Cell
   */
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
