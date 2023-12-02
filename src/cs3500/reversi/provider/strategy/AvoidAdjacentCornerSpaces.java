package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

/**
 * <h3>AvoidAdjacentCornerSpaces Class</h3>
 * This {@link ReversiStrategy} ensures that this move avoids moving into spaces near corners.
 *     This Strategy takes in a backup Strategy in case it doesn't work with the current
 *     state of this Reversi game.
 *
 */
public class AvoidAdjacentCornerSpaces extends CornerStrategies {

  // the fallback ReversiStrategy to be used in case this one doesn't find any
  private final ReversiStrategy backup;

  /**
   * Creates a new AvoidAdjacentCornerSpaces Object with the backup {@link ReversiStrategy}.
   * @param backup the fallback ReversiStrategy in case this one fails
   */
  public AvoidAdjacentCornerSpaces(ReversiStrategy backup) {
    this.backup = backup;
  }

  @Override
  public Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException {

    super.emptyColorException(gpc);
    super.nullModelException(model);

    List<Coordinate> coorsToAvoid = super.findCorners(model);

    for (Piece gp : model.getBoard()) {
      boolean foundCoor = true;
      List<Coordinate> surrounding = this.loopSurroundingCoors(gp.getCoordinate());
      for (Coordinate coor : coorsToAvoid) {
        if (this.adjacentToCorner(coor, surrounding)) {
          foundCoor = false;
          break;
        }
      }
      if (foundCoor && model.isMoveValid(gp.getCoordinate(), gpc)) {
        return gp.getCoordinate();
      }
    }
    return this.backup.findBestTurn(model, gpc);
  }

  /**
   * Checks if the list of coordinates has a neighboring {@Coordinate} at the corner
   *     of the game board.
   * @param corner the corner to check.
   * @param loc the {@code List<Coordinate>} to check if any element is a corner
   * @return {@code true} if any element in the given list is a corner
   */
  private boolean adjacentToCorner(Coordinate corner, List<Coordinate> loc) {
    for (Coordinate coordinate : loc) {
      if (coordinate.equals(corner)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Loops through the surrounding coordinates of the given {@link Coordinate}.
   * @param coor the Coordinate to find the neighbors of
   * @return A {@code List<Coordinate>} of all Coordinates surrounding the given Coordinate.
   */
  private List<Coordinate> loopSurroundingCoors(Coordinate coor) {
    ArrayList<Coordinate> answer = new ArrayList<>();
    answer.add(new Coordinate(coor.getX() + 2, coor.getY()));
    answer.add(
            new Coordinate(coor.getX() + 1, coor.getY() + 1));
    answer.add(
            new Coordinate(coor.getX() + 1, coor.getY() - 1));
    answer.add(
            new Coordinate(coor.getX() - 1, coor.getY() + 1));
    answer.add(new Coordinate(coor.getX() - 2, coor.getY()));
    answer.add(
            new Coordinate(coor.getX() - 1, coor.getY() - 1));

    return answer;
  }
}
