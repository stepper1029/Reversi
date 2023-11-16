package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ModelMock;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.strategy.AvoidCornerAdjacent;
import cs3500.reversi.strategy.ChooseCorners;
import cs3500.reversi.strategy.CombineStrategies;
import cs3500.reversi.strategy.FallibleReversiStrategy;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.MostPieces;

/**
 * Tests for the strategy of Reversi.
 */
public class TestStrategy {
  // model fields for testing
  private MutableModel model3;
  private MutableModel model4;

  // strategy fields to test
  private FallibleReversiStrategy mostPieces;
  private InfallibleReversiStrategy mostPiecesInfallible;
  private FallibleReversiStrategy chooseCorners;
  private FallibleReversiStrategy avoidCornerAdjacent;
  private FallibleReversiStrategy minimax;

  // fields for testing the strategy with the mock
  private ModelMock mock;
  private ModelMock lyingMock;
  private StringBuilder log;

  // initializes the models
  private void initModels() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
  }

  // initializes strategies
  private void initStrategies() {
    this.initModels();
    this.mostPieces = new MostPieces();
    this.mostPiecesInfallible = new InfallibleReversiStrategy(this.mostPieces);
    this.chooseCorners = new ChooseCorners();
    this.avoidCornerAdjacent = new AvoidCornerAdjacent();
    this.minimax = new MiniMax();
  }

  // initializes fields for testing the strategy with the mock
  private void initMockStrategies() {
    this.initModels();
    this.initStrategies();
    this.log = new StringBuilder();
    this.mock = new ModelMock(this.model3, false, this.log);
    this.lyingMock = new ModelMock(this.model3, true, this.log);
  }

  /**
   * Tests that the MostPieces strategy can break ties between moves that have the same value.
   */
  @Test
  public void testMostPiecesBreakingTies() {
    this.initStrategies();
    // testing correct move chosen for black piece's starting move
    Assert.assertEquals(this.model3.allPossibleMoves(DiskColor.Black),
            this.mostPieces.allGoodMoves(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.model3.getCellAt(0, 1)),
            this.mostPieces.bestPotentialMove(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
    // making that move for the black piece
    this.model3.place(this.model3.getCellAt(0, 1), DiskColor.Black);
    // testing correct move chosen for white piece after the black piece is placed
    Assert.assertEquals(new ArrayList<ReversiCell>(Arrays.asList(
            this.model3.getCellAt(1, 3),
            this.model3.getCellAt(4, 1),
            this.model3.getCellAt(3, 0))),
            this.mostPieces.allGoodMoves(this.model3, DiskColor.White,
                    this.model3.allPossibleMoves(DiskColor.White)));
    Assert.assertEquals(Optional.ofNullable(this.model3.getCellAt(1, 3)),
            this.mostPieces.bestPotentialMove(this.model3, DiskColor.White,
                    this.model3.allPossibleMoves(DiskColor.White)));
  }

  /**
   * Tests that MostPieces correctly selects the move that will move more pieces.
   */
  @Test
  public void testMostPiecesDifferentScore() {
    this.initStrategies();
    this.model3.place(this.model3.getCellAt(0, 1), DiskColor.Black);
    this.model3.place(this.model3.getCellAt(1, 3), DiskColor.White);
    Assert.assertEquals(new ArrayList<ReversiCell>(
            Collections.singletonList(this.model3.getCellAt(3, 3))),
            this.mostPieces.allGoodMoves(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.model3.getCellAt(3, 3)),
            this.mostPieces.bestPotentialMove(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
  }

  /**
   * Tests the same functions on a bigger model.
   */
  @Test
  public void testMostPiecesSize4() {
    this.initStrategies();
    this.model4.place(this.model4.getCellAt(4, 1), DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(4, 0)),
            this.mostPieces.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
    this.model4.place(this.model4.getCellAt(4, 0), DiskColor.White);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(5, 2)),
            this.mostPieces.bestPotentialMove(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
  }

  @Test
  public void testInfallibleStrategy() {
    this.initStrategies();
    this.model3.place(this.model3.getCellAt(0, 1), DiskColor.Black);
    this.model3.pass(DiskColor.White);
    this.model3.place(this.model3.getCellAt(4, 1), DiskColor.Black);
    // white should have no legal moves
    Assert.assertThrows(IllegalStateException.class, () ->
            this.mostPiecesInfallible.chooseMove(this.model3, DiskColor.White));
  }

  @Test
  public void testMostPiecesMock() {
    this.initMockStrategies();
    Assert.assertEquals(Optional.ofNullable(this.mock.getCellAt(0, 1)),
            this.mostPieces.bestPotentialMove(this.mock, DiskColor.Black,
                    this.mock.allPossibleMoves(DiskColor.Black)));
    // checking that the log tried placing all of the possible moves and received the correct
    // value for each move
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: 2 r: -1 s: -1\n" +
                    "Returning score: 5"));
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: -1 r: -1 s: 2\n" +
                    "Returning score: 5"));
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: 1 r: -2 s: 1\n" +
                    "Returning score: 5"));
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: -1 r: 2 s: -1\n" +
                    "Returning score: 5"));
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: -2 r: 1 s: 1\n" +
                    "Returning score: 5"));
    Assert.assertTrue(this.log.toString().contains(
            "Checking/ placing black disk at Cell: q: 1 r: 1 s: -2\n" +
                    "Returning score: 5"));
  }

  /**
   * Lying mock returns the same value for the score for every move and returns illegal moves.
   * No matter whose turn it is, the mock should return Cell: q = 0 , r = -2, s = 2 for the
   * most pieces strategy because it breaks ties by finding the top-most left-most option.
   */
  @Test
  public void testLyingMock() {
    this.initMockStrategies();
    // this is an invalid move for Black, but the strategy should still choose it based on how
    // it calculates the best moves
    Assert.assertEquals(Optional.ofNullable(this.lyingMock.getCellAt(0, 0)),
            this.mostPieces.bestPotentialMove(this.lyingMock, DiskColor.Black,
                    this.lyingMock.allPossibleMoves(DiskColor.Black)));
    this.lyingMock.place(this.lyingMock.getCellAt(0, 0), DiskColor.Black);
    // this is the same move and also invalid for white, but the strategy should still choose it
    Assert.assertEquals(Optional.ofNullable(this.lyingMock.getCellAt(0, 0)),
            this.mostPieces.bestPotentialMove(this.lyingMock, DiskColor.White,
                    this.lyingMock.allPossibleMoves(DiskColor.White)));
  }

  @Test
  public void testChooseCornersNoCorners() {
    this.initStrategies();
    Assert.assertTrue(this.chooseCorners.allGoodMoves(this.model3, DiskColor.Black,
            this.model3.allPossibleMoves(DiskColor.Black)).isEmpty());
    Assert.assertTrue(this.chooseCorners.allGoodMoves(this.model3, DiskColor.White,
            this.model3.allPossibleMoves(DiskColor.White)).isEmpty());
  }

  @Test
  public void testChooseCornersMultipleMoves() {
    this.initStrategies();
    this.model4.place(this.model4.getCellAt(4, 1), DiskColor.Black);
    this.model4.place(this.model4.getCellAt(4, 0), DiskColor.White);
    this.model4.place(this.model4.getCellAt(5, 0), DiskColor.Black);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(
            this.model4.getCellAt(6, 0))),
            this.chooseCorners.allGoodMoves(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }

  // Only one valid corner move. Checking that the log checks it.
  @Test
  public void testChooseCornerMock() {
    this.initMockStrategies();
    this.mock = new ModelMock(this.model4, false, this.log);
    this.mock.place(this.mock.getCellAt(4, 1), DiskColor.Black);
    this.mock.place(this.mock.getCellAt(4, 0), DiskColor.White);
    this.mock.place(this.mock.getCellAt(5, 0), DiskColor.Black);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(
                    this.mock.getCellAt(6, 0))),
            this.chooseCorners.allGoodMoves(this.mock, DiskColor.White,
                    this.mock.allPossibleMoves(DiskColor.White)));
    Assert.assertTrue(this.log.toString().contains(
            this.mock.getCellAt(6, 0).toString()));
  }

  // test that there is a valid corner move in the lying mock even though there should not be
  @Test
  public void testChooseCornerLyingMock() {
    this.initMockStrategies();
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(
                    this.lyingMock.getCellAt(0, 0))),
            this.chooseCorners.allGoodMoves(this.lyingMock, DiskColor.Black,
                    this.lyingMock.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.lyingMock.getCellAt(0, 0)),
            this.chooseCorners.bestPotentialMove(this.lyingMock, DiskColor.Black,
                    this.lyingMock.allPossibleMoves(DiskColor.Black)));
  }

  // should have no good moves because a board with side length 3 has no possible moves
  // that are not next to a corner cell
  @Test
  public void testAvoidCornerAdjacentSize3() {
    this.initStrategies();
    Assert.assertTrue(this.avoidCornerAdjacent.allGoodMoves(this.model3, DiskColor.Black,
            this.model3.allPossibleMoves(DiskColor.Black)).isEmpty());
  }

  @Test
  public void testAvoidCornerAdjacentMultipleMoves() {
    this.initStrategies();
    Assert.assertEquals(Optional.of(this.model4.getCellAt(1, 2)),
            this.avoidCornerAdjacent.bestPotentialMove(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
    this.model4.place(this.model4.getCellAt(1, 2), DiskColor.Black);
    // white has a move that flips more cells but is next to a corner so ensure that that move
    // is not chosen
    Assert.assertEquals(Optional.of(this.model4.getCellAt(2, 4)),
            this.avoidCornerAdjacent.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }

  // tests that a cell adjacent to a corner is tested but is not ultimately chosen
  @Test
  public void testAvoidCornerAdjacentMock() {
    this.initMockStrategies();
    this.mock = new ModelMock(this.model4, false, this.log);
    this.mock.place(this.mock.getCellAt(1, 2), DiskColor.Black);
    Assert.assertEquals(Optional.of(this.mock.getCellAt(2, 4)),
            this.avoidCornerAdjacent.bestPotentialMove(this.mock, DiskColor.White,
                    this.mock.allPossibleMoves(DiskColor.White)));
    // cell that is valid and flips more pieces but is adjacent to a corner
    Assert.assertTrue(this.log.toString().contains("Cell: q: 2 r: -3 s: 1 "));
    System.out.println(this.model4.getCellAt(0, 2));
  }

  // test that there is a valid move not next to a corner for a size 3 board when there is not
  @Test
  public void testAvoidCornerAdjacentLyingMock() {
    this.initMockStrategies();
    Assert.assertEquals(new ArrayList<ReversiCell>(Arrays.asList(
                    this.lyingMock.getCellAt(0, 0),
                    this.lyingMock.getCellAt(2, 2))),
            this.avoidCornerAdjacent.allGoodMoves(this.lyingMock, DiskColor.Black,
                    this.lyingMock.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.lyingMock.getCellAt(0, 0)),
            this.avoidCornerAdjacent.bestPotentialMove(this.lyingMock, DiskColor.Black,
                    this.lyingMock.allPossibleMoves(DiskColor.Black)));
  }

  // tests combining two strategies
  @Test
  public void testCombineMostPiecesAvoid() {
    this.initStrategies();
    FallibleReversiStrategy combined =
            new CombineStrategies(this.mostPieces, this.avoidCornerAdjacent);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(1, 2)),
            combined.bestPotentialMove(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
    this.model4.place(this.model4.getCellAt(1, 2), DiskColor.Black);
  }
}
