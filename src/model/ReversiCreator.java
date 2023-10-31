package model;

public class ReversiCreator {
  public static MutableModel create(int boardSize) {
    Board board = new HexBoard(boardSize);
    return new BasicReversi(board);
  }
}
