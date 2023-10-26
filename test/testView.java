import org.junit.Test;

import model.HexagonalReversi;
import view.ReversiView;
import view.TextualView;

public class testView {
  @Test
  public void testToString() {
    HexagonalReversi model = new HexagonalReversi(3);
    model.startGame();
    ReversiView tv = new TextualView(model);
    System.out.println(tv);
  }

}
