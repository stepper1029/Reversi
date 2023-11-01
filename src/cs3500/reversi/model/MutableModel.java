package cs3500.reversi.model;

/**
 * An interface to represent the mutable methods of a model. The Model is responsible for keeping
 * track of the rules and moves of the game. The two phase interface is used to restrict access
 * to the methods of a model, only giving observable methods to classes that
 * should not be able to mutate, and giving full access to those who should.
 */
public interface MutableModel extends ReadOnlyModel {

  /**
   * Allows the current player to pass their turn,
   * if they have no moves or do not want to make a move
   * and changes the current color (whose turn it is).
   */
  void pass();

  /**
   * Places a disc of the color of the current player on the board
   * and changes the current color (whose turn it is).
   * @param cell given cell to place the disc
   * @throws IllegalStateException if placing a disc at the given cell is not a legal move.
   * @throws IllegalArgumentException if the given cell is not valid or if it is not empty
   */
  void place(ReversiCell cell);
}
