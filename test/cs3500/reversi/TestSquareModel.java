package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.ReversiCreator;

/**
 * Class to extensively test the square model.
 */
public class TestSquareModel {
  // ReversiCreator tests
  @Test
  public void testReversiCreator() {
    Assert.assertTrue(ReversiCreator.createSquare(4) instanceof MutableModel);
    Assert.assertTrue(ReversiCreator.createSquare(6) instanceof MutableModel);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.createSquare(2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.createSquare(4));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.createSquare(0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.createSquare(-1));
  }

  // Model tests
  private MutableModel model4;

  // initializes models to be testing
  private void initModel() {
    this.model4 = ReversiCreator.createSquare(4);
  }

  @Test
  public void testGetCellAt() {
    this.initModel();
    Assert.assertEquals(0,
            this.model4.getCellAt(0, 0).getCoord('x'));
    Assert.assertEquals(0,
            this.model4.getCellAt(2, 0).getCoord('x'));
    Assert.assertEquals(1,
            this.model4.getCellAt(1, 3).getCoord('y'));
    Assert.assertEquals(2,
            this.model4.getCellAt(2, 2).getCoord('y'));
  }

  @Test
  public void testGetNumRows() {
    this.initModel();
    Assert.assertEquals(4, this.model4.getNumRows());
  }

  @Test
  public void testGetRowSize() {
    this.initModel();
    Assert.assertEquals(4, this.model4.getRowSize(0));
    Assert.assertEquals(4, this.model4.getRowSize(1));
    Assert.assertEquals(4, this.model4.getRowSize(2));
    Assert.assertEquals(4, this.model4.getRowSize(3));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model4.getRowSize(7));
  }

  @Test
  public void testPass() {
    this.initModel();
    Assert.assertFalse(this.model4.isGameOver());
    this.model4.pass(DiskColor.Black);
    Assert.assertFalse(this.model4.isGameOver());
    this.model4.pass(DiskColor.White);
    Assert.assertTrue(this.model4.isGameOver());
  }

  @Test
  public void testAllPossibleMoves() {
    this.initModel();
    Assert.assertEquals(new ArrayList<>(Arrays.asList(
            this.model4.getCellAt(3, 1),
            this.model4.getCellAt(2, 0),
            this.model4.getCellAt(1, 3),
            this.model4.getCellAt(0, 2))),
            this.model4.allPossibleMoves(DiskColor.Black));
    Assert.assertEquals(new ArrayList<>(Arrays.asList(
                    this.model4.getCellAt(2, 3),
                    this.model4.getCellAt(0, 1),
                    this.model4.getCellAt(1, 0),
                    this.model4.getCellAt(3, 2))),
            this.model4.allPossibleMoves(DiskColor.White));
  }

  @Test
  public void testPlaceAndFlip() {
    this.initModel();
    Assert.assertEquals(2, this.model4.getScore(DiskColor.Black));
    Assert.assertEquals(2, this.model4.getScore(DiskColor.White));
    Assert.assertTrue(this.model4.isEmpty(this.model4.getCellAt(0, 2)));
    this.model4.place(this.model4.getCellAt(0, 2), DiskColor.Black);
    Assert.assertFalse(this.model4.isEmpty(this.model4.getCellAt(0, 2)));
    Assert.assertEquals(4, this.model4.getScore(DiskColor.Black));
    Assert.assertEquals(1, this.model4.getScore(DiskColor.White));
    Assert.assertEquals(new ArrayList<>(Arrays.asList(
            this.model4.getCellAt(2, 3),
            this.model4.getCellAt(0, 1),
            this.model4.getCellAt(0, 3))),
            this.model4.allPossibleMoves(DiskColor.White));
    this.model4.place(this.model4.getCellAt(0, 3), DiskColor.White);
    Assert.assertEquals(3, this.model4.getScore(DiskColor.Black));
    Assert.assertEquals(3, this.model4.getScore(DiskColor.White));
  }

  @Test
  public void testIllegalMoves() {
    this.initModel();
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.place(this.model4.getCellAt(0, 1), DiskColor.Black));
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.place(this.model4.getCellAt(3, 3), DiskColor.Black));
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.place(this.model4.getCellAt(0, 1), DiskColor.Black));
  }

  @Test
  public void testPlayingOutOfTurn() {
    this.initModel();
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.pass(DiskColor.White));
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.place(this.model4.getCellAt(0, 0),
                    DiskColor.White));
    this.model4.pass(DiskColor.Black);
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.pass(DiskColor.Black));
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model4.place(this.model4.getCellAt(0, 0),
                    DiskColor.Black));
  }
}
