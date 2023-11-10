package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCreator;

/**
 * Tests for package-private methods/ classes in the strategy package.
 */
public class PackagePrivateStrategyTests {
  // test TopLeftComparator

  // model to use for retrieving cells
  ReadOnlyModel model;

  // initializes the model to a board of size 4
  private void initModel() {
    this.model = ReversiCreator.create(4);
  }

  @Test
  public void testTopLeftComparatorEqual() {
    this.initModel();
    Assert.assertEquals(new TopLeftComparator().compare(this.model.getCellAt(1, 2),
            this.model.getCellAt(1, 2)), 0);
    Assert.assertEquals(new TopLeftComparator().compare(this.model.getCellAt(3, 3),
            this.model.getCellAt(3, 3)), 0);
    Assert.assertEquals(new TopLeftComparator().compare(this.model.getCellAt(5, 2),
            this.model.getCellAt(5, 2)), 0);
    Assert.assertEquals(new TopLeftComparator().compare(this.model.getCellAt(4, 1),
            this.model.getCellAt(4, 1)), 0);
  }

  @Test
  public void testNegValue() {
    this.initModel();
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(1, 2),
            this.model.getCellAt(0, 0)) < 0);
  //  Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(1, 3), this.model.getCellAt(4, 0)));
  }
}
