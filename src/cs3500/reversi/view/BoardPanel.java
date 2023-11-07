package cs3500.reversi.view;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;

public class BoardPanel extends JPanel {

  private final ReadOnlyModel model;

  private final Hexagon[][] boardCells;

  private final List<ViewFeatures> featuresListeners;

  private boolean mouseIsDown;

  private DiskColor activeColor;

  public BoardPanel(ReadOnlyModel model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    boardCells = new Hexagon[this.model.getNumRows()][];
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  /**
   * Conceptually, we can choose a different coordinate system
   * and pretend that our panel is 40x40 "cells" big. You can choose
   * any dimension you want here, including the same as your physical
   * size (in which case each logical pixel will be the same size as a physical
   * pixel, but perhaps your calculations to position things might be trickier)
   * @return Our preferred *logical* size.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension((this.model.getRowSize((this.model.getNumRows() / 2) - 1)),
            (this.model.getNumRows()));
  }

  public void addFeaturesListener(ViewFeatures features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setColor(Color.BLACK);

    // center hexCell
    int centerX = getPreferredSize().width / 2; // start at the center of the frame
    int centerY = getPreferredSize().height / 2; // start at the center of the frame
    int cellWidth = getPreferredSize().width / getPreferredLogicalSize().width;
    g2d.draw(new Hexagon(centerX, centerY, cellWidth));
//
//    // center row to the left
//    int numRows = model.getNumRows();
//    int midRow = numRows / 2;
//    int midRowSize = model.getRowSize(midRow);
//
//    int x = centerX;
//    int y = centerY;
//
//    for (int cell = 0; cell < midRowSize / 2; cell ++) {
//      x -= cellWidth;
//    }
  }

  private static class Hexagon extends Path2D.Double {
    private Hexagon(int centerX, int centerY, int width) {
      double size = width / Math.sqrt(3);

      moveTo(centerX + size / 2, centerY);
      for (int i = 0; i < 6; i++) {
        double angle = 2 * Math.PI / 6 * i;
        double x = centerX + size / 2 * Math.cos(angle);
        double y = centerY + size / 2 * Math.sin(angle);
        lineTo(x, y);
      }
      closePath();
    }
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
   * <p>
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    ret.scale(1, -1);
    return ret;
  }

  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in center, width and height
   * our logical size).
   * <p>
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      BoardPanel.this.mouseIsDown = true;
      this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      BoardPanel.this.mouseIsDown = false;

    }
  }
}

