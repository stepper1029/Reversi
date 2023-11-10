package cs3500.reversi.strategy;
import java.util.Comparator;
import cs3500.reversi.model.ReversiCell;

/**
 * Class to compare two ReversiCells and determine which is the topmost, leftmost cell between the
 * two.
 */
class TopLeftComparator implements Comparator<ReversiCell> {
  /**
   * Function to compare the two cells. Top-ness takes precedent over left-ness:
   * A cell in the furthest right spot in the top row will have a greater value than a cell that
   * is in the left-most spot in the second row.
   *
   * @param c1 the first object to be compared.
   * @param c2 the second object to be compared.
   * @return 0 if the cells are in the same position
   *         < 0 if the second cell is closer to the top of the board and further to the left
   *         > 0 if the first cell is closer to the top of the board and further to the left
   */
  @Override
  public int compare(ReversiCell c1, ReversiCell c2) {
    int rComparison = Integer.compare(c1.getCoord('r'), c2.getCoord('r'));

    if (rComparison != 0) {
      return rComparison;
    }
    else {
      return Integer.compare(c1.getCoord('q'), c2.getCoord('q'));
    }
  }
}
