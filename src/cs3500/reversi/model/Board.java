package cs3500.reversi.model;

import java.util.List;

/**
 * Board interfaces represents a game board which contains cells and can contain game pieces.
 * Makes promises that a board of any size or shape can perform certain actions and observables
 * on a board. The interface is package private because no other package should have access
 * to the board. All observations or mutations should be done through the model.
 */
interface Board {
  /**
   * Gets the cell immediately next to the given cell in the given direction. The model will need
   * this functionality from every board, regardless of shape or size, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @param cell      starting reference cell whose neighbor you're looking for
   * @param direction direction from the starting cell of the neighbor you want
   * @return the neighboring cell
   * @throws IllegalArgumentException if the given cell does not have a neighboring cell in the
   *                                  given direction. i.e. a cell in the top row of the board does
   *                                  not have a cell above it.
   */
  ReversiCell getNeighborCell(ReversiCell cell, CellDirection direction);

  /**
   * Observes which cells have a game piece of the given color on them. The model will need
   * this functionality from every board, regardless of shape or size, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @param color the DiscColor that you are looking for
   * @return all cells that have a disc of that color on it
   * @throws IllegalArgumentException if the given color does not represent one of the players of
   *                                  the game.
   */
  List<ReversiCell> getCells(DiscColor color);

  /**
   * Checks if a cell has a disc in it. The model will need this functionality from every board,
   * regardless of shape or size, in order to perform as promised. Therefore, it is in the
   * interface as a public method.
   *
   * @param c given cell you're checking.
   * @return whether the cell has a disc tied to it.
   * @throws IllegalArgumentException if the cell is not in the board.
   */
  boolean isEmpty(ReversiCell c);

  /**
   * Returns the row of the given index, meaning the horizontal cells where horizontal plane
   * coordinate is the same for all the cells, numRow is indexed at zero starting from the
   * top of the board shape. The model will need this functionality from every board, regardless
   * of shape or size, in order to perform as promised. Therefore, it is in the interface as a
   * public method.
   *
   * @param numRow index of the row you want
   * @return an array of ReversiCell that are in the specified row.
   * @throws IllegalArgumentException if param numRow is not a valid index
   */
  ReversiCell[] getRow(int numRow);

  /**
   * The size of the board, meaning the length (number of cells) of one side of the board shape.
   * The model will need this observable from every board, regardless of shape, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @return int side length of the board
   */
  int getBoardSize();

  /**
   * Places a disc of the given color on the given cell. The model will need
   * this functionality from every board, regardless of shape or size, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @param c     given cell you want to place the disc
   * @param color color of the disc to place.
   * @throws IllegalStateException    if the given cell is not empty
   * @throws IllegalArgumentException if the given cell is not a valid cell within the board,
   *                                  ex. new HexCell(-100, 100, 0) when the board size is 3.
   */
  void placeDisc(ReversiCell c, DiscColor color);

  /**
   * Returns the starting position of a board, meaning the list of cells where the initial discs
   * must go in order to start the game. The list is meant to be assigned to alternating colors, ie
   * for two players, first cell is black, second cell is white, third cell is black, fourth cell
   * is white, .... and so on with the first cell being the top left of the starting cells.
   * The model will need this functionality from every board, regardless of shape or size, in
   * order to perform as promised. Therefore, it is in the interface as a public method.
   *
   * @return a list of cells where initial starting discs should be placed.
   */
  List<ReversiCell> getInitPositions();

  /**
   * Flips a disc to show the opposite color of what it is. The model will need
   * this functionality from every board, regardless of shape or size, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @param c the given cell which is tied to the disc you want to flip.
   * @throws IllegalArgumentException if c is not a valid cell in the board
   * @throws IllegalStateException    if c is empty, so does not have a disc to flip
   */
  void flipDisc(ReversiCell c);

  /**
   * Gets a list of cells which are directly between the two given cells. The model will need
   * this functionality from every board, regardless of shape or size, in order to perform
   * as promised. Therefore, it is in the interface as a public method.
   *
   * @param cell1 starting cell
   * @param cell2 ending cell
   * @return list of ReversiCells that make a line which connects the two cells.
   * @throws IllegalArgumentException if a line cannot be formed between the given cells.
   */
  List<ReversiCell> getCellsBetween(ReversiCell cell1, ReversiCell cell2);

  /**
   * Observes the total number of cells in the board. The model will need this functionality
   * from every board, regardless of shape or size, in order to perform as promised.
   * Therefore, it is in the interface as a public method.
   *
   * @return number of cells in the board.
   */
  int getTotalNumCells();
}
