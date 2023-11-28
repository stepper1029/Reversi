package cs3500.reversi.model;

/**
 * This Features interface connects the model and the controller in order to sent notifications
 * between the two.
 */
public interface ModelFeatures {

  /**
   * The controller has been told that it is this player's turn. Now the controller will
   * relay the information to the player via a message displayed on the view.
   */
  void receiveTurnNotif();

  /**
   * The controller has been told that the game is over by the model. Now the controller
   * will tell the view that the game is over through a displayed message.
   */
  void receiveGameOverNotif();
}
