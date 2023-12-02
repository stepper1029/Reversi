package cs3500.reversi.provider.view;

/**
 * <h3>ReversiView Interface</h3>
 * Represents the View of this Reversi game. The view handles how this game is presented to the
 * user based on the Reversi model.
 */
public interface ReversiView {

  /**
   * Renders this ReversiView according to each implementation.
   */
  void render();

  /**
   * Adds a new {@link ViewFeatures} listener to this Reversi View if applicable.
   * @param listener the ViewFeatures to add to the list of all listeners
   */
  void addFeatures(ViewFeatures listener);

  /**
   * Displays an error message if an invalid move is tried by a user.
   * @param message the {@link Message} to be displayed to the user
   */
  void displayPanel(Message message);
}
