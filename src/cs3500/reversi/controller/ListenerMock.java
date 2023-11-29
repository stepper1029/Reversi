package cs3500.reversi.controller;

import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.player.Player;

/**
 * A mock to test that the PlayActions and ModelFeatures interfaces work properly and the control
 * flow with the model, view, and players is correct.
 */
public class ListenerMock implements PlayerActions, ModelFeatures {
  private final StringBuilder log;
  private final Player player;

  /**
   * Constructor to initialize the log.
   *
   * @param log StringBuilder to keep track of methods that are called on this class.
   */
  public ListenerMock(StringBuilder log, Player player, Controller controller) {
    this.log = log;
    this.player = player;
  }

  @Override
  public void receiveTurnNotif() {
    this.log.append("Listener is receiving turn notification for ").
            append(this.player.getColor()).append("\n");
    this.player.play();
  }

  @Override
  public void receiveGameOverNotif() {
    this.log.append("Listener is receiving game over notification\n");
  }

  @Override
  public void receivePlace(ReversiCell cell) {
    this.log.append("Listener is receiving notification to place a piece " +
            "at: ").append(cell.toString()).append("\n");
  }

  @Override
  public void receivePass() {
    this.log.append("Listener is receiving notification to pass\n");
  }
}
