package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class PackagePrivateModelTests extends AbstractTestClass {
  // CellDirection tests
  @Test
  public void testgetHexDirectionCoordinates() {
    Assert.assertArrayEquals(new int[]{-1, 0, 1},
            CellDirection.Left.getHexDirectionCoordinates());
    Assert.assertArrayEquals(new int[]{1, 0, -1},
            CellDirection.Right.getHexDirectionCoordinates());
    Assert.assertArrayEquals(new int[]{0, -1, 1},
            CellDirection.UpperLeft.getHexDirectionCoordinates());
    Assert.assertArrayEquals(new int[]{1, -1, 0},
            CellDirection.UpperRight.getHexDirectionCoordinates());
    Assert.assertArrayEquals(new int[]{-1, 1, 0},
            CellDirection.BottomLeft.getHexDirectionCoordinates());
    Assert.assertArrayEquals(new int[]{0, 1, -1},
            CellDirection.BottomRight.getHexDirectionCoordinates());
  }

  // HexBoard tests
  private HexBoard board3;
  private HexBoard board4;
  private void initHexBoards() {
    this.board3 = new HexBoard(3);
    this.board4 = new HexBoard(4);
  }

  @Test
  public void testHexBoardConstructor() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(2));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(-1));
  }

  @Test
  public void testGetInitPositions() {
    this.initHexBoards();
    ArrayList<ReversiCell> expected = new ArrayList<>(Arrays.asList(new HexCell(0, -1, 1),
            new HexCell(1, -1, 0), new HexCell(1, 0, -1),
            new HexCell(0, 1, -1), new HexCell(-1, 1, 0),
            new HexCell(-1, 0, 1)));
    Assert.assertEquals(expected, this.board3.getInitPositions());
    Assert.assertEquals(expected, this.board4.getInitPositions());
  }

  @Test
  public void testGetNeighborCell() {
    this.initHexBoards();
    this.initCells();
    Assert.assertEquals(this.left, this.board3.getNeighborCell(this.center, CellDirection.Left));
  }
}
