package view;

/**
 * <h3>Message Enumeration</h3>
 * Represents a message to be displayed in this Reversi game. There are three options for popup
 *     messages-if the player is passing their turn, if the player made an invalid move, or if
 *     the player won the game.
 */
public enum Message {

  PassedTurn,
  InvalidMove,
  GameWon;

  /**
   * Returns a {@link String} that correlates to the given message.
   * @param playerNumber the {@code int} representation of the player's number
   * @return the {@code String} of the message correlating to this Enumeration
   */
  public String returnMessage(int playerNumber) {
    switch (this) {
      case PassedTurn:
        return "Player " + playerNumber + " is passing their turn";
      case InvalidMove:
        return "Illegal move for player " + playerNumber;
      case GameWon:
        return "Player " + playerNumber + " has won the game!";
      default:
        throw new IllegalArgumentException("Invalid Message");
    }
  }
}
