import org.junit.Assert;
import org.junit.Test;

import model.HexagonalReversi;
import model.Cell;

public class testModel {

  @Test
  public void testIsBlack() {
    HexagonalReversi model = new HexagonalReversi(3);
    model.startGame();
    Assert.assertTrue(model.isBlack(new Cell(0, -1, 1)));
  }
}
