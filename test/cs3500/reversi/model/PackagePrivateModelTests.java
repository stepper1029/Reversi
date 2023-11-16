package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Tests for package-private classes/ methods.
 */
public class PackagePrivateModelTests {
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

  // DiskColor tests
  @Test
  public void testGetNextColor() {
    Assert.assertEquals(DiskColor.Black, DiskColor.getNextColor(DiskColor.White));
    Assert.assertEquals(DiskColor.White, DiskColor.getNextColor(DiskColor.Black));
    Assert.assertEquals(DiskColor.Black, DiskColor.getNextColor(DiskColor.White));
  }
  
  // HexCell tests
  // cells for making a board of length 3
  // center cell (0, 0, 0)
  private HexCell center;
  // cells 1 away from the center
  private HexCell left;
  private HexCell upperLeft;
  private HexCell upperRight;
  private HexCell right;
  private HexCell bottomRight;
  private HexCell bottomLeft;
  // cells 2 away from the center
  private HexCell outerLeft;
  private HexCell upperLeftMiddle;
  private HexCell outerUpperLeft;
  private HexCell upperMiddle;
  private HexCell outerUpperRight;
  private HexCell upperRightMiddle;
  private HexCell outerRight;
  private HexCell bottomRightMiddle;
  private HexCell outerBottomRight;
  private HexCell bottomMiddle;
  private HexCell outerBottomLeft;
  private HexCell bottomLeftMiddle;

  // initializes cells to be tested
  private void initCells() {
    this.center = new HexCell(0, 0, 0);
    this.left = new HexCell(-1, 0, 1);
    this.upperLeft = new HexCell(0, -1, 1);
    this.upperRight = new HexCell(1, -1, 0);
    this.right = new HexCell(1, 0, -1);
    this.bottomRight = new HexCell(0, 1, -1);
    this.bottomLeft = new HexCell(-1, 1, 0);
    this.outerLeft = new HexCell(-2, 0, 2);
    this.upperLeftMiddle = new HexCell(-1, -1, 2);
    this.outerUpperLeft = new HexCell(0, -2, 2);
    this.upperMiddle = new HexCell(1, -2, 1);
    this.outerUpperRight = new HexCell(2, -2, 0);
    this.upperRightMiddle = new HexCell(2, -1, -1);
    this.outerRight = new HexCell(2, 0, -2);
    this.bottomRightMiddle = new HexCell(1, 1, -2);
    this.outerBottomRight = new HexCell(0, 2, -2);
    this.bottomMiddle = new HexCell(-1, 2, -1);
    this.outerBottomLeft = new HexCell(-2, 2, 0);
    this.bottomLeftMiddle = new HexCell(-2, 1, 1);
  }

  // HexCell tests
  @Test
  public void testGetCoord() {
    this.initCells();
    Assert.assertEquals(0, this.center.getCoord('q'));
    Assert.assertEquals(0, this.center.getCoord('r'));
    Assert.assertEquals(0, this.center.getCoord('s'));
    Assert.assertEquals(-1, this.left.getCoord('q'));
    Assert.assertEquals(0, this.bottomLeft.getCoord('s'));
    Assert.assertEquals(2, this.bottomMiddle.getCoord('r'));
    Assert.assertEquals(0, this.outerUpperRight.getCoord('s'));
    Assert.assertEquals(-2, this.outerLeft.getCoord('q'));
    Assert.assertEquals(2, this.outerBottomLeft.getCoord('r'));
    Assert.assertEquals(2, this.outerUpperLeft.getCoord('s'));
    Assert.assertEquals(-2, this.outerUpperRight.getCoord('r'));
  }

  @Test
  public void testGetCoordInvalidInput() {
    this.initCells();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.center.getCoord('p'));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.bottomLeft.getCoord('z'));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.upperLeft.getCoord('R'));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.right.getCoord('Q'));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.left.getCoord('S'));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.bottomMiddle.getCoord('!'));
  }

  @Test
  public void testEqualsAndHashcode() {
    this.initCells();
    // testing equals method
    Assert.assertEquals(this.center, new HexCell(0, 0, 0));
    Assert.assertEquals(this.bottomLeft, new HexCell(-1, 1, 0));
    Assert.assertEquals(this.outerUpperLeft, new HexCell(0, -2, 2));
    Assert.assertEquals(this.bottomMiddle, new HexCell(-1, 2, -1));
    Assert.assertNotEquals(this.center, this.upperLeft);
    Assert.assertNotEquals(this.upperRight, this.upperLeft);
    Assert.assertNotEquals(this.outerBottomLeft, this.bottomLeft);
    Assert.assertNotEquals(this.right, this.left);
    // testing hashcode
    Assert.assertEquals(this.center.hashCode(), new HexCell(0, 0, 0).hashCode());
    Assert.assertEquals(this.bottomLeft.hashCode(), new HexCell(-1, 1, 0).hashCode());
    Assert.assertEquals(this.outerBottomRight.hashCode(), new HexCell(0, 2, -2).hashCode());
    Assert.assertNotEquals(this.center.hashCode(), this.left.hashCode());
    Assert.assertNotEquals(this.right.hashCode(), this.upperRight.hashCode());
    Assert.assertNotEquals(this.bottomMiddle.hashCode(), this.upperMiddle.hashCode());
  }

  @Test
  public void testAddVector() {
    this.initCells();
    Assert.assertEquals(this.center, this.center.addVector(new int[] {0, 0, 0}));
    Assert.assertEquals(this.bottomRight, this.center.addVector(new int [] {0, 1, -1}));
    Assert.assertEquals(this.bottomMiddle, this.center.addVector(new int [] {-1, 2, -1}));
    Assert.assertEquals(this.outerBottomLeft, this.bottomLeft.addVector(new int [] {-1, 1, 0}));
    Assert.assertEquals(this.center, this.upperLeft.addVector(new int [] {0, 1, -1}));
    Assert.assertEquals(this.right, this.outerRight.addVector(new int [] {-1, 0, 1}));
    // invalid vector lengths
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.center.addVector(new int[] {}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.bottomLeft.addVector(new int[] {1}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.upperLeft.addVector(new int[] {-1, 0}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.upperMiddle.addVector(new int[] {1, 3, 1, 7, 3}));
    // invalid vector values
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.upperMiddle.addVector(new int[] {3, -3, 1}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.outerRight.addVector(new int[] {-1, -1, 0}));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.upperMiddle.addVector(new int[] {1, -2, 2}));
  }

  @Test
  public void testContainsAllCoords() {
    this.initCells();
    Assert.assertTrue(this.center.containsAllCoords(new int[] {0, 0, 0}));
    Assert.assertTrue(this.upperLeft.containsAllCoords(new int[] {-1, 1, 0}));
    Assert.assertTrue(this.upperMiddle.containsAllCoords(new int[] {1, -2, 1}));
    Assert.assertTrue(this.outerUpperLeft.containsAllCoords(new int[] {0, 2, -2}));
    Assert.assertTrue(this.right.containsAllCoords(new int[] {1, 0, -1}));
    Assert.assertFalse(this.center.containsAllCoords(new int[] {0, 2, -2}));
    Assert.assertFalse(this.left.containsAllCoords(new int[] {1, -1, 0}));

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
    Assert.assertEquals(this.left,
            this.board3.getNeighborCell(this.center, CellDirection.Left));
    Assert.assertEquals(this.outerUpperLeft,
            this.board3.getNeighborCell(this.upperLeft, CellDirection.UpperLeft));
    Assert.assertEquals(this.bottomRight,
            this.board3.getNeighborCell(this.bottomMiddle, CellDirection.UpperRight));
    Assert.assertEquals(this.upperLeft,
            this.board4.getNeighborCell(this.upperLeftMiddle, CellDirection.Right));
    Assert.assertEquals(this.upperRightMiddle,
            this.board4.getNeighborCell(this.outerUpperRight, CellDirection.BottomRight));
    Assert.assertEquals(this.bottomMiddle,
            this.board4.getNeighborCell(this.bottomRight, CellDirection.BottomLeft));
    // moving off of the board (invalid tests)
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.bottomLeftMiddle, CellDirection.Left));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.outerUpperLeft, CellDirection.UpperLeft));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.upperRightMiddle, CellDirection.UpperRight));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.bottomRightMiddle, CellDirection.Right));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.bottomMiddle, CellDirection.BottomRight));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this. board3.getNeighborCell(this.outerBottomLeft, CellDirection.BottomLeft));
  }

  @Test
  public void testGetRow() {
    this.initHexBoards();
    this.initCells();
    ReversiCell[] row0 =
            new ReversiCell[]{this.outerUpperLeft, this.upperMiddle, this.outerUpperRight};
    ReversiCell[] row2 =
            new ReversiCell[]{this.outerLeft, this.left, this.center, this.right, this.outerRight};
    Assert.assertArrayEquals(row0, this.board3.getRow(0));
    Assert.assertArrayEquals(row2, this.board3.getRow(2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board3.getRow(5));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board3.getRow(-1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board4.getRow(7));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board4.getRow(-1));
  }

  @Test
  public void testGetBoardSize() {
    this.initHexBoards();
    Assert.assertEquals(3, this.board3.getBoardSize());
    Assert.assertEquals(4, this.board4.getBoardSize());
  }

  // tests placeDesk, getCells, and isEmpty
  @Test
  public void testPlaceDiskGetCellsisEmpty() {
    this.initHexBoards();
    this.initCells();
    Assert.assertEquals(new ArrayList<>(), this.board3.getCells(DiskColor.White));
    Assert.assertEquals(new ArrayList<>(), this.board3.getCells(DiskColor.Black));
    Assert.assertTrue(this.board3.isEmpty(this.upperLeft));
    Assert.assertTrue(this.board3.isEmpty(this.right));
    Assert.assertTrue(this.board3.isEmpty(this.upperRight));
    Assert.assertTrue(this.board3.isEmpty(this.left));
    this.board3.placeDisk(this.upperLeft, DiskColor.Black);
    this.board3.placeDisk(this.right, DiskColor.Black);
    this.board3.placeDisk(this.upperRight, DiskColor.White);
    this.board3.placeDisk(this.left, DiskColor.White);
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.upperLeft, this.right)),
            this.board3.getCells(DiskColor.Black));
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.upperRight, this.left)),
            this.board3.getCells(DiskColor.White));
    Assert.assertFalse(this.board3.isEmpty(this.upperLeft));
    Assert.assertFalse(this.board3.isEmpty(this.right));
    Assert.assertFalse(this.board3.isEmpty(this.upperRight));
    Assert.assertFalse(this.board3.isEmpty(this.left));
  }

  @Test
  public void testGetCellsBetween() {
    this.initHexBoards();
    this.initCells();
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.upperRight)),
            this.board3.getCellsBetween(this.outerUpperRight, this.center));
    Assert.assertEquals(new ArrayList<ReversiCell>(Arrays.asList(this.bottomLeft,
                    this.center, this.upperRight)),
            this.board3.getCellsBetween(this.outerUpperRight, this.outerBottomLeft));
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.center)),
            this.board3.getCellsBetween(this.upperLeft, this.bottomRight));
    // tests with no cells in between
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCellsBetween(this.center, this.bottomRight));
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCellsBetween(this.right, this.upperRightMiddle));
    // tests with cells not in a row
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board3.getCellsBetween(this.upperLeft, this.right));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.board3.getCellsBetween(this.left, this.bottomRightMiddle));
  }

  @Test
  public void testGetTotalNumCells() {
    this.initHexBoards();
    this.initCells();
    Assert.assertEquals(19, this.board3.getTotalNumCells());
    Assert.assertEquals(37, this.board4.getTotalNumCells());
  }

  @Test
  public void testFlipDisk() {
    this.initHexBoards();
    this.initCells();
    Assert.assertTrue(this.board3.isEmpty(this.center));
    this.board3.placeDisk(this.center, DiskColor.White);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.center)),
            this.board3.getCells(DiskColor.White));
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCells(DiskColor.Black));
    this.board3.flipDisk(this.center);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.center)),
            this.board3.getCells(DiskColor.Black));
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCells(DiskColor.White));
    // flips disk back to see ensure if flips back to the original color
    this.board3.flipDisk(this.center);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.center)),
            this.board3.getCells(DiskColor.White));
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCells(DiskColor.Black));
    // places another piece
    this.board3.placeDisk(this.upperLeft, DiskColor.White);
    Assert.assertEquals(new ArrayList<ReversiCell>(Arrays.asList(this.center, this.upperLeft)),
            this.board3.getCells(DiskColor.White));
    Assert.assertEquals(new ArrayList<ReversiCell>(),
            this.board3.getCells(DiskColor.Black));
    this.board3.flipDisk(this.upperLeft);
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.center)),
            this.board3.getCells(DiskColor.White));
    Assert.assertEquals(new ArrayList<ReversiCell>(Collections.singletonList(this.upperLeft)),
            this.board3.getCells(DiskColor.Black));
  }

  @Test
  public void testCopy() {
    this.initHexBoards();
    Board copiedBoard = this.board3.copy();
    Assert.assertEquals(this.board3.getBoardSize(), 3);
    Assert.assertEquals(copiedBoard.getBoardSize(), 3);
    Assert.assertEquals(this.board3.getCells(DiskColor.Black).size(), 0);
    Assert.assertEquals(this.board3.getCells(DiskColor.White).size(), 0);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.Black).size(), 0);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.White).size(), 0);
    // adding a black disk to the copied board and a white disk to the regular board
    copiedBoard.placeDisk(new HexCell(0, -1, 1), DiskColor.Black);
    this.board3.placeDisk(new HexCell(2, -1, -1), DiskColor.White);
    Assert.assertEquals(this.board3.getCells(DiskColor.Black).size(), 0);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.Black).size(), 1);
    Assert.assertEquals(this.board3.getCells(DiskColor.White).size(), 1);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.White).size(), 0);
    // flips the disks in each board
    copiedBoard.flipDisk(new HexCell(0, -1, 1));
    this.board3.flipDisk(new HexCell(2, -1, -1));
    Assert.assertEquals(this.board3.getCells(DiskColor.White).size(), 0);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.White).size(), 1);
    Assert.assertEquals(this.board3.getCells(DiskColor.Black).size(), 1);
    Assert.assertEquals(copiedBoard.getCells(DiskColor.Black).size(), 0);
  }

  // BasicReversi null constructor test
  @Test
  public void testNullBoard() {
    Assert.assertThrows(NullPointerException.class, () ->
            new BasicReversi(null));
  }
}