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
  BasicReversi hexModel;

  private void initData() {
    this.cell1 = new HexCell(1, 1, -2);
    this.cell2 = new HexCell(1, -2, 1);
    this.cell3 = new HexCell(-1, 2, -1);
    this.hexModel = new BasicReversi(4);
  }
}
