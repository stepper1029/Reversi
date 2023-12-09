package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.adapters.ValueClassAdapters;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.provider.model.Coordinate;

/**
 * Tests for the ValueClassAdapter.
 */
public class ValueClassAdapterTests {
  @Test
  public void testCoordinateToCell() {
    ReadOnlyModel model = ReversiCreator.createHex(4);
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(-3, -3), model),
            model.getCellAt(0,0));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(-3, 3), model),
            model.getCellAt(6,0));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(3, 1), model),
            model.getCellAt(4,4));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(0, 0), model),
            model.getCellAt(3,3));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(3, -3), model),
            model.getCellAt(0,3));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(-6, 0), model),
            model.getCellAt(3,0));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(6, 0), model),
            model.getCellAt(3,6));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(3, 3), model),
            model.getCellAt(6,3));
    Assert.assertEquals(ValueClassAdapters.coordinateToCell(new Coordinate(-1, -3), model),
            model.getCellAt(0, 1));
  }
}
