package cs3500.reversi.adapters;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.provider.model.GamePieceColor;

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
}
