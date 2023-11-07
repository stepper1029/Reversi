package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiscColor;
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
    Assert.assertEquals(3, this.model.getScore(DiscColor.White));
    Assert.assertEquals(3, this.model.getScore(DiscColor.Black));
    this.model.place(this.model.getCellAt(0, 1), DiscColor.Black);
    this.model.place(this.model.getCellAt(3, 0), DiscColor.White);
    this.model.place(this.model.getCellAt(4, 1), DiscColor.Black);
    this.model.pass();
    this.model.place(this.model.getCellAt(1, 0), DiscColor.Black);
    this.model.place(this.model.getCellAt(3, 3), DiscColor.White);
    this.model.pass();
    this.model.place(this.model.getCellAt(1, 3), DiscColor.White);
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(6, this.model.getScore(DiscColor.White));
    Assert.assertEquals(6, this.model.getScore(DiscColor.Black));
  }

  // checks that the game is over after passing twice in a row
  @Test
  public void testPass() {
    this.model = ReversiCreator.create(3);
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(this.model.isGameOver());
  }

  @Test
  public void testPlace() {
    this.model = ReversiCreator.create(3);
    Assert.assertTrue(model.isEmpty(model.getCellAt(0, 1)));
    Assert.assertEquals(3, model.getScore(DiscColor.Black));
    Assert.assertEquals(3, model.getScore(DiscColor.White));
    model.place(model.getCellAt(0, 1), DiscColor.Black);
    Assert.assertEquals(5, model.getScore(DiscColor.Black));
    Assert.assertEquals(2, model.getScore(DiscColor.White));
    Assert.assertEquals(DiscColor.Black, model.getColorAt(model.getCellAt(0, 1)));
  }
}
