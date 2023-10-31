package model;

import model.HexCell;
import model.MutableModel;
import model.ReversiCreator;

/**
 * Class for initializing data to be reused in multiple test classes. Additional tests to be
 * reused may be added in the future.
 */
public abstract class AbstractTestClass {
  // cells for making a board of length 3
  // center cell (0, 0, 0)
  protected HexCell center;
  // cells 1 away from the center
  protected HexCell left;
  protected HexCell upperLeft;
  protected HexCell upperRight;
  protected HexCell right;
  protected HexCell bottomRight;
  protected HexCell bottomLeft;
  // cells 2 away from the center
  protected HexCell outerLeft;
  protected HexCell upperLeftMiddle;
  protected HexCell outerUpperLeft;
  protected HexCell upperMiddle;
  protected HexCell outerUpperRight;
  protected HexCell upperRightMiddle;
  protected HexCell outerRight;
  protected HexCell bottomRightMiddle;
  protected HexCell outerBottomRight;
  protected HexCell bottomMiddle;
  protected HexCell outerBottomLeft;

  // initializes cells to be tested
  protected void initCells() {
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
  }

  // basic models to test
  protected MutableModel basicReversi3;
  protected MutableModel basicReversi4;

  // initializes models to test
  protected void initModels() {
    this.basicReversi3 = ReversiCreator.create(3);
    this.basicReversi4 = ReversiCreator.create(4);
  }
}
