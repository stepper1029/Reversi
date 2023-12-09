package cs3500.reversi.view.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public abstract class AbstractPanel extends JPanel {
  // Private final ReadOnlyModel so the model can be observed but not mutated. Does not need to
  // reassigned or visible outside of this class.
  protected ReadOnlyModel model;

  // the X (or row) coordinate of the currently highlighted cell.
  protected Optional<Integer> selectedX; // optional: null when nothing is highlighted

  // the Y (or Hexagon / column) coordinate of the currently highlighted cell.
  protected Optional<Integer> selectedY; // optional: null when nothing is highlighted

  // color of the play which this view belongs to
  protected DiskColor color;

  protected boolean showHint;

  /**
   * Constructor for the class, initializes the model and cells structure,and the mouse listener.
   * Creates a new instance of the Panel.
   *
   * @param model ReadOnlyModel because the view is only allowed observability not mutability.
   */
  public AbstractPanel(ReadOnlyModel model, DiskColor color) {
    this.color = color;
    this.model = Objects.requireNonNull(model);
    this.selectedX = Optional.empty();
    this.selectedY = Optional.empty();
    this.showHint = false;
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

  protected abstract Dimension getPreferredLogicalSize();

  abstract void place(DiskColor color);

  abstract void update();

  void toggleHint() {
    showHint = !showHint;
    update();
  }

  protected int numCellsFlipped(ReversiCell cell) {
    int currScore = this.model.getScore(this.color);
    MutableModel copy = this.model.copy();
    copy.place(cell, this.color);
    int potentialScore = copy.getScore(this.color);
    return potentialScore - currScore - 1;
  }

  /**
   * Package private so it can be called by the Frame. Returns the X coordinate of the
   * highlighted cell, so it can be observed by the controller and potentially observed by the
   * model. Returns and Optional Integer because there is not always a highlighted cell of which
   * to observe an X coordinate.
   *
   * @return optional Integer x coordinate of the highlighted cell.
   */
  Optional<Integer> getSelectedX() {
    return this.selectedX;
  }


  /**
   * Package private so it can be called by the Frame. Returns the Y coordinate of the
   * highlighted cell, so it can be observed by the controller and potentially observed by the
   * model. Returns and Optional Integer because there is not always a highlighted cell of which
   * to observe an Y coordinate.
   *
   * @return optional Integer Y coordinate of the highlighted cell.
   */
  Optional<Integer> getSelectedY() {
    return this.selectedY;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    paintComponentHelper(g2d);
  }

  protected abstract void paintComponentHelper(Graphics2D g2d);

  // calculates the width of a cell from the distance between two logical coordinates.
  protected abstract Double getCellWidth();

  protected void makeRows(double x, double y, double cellWidth) {
    for (int i = 0; i < this.model.getNumRows(); i++) {
      makeRow(i, x, y, cellWidth);
    }
  }

  protected abstract void makeRow(int rowNum, double x, double y, double cellWidth);

  protected abstract void iterateCells(int rowNum, double x, double y);

  // finds the physical center by transforming the logical center.
  protected Point2D setPhysicalCoords() {
    Point2D logicalCenterCell = new Point2D.Double(0, 0);
    Point2D physicalCenterCell = new Point2D.Double();
    transformLogicalToPhysical().transform(logicalCenterCell, physicalCenterCell);
    return physicalCenterCell;
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
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

}
