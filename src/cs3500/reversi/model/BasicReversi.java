package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class BasicReversi represents a game of Reversi with standard rules and gameplay. Implements
 * MutableModel interface. Class is package private because the controller and view should go
 * through the interface and not the class.
 */
class BasicReversi implements MutableModel {

  // currColor to store what color should be used for moves like place. Should only be visible to
  // this BasicReversi.
  private DiscColor currColor;

  // stores number of times pass has been played consecutively. Should only be visible to this
  // BasicReversi
  private int numPasses;

  // stores a board to observe and mutate, assumes any invariants from the board class like
  // the board size being >2. Should only be visible to this BasicReversi
  private final Board board;

  /**
   * Constructor for BasicReversi initializes all fields. Takes in a board (which has a size, shape,
   * and cell structure) but is completely empty. Constructor sets the board with the initial
   * starting piece required to play the game. numPasses is initialized to zero because when no
   * moves have been made, no player could have passed. currColor begins at black as is
   * convention in Reversi, black moves first.
   *
   * @param board board with a size and shape that the game should be played on.
   */
  BasicReversi(Board board) {
    this.numPasses = 0;
    this.board = Objects.requireNonNull(board);
    this.setBoard();
    this.currColor = DiscColor.Black;
  }

  /**
   * sets the initial positions for a given board shape to the starting colors. Should not be
   * called outside the class so is private and not in the interface. Should not be called
   * once the players have begun making moves. Alternates through the colors and sets the cells
   * appropriately. For example, for a game with two players, first cell is black, second cell
   * is white, third cell is black, fourth cell is white, ... and so on.
   *
   * @throws IllegalStateException if the board is not empty when the method is called.
   */
  private void setBoard() {
    DiscColor[] colors = DiscColor.values();
    List<ReversiCell> cells = board.getInitPositions();
    if (!this.isEmpty(cells.get(0))) {
      throw new IllegalStateException("Board has already been set");
    }
    for (int i = 0; i < cells.size(); i++) {
      ReversiCell cell = cells.get(i);
      DiscColor color = colors[i % colors.length];
      board.placeDisc(cell, color);
    }
  }

  @Override
  public int getScore(DiscColor color) {
    return this.board.getCells(color).size();
  }

  @Override
  public boolean isGameOver() {
    if (this.numPasses >= DiscColor.values().length) {
      return true;
    } else if (this.getScore(DiscColor.Black) == 0
            || this.getScore(DiscColor.White) == 0) {
      return true;
    } else {
      return this.getScore(DiscColor.Black)
              + this.getScore(DiscColor.White)
              == this.board.getTotalNumCells();
    }
  }

  @Override
  public void pass() {
    this.numPasses += 1;
    this.setNextColor();
  }

  /**
   * Flips all the discs in the given list. Used when a player makes a move and the cells that
   * connect the cell where the player placed a disc and the existing discs of the same color on
   * the board are now captured by the player, meaning they should change colors to that of
   * the player who moved. Private because no other class should have permission to change the
   * color of cells.
   */
  private void flipAll(List<ReversiCell> cells) {
    for (ReversiCell c : cells) {
      this.board.flipDisc(c);
    }
  }

  @Override
  public void place(ReversiCell cell) {
    if (this.allPossibleMoves().contains(cell)) {
      this.numPasses = 0;
      this.board.placeDisc(cell, this.currColor);
      for (ReversiCell connectingCell : this.getConnections(cell)) {
        this.flipAll(this.board.getCellsBetween(cell, connectingCell));
      }
      this.setNextColor();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }

  /**
   * switches currColor to the next color in the DiscColor enum to switch whose turn it is.
   */
  private void setNextColor() {
    this.currColor = DiscColor.getNextColor(this.currColor);
  }

  @Override
  public int getNumRows() {
    return (this.board.getBoardSize() * 2) - 1;
  }

  @Override
  public int getRowSize(int rowNum) {
    if (rowNum >= 0 && rowNum < this.getNumRows()) {
      return this.board.getRow(rowNum).length;
    } else {
      throw new IllegalArgumentException("Invalid numRow: " + rowNum);
    }
  }

  @Override
  public int getBoardSize() {
    return this.board.getBoardSize();
  }

  @Override
  public DiscColor getColorAt(ReversiCell cell) {
    if (this.isEmpty(cell)) {
      throw new IllegalArgumentException("Cell is empty");
    } else if (this.board.getCells(DiscColor.Black).contains(cell)) {
      return DiscColor.Black;
    } else {
      return DiscColor.White;
    }
  }

  @Override
  public boolean isEmpty(ReversiCell cell) {
    return this.board.isEmpty(cell);
  }

  @Override
  public ReversiCell getCellAt(int numRow, int numCell) {
    if (numRow >= 0 && numRow < this.getNumRows() && numCell >= 0 && numCell < getRowSize(numRow)) {
      return this.board.getRow(numRow)[numCell];
    } else {
      throw new IllegalArgumentException("invalid numRow: " + numRow + "or numCell: " + numCell);
    }
  }

  // compares the two cells and sees if they have the same color. private because only BasicReversi
  // needs this functionality.
  private boolean sameColor(ReversiCell cell1, ReversiCell cell2) {
    if (!this.isEmpty(cell1) && !this.isEmpty(cell2)) {
      return (this.getColorAt(cell1).equals(this.getColorAt(cell2)));
    } else {
      throw new IllegalArgumentException("One or both of the cells are empty.");
    }
  }

  // for a valid move, determines all connections to this cell (discs between this
  // given cell and the connections should be flipped). private because no other class needs to
  // know the connections of a cell.
  private List<ReversiCell> getConnections(ReversiCell c) {
    ArrayList<ReversiCell> connections = new ArrayList<>();
    ReversiCell currCell;
    for (CellDirection direction : CellDirection.values()) {
      try {
        currCell = this.board.getNeighborCell(c, direction);
        while (!this.board.isEmpty(currCell) && !this.sameColor(c, currCell)) {
          currCell = this.board.getNeighborCell(currCell, direction);
          if (this.sameColor(currCell, c)) {
            connections.add(currCell);
            break;
          }
        }
      } catch (IllegalArgumentException e) {
        // do nothing because we want to continue through the for loop to
        // check the other directions
      }
    }
    return connections;
  }

  // determines if there is a valid move in the given direction with the given starting cell.
  // private because no other class needs this functionality.
  private boolean validMoveInOneD(CellDirection direction, ReversiCell startingCell) {
    ReversiCell currCell = this.board.getNeighborCell(startingCell, direction);
    // returns false if the cell directly next to this one is empty
    if (this.board.isEmpty(currCell)) {
      return false;
    }
    while (!this.board.isEmpty(currCell) && !this.sameColor(startingCell, currCell)) {
      try {
        currCell = this.board.getNeighborCell(currCell, direction);
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
    return this.isEmpty(currCell);
  }

  // determines all valid moves from the given starting cell. private because no other class
  // needs this functionality.
  private List<ReversiCell> validMovesInAllDirections(ReversiCell startingCell) {
    List<ReversiCell> validMoves = new ArrayList<>();
    ReversiCell currCell;
    for (CellDirection direction : CellDirection.values()) {
      try {
        currCell = this.board.getNeighborCell(startingCell, direction);
        if (this.validMoveInOneD(direction, startingCell)) {
          while (!this.board.isEmpty(currCell)) {
            currCell = this.board.getNeighborCell(currCell, direction);
          }
          validMoves.add(currCell);
        }
      } catch (IllegalArgumentException e) {
        // do nothing because we want to continue testing other directions
      }
    }
    return validMoves;
  }

  @Override
  public List<ReversiCell> allPossibleMoves() {
    List<ReversiCell> validMoves = new ArrayList<>();
    for (ReversiCell cell : this.board.getCells(this.currColor)) {
      validMoves.addAll(this.validMovesInAllDirections(cell));
    }
    return validMoves;
  }

  @Override
  public Board getBoardCopy() {
    return this.board;
  }
}
