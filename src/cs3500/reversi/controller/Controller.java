package cs3500.reversi.controller;

import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.gui.ReversiView;

/**
 * Controller class to facilitate interactions between the model and the view. THIS IS A
 * STUB OF THE CONTROLLER FOR THE PURPOSES OF CREATING FUNCTIONING KEY HANDLERS. There are many
 * things that this controller does not yet implement, like a game over method, keeping track
 * of whose turn it is, check if a move is valid, etc. The only functionality it currently supports
 * is for the keys 'p' and 'enter' to pass and place cells, respectively.
 */
public class Controller implements PlayerActions, ModelFeatures {

  // private final: one model for the game, it should never be reassigned. Only the controller
  // should have access to this model. Represents the model for this game.
  private final MutableModel model;

  // private final: one view for each controller, it does not need to be reassigned. Only the
  // controller should have access to this view. Represents the view connected to this
  // controller.
  private final ReversiView view;

  // private final: one player for each controller, it does not need to be reassigned. Only the
  // controller should have access to the player. Represents the player connected to this
  // controller.
  private final Player player;

  /**
   * Constructor for the controller class initializes the model and view, sets this as
   * a listener to the view and model, and makes the view visible to the user.
   *
   * @param model model of the current game
   * @param view  view for the current game
   */
  public Controller(MutableModel model, ReversiView view, Player player) {
    this.model = model;
    this.model.addListener(player.getColor(), this);
    this.view = view;
    view.makeVisible();
    view.addFeatures(this);
    this.player = player;
    this.player.setListener(this);
  }

  @Override
  public void receivePlace(ReversiCell cell) {
    try {
      System.out.println("placed");
      this.model.place(cell, this.player.getColor());
      this.view.place(this.player.getColor());
      view.update();
    }
    catch (IllegalStateException e) {
      this.view.popUpError(e.getMessage());
    }
  }

  @Override
  public void receivePass() {
    try {
      System.out.println("passed");
      this.model.pass(this.player.getColor());
      if (!this.model.isGameOver()) {
        this.view.update();
      }
    }
    catch (IllegalStateException e) {
      this.view.popUpError(e.getMessage());
    }
  }

  @Override
  public void receiveTurnNotif() {
    view.update();
    this.player.play();
  }

  @Override
  public void receiveGameOverNotif() {
    view.gameOver();
  }
}

