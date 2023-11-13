package cs3500.reversi.view;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
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
import cs3500.reversi.model.ReversiCell;

public class BoardPanel extends JPanel {

  private final ReadOnlyModel model;

  private final List<ViewFeatures> featuresListeners;
  // private final double cellWidth;
  //private final double cellHeight;
  protected final Hexagon[][] cells;

  private boolean mouseIsDown;

  private DiskColor activeColor;

  public BoardPanel(ReadOnlyModel model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    cells = new Hexagon[this.model.getNumRows()][];
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 500x425 pixels.
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
    return new Dimension(this.model.getRowSize(this.model.getBoardSize() / 4),
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


    for (int row = 0; row < this.model.getNumRows(); row ++) {
      this.cells[row] = new Hexagon[this.model.getRowSize(row)];
      this.makeRows(g2d, row);
    }
    this.setDisksForAllRows(g2d);
  }

  private void setDisksForAllRows(Graphics2D g2d) {
    for (int row = 0; row < cells.length; row++) {
      setDisksForOneRow(g2d, row);
    }
  }

  private void setDisksForOneRow(Graphics2D g2d, int rowNum) {
    int rowSize = cells[rowNum].length;
    for (int cellIndex = 0; cellIndex < rowSize; cellIndex++) {
      ReversiCell currCell = this.model.getCellAt(rowNum, cellIndex);
      Hexagon currHex = cells[rowNum][cellIndex];
      if (!this.model.isEmpty(currCell)) {
        DiskColor color = this.model.getColorAt(currCell);
        currHex.addDisk(g2d, color);
      }
    }
  }

  private Double getCellWidth() {
    Point2D logicalPointA = new Point2D.Double(0, 0);
    Point2D logicalPointB = new Point2D.Double(1, 0);
    Point2D physicalPointA = new Point2D.Double();
    Point2D physicalPointB = new Point2D.Double();
    this.transformLogicalToPhysical().transform(logicalPointA, physicalPointA);
    this.transformLogicalToPhysical().transform(logicalPointB, physicalPointB);

    return physicalPointB.getX() - physicalPointA.getX();
  }

  private void makeRows(Graphics2D g2d, int rowNum) {
    int rowSize = this.model.getRowSize(rowNum);
    this.cells[rowNum] = new Hexagon[rowSize];
    double cellWidth = this.getCellWidth();
    double cellHeight = (cellWidth / Math.sqrt(3)) * 2;
    double x = setPhysicalCoords().getX();
    double y = setPhysicalCoords().getY();
    int midRowNum;
    if (this.model.getBoardSize()%2 == 0) {
      midRowNum = this.model.getNumRows() / 2;
      if (rowNum%2 != 0) {
        x -= (cellWidth / 2) * ((this.model.getRowSize(rowNum) - 1) / 2) ;
      } else {
        x -= ((cellWidth / 2) * (this.model.getRowSize(rowNum) / 2)) - (cellWidth / 4);
      }
    } else {
      midRowNum = (this.model.getNumRows() - 1) / 2;
      if (rowNum % 2 == 0) {
        x -= (cellWidth / 2) * ((this.model.getRowSize(rowNum) - 1) / 2);
      } else {
        x -= ((cellWidth / 2) * (this.model.getRowSize(rowNum) / 2)) - (cellWidth / 4);
      }
    }

    y += ((cellHeight * 3) / 8) * (rowNum - midRowNum);
    iterateCells(g2d, rowNum, x, y);
  }

  private void iterateCells(Graphics2D g2d, int rowNum, double x, double y) {
    double cellWidth = this.getCellWidth();
    int rowSize = this.model.getRowSize(rowNum);
    for (int i = 0; i < rowSize; i++) {
      Point2D currPoint = new Point2D.Double(x, y);
      Hexagon currHex = new Hexagon(currPoint, cellWidth);
      this.drawHex(g2d, currHex);
      this.placeHex(currHex, rowNum, i);

      x += cellWidth / 2;
    }
  }

  private Point2D setPhysicalCoords() {
    Point2D logicalCenterCell = new Point2D.Double(0, 0);
    Point2D physicalCenterCell = new Point2D.Double();
    transformLogicalToPhysical().transform(logicalCenterCell, physicalCenterCell);
    return physicalCenterCell;
  }

  private void drawHex(Graphics2D g2d, Hexagon hex) {
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fill(hex);
    g2d.setColor(Color.BLACK);
    g2d.draw(hex);
  }

  private void placeHex(Hexagon hex, int numRow, int numCell) {
    this.cells[numRow][numCell] = hex;
  }

  private class Hexagon extends Path2D.Double {
    private final Point2D center;
    private final double size;
    private boolean hasDisk;
    private boolean filled;

    private Hexagon(Point2D center, double width) {
      this.size = width / Math.sqrt(3);
      this.center = center;
      this.hasDisk = false;
      this.filled = false;


      moveTo(this.center.getX(), this.center.getY() - size / 2);
      for (int i = 0; i < 7; i++) {
        double angle = Math.PI / 6 + i * Math.PI / 3;
        double x = this.center.getX() + size / 2 * Math.cos(angle);
        double y = this.center.getY() - size / 2 * Math.sin(angle);
        lineTo(x, y);
      }
    }

    private void addDisk(Graphics2D g2d, DiskColor color) {
      this.hasDisk = true;
      if (color.equals(DiskColor.Black)) {
        g2d.setColor(Color.BLACK);
      } else {
        g2d.setColor(Color.WHITE);
      }
      Shape disk = new Ellipse2D.Double(this.center.getX() - (size * .25),
              this.center.getY() - (size * .25), (size * .5), (size * .5));
      g2d.draw(disk);
      g2d.fill(disk);
    }

    private void unfill(Graphics2D g2d) {
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fill(this);
      g2d.setColor(Color.BLACK);
      g2d.draw(this);
    }

    private void fill(Graphics2D g2d) {
      for (Hexagon[] row : cells) {
        for (Hexagon hex : row) {
          if (this.filled) {
            hex.unfill(g2d);
          }
        }
      }
      this.filled = true;
      if (!hasDisk) {
        g2d.setColor(Color.CYAN);
        g2d.fill(this);
        g2d.setColor(Color.BLACK);
        g2d.draw(this);
      }
    }
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
   * <p>
   *
   * @return The necessary transformation
   */
  protected AffineTransform transformLogicalToPhysical() {
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
   *
   * @return The necessary transformation
   */
  protected AffineTransform transformPhysicalToLogical() {
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

