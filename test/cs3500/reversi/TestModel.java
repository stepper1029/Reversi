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
  private MutableModel model3;
  private MutableModel model4;

  // tests ReversiCreator
  @Test
  public void testReversiCreator() {
    Assert.assertTrue(ReversiCreator.create(10) instanceof MutableModel);
  }
}
