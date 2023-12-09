package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cs3500.reversi.controller.ModelFeatures;

/**
 * Class BasicReversi represents a game of Reversi with standard rules and gameplay. Implements
 * MutableModel interface. Class is package private because the controller and view should go
 * through the interface and not the class.
 */
class HexReversi extends AbstractModel {

  /**
   * Constructor for BasicReversi initializes all fields. Takes in a board (which has a size, shape,
   * and cell structure) but is completely empty. Constructor sets the board with the initial
   * starting piece required to play the game. numPasses is initialized to zero because when no
   * moves have been made, no player could have passed. currColor begins at black as is
   * convention in Reversi, black moves first. The ObserverMap is not initialized because it is not
   * needed for testing, so it is initialized only when the first value is added to it.
   * Package-private so that classes outside of this package can only create a new model through
   * the factory class.
   *
   * @param board board with a size and shape that the game should be played on.
   */
  HexReversi(Board board) {
    super(board);
  }

  /**
   * Constructor to initialize the fields to all the given values. Used for creating a copy
   * of the model. Private because it is only used to create a copy of the model within this class.
   *
   * @param board     the board that represents this placement of the pieces in this game
   * @param numPasses the number of passes in a row that have occurred
   * @param currColor the current color of the disc being placed
   */
  private HexReversi(Board board, int numPasses, DiskColor currColor) {
    super(board);
  }

  @Override
  public int getNumRows() {
    return (this.board.getBoardSize() * 2) - 1;
  }

  public MutableModel copy() {
    return new HexReversi(this.board.copy(), this.numPasses, this.currColor);
  }
}
