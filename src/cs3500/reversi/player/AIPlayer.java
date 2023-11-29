package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.strategy.InfallibleReversiStrategy;

/**
 * Represents an AI player in a game of Reversi.
 */
public class AIPlayer implements Player {
  // represents the color of the disks corresponding to this player
  private final DiskColor color;
  // the strategy that this player is using
  private final InfallibleReversiStrategy strategy;
  // represents the controller for this player
  private PlayerActions controller;
  // the model that this AI player is making moves based on
  private MutableModel model;

  /**
   * Constructor for a player.
   * @param color the color to assign to this player
   * @param strategy the strategy this player is using
   */
  public AIPlayer(DiskColor color, InfallibleReversiStrategy strategy, MutableModel model) {
    this.color = color;
    this.strategy = strategy;
    this.model = model;
  }

  @Override
  public void setListener(PlayerActions listener) {
    this.controller = listener;
  }

  @Override
  public DiskColor getColor() {
    return this.color;
  }

  /**
   * Returns the move that the player wants to make or empty if the player is passing. The
   * value returned by this method will be handled in the play method.
   *
   * @return a ReversiCell representing where the player wants to move or empty if the player
   *         wants to pass.
   */
  private Optional<ReversiCell> chooseMove() {
    if (this.strategy.shouldPass(this.model, this.color)) {
      return Optional.empty();
    }
    else {
      return Optional.ofNullable(this.strategy.chooseMove(this.model, this.color));
    }
  }

  @Override
  public void play() {
    Optional<ReversiCell> move = this.chooseMove();
    if (move.isEmpty()) {
      this.controller.receivePass();
    }
    else {
      this.controller.receivePlace(move.get());
    }
  }
}