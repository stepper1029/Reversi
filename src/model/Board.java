package model;

import java.util.List;

/**
 * Board interfaces represents a game board which contains cells and can contain game pieces.
 * Makes promises that a board of any size or shape can perform certain actions and observables
 * on a board.
 */
interface Board {
  /**
   * Gets the cell immediately next to the given cell in the given direction.
   * @param cell starting reference cell whose neighbor you're looking for
   * @param direction direction from the starting cell of the neighbor you want
   * @throws IllegalArgumentException if the given cell does not have a neighboring cell in the
   *                                  given direction. i.e. a cell in the top row of the board does
   *                                  not have a cell above it.
   * @return the neighboring cell
   */
  ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);

  /**
   * Observes which cells have a game piece of the given color on them.
   * @param color the DiscColor that you are looking for
   * @throws IllegalArgumentException if the given color does not represent one of the players of
   *                                  the game.
   * @return all cells that have a disc of that color on it
   */
  List<ReversiCell> getCells(DiscColor color);

  /**
   * Checks if a cell has a disc in it.
   * @param c given cell you're checking.
   * @throws IllegalArgumentException if the cell is not in the board.
   * @return whether the cell has a disc tied to it.
   */
  boolean isEmpty(ReversiCell c);

  /**
   * Returns the row of the given index, meaning the horizontal cells where horizontal plane
   * coordinate is the same for all the cells, numRow is indexed at zero starting from the
   * top of the board shape.
   * @param numRow index of the row you want
   * @throws IllegalArgumentException if param numRow is not a valid index
   * @return an array of ReversiCell that are in the specified row.
   */
  ReversiCell[] getRow(int numRow);

  /**
   * The size of the board, meaning the length (number of cells) of one side of the board shape.
   * @return int side length of the board
   */
  int getBoardSize();

  /**
   * Places a disc of the given color on the given cell.
   * @param c given cell you want to place the disc
   * @param color color of the disc to place.
   * @throws IllegalStateException if the given cell is not empty
   * @throws IllegalArgumentException if the given cell is not a valid cell within the board,
   *                                  ex. new HexCell(-100, 100, 0) when the board size is 3.
   */
  void placeDisc(ReversiCell c, DiscColor color);

  /**
   * Returns the starting position of a board, meaning the list of cells where the initial discs
   * must go in order to start the game. The list is meant to be assigned to alternating colors, ie
   * for two players, first cell is black, second cell is white, third cell is black, fourth cell
   * is white, .... and so on with the first cell being the top left of the starting cells.
   * @return a list of cells where initial starting discs should be placed.
   */
  List<ReversiCell> getInitPositions();

  /**
   * Flips a disc to show the opposite color of what it is.
   * @param c the given cell which is tied to the disc you want to flip.
   * @throws IllegalArgumentException if c is not a valid cell in the board
   * @throws IllegalStateException if c is empty, so does not have a disc to flip
   */
  void flipDisc(ReversiCell c);

  /**
   * Gets a list of cells which are directly between the two given cells.
   * @param cell1 starting cell
   * @param cell2 ending cell
   * @throws IllegalArgumentException if a line cannot be formed between the given cells.
   * @return list of ReversiCells that make a line which connects the two cells.
   */
  List<ReversiCell> getCellsBetween (ReversiCell cell1, ReversiCell cell2);

  /**
   * Observes the total number of cells in the board.
   * @return number of cells in the board.
   */
  int getTotalNumCells();
}
