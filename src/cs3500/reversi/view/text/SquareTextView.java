package cs3500.reversi.view.text;

import java.util.Objects;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class SquareTextView extends AbstractTextView {

  public SquareTextView(ReadOnlyModel model) {
    super();
    this.model = Objects.requireNonNull(model);
  }


  @Override
  public String toString() {
    String output = "";
    for (int numRow = 0; numRow < model.getNumRows(); numRow++) {
      output += cellIterator(output, numRow);
    }
    return output;
  }
}
