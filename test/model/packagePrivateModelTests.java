package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class packagePrivateModelTests {
  HexCell cell1;
  HexCell cell2;
  HexCell cell3;
  HexModel hexModel;

  private void initData() {
    this.cell1 = new HexCell(1, 1, -2);
    this.cell2 = new HexCell(1, -2, 1);
    this.cell3 = new HexCell(-1, 2, -1);
    this.hexModel = new HexModel(4);
  }

  @Test
  public void testIsBlack() {
    HexModel model = new HexModel(3);
    model.startGame();
    Assert.assertTrue(model.isBlack(new HexCell(0, -1, 1)));
  }

  @Test
  public void testGetCellsBetween() {
    this.initData();
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.hexModel.getDiscsBetweenCells(this.cell1, this.cell3));
    List<HexCell> expected = new ArrayList<>(Arrays.asList(new HexCell(1, -1, 0),
            new HexCell(1, 0, -1)));
    Assert.assertEquals(this.hexModel.getDiscsBetweenCells(this.cell2, this.cell1), expected);
  }
}
