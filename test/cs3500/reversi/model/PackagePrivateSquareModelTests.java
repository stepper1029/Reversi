package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Test;

public class PackagePrivateSquareModelTests {
  // square cells to initialize
  private SquareCell cell00;
  private SquareCell cell10;
  private SquareCell cell20;
  private SquareCell cell30;
  private SquareCell cell01;
  private SquareCell cell11;
  private SquareCell cell21;
  private SquareCell cell31;
  private SquareCell cell02;
  private SquareCell cell12;
  private SquareCell cell22;

  // initializing square cells
  private void initCells() {
    this.cell00 = new SquareCell(0, 0);
    this.cell10 = new SquareCell(1, 0);
    this.cell20 = new SquareCell(2, 0);
    this.cell30 = new SquareCell(3, 0);
    this.cell01 = new SquareCell(0, 1);
    this.cell11 = new SquareCell(1, 1);
    this.cell21 = new SquareCell(2, 1);
    this.cell31 = new SquareCell(3, 1);
    this.cell02 = new SquareCell(0, 2);
    this.cell12 = new SquareCell(1, 2);
    this.cell22 = new SquareCell(2, 2);
  }

  @Test
  public void testGetCoord() {
    this.initCells();
    Assert.assertEquals(0, this.cell00.getCoord('x'));
    Assert.assertEquals(0, this.cell00.getCoord('y'));
    Assert.assertEquals(0, this.cell01.getCoord('x'));
    Assert.assertEquals(1, this.cell01.getCoord('y'));
    Assert.assertEquals(2, this.cell21.getCoord('x'));
    Assert.assertEquals(1, this.cell31.getCoord('y'));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.cell30.getCoord('f'));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.cell30.getCoord('q'));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.cell00.getCoord('r'));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.cell20.getCoord('s'));
  }

  @Test
  public void testAddVector() {
    this.initCells();
    Assert.assertEquals(this.cell00, this.cell00.addVector(new int[]{0, 0}));
    Assert.assertEquals(this.cell10, this.cell00.addVector(new int[]{1, 0}));
    Assert.assertEquals(this.cell11, this.cell00.addVector(new int[]{1, 1}));
    Assert.assertEquals(this.cell31, this.cell21.addVector(new int[]{1, 0}));
    Assert.assertEquals(this.cell20, this.cell31.addVector(new int[]{-1, -1}));
    Assert.assertThrows(IllegalArgumentException.class, () -> this.cell00.addVector(new int[]{}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.cell21.addVector(new int[]{1, 2, 3}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.cell31.addVector(new int[]{1}));
  }

  // SquareBoard
  private SquareBoard board4;

  // initialize the board
  private void initBoard() {
    this.board4 = new SquareBoard(4);
  }

  // SquareBoard tests
  @Test
  public void testConstructor() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(-1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(2));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(3));
    Assert.assertThrows(IllegalArgumentException.class, () -> new HexBoard(1));
  }

  @Test
  public void testGetNeighborCell() {
    Assert.assertEquals(this.cell00,
            this.board4.getNeighborCell(this.cell11, CellDirection.UpperLeft));
    Assert.assertEquals(this.cell10,
            this.board4.getNeighborCell(this.cell11, CellDirection.Up));
    Assert.assertEquals(this.cell20,
            this.board4.getNeighborCell(this.cell11, CellDirection.UpperRight));
    Assert.assertEquals(this.cell21,
            this.board4.getNeighborCell(this.cell11, CellDirection.Right));
    Assert.assertEquals(this.cell22,
            this.board4.getNeighborCell(this.cell11, CellDirection.BottomRight));
    Assert.assertEquals(this.cell12,
            this.board4.getNeighborCell(this.cell11, CellDirection.Down));
    Assert.assertEquals(this.cell02,
            this.board4.getNeighborCell(this.cell11, CellDirection.BottomLeft));
    Assert.assertEquals(this.cell01,
            this.board4.getNeighborCell(this.cell11, CellDirection.Left));
    // moving off of the board (invalid tests)
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board4.getNeighborCell(this.bottomLeftMiddle, CellDirection.Left));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board4.getNeighborCell(this.outerUpperLeft, CellDirection.UpperLeft));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.upperRightMiddle, CellDirection.UpperRight));
  }
}
