package strategies;

import java.util.List;

import model.Coordinate;
import model.GamePieceColor;
import model.ReadonlyReversiModel;
import model.ReversiModel;

/**
 * <h3>PlayCorner Class</h3>
 * This class is a {@link ReversiStrategy} that returns the piece at a corner of the
 *     {@link ReversiModel}'s game board if the corner happens to be an
 *     Empty {@link GamePieceColor}. This Strategy will return a given Strategy backup if
 *     all corners are filled with non-empty pieces.
 * @see ReversiStrategy
 */
public class PlayCorner extends CornerStrategies {

  // the backup ReversiStrategy to be used in case of failure to find an empty corner
  // with a valid move
  private final ReversiStrategy backup;

  /**
   * Constructs a new PlayCorner with a {@link ReversiStrategy} backup Strategy.
   * @param backup the backup ReversiStrategy
   */
  public PlayCorner(ReversiStrategy backup) {
    this.backup = backup;
  }

  @Override
  public Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException {

    super.emptyColorException(gpc);
    super.nullModelException(model);

    List<Coordinate> potentialCoors = super.findCorners(model);
    for (Coordinate coor : potentialCoors) {
      if (model.isMoveValid(coor, gpc)) {
        return coor;
      }
    }
    return this.backup.findBestTurn(model, gpc);
  }
}
