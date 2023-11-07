package cs3500.reversi.strategy;

import java.util.Scanner;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Strategy: asks the user where to place the next disk
 */
public class PromptUser implements CellStrategy {
  Scanner input;

  PromptUser() {
    this(new Scanner(System.in));
  }

  public PromptUser(Scanner input) {
    this.input = input;
  }

  @Override
  public ReversiCell chooseCell(ReadOnlyModel model, DiskColor color) {
    System.out.println("Enter the row and cell number of where you want to place your disk: ");
    int r = input.nextInt();
    int cell = input.nextInt();
    return model.getCellAt(r, cell);
  }
}
