package model;

import org.junit.Assert;
import org.junit.Test;

import view.HexTextView;
import view.ReversiView;

public class PackagePrivateModelTests {
  HexCell cell1;
  HexCell cell2;
  HexCell cell3;
  Board hexBoard;
  BasicReversi hexModel;

  private void initData() {
    this.cell1 = new HexCell(1, 1, -2);
    this.cell2 = new HexCell(1, -2, 1);
    this.cell3 = new HexCell(-1, 2, -1);
    hexBoard = new HexBoard(3);
    this.hexModel = new BasicReversi(hexBoard);
  }
}
