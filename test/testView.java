import org.junit.Test;

import model.BasicReversi;
import view.ReversiView;
import view.HexTextView;

public class testView {
  @Test
  public void testToString() {
    BasicReversi model = new BasicReversi(3);
    model.startGame();
    ReversiView tv = new HexTextView(model);
    System.out.println(tv);
  }

}
