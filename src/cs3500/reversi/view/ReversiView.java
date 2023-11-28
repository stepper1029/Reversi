package cs3500.reversi.view;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;

/**
 * An interface to represent the view (frame) of a game of Reversi.
 */
public interface ReversiView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Creates a popup window and shows the provided message. Displays an icon
   * which represents an error.
   * @param message string to be displayed
   */
  void popUpError(String message);

  /**
   * Creates a popup window and shows the provided message. Displays an icon
   * which represents an informational message.
   * @param message
   */
  void popUpMessage(String message);

  /**
   * Add a feature, or increase the functionality of a player.
   * @param playerActions the new functionality
   */
  void addFeatures(PlayerActions playerActions);

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Refreshes the view to include any mutations or changes.
   */
  void update();

  /**
   * Places a disk of the given color on the highlighted cell. The location of the highlighted cell
   * is known locally within the view, so it doesn't need to be passed in.
   * @param color the color of the disk to be placed based on the current player.
   */
  void place(DiskColor color);

  /**
   * Returns the x coordinate of the user highlighted cell, so it can be passed from the view
   * to the controller then to the model. The x coordinate refers to the row index starting from
   * the 0th index top to bottom. The output is an optional integer because there is no x coordinate
   * for the selected cell when no cell is highlighted. If the output is not an integer, it's the
   * responsibility of the controller to catch it.
   * @return optional integer x coordinate of the highlighted cell
   */
  Optional<Integer> getSelectedX();

  /**
   * Returns the y coordinate of the user highlighted cell, so it can be passed from the view to
   * the controller then to the model. The y coordinate refers to the cell or column index starting
   * from the 0th index left to right. The output is an optional integer because there is no y
   * coordinate for the selected cell when no cell is highlight. If the output is not an integer,
   * it's the responsibility of the controller to catch it.
   * @return optional integer y coordinate of the highlighted cell
   */
  Optional<Integer> getSelectedY();
}
