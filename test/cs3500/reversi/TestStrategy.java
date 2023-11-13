package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.swing.text.html.Option;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.strategy.FallibleReversiStrategy;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MostPieces;

/**
 * Tests for the strategy of Reversi.
 */
public class TestStrategy {
  private MutableModel model3;
  private MutableModel model4;
  private FallibleReversiStrategy mostPieces;
  private InfallibleReversiStrategy mostPiecesInfallible;

  private void initStrategies() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
    this.mostPieces = new MostPieces();
    this.mostPiecesInfallible = new InfallibleReversiStrategy(this.mostPieces);
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
            this.model3.getCellAt(3, 0),
            this.model3.getCellAt(1, 3),
            this.model3.getCellAt(4, 1))),
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
}
