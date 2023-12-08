package cs3500.reversi.model;

/**
 * Creates a Reversi game by instantiating and returning a model.
 * This is public so it can be called by the main class.
 */
public class ReversiCreator {
  public static MutableModel createHex(int boardSize) {
    Board board = new HexBoard(boardSize);
    return new BasicReversi(board);
  }

  public static MutableModel createSquare(int boardSize) {
    Board board = new SquareBoard(boardSize);
    return new BasicReversi(board);
  }
}
