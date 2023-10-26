import org.junit.Assert;
import org.junit.Test;

import model.HexagonalReversi;
import model.Cell;
import view.ReversiView;
import view.TextualView;

public class testModel {
  @Test
  public void testToString() {
    HexagonalReversi model = new HexagonalReversi(3);
    model.startGame();
    ReversiView tv = new TextualView(model);
    System.out.println(tv.toString());
  }

  @Test
  public void testIsBlack() {
    HexagonalReversi model = new HexagonalReversi(3);
    model.startGame();
    Assert.assertTrue(model.isBlack(new Cell(0, -1, 1)));
  }
}
