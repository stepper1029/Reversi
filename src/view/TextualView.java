package view;

import java.util.List;
import java.util.Objects;

import model.HexagonalReversi;
import model.Cell;

public class TextualView implements ReversiView {
  private HexagonalReversi hexagonalReversi;

  public TextualView(HexagonalReversi hexagonalReversi) {
    this.hexagonalReversi = Objects.requireNonNull(hexagonalReversi);
  }

  public String toString() {
    String output = "";
    List<List<Cell>> cells = hexagonalReversi.getCells();
    int boardSize = hexagonalReversi.getBoardSize();
    int maxWidth = (((boardSize * 2) - 1) * 2) - 1;
    int numSpacesOnEachSide = (maxWidth - (boardSize * 2) - 1) / 2;

    for(int row = 0; row < cells.size(); row ++) {
      int widthCount = 0;
      while (widthCount <= numSpacesOnEachSide) {
        output += " ";
        widthCount++;
      }
      for (Cell c : cells.get(row)) {
        System.out.println(c.toString());
        System.out.println("isBlack: " + hexagonalReversi.isBlack(c));
        System.out.println("isWhite: " + hexagonalReversi.isWhite(c));
        if (hexagonalReversi.isBlack(c)) {
          output += "X ";
          widthCount += 2;
        } else if (hexagonalReversi.isWhite(c)) {
          output += "O ";
          widthCount += 2;
        } else {
          output += "_ ";
          widthCount += 2;
        }
      }
      for (int i = 0; i < numSpacesOnEachSide; i++) {
        output += " ";
      }
      if (row < cells.size()/2) {
        numSpacesOnEachSide--;
      } else {
        numSpacesOnEachSide++;
      }
      output += "\n";
    }
    return output;
  }
}
