package cs3500.reversi.model;

/**
 * Creates a Reversi game by instantiating and returning a model.
 * This is public so it can be called by the main class.
 */
public class ReversiCreator {
  public static MutableModel create(int boardSize) {
    Board board = new HexBoard(boardSize);
    return new BasicReversi(board);
  }
}
