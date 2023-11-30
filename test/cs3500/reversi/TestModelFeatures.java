package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiskColor;

/**
 * Test class to test the functionality of the ModelFeatures interface.
 */
public class TestModelFeatures extends AbstractTestClass {
  @Test
  public void testReceiveTurnNotif() {
    this.initMocks();
    this.listenerMockBlack.receiveTurnNotif();
    Assert.assertTrue(this.log.toString().contains("Listener is receiving turn notification for " +
            "black disk"));
    this.listenerMockWhite.receiveTurnNotif();
    Assert.assertTrue(this.log.toString().contains("Listener is receiving turn notification for " +
            "white disk"));
  }

  @Test
  public void testReceiveGameOverNotif() {
    this.initMocks();
    this.listenerMockBlack.receiveGameOverNotif();
    Assert.assertTrue(this.log.toString().contains("Listener is receiving game over " +
            "notification"));
    this.initMocks();
    this.listenerMockWhite.receiveGameOverNotif();
    Assert.assertTrue(this.log.toString().contains("Listener is receiving game over " +
            "notification"));
  }

  @Test
  public void testControllerLogTurnNotif() {
    this.initControllerMock();
    this.model4.addListener(DiskColor.Black, this.listenerMockBlack);
    this.model4.addListener(DiskColor.White, this.listenerMockWhite);
    this.model4.startGame();
    Assert.assertTrue(this.log.toString().contains("Listener is receiving turn notification for " +
            "black disk"));
    this.model4.place(this.model4.getCellAt(1, 2), DiskColor.Black);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving turn notification for " +
            "black disk\n" + "Listener is receiving turn notification for white disk\n" +
            "Listener is receiving turn notification for black disk\n"));
  }

  @Test
  public void testControllerLogGameOverNotif() {
    this.initControllerMock();
    this.model4.addListener(DiskColor.Black, this.listenerMockBlack);
    this.model4.addListener(DiskColor.White, this.listenerMockWhite);
    this.model4.startGame();
    this.model4.pass(DiskColor.Black);
    this.model4.pass(DiskColor.White);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving game over notification"));
  }

  @Test
  public void testListenerCallsModel() {
    this.initMocks();
    this.controllerBlack.receiveTurnNotif();
    this.model3.place(this.model3.getCellAt(0, 1), DiskColor.Black);
    Assert.assertTrue(this.log.toString().contains("Checking/ placing white disk at Cell: q: -2 r:"
            + " 1 s: 1\n" +
            "Returning score: 4\n" +
            "Making a move for black disk player"));
  }
}
