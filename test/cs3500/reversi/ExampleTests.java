package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCreator;

/**
 * Represents a set of tests for the basic functionality of the model.
 */
public class ExampleTests {
  private MutableModel model;

  // tests a small game from start to finish
  @Test
  public void testFullGame() {
    this.model = ReversiCreator.create(3);
    Assert.assertEquals(3, this.model.getScore(DiskColor.White));
    Assert.assertEquals(3, this.model.getScore(DiskColor.Black));
    this.model.place(this.model.getCellAt(0, 1), DiskColor.Black);
    this.model.place(this.model.getCellAt(3, 0), DiskColor.White);
    this.model.place(this.model.getCellAt(4, 1), DiskColor.Black);
    this.model.pass(DiskColor.White);
    this.model.place(this.model.getCellAt(1, 0), DiskColor.Black);
    this.model.place(this.model.getCellAt(3, 3), DiskColor.White);
    this.model.pass(DiskColor.Black);
    this.model.place(this.model.getCellAt(1, 3), DiskColor.White);
    this.model.pass(DiskColor.Black);
    this.model.pass(DiskColor.White);
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(6, this.model.getScore(DiskColor.White));
    Assert.assertEquals(6, this.model.getScore(DiskColor.Black));
  }

  // checks that the game is over after passing twice in a row
  @Test
  public void testPass() {
    this.model = ReversiCreator.create(3);
    this.model.pass(DiskColor.Black);
    this.model.pass(DiskColor.White);
    Assert.assertTrue(this.model.isGameOver());
  }

  @Test
  public void testPlace() {
    this.model = ReversiCreator.create(3);
    Assert.assertTrue(model.isEmpty(model.getCellAt(0, 1)));
    Assert.assertEquals(3, model.getScore(DiskColor.Black));
    Assert.assertEquals(3, model.getScore(DiskColor.White));
    model.place(model.getCellAt(0, 1), DiskColor.Black);
    Assert.assertEquals(5, model.getScore(DiskColor.Black));
    Assert.assertEquals(2, model.getScore(DiskColor.White));
    Assert.assertEquals(DiskColor.Black, model.getColorAt(model.getCellAt(0, 1)));
  }
}
