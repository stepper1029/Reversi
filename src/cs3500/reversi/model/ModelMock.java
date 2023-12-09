package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.controller.ModelFeatures;

/**
 * Mock model for testing the strategy. Keeps a log of certain calls that have been made to
 * the mock and can lie about the legality of moves and the score.
 */
public class ModelMock implements MutableModel, ReadOnlyModel {
  private MutableModel realModel;
  private boolean shouldLie;
  private final StringBuilder log;

  /**
   * Constructor for the mock.
   *
   * @param realModel a real model to delegate to if the model is not lying
   * @param shouldLie whether the mock should lie about the legality of moves and the score or not
   * @param log keeps track of calls made to this mock
   */
  public ModelMock(MutableModel realModel, boolean shouldLie, StringBuilder log) {
    this.realModel = realModel;
    this.shouldLie = shouldLie;
    this.log = log;
  }

  @Override
  public void addListener(DiskColor color, ModelFeatures listener) {
    this.log.append("Adding a ModelFeatures listener for " + color + "\n");
    this.realModel.addListener(color, listener);
  }

  @Override
  public void startGame() {
    this.realModel.startGame();
  }

  @Override
  public void pass(DiskColor color) {
    this.log.append("Passing for " + color + "\n");
    this.realModel.pass(color);
  }

  @Override
  public void place(ReversiCell cell, DiskColor color) {
    if (!this.shouldLie) {
      this.realModel.place(cell, color);
      this.log.append("Checking/ placing " + color + " at " + cell + "\n");
    }
    else if (this.allPossibleMoves(color).contains(cell)) {
      this.log.append("Mock is lying and placed " + color + " at " + cell + "\n");
    }
    else {
      this.log.append("Mock is lying and cannot place " + color + " at " + cell + "\n");
    }
  }

  @Override
  public int getBoardSize() {
    return this.realModel.getBoardSize();
  }

  @Override
  public DiskColor getColorAt(ReversiCell cell) {
    return null;
  }

  /**
   * Returns the real score of the game or a constant value of 3, so that all moves have the same
   * numerical value.
   *
   * @param color the color which indicates which player
   * @return the correct score of the game or 3 if the mock should lie.
   */
  @Override
  public int getScore(DiskColor color) {
    if (!this.shouldLie) {
      int realScore = this.realModel.getScore(color);
      this.log.append("Returning score: " + realScore + "\n");
      return realScore;
    }
    else {
      this.log.append("Lying about score, returning 3 ");
      return 3;
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * If the mock should be lying, returns a set list of cells. Otherwise, delegates to the
   * real model.
   *
   * @param color the given color to find possible moves for
   * @return a list of possible moves or a fake list
   */
  @Override
  public List<ReversiCell> allPossibleMoves(DiskColor color) {
    if (!shouldLie) {
      this.log.append("Returning true possible moves\n" +
              this.realModel.allPossibleMoves(color) + "\n");
      return this.realModel.allPossibleMoves(color);
    }
    else {
      List<ReversiCell> fakeList = new ArrayList<>(Arrays.asList(
              this.realModel.getCellAt(0, 0),
              this.realModel.getCellAt(2, 2),
              this.realModel.getCellAt(3, 2)));
      this.log.append("Log is lying about possible moves and returning:\n" + fakeList + "\n");
      return fakeList;
    }
  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getRowSize(int rowNum) {
    return 0;
  }

  @Override
  public ReversiCell getCellAt(int numRow, int numCell) {
    return this.realModel.getCellAt(numRow, numCell);
  }

  @Override
  public boolean isEmpty(ReversiCell c) {
    return false;
  }

  @Override
  public DiskColor getTurn() {
    return this.realModel.getTurn();
  }

  @Override
  public MutableModel copy() {
    return new ModelMock(this.realModel.copy(), this.shouldLie, this.log);
  }

  @Override
  public Optional<DiskColor> getWinner() {
    return Optional.empty();
  }

  @Override
  public boolean isCorner(ReversiCell cell) {
    return this.realModel.isCorner(cell);
  }

  @Override
  public boolean isCornerAdjacent(ReversiCell cell) {
    return this.realModel.isCornerAdjacent(cell);
  }
}
