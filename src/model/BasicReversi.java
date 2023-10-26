package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicReversi implements ReversiModel {

  DiscColor currColor;
  private int numPasses;

  public BasicReversi() {
    this.numPasses = 0;
  }


  private void setBoard(Board board) {
    if (board.getBoardSize() > 2) {


      board.placeDisc(new HexCell(0, -1, 1), DiscColor.Black);
      board.placeDisc(new HexCell(1, 0, -1), DiscColor.Black);
      board.placeDisc(new HexCell(-1, 1, 0), DiscColor.Black);
      board.placeDisc(new HexCell(1, -1, 0), DiscColor.White);
      board.placeDisc(new HexCell(0, 1, -1), DiscColor.White);
      board.placeDisc(new HexCell(-1, 0, 1), DiscColor.White);
    }
  }
}
