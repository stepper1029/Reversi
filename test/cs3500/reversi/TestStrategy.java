package cs3500.reversi;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.swing.text.html.Option;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.ReversiCreator;
import cs3500.reversi.strategy.FallibleReversiStrategy;
import cs3500.reversi.strategy.InfallibleReversiStrategy;
import cs3500.reversi.strategy.MostPieces;

/**
 * Tests for the strategy of Reversi.
 */
public class TestStrategy {
  private ReadOnlyModel model3;
  private ReadOnlyModel model4;
  private FallibleReversiStrategy mostPieces;
  private InfallibleReversiStrategy mostPiecesInfallible;

  private void initStrategies() {
    this.model3 = ReversiCreator.create(3);
    this.model4 = ReversiCreator.create(4);
    this.mostPieces = new MostPieces();
    this.mostPiecesInfallible = new InfallibleReversiStrategy(this.mostPieces);
  }

  @Test
  public void testMostPieces() {
    this.initStrategies();
    Assert.assertEquals(this.model3.allPossibleMoves(DiskColor.Black),
            this.mostPieces.allGoodMoves(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
    Assert.assertEquals(Optional.ofNullable(this.model3.getCellAt(1, 3)),
            this.mostPieces.bestPotentialMove(this.model3, DiskColor.Black,
                    this.model3.allPossibleMoves(DiskColor.Black)));
  }
}
