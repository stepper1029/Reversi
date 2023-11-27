package cs3500.reversi.model;

public interface ModelFeatures {
  void recieveTurnNotif();
  
  void receiveInvalidMoveNotif();
  
  void receiveOutOfTurnNotif();
}
