package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * Strategy: asks the user where to place the next disk
 */
public class PromptUser implements FallibleReversiStrategy {
  Scanner input;

  PromptUser() {
    this(new Scanner(System.in));
  }

  public PromptUser(Scanner input) {
    this.input = input;
  }

  @Override
  public List<ReversiCell> allGoodMoves(ReadOnlyModel model, DiskColor player, List<ReversiCell>
          possibleMoves) {
    Optional<ReversiCell> bestMove = this.bestPotentialMove(model, player, possibleMoves);
    if (bestMove.isEmpty()) {
      return new ArrayList<>();
    }
    else {
      return new ArrayList<>(Collections.singletonList(bestMove.get()));
    }
  }

  @Override
  public Optional<ReversiCell> bestPotentialMove(ReadOnlyModel model, DiskColor player,
                                                 List<ReversiCell> possibleMoves) {
    System.out.println("Enter the row and cell number of where you want to place your disk: ");
    int r = input.nextInt();
    int cell = input.nextInt();
    return Optional.ofNullable(model.getCellAt(r, cell));
  }
}
