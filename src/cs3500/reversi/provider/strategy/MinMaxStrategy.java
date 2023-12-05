package strategies;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.GamePieceColor;
import model.ReadonlyReversiModel;
import model.ReversiModel;

/**
 * <h3>MinMaxStrategy Class</h3>
 * This {@link ReversiStrategy} is responsible for finding the best move that leaves
 *     the opposing player with the least amount of captures possible for their next turn.
 *     This strategy has a fallback just in case this one doesn't find any good moves.
 * @implNote This class assumes the opponent is trying to capture the most amount of tiles
 *     on their turn with the {@link MaximumCaptures} ReversiStrategy
 * @see ReversiModel
 * @see GamePieceColor
 */
public class MinMaxStrategy extends ReversiStrategyExceptions {

  // The fallback ReversiStrategy to be used
  ReversiStrategy fallback;

  /**
   * Constructs a new MixMaxStrategy Object. This strategy also has a fallback option.
   * @param fallback the fallback {@link ReversiStrategy} to try if this one fails
   */
  public MinMaxStrategy(ReversiStrategy fallback) {
    this.fallback = fallback;
  }

  @Override
  public Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException {

    super.emptyColorException(gpc);
    super.nullModelException(model);

    // Assuming the other player is trying to find the maximum number of captures
    ReversiStrategy opposingStrategy = new MaximumCaptures();

    GamePieceColor opposingColor;

    switch (gpc) {
      case Black:
        opposingColor = GamePieceColor.White;
        break;
      case White:
        opposingColor = GamePieceColor.Black;
        break;
      default:
        throw new IllegalArgumentException("Cannot find a strategy with an Empty GamePieceColor");
    }

    Coordinate opposingBestCoor = opposingStrategy.findBestTurn(model, opposingColor);

    List<Coordinate> listOfPotentialmoves = this.loopSurroundingCoors(opposingBestCoor);
    for (Coordinate coordinate : listOfPotentialmoves) {
      if (model.getColorAt(coordinate).equals(opposingColor)
              && model.isMoveValid(coordinate, gpc)) {
        return coordinate;
      }
    }

    return fallback.findBestTurn(model, gpc);
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