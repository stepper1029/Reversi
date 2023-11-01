package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiscColor;
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
  public void testPass() {
    this.initModels();
    this.model3.pass();
    this.model3.place(this.model3.getCellAt(0, 1));
    this.model3.pass();
    // game should not be over because pass is between another move
    Assert.assertFalse(this.model3.isGameOver());
    this.model3.pass();
    this.model3.pass();
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
    Assert.assertEquals(DiscColor.White,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
    this.model4.place(this.model4.getCellAt(2, 4));
    // tests that the in between discs are properly flipped
    Assert.assertEquals(DiscColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
    Assert.assertEquals(DiscColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 3)));
  }

  // tests a move that flips multiple discs in multiple directions at once
  @Test
  public void testFlippingMultipleDiscs() {
    this.initModels();
    this.model4.place(this.model4.getCellAt(2, 4));
    this.model4.place(this.model4.getCellAt(1, 2));
    Assert.assertEquals(DiscColor.White,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiscColor.White,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
    this.model4.place(this.model4.getCellAt(2, 1));
    Assert.assertEquals(DiscColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(2, 2)));
    Assert.assertEquals(DiscColor.Black,
            this.model4.getColorAt(this.model4.getCellAt(3, 2)));
  }

  // tests a move that flips multiple discs in the same directions at once
  @Test
  public void testValidMoves() {

  }

  @Test
  public void testAllPossibleMoves() {

  }
}
