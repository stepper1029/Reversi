package cs3500.reversi.model;

import cs3500.reversi.controller.ModelFeatures;

/**
 * An interface to represent the mutable methods of a model. The Model is responsible for keeping
 * track of the rules and moves of the game. The two phase interface is used to restrict access
 * to the methods of a model, only giving observable methods to classes that
 * should not be able to mutate, and giving full access to those who should. This interface will
 * still be public, so it can be called when needed, but each class that has a model field will be
 * given only one interface.
 */
public interface MutableModel extends ReadOnlyModel {
  /**
   * Adds a new listener with the given disk color to the observerMap.
   *
   * @param color the color corresponding to this observer
   * @param listener the listener to add
   */
  void addListener(DiskColor color, ModelFeatures listener);

  /**
   * Starts the game of Reversi after the fields have been initialized and sends the first
   * your turn notification to the first player.
   */
  void startGame();

  /**
   * Allows the given player to pass their turn, if they have no moves or do not want to make
   * a move and changes the current color (whose turn it is). In the interface because every model
   * needs to have the pass functionality, and it needs to called by the controller. Enforces
   * that the correct player is passing.
   *
   * @param color the color corresponding to the player who is passing
   * @throws IllegalStateException if the incorrect player is passing
   */
  void pass(DiskColor color);

  /**
   * Places a disk of the given color on the board. In the interface because every model needs
   * to have the place functionality, and it needs to be called by the controller. Enforces that
   * the correct player is placing a piece.
   *
   * @param cell given cell to place the disk
   * @param color color of the disk to place
   * @throws IllegalStateException    if placing a disk at the given cell is not a legal move.
   * @throws IllegalStateException    if the incorrect player is placing a disk
   * @throws IllegalArgumentException if the given cell is not valid or if it is not empty
   */
  void place(ReversiCell cell, DiskColor color);
}
