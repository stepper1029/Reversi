package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
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
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(0, 0),
            this.model.getCellAt(0, 3)) < 0);
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(1, 3),
            this.model.getCellAt(5, 1)) < 0);
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(0, 2),
            this.model.getCellAt(2, 0)) < 0);
  }

  @Test
  public void testPosValue() {
    this.initModel();
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(4, 2),
            this.model.getCellAt(0, 0)) > 0);
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(3, 3),
            this.model.getCellAt(3, 1)) > 0);
    Assert.assertTrue(new TopLeftComparator().compare(this.model.getCellAt(3, 0),
            this.model.getCellAt(1, 3)) > 0);
  }

  @Test
  public void testSort() {
    this.initModel();
    ArrayList<ReversiCell> sorted = new ArrayList<>(Arrays.asList(
            this.model.getCellAt(0, 0),
            this.model.getCellAt(3, 2),
            this.model.getCellAt(2, 3)));
    sorted.sort(new TopLeftComparator());
    Assert.assertEquals(sorted, new ArrayList<>(Arrays.asList(
            this.model.getCellAt(0, 0),
            this.model.getCellAt(2, 3),
            this.model.getCellAt(3, 2))));
    ArrayList<ReversiCell> sorted2 = new ArrayList<>(Arrays.asList(
            this.model.getCellAt(3, 0),
            this.model.getCellAt(0, 2),
            this.model.getCellAt(4, 1),
            this.model.getCellAt(3, 1)));
    sorted2.sort(new TopLeftComparator());
    Assert.assertEquals(sorted2, new ArrayList<>(Arrays.asList(
            this.model.getCellAt(0, 2),
            this.model.getCellAt(3, 0),
            this.model.getCellAt(3, 1),
            this.model.getCellAt(4, 1))));
  }
}
