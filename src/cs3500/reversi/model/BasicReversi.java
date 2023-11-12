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
  // keeps track of the color that should be placed by the current player. Each player should
  // only move once at a time, so this keeps track of whose turn it is, allowing the model
  // to ensure that the correct player is placing a piece.
  private DiskColor currColor;

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
   * convention in Reversi, black moves first. Package-private so that classes outside of this
   * package can only create a new model through the factory class.
   *
   * @param board board with a size and shape that the game should be played on.
   */
  BasicReversi(Board board) {
    this.numPasses = 0;
    this.board = Objects.requireNonNull(board);
    this.setBoard();
    this.currColor = DiskColor.Black;
  }

  /**
   * Constructor to initialize the fields to the given values. Package-private so that classes
   * outside of this package can only create a new model through the factory class.
   *
   * @param board the board that represents this placement of the pieces in this game
   * @param numPasses the number of passes in a row that have occurred
   * @param currColor the current color of the disc being placed
   */
  BasicReversi(Board board, int numPasses, DiskColor currColor) {
    this.board = board;
    this.numPasses = numPasses;
    this.currColor = currColor;
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
    DiskColor[] colors = DiskColor.values();
    List<ReversiCell> cells = board.getInitPositions();
    if (!this.isEmpty(cells.get(0))) {
      throw new IllegalStateException("Board has already been set");
    }
    for (int i = 0; i < cells.size(); i++) {
      ReversiCell cell = cells.get(i);
      DiskColor color = colors[i % colors.length];
      board.placeDisk(cell, color);
    }
  }

  @Override
  public int getScore(DiskColor color) {
    return this.board.getCells(color).size();
  }

  @Override
  public boolean isGameOver() {
    if (this.numPasses >= DiskColor.values().length) {
      return true;
    } else if (this.getScore(DiskColor.Black) == 0
            || this.getScore(DiskColor.White) == 0) {
      return true;
    } else {
      return this.getScore(DiskColor.Black)
              + this.getScore(DiskColor.White)
              == this.board.getTotalNumCells();
    }
  }

  /**
   * Throws an exception if the given color does not match the current color, meaning the player
   * is trying to move out of turn.
   *
   * @param color color corresponding to the player trying to move
   * @throws IllegalStateException if the color does not match the current player whose turn it is
   */
  private void outOfTurnException(DiskColor color) {
    if (!this.currColor.equals(color)) {
      throw new IllegalStateException("Player is playing out of turn");
    }
  }

  @Override
  public void pass(DiskColor color) {
    this.outOfTurnException(color);
    this.numPasses += 1;
    this.setNextColor();
  }

  /**
   * Flips all the disks in the given list. Used when a player makes a move and the cells that
   * connect the cell where the player placed a disk and the existing disks of the same color on
   * the board are now captured by the player, meaning they should change colors to that of
   * the player who moved. Private because no other class should have permission to change the
   * color of cells.
   */
  private void flipAll(List<ReversiCell> cells) {
    for (ReversiCell c : cells) {
      this.board.flipDisk(c);
    }
  }

  @Override
  public void place(ReversiCell cell, DiskColor color) {
    this.outOfTurnException(color);
    if (this.allPossibleMoves(color).contains(cell)) {
      this.numPasses = 0;
      this.board.placeDisk(cell, color);
      for (ReversiCell connectingCell : this.getConnections(cell)) {
        this.flipAll(this.board.getCellsBetween(cell, connectingCell));
      }
      this.setNextColor();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }

  /**
   * switches currColor to the next color in the DiskColor enum to switch whose turn it is.
   */
  private void setNextColor() {
    this.currColor = DiskColor.getNextColor(this.currColor);
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
  public DiskColor getColorAt(ReversiCell cell) {
    if (this.isEmpty(cell)) {
      throw new IllegalArgumentException("Cell is empty");
    } else if (this.board.getCells(DiskColor.Black).contains(cell)) {
      return DiskColor.Black;
    } else {
      return DiskColor.White;
    }
  }

  @Override
  public boolean isEmpty(ReversiCell cell) {
    return this.board.isEmpty(cell);
  }

  @Override
  public DiskColor getTurn() {
    return this.currColor;
  }

  @Override
  public MutableModel copy() {
    return new BasicReversi(this.board.copy(), this.numPasses, this.currColor);
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

  // for a valid move, determines all connections to this cell (disks between this
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
  public List<ReversiCell> allPossibleMoves(DiskColor color) {
    List<ReversiCell> validMoves = new ArrayList<>();
    for (ReversiCell cell : this.board.getCells(color)) {
      validMoves.addAll(this.validMovesInAllDirections(cell));
    }
    return validMoves;
  }

}
