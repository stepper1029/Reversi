package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.Reversi;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.strategy.TopLeftComparator;

/**
 * Tests for package-private methods/ classes in the strategy package.
 */
public class PackagePrivateStrategyTests {
  // test TopLeftComparator
  ReadOnlyModel model;
  // initializes the model to be able to retrieve cells
  private void initModel() {
    this.model = ReversiCreator.create(4);
  }
  @Test
  public void testTopLeftComparatorEqual() {
    Assert.assertTrue(new TopLeftComparator().compare());
  }
}
