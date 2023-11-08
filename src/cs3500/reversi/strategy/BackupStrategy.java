package cs3500.reversi.strategy;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class BackupStrategy implements CellStrategy {

  private final CellStrategy strategyOne, strategyTwo;

  public BackupStrategy(CellStrategy strategyOne, CellStrategy strategyTwo) {
    this.strategyOne = strategyOne;
    this.strategyTwo = strategyTwo;
  }

  @Override
  public ReversiCell chooseCell(ReadOnlyModel model, DiskColor color) {
    return null;
  }
}
