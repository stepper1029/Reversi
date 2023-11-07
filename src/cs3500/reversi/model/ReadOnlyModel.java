package cs3500.reversi.model;

import java.util.List;

/**
 * An interface to represent the mutable methods of a model. The two phase interface is used
 * to restrict access to the methods of a model, only giving observable methods to classes that
 * should not be able to mutate, and giving full access to those who should. This interface will
 * still be public, so it can be called when needed, but each class that has a model field will be
 * given only one interface.
 */
public interface ReadOnlyModel {

  /**
   * Observes the size of the board, which we call the number of cells on one side length.
   * This is in the interface because the view needs to have access to this observable method.
   * @return int side length in cells
   */
  int getBoardSize();

  /**
   * Observes the color of a disk on the given cell. This is in the interface because the view
   * needs to have access to this observable method.
   *
   * @param cell the cell you want to observe.
   * @return the color of the disk
   * @throws IllegalArgumentException if the given cell is empty
   */
  DiskColor getColorAt(ReversiCell cell);

  /**
   * Observes the score for a given player (by their color pieces). This is in the interface
   * because the view and controller need to have access to this observable method.
   *
   * @param color the color which indicates which player
   * @return the number of pieces that player has on the board.
   */
  int getScore(DiskColor color);

  /**
   * Checks if the game is over. Game is over if the board is full, one player has no pieces left,
   * or both players pass consecutively. This is in the interface because the view and
   * controller need to have access to this observable method.
   *
   * @return boolean is the game over.
   */
  boolean isGameOver();

  /**
   * Observes all possible cells where the given player color can place a disk and make a legal
   * move (a move that flips one or more disks of the other color). This is in the interface
   * because the view needs to have access to this observable method so all possible moves can be
   * highlighted and shown to the player.
   * @param color the given color to find possible moves for
   * @return a list of cells where the player can move
   */
  List<ReversiCell> allPossibleMoves(DiskColor color);

  /**
   * Observes the number of rows in the board or, the board height. This is in the interface
   * because the view needs to have access to this observable method.
   *
   * @return int number of rows.
   */
  int getNumRows();

  /**
   * Observes the size of a row, or the width of that row. This is in the interface because the
   * view needs to have access to this observable method.
   *
   * @param rowNum index of the row you want to observe, starting at zero indicating the topmost
   *               row of the board.
   * @return the width of the row, how many cells are in that row (have that r value)
   * @throws IllegalArgumentException if the rowNum is not a valid row index of the board
   */
  int getRowSize(int rowNum);

  /**
   * Gets the cell based on the position in the board rather than q, r, s coordinates. Is used to
   * convert between coordinate systems.The player only communicates with the view, therefore only
   * has access to the position of the cells relative to the board, and knows nothing of the
   * coordinate system used or the cell's q, r, s values. This is in the interface because the
   * view and controller need to have access to this observable method.
   *
   * @param numRow  row index starting at zero being the topmost horizontal row of the board.
   * @param numCell cell index starting at zero being the leftmost cell in the row.
   * @return the cell at the specified position in the board.
   * @throws IllegalArgumentException if index numRow or numCell is invalid
   */
  ReversiCell getCellAt(int numRow, int numCell);

  /**
   * Observes if there is a disk in the given cell. Used to see if it is possible to place a disk
   * in the cell, or if it has already been captured. This is in the interface because the
   * controller and view need to have access to this observable method.
   *
   * @param c given cell
   * @return if the cell has a disk in it.
   * @throws IllegalArgumentException if the cell is not in the board.
   */
  boolean isEmpty(ReversiCell c);

  /**
   * Determines which player's turn it is to move.
   *
   * @return the color corresponding to which player's turn it is
   */
  DiskColor getTurn();
}
