import org.junit.Test;

import model.HexModel;
import view.ReversiView;
import view.TextualView;

public class testView {
  @Test
  public void testToString() {
    HexModel model = new HexModel(3);
    model.startGame();
    ReversiView tv = new TextualView(model);
    System.out.println(tv);
  }

}
