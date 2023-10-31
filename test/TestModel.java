import org.junit.Assert;
import org.junit.Test;

import model.AbstractTestClass;
import model.DiscColor;
import model.HexCell;

/**
 * Class to test the model and related classes.
 */
public class TestModel extends AbstractTestClass {
  // DiscColor tests
  @Test
  public void testGetNextColor() {
    Assert.assertEquals(DiscColor.Black, DiscColor.getnextColor(DiscColor.White));
    Assert.assertEquals(DiscColor.White, DiscColor.getnextColor(DiscColor.Black));
    Assert.assertEquals(DiscColor.Black, DiscColor.getnextColor(DiscColor.White));
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

  // model tests
}
