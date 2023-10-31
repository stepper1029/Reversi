import org.junit.Assert;
import org.junit.Test;

import model.HexCell;
import model.ReversiCreator;
import model.ReadOnlyModel;
import model.MutableModel;
import view.ReversiView;
import view.HexTextView;
import model.ReversiCell;
import model.DiscColor;

public class TestView {

  @Test
  public void testToStringSizeThree() {
    ReadOnlyModel model = ReversiCreator.create(3);
    ReversiView tv = new HexTextView(model);
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
    ReadOnlyModel model = ReversiCreator.create(6);
    ReversiView tv = new HexTextView(model);
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

  // todo finish debugging
  @Test
  public void testToStringAfterMovesMade() {
    MutableModel model = ReversiCreator.create(3);
    ReversiCell cell = new HexCell(1, 1, -2);
    Assert.assertTrue(model.isEmpty(cell));
    model.place(cell);
    ReversiView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected = "  _ _ _\n"
            + " _ X O _\n"
            + "_ O _ X _\n"
            + " _ X X X\n"
            + "  _ _ _";
    Assert.assertEquals(DiscColor.Black, model.getColorAt(cell));
    Assert.assertEquals(4, model.getRowSize(3));
    Assert.assertEquals(cell, model.getCellAt(3, 3));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testToStringAfterAMoveFlipsTwo() {
    MutableModel model = ReversiCreator.create(3);
    model.place(new HexCell(1, -2, 1));
    model.setNextColor();
    model.place(new HexCell(2, -1, -1));
    model.setNextColor();
    model.place(new HexCell(1, 1, -2));
    ReversiView tv = new HexTextView(model);
    String actual = tv.toString();
    String expected = "";
    Assert.assertEquals(expected, actual);
  }
}
