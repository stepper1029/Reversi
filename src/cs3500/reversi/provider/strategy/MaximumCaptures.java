package cs3500.reversi.provider.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReadonlyReversiModel;

/**
 * <h3>MaximumCaptures Class</h3>>
 * This strategy finds the piece of the game board in a given model that will capture the most
 *     amount of game pieces for the given {@link GamePieceColor}. If there are multiple
 *     moves that would yield the same maximum, the method findBestTurn() returns the top-leftmost
 *     {@link Coordinate}.
 * @see ReversiStrategy
 */
public class MaximumCaptures extends ReversiStrategyExceptions {

  @Override
  public Coordinate findBestTurn(ReadonlyReversiModel model, GamePieceColor gpc)
          throws IllegalArgumentException, IllegalStateException {

    super.emptyColorException(gpc);
    super.nullModelException(model);

    List<List<Piece>> listOfCorrectRun;
    Map<Coordinate, Integer> runs = new HashMap<>();

    // checks if there are any moves that are valid
    if (model.noMoves(gpc)) {
      throw new IllegalArgumentException("There are no moves available");
    }

    // finds all possible moves and adds it to a HashMap
    for (Piece piece : model.getBoard()) {
      int acc = 0;
      Coordinate gpCoor = piece.getCoordinate();

      if (model.isMoveValid(gpCoor, gpc)) {
        listOfCorrectRun = model.returnRuns();
        listOfCorrectRun = listOfCorrectRun.stream().distinct().collect(Collectors.toList());

        for (List<Piece> logp : listOfCorrectRun) {
          for (Piece gp : logp) {
            if (!gp.getColor().equals(gpc) && !gp.getColor().equals(GamePieceColor.Empty)) {
              acc++;
            } else if (gp.getColor().equals(GamePieceColor.Empty)) {
              acc++;
            }
          }
        }
      }
      runs.put(gpCoor, acc);
    }

    int max = 0;
    Map<Coordinate, Integer> maxRuns = new HashMap<>();

    // finds the maximum value
    for (Map.Entry<Coordinate, Integer> element : runs.entrySet()) {
      if (element.getValue() > max) {
        max = element.getValue();
      }
    }

    for (Map.Entry<Coordinate, Integer> element : runs.entrySet()) {
      if (element.getValue() == max) {
        maxRuns.put(element.getKey(), element.getValue());
      }
    }

    Coordinate prevCoor = maxRuns.entrySet().iterator().next().getKey();
    Coordinate bestCoor = maxRuns.entrySet().iterator().next().getKey();

    // if multiple, finds the top-left coordinate
    for (Map.Entry<Coordinate, Integer> element : maxRuns.entrySet()) {
      Coordinate curCoor = element.getKey();
      if (curCoor.getX() < prevCoor.getX() || curCoor.getY() < prevCoor.getY()) {
        bestCoor = curCoor;
        prevCoor = curCoor;
      }
    }

    return bestCoor;
  }
}
