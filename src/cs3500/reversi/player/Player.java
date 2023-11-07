package cs3500.reversi.player;

/**
 * Represents a player in the game of Klondike: can be a person or an AI player.
 */
public interface Player {
  /**
   * Allows a player to place a new disk on the game board. When this is actually implemented,
   * it will be able to handle a mouse click from the player and pass that information to the
   * controller, which will call the appropriate method in the model.
   */
  void place();

  /**
   * Allows a player to pass their turn (which is the only option if there are no valid moves).
   */
  void pass();
}
