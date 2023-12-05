package cs3500.reversi.provider.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.ReadonlyReversiModel;
import cs3500.reversi.provider.model.ReversiModel;

/**
 * <h3>CornerStrategies Abstract Class</h3>
 * This Abstract Class is responsible for abstracting any similar methods
 *     that the corner strategies might have. For instance, all corner strategies
 *     require a method that returns a list of the corner pieces for the given model.
 * @see cs3500.reversi.provider.model.ReversiModel
 */
abstract class CornerStrategies extends ReversiStrategyExceptions {

  /**
   * Finds all the corners of this {@link ReversiModel}'s game board.
   * @param model the model to find the corners of
   * @return the List of {@link Coordinate}s of the corners in this Reversi game
   */
  protected List<Coordinate> findCorners(ReadonlyReversiModel model) {

    List<Coordinate> answer = new ArrayList<>();

    answer.add(new Coordinate(model.getMaxSize() - 1, 0));
    answer.add(new Coordinate(-1 * (model.getMaxSize() - 1), 0));
    answer.add(new Coordinate(model.getMaxSize() / 2, model.getMaxSize() / 2));
    answer.add(new Coordinate(-1 * model.getMaxSize() / 2, model.getMaxSize() / 2));
    answer.add(new Coordinate(-1 * model.getMaxSize() / 2, -1 * model.getMaxSize() / 2));
    answer.add(new Coordinate(model.getMaxSize() / 2, -1 * model.getMaxSize() / 2));

    return answer;
  }
}
