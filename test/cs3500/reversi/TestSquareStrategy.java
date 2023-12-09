package cs3500.reversi;


import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;

/**
 * Strategy tests for a square board.
 */
public class TestSquareStrategy extends AbstractTestClass {
  private MutableModel model4;

  @Override
  protected void initStrategies() {
    super.initStrategies();
    this.model4 = ReversiCreator.createSquare(4);
  }

  @Test
  public void testMostPiecesBreakingTies() {
    this.initStrategies();
    Assert.assertEquals(this.model4.allPossibleMoves(DiskColor.Black),
            this.mostPieces.allGoodMoves(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 2)),
            this.mostPieces.bestPotentialMove(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 1)),
            this.mostPieces.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }

  @Test
  public void testMostPiecesDifferentScore() {
    this.initStrategies();
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    this.model4.place(this.model4.getCellAt(0, 3), DiskColor.White);
    this.model4.pass(DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 1)),
            this.mostPieces.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }

  @Test
  public void testChooseCornersNoCorners() {
    this.initStrategies();
    Assert.assertTrue(this.chooseCorners.allGoodMoves(this.model4, DiskColor.Black,
            this.model4.allPossibleMoves(DiskColor.Black)).isEmpty());
    Assert.assertTrue(this.chooseCorners.allGoodMoves(this.model4, DiskColor.White,
            this.model4.allPossibleMoves(DiskColor.White)).isEmpty());
  }

  @Test
  public void testChooseCornersMovesPossible() {
    this.initStrategies();
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 3)),
            this.chooseCorners.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
    this.model4.place(this.model4.getCellAt(0, 3), DiskColor.White);
    Assert.assertTrue(this.chooseCorners.allGoodMoves(this.model4, DiskColor.Black,
            this.model4.allPossibleMoves(DiskColor.Black)).isEmpty());
  }

  @Test
  public void testAvoidCornerAdjacent() {
    this.initStrategies();
    Assert.assertTrue(this.avoidCornerAdjacent.allGoodMoves(this.model4, DiskColor.Black,
            this.model4.allPossibleMoves(DiskColor.Black)).isEmpty());
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 3)),
            this.avoidCornerAdjacent.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }

  @Test
  public void testMinimax() {
    this.initStrategies();
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 2)),
            this.minimax.bestPotentialMove(this.model4, DiskColor.Black,
                    this.model4.allPossibleMoves(DiskColor.Black)));
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    Assert.assertEquals(Optional.ofNullable(this.model4.getCellAt(0, 1)),
            this.minimax.bestPotentialMove(this.model4, DiskColor.White,
                    this.model4.allPossibleMoves(DiskColor.White)));
  }
}
