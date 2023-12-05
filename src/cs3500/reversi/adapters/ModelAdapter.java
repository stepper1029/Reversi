package cs3500.reversi.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs3500.reversi.model.MutableModel;
import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.ModelFeatures;
import cs3500.reversi.provider.model.Piece;
import cs3500.reversi.provider.model.ReversiModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.DiskColor;

public class ModelAdapter implements ReversiModel {

  // from ours to theirs
  // our model needs to fit into their view/strategy

  private MutableModel adaptee;

  public ModelAdapter(MutableModel adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public GamePieceColor getColorAt(Coordinate coordinate) throws IllegalArgumentException {
    // fixme: adapt between coordinate grids and adapt colors
    ReversiCell cell = this.adaptee.getCellAt(coordinate.getX(), coordinate.getY());
    return ValueClassAdapters.dcToGPC(this.adaptee.getColorAt(cell));
  }

  @Override
  public int getScoreForColor(GamePieceColor gpc) {
    if (gpc.equals(GamePieceColor.Empty)) {
      return 0;
    } else {
      return this.adaptee.getScore(ValueClassAdapters.gpcToDC(gpc).get());
    }
  }

  @Override
  public List<Piece> getPiecesFromColor(GamePieceColor gpc) {
    return null;
  }

  @Override
  public List<Piece> getBoard() {
    return null;
  }

  @Override
  public int getNumRows() {
    return this.adaptee.getNumRows();
  }

  @Override
  public int getMaxSize() {
    return this.adaptee.getRowSize((this.adaptee.getNumRows() + 1) / 2);
  }

  @Override
  public int getBoardSize() {
    int count = 0;
    for (int numRow = 0; numRow < this.getNumRows(); numRow ++) {
      count += this.adaptee.getRowSize(numRow);
    }
    return count;
  }

  @Override
  public boolean isMoveValid(Coordinate coor, GamePieceColor color) {
    return false;
  }

  @Override
  public List<List<Piece>> returnRuns() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return this.adaptee.isGameOver();
  }

  @Override
  public boolean didColorWin(GamePieceColor gpc) throws IllegalArgumentException {
    return this.adaptee.getWinner().equals(ValueClassAdapters.gpcToDC(gpc));
  }

  @Override
  public boolean noMoves(GamePieceColor color) {
    return false;
  }

  @Override
  public void startGame() throws IllegalArgumentException, IllegalStateException {
    this.adaptee.startGame();
  }

  @Override
  public void flipRun(List<Piece> run, GamePieceColor color) {

  }

  @Override
  public void flipDisc(Piece gp) {

  }

  @Override
  public void turn(Coordinate destCoordinate, GamePieceColor color) {

  }

  @Override
  public void setIsGameOver(boolean b) {
  }

  @Override
  public GamePieceColor passTurn(GamePieceColor color) throws IllegalArgumentException {
    Optional<DiskColor> dc = ValueClassAdapters.gpcToDC(color);
    if (dc.isPresent()) {
      this.adaptee.pass(dc.get());
      return ValueClassAdapters.dcToGPC(DiskColor.getNextColor(dc.get()));
    } else {
      throw new IllegalArgumentException("Empty cannot pass.");
    }
  }

  @Override
  public List<Piece> getBoardCopy() {
    List<ReversiCell> list = new ArrayList<>(List.of());
    for (int row = 0; row < this.getNumRows(); row ++) {
      for (int cell = 0; cell < this.adaptee.getRowSize(row); cell ++) {
        ReversiCell rc = this.adaptee.getCellAt(row, cell);
        // fixme : adapt to use Pieces instead of ReversiCells
        list.add(rc);
      }
    }
  }

  @Override
  public void addListener(ModelFeatures listener) {
    this.adaptee.addListener(listener);
  }
}
