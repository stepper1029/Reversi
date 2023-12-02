package cs3500.reversi.provider.model;

/**
 * <h3>ModelFeatures Interface</h3>
 * This {@code interface} represents the various notifications a model might need to send
 * to the view or the controller. This includes a notification when the user's turn has
 * been completed or when there are no possible moves left for the user.
 */
public interface ModelFeatures {

  /**
   * Notifies any Observers if the turn of the {@link ReversiModel} was completed successfully.
   * @param gpc the {@link GamePieceColor} of the user who completed their turn
   */
  void turnComplete(GamePieceColor gpc);

  /**
   * A callback to represent if there are any possible moves left for the given player's
   *     {@link GamePieceColor}. If this is triggered, the {@link controller.ReversiController}
   *     should take the appropriate actions.
   * @param gpc the GamePieceColor to check possible moves of
   */
  void pass(GamePieceColor gpc);

  /**
   * Called only when this Reversi game is concluded by the model to be over. This will send
   *     a notification to all listeners if this is the case.
   */
  void gameOver();

}
