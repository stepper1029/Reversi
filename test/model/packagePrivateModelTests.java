package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class packagePrivateModelTests {
  Cell cell1;
  Cell cell2;
  Cell cell3;
  HexagonalReversi hexagonalReversi;

  private void initData() {
    this.cell1 = new Cell(1, 1, -2);
    this.cell2 = new Cell(1, -2, 1);
    this.cell3 = new Cell(-1, 2, -1);
    this.hexagonalReversi = new HexagonalReversi(4);
  }

  @Test
  public void testGetCellsBetween() {
    this.initData();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.hexagonalReversi.getDiscsBetweenCells(this.cell1, this.cell3));
    List<Cell> expected = new ArrayList<>(Arrays.asList(new Cell(1, -1, 0),
            new Cell(1, 0, -1)));
    Assert.assertEquals(this.hexagonalReversi.getDiscsBetweenCells(this.cell2, this.cell1), expected);
  }
}
