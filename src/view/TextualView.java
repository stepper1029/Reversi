package view;

import java.util.List;
import java.util.Objects;

import model.HexModel;
import model.ReversiCell;

public class TextualView implements ReversiView {
  private final HexModel hexModel;

  public TextualView(HexModel hexModel) {
    this.hexModel = Objects.requireNonNull(hexModel);
  }

  public String toString() {
    String output = "";
    List<List<ReversiCell>> cells = hexModel.getCells();
    int boardSize = hexModel.getBoardSize();
    int maxWidth = (((boardSize * 2) - 1) * 2) - 1;
    int numSpacesOnEachSide = (maxWidth - (boardSize * 2) - 1) / 2;

    for(int row = 0; row < cells.size(); row ++) {
      int widthCount = 0;
      while (widthCount <= numSpacesOnEachSide) {
        output += " ";
        widthCount++;
      }
      for (ReversiCell c : cells.get(row)) {
        if (hexModel.isBlack(c)) {
          output += "X ";
          widthCount += 2;
        } else if (hexModel.isWhite(c)) {
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
