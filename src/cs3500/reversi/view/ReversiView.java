package cs3500.reversi.view;

import java.awt.*;

/**
 * An interface to represent the view of a game of Reversi.
 */
public interface ReversiView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself
   */
  void refresh();

  void addFeatureListener(ViewFeatures features);
}
