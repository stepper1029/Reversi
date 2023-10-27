import org.junit.Test;

import model.BasicReversi;
import model.Board;
import model.HexBoard;
import model.ReversiModel;
import view.ReversiView;
import view.HexTextView;

public class testView {
  @Test
  public void testToString() {
    ReversiModel model = new BasicReversi();
    Board board = new HexBoard(3);
    ReversiView tv = new HexTextView(board);
    System.out.println(tv);
  }

}
