package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.DiskColor;

/**
 * Tests for PlayerActions.
 */
public class TestPlayerActions extends AbstractTestClass {
  @Test
  public void testReceivePlace() {
    this.initMocks();
    this.listenerMockBlack.receivePlace(this.model4.getCellAt(1, 2));
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to place " +
            "a piece at: Cell: q: 1 r: -2 s: 1"));
  }

  @Test
  public void testReceivePass() {
    this.initMocks();
    this.listenerMockBlack.receivePass();
    System.out.println(this.log);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to pass"));
  }

  @Test
  public void testViewCallsListener() {
    this.initMocks();
    this.viewMockBlack.addFeatures(this.listenerMockBlack);
    this.viewMockBlack.addFeatures(this.listenerMockWhite);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to place a "
            + "piece at: Cell: q: 0 r: 0 s: 0\nListener is receiving notification to pass"));
  }

  @Test
  public void testPlayerCallsListener() {
    this.initMocks();
    this.mock.place(this.mock.getCellAt(0, 1), DiskColor.Black);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to place a "
            + "piece at: Cell: q: 0 r: -2 s: 2"));
    this.mock.pass(DiskColor.Black);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to pass"));
    Assert.assertTrue(this.log.toString().contains("Making a move for white disk player"));
  }

  @Test
  public void testListenerCallsPlayer() {
    this.initMocks();
    this.mock.place(this.mock.getCellAt(0, 1), DiskColor.Black);
    this.listenerMockWhite.receiveTurnNotif();
    System.out.println(this.log);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to place a "
            + "piece at: Cell: q: 1 r: 1 s: -2"));
    this.mock.pass(DiskColor.White);
    Assert.assertTrue(this.log.toString().contains("Listener is receiving notification to pass"));
  }
}