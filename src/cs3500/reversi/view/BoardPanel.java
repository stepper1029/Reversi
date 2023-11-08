package cs3500.reversi.view;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
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

  private final List<ViewFeatures> featuresListeners;

  private final int centerX;
  private final int centerY;
  private final double cellWidth;
  private final double cellHeight;
  private final Hexagon[][] cells;

  private boolean mouseIsDown;

  private DiskColor activeColor;

  public BoardPanel(ReadOnlyModel model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    centerX = getPreferredSize().width / 2;
    centerY = getPreferredSize().height / 2;
    cellWidth = getPreferredSize().width / getPreferredLogicalSize().width;
    cellHeight = (cellWidth / Math.sqrt(3)) * 2;
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    cells = new Hexagon[this.model.getNumRows()][];
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   *
   * @return Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 425);
  }

  /**
   * Conceptually, we can choose a different coordinate system
   * and pretend that our panel is 40x40 "cells" big. You can choose
   * any dimension you want here, including the same as your physical
   * size (in which case each logical pixel will be the same size as a physical
   * pixel, but perhaps your calculations to position things might be trickier)
   *
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

    this.drawCenterRow(g2d);
    this.drawTopRows(g2d);
    this.drawBottomRows(g2d);
  }

  private void drawBottomRows(Graphics2D g2d) {
    int midRow = this.model.getNumRows() / 2;
    int rowSize = model.getRowSize(midRow + 1);
    Hexagon[] rowArray = new Hexagon[rowSize];

    g2d.setColor(Color.BLACK);

    double x = centerX + (cellWidth / 4) * ((rowSize / 2) + 1);
    double y = centerY + (this.cellHeight * 3) / 8;
    g2d.draw(new Hexagon(x, y, this.cellWidth));
    rowArray[0] = new Hexagon(x, y, this.cellWidth);

    for (int row = midRow + 1; row < this.model.getNumRows(); row++) {
      for (int i = 0; i < this.model.getRowSize(row) - 1; i++) {
        if (row % 2 == 0) {
          x += this.cellWidth / 2;
        } else {
          x -= this.cellWidth / 2;
        }
        g2d.draw(new Hexagon(x, y, this.cellWidth));
        rowArray[i] = new Hexagon(x, y, this.cellWidth);
      }
      x -= cellWidth / 4;
      y += (this.cellHeight * 3) / 8;
      cells[row] = rowArray;
      rowArray = new Hexagon[this.model.getRowSize(row)];
    }
  }

  // FIXME: draws one extra cell on the left of row 1
  private void drawTopRows(Graphics2D g2d) {
    int midRow = this.model.getNumRows() / 2;
    int rowSize = model.getRowSize(midRow - 1);
    Hexagon[] rowArray = new Hexagon[rowSize];

    g2d.setColor(Color.BLACK);

    double x = centerX + (cellWidth / 4) * ((rowSize / 2) + 1);
    double y = centerY - (this.cellHeight * 3) / 8;
    g2d.draw(new Hexagon(x, y, this.cellWidth));
    rowArray[0] = new Hexagon(x, y, this.cellWidth);

    for (int row = midRow - 1; row >= 0; row--) {
      for (int i = 0; i < this.model.getRowSize(row) - 1; i++) {
        if (row % 2 == 0) {
          x += this.cellWidth / 2;
          rowArray[i] = new Hexagon(x, y, this.cellWidth);
        } else {
          x -= this.cellWidth / 2;
          rowArray[this.model.getRowSize(row) - i - 1] = new Hexagon(x, y, this.cellWidth);
        }
        g2d.draw(new Hexagon(x, y, this.cellWidth));
      }
      if (row%2 ==0) {
        x -= cellWidth / 4;
      } else {
        x += cellWidth / 4;
      }
      y -= (this.cellHeight * 3) / 8;
      cells[row] = rowArray;
      rowArray = new Hexagon[this.model.getRowSize(row)];
    }
  }

  private void drawCenterRow(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);

    int numRows = model.getNumRows();
    int midRow = numRows / 2;
    int midRowSize = model.getRowSize(midRow);
    Hexagon[] row = new Hexagon[midRowSize];

    // center hexCell
    g2d.draw(new Hexagon(this.centerX, this.centerY, this.cellWidth));
    row[(midRowSize + 1) / 2] = new Hexagon(this.centerX, this.centerY, this.cellWidth);

    double x = this.centerX;
    double y = this.centerY;
    int index = (midRowSize + 1) / 2;

    // whole row
    for (int cell = 0; cell < midRowSize + 1; cell++) {
      if (cell < midRowSize / 2) { // cells left of the center
        x -= this.cellWidth / 2;
        g2d.draw(new Hexagon(x, y, this.cellWidth));
        row[index - 1] = new Hexagon(x, y, this.cellWidth);
      } else if (cell > midRowSize / 2) { // cells right of the center
        x += cellWidth / 2;
        g2d.draw(new Hexagon(x, y, this.cellWidth));
        row[index + 1] = new Hexagon(x, y, this.cellWidth);
      } else { // center
        x += cellWidth / 2;
        index = (midRowSize + 1) / 2;
      }
    }
    cells[midRow] = row;
  }

  private static class Hexagon extends Path2D.Double {
    private Hexagon(double centerX, double centerY, double width) {
      double size = width / Math.sqrt(3);

      moveTo(centerX, centerY - size / 2);
      for (int i = 0; i < 7; i++) {
        double angle = Math.PI / 6 + i * Math.PI / 3;
        double x = centerX + size / 2 * Math.cos(angle);
        double y = centerY - size / 2 * Math.sin(angle);
        lineTo(x, y); // Draw edges (excluding the last vertex)
      }
    }
  }

//  /**
//   * Computes the transformation that converts board coordinates
//   * (with (0,0) in center, width and height our logical size)
//   * into screen coordinates (with (0,0) in upper-left,
//   * width and height in pixels).
//   * <p>
//   *
//   * @return The necessary transformation
//   */
//  private AffineTransform transformLogicalToPhysical() {
//    AffineTransform ret = new AffineTransform();
//    Dimension preferred = getPreferredLogicalSize();
//    ret.translate(getWidth() / 2., getHeight() / 2.);
//    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
//    ret.scale(1, -1);
//    return ret;
//  }
//
//  /**
//   * Computes the transformation that converts screen coordinates
//   * (with (0,0) in upper-left, width and height in pixels)
//   * into board coordinates (with (0,0) in center, width and height
//   * our logical size).
//   * <p>
//   *
//   * @return The necessary transformation
//   */
//  private AffineTransform transformPhysicalToLogical() {
//    AffineTransform ret = new AffineTransform();
//    Dimension preferred = getPreferredLogicalSize();
//    ret.scale(1, -1);
//    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
//    ret.translate(-getWidth() / 2., -getHeight() / 2.);
//    return ret;
//  }

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

