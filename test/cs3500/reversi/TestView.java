package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.view.text.HexTextView;
import cs3500.reversi.view.text.SquareTextView;

/**
 * Tests the that the view accurately represents the game state in
 * a game of Reversi.
 */
public class TestView {

  @Test
  public void testToStringSizeThree() {
    ReadOnlyModel model = ReversiCreator.createHex(3);
    HexTextView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected = "  _ _ _\n"
            + " _ X O _\n"
            + "_ O _ X _\n"
            + " _ X O _\n"
            + "  _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringSizeSix() {
    ReadOnlyModel model = ReversiCreator.createHex(6);
    HexTextView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected = "     _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + " _ _ _ _ X O _ _ _ _\n"
            + "_ _ _ _ O _ X _ _ _ _\n"
            + " _ _ _ _ X O _ _ _ _\n"
            + "  _ _ _ _ _ _ _ _ _\n"
            + "   _ _ _ _ _ _ _ _\n"
            + "    _ _ _ _ _ _ _\n"
            + "     _ _ _ _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterMovesMade() {
    MutableModel model = ReversiCreator.createHex(3);
    ReversiCell cell = model.getCellAt(3, 3);
    Assert.assertTrue(model.isEmpty(cell));
    model.place(cell, DiskColor.Black);
    HexTextView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected = "  _ _ _\n"
            + " _ X O _\n"
            + "_ O _ X _\n"
            + " _ X X X\n"
            + "  _ _ _";
    Assert.assertEquals(DiskColor.Black, model.getColorAt(cell));
    Assert.assertEquals(4, model.getRowSize(3));
    Assert.assertEquals(cell, model.getCellAt(3, 3));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterAMoveFlipsInTwoDirections() {
    MutableModel model = ReversiCreator.createHex(3);
    model.place(model.getCellAt(0, 1), DiskColor.Black);
    model.place(model.getCellAt(1, 3), DiskColor.White);
    model.place(model.getCellAt(3, 3), DiskColor.Black);
    HexTextView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected =
            "  _ X _\n"
                    + " _ X X O\n"
                    + "_ O _ X _\n"
                    + " _ X X X\n"
                    + "  _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterAMoveFlipsTwoInARow() {
    MutableModel model = ReversiCreator.createHex(3);
    model.place(model.getCellAt(0, 1), DiskColor.Black);
    model.place(model.getCellAt(1, 3), DiskColor.White);
    model.place(model.getCellAt(3, 3), DiskColor.Black);
    model.place(model.getCellAt(1, 0), DiskColor.White);
    HexTextView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected =
            "  _ X _\n"
                    + " O O O O\n"
                    + "_ O _ X _\n"
                    + " _ X X X\n"
                    + "  _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testNull() {
    Assert.assertThrows(NullPointerException.class, () -> new HexTextView(null));
  }

  @Test
  public void testToStringSizeThreeSquare() {
    ReadOnlyModel model = ReversiCreator.createSquare(4);
    SquareTextView tv = new SquareTextView(model);
    String actual = tv.toString();
    String expected = "_ _ _ _\n"
            + "_ X O _\n"
            + "_ O X _\n"
            + "_ _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringSizeSixSquare() {
    ReadOnlyModel model = ReversiCreator.createSquare(6);
    SquareTextView tv = new SquareTextView(model);
    String actual = tv.toString();
    String expected = "_ _ _ _ _ _\n"
            + "_ _ _ _ _ _\n"
            + "_ _ X O _ _\n"
            + "_ _ O X _ _\n"
            + "_ _ _ _ _ _\n"
            + "_ _ _ _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterMovesMadeSquare() {
    MutableModel model = ReversiCreator.createSquare(4);
    ReversiCell cell = model.getCellAt(3, 1);
    Assert.assertTrue(model.isEmpty(cell));
    model.place(cell, DiskColor.Black);
    SquareTextView tv = new SquareTextView(model);
    String actual = tv.toString();
    String expected =
              "_ _ _ _\n"
            + "_ X O _\n"
            + "_ X X _\n"
            + "_ X _ _";
    Assert.assertEquals(DiskColor.Black, model.getColorAt(cell));
    Assert.assertEquals(4, model.getRowSize(3));
    Assert.assertEquals(cell, model.getCellAt(3, 1));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterAMoveFlipsInTwoDirectionsSquare() {
    MutableModel model = ReversiCreator.createSquare(3);
    model.place(model.getCellAt(0, 1), DiskColor.Black);
    model.place(model.getCellAt(1, 3), DiskColor.White);
    model.place(model.getCellAt(3, 3), DiskColor.Black);
    SquareTextView tv = new SquareTextView(model);
    String actual = tv.toString();
    String expected = "_ _ X _\n"
                    + "_ X X O\n"
                    + "_ X X X\n"
                    + "_ _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterAMoveFlipsTwoInARowSquare() {
    MutableModel model = ReversiCreator.createSquare(3);
    model.place(model.getCellAt(0, 1), DiskColor.Black);
    model.place(model.getCellAt(1, 3), DiskColor.White);
    model.place(model.getCellAt(3, 3), DiskColor.Black);
    model.place(model.getCellAt(1, 0), DiskColor.White);
    SquareTextView tv = new SquareTextView(model);
    String actual = tv.toString();
    String expected = "_ _ X _\n"
                    + "O O O O\n"
                    + "_ X X X\n"
                    + "_ _ _ _";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testNullSquare() {
    Assert.assertThrows(NullPointerException.class, () -> new SquareTextView(null));
  }
}
