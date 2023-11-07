package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;

/**
 * Class to extensively test the model.
 */
public class TestModel {
  // ReversiCreator tests
  @Test
  public void testReversiCreator() {
    Assert.assertTrue(ReversiCreator.create(10) instanceof MutableModel);
    Assert.assertTrue(ReversiCreator.create(3) instanceof MutableModel);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.create(2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.create(1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.create(0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            ReversiCreator.create(-1));
  }

  // Model tests
  private MutableModel model3;
  private MutableModel model4;

  // initializes models to be testing
  private void initModels() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
  }

  @Test
  public void testGetCellAt() {
    this.initModels();
    Assert.assertEquals(1,
            this.model3.getCellAt(0, 1).getCoord('q'));
    Assert.assertEquals(-2,
            this.model3.getCellAt(0, 1).getCoord('r'));
    Assert.assertEquals(1,
            this.model3.getCellAt(0, 1).getCoord('s'));
    Assert.assertEquals(0,
            this.model3.getCellAt(2, 2).getCoord('q'));
    Assert.assertEquals(0,
            this.model3.getCellAt(2, 2).getCoord('r'));
    Assert.assertEquals(0,
            this.model3.getCellAt(2, 2).getCoord('s'));
    Assert.assertEquals(0,
            this.model3.getCellAt(3, 2).getCoord('q'));
    Assert.assertEquals(1,
            this.model3.getCellAt(3, 2).getCoord('r'));
    Assert.assertEquals(-1,
            this.model3.getCellAt(3, 2).getCoord('s'));
  }

  @Test
  public void testGetNumRows() {
    this.initModels();
    Assert.assertEquals(5, this.model3.getNumRows());
    Assert.assertEquals(7, this.model4.getNumRows());
  }

  @Test
  public void testGetRowSize() {
    this.initModels();
    Assert.assertEquals(5, this.model3.getRowSize(2));
    Assert.assertEquals(3, this.model3.getRowSize(0));
    Assert.assertEquals(4, this.model3.getRowSize(3));
    Assert.assertEquals(7, this.model4.getRowSize(3));
    Assert.assertEquals(4, this.model4.getRowSize(0));
    Assert.assertEquals(6, this.model4.getRowSize(2));
  }

  @Test
  public void testPass() {
    this.initModels();
    this.model3.pass(DiskColor.Black);
    this.model3.place(this.model3.getCellAt(0, 1), DiskColor.White);
    this.model3.pass(DiskColor.Black);
    // game should not be over because pass is between another move
    Assert.assertFalse(this.model3.isGameOver());
    this.model3.pass(DiskColor.White);
    this.model3.pass(DiskColor.Black);
    Assert.assertTrue(this.model3.isGameOver());
  }

  @Test
  public void testGetBoardSize() {
    this.initModels();
    Assert.assertEquals(3, this.model3.getBoardSize());
    Assert.assertEquals(4, this.model4.getBoardSize());
  }

  @Test
  public void testPlaceAndFlipping() {
    this.initModels();
    Assert.assertTrue(this.model4.isEmpty(this.model4.getCellAt(1, 3)));
    Assert.assertEquals(DiskColor.White,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
    this.model4.place(this.model4.getCellAt(2, 4), DiskColor.Black);
    // tests that the in between disks are properly flipped
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
  }

  // tests a move that flips multiple disks in multiple directions at once
  @Test
  public void testFlippingMultipleDisksDiffD() {
    this.initModels();
    this.model4.place(this.model4.getCellAt(2, 4), DiskColor.Black);
    this.model4.place(this.model4.getCellAt(1, 2), DiskColor.White);
    Assert.assertEquals(DiskColor.White,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiskColor.White,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
    this.model4.place(this.model4.getCellAt(2, 1), DiskColor.Black);
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
  }

  // tests a move that flips multiple disks in the same directions at once
  @Test
  public void testFlippingMultipleDisksSameD() {
    this.initModels();
    this.model4.place(this.model4.getCellAt(2, 4), DiskColor.Black);
    this.model4.place(this.model4.getCellAt(1, 2), DiskColor.White);
    this.model4.place(this.model4.getCellAt(2, 1), DiskColor.Black);
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiskColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
    this.model4.place(this.model4.getCellAt(4, 1), DiskColor.White);
    Assert.assertEquals(DiskColor.White,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiskColor.White,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
  }

  @Test
  public void testPossibleMoves() {
    this.initModels();
    Assert.assertEquals(6, this.model3.allPossibleMoves(DiskColor.Black).size());
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(0, 1)));
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(1, 0)));
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(1, 3)));
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(3, 0)));
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(3, 3)));
    Assert.assertTrue(this.model3.allPossibleMoves(DiskColor.Black).contains(
            this.model3.getCellAt(4, 1)));
    this.model3.place(this.model3.getCellAt(4, 1), DiskColor.Black);
    Assert.assertEquals(3, this.model3.allPossibleMoves(DiskColor.White).size());
  }

  @Test
  public void testIllegalPlace() {
    this.initModels();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model3.place(this.model3.getCellAt(3, 5), DiskColor.Black));
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model3.place(this.model3.getCellAt(0, 0), DiskColor.Black));
    this.model3.place(this.model3.getCellAt(4, 1), DiskColor.Black);
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model3.place(this.model3.getCellAt(4, 1), DiskColor.White));
  }

  @Test
  public void testIllegalCells() {
    this.initModels();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model3.getColorAt(this.model3.getCellAt(0, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model3.getRowSize(5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model4.getCellAt(5, 6));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model3.isEmpty(this.model3.getCellAt(5, 3)));
  }

  @Test
  public void testPlayingOutOfTurn() {
    this.initModels();
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model3.place(this.model3.getCellAt(4, 1), DiskColor.White));
  }
}
