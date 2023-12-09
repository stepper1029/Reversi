package cs3500.reversi.view.gui;

import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.event.MouseInputAdapter;

import java.awt.event.MouseEvent;
import java.util.Optional;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

/**
 * SimpleReversiBoard extends AbstractPanel. This class represents a JPanel which holds a rendering
 * of the current board in a game of Reversi. This class has features that allow the user to
 * highlight cells using mouse clicks as well as place disks.
 */
public class HexGUI extends AbstractPanel {

  // Protected final cells to hold the rendered Hexagons in the same coordinate grid as the Board
  // holds ReversiCells. The subclasses Hexagon and MouseEventsListener need access.
  // outer array holds rows (Hexagon[]), inner array holds Hexagons
  protected HexGUI.Hexagon[][] cells;

  /**
   * Constructor for the class, initializes the model and cells structure,and the mouse listener.
   * Creates a new instance of the Panel.
   *
   * @param model ReadOnlyModel because the view is only allowed observability not mutability.
   */
  public HexGUI(ReadOnlyModel model, DiskColor color) {
    super(model, color);
    this.cells = new Hexagon[this.model.getNumRows()][];
    HexGUI.MouseEventsListener listener = new HexGUI.MouseEventsListener();
    this.addMouseListener(listener);
  }

  /**
   * The "logical" size of our board, in cells. This uses the width of the widest row and the
   * number of rows.
   *
   * @return Our preferred *logical* size.
   */
  protected Dimension getPreferredLogicalSize() {
    return new Dimension(this.model.getRowSize(this.model.getBoardSize() / 4),
            (this.model.getNumRows()));
  }

  /**
   * Package private so it can be called by the Frame. Places a disk of the given color
   * in the currently highlighted cell. The controller handles the case in which no cell
   * is highlighted or the currently highlighted cell is an invalid move.
   *
   * @param color color of the disk to be placed.
   */
  void place(DiskColor color) {
    if (selectedX.isPresent() && selectedY.isPresent()) {
      Hexagon hex = this.cells[selectedX.get()][selectedY.get()];
      hex.addDisk(color);
      update();
      repaint();
    }
  }

  /**
   * Package private so it can be called by the Frame. Updates rendering so that no cell is
   * highlighted.
   */
  void update() {
    if (this.selectedX.isPresent() && this.selectedY.isPresent()) {
      Hexagon selectedHex = cells[this.selectedX.get()][this.selectedY.get()];
      selectedHex.unfill();
    }
    repaint();
  }



  @Override
  protected void paintComponentHelper(Graphics2D g2d) {
    // Set font and draw the text at the center of the shape
    Font font = new Font("Arial", Font.PLAIN, 16);
    g2d.setFont(font);
    FontMetrics metrics = g2d.getFontMetrics();


    if (this.color == this.model.getTurn()) {
      this.setBackground(new Color(179, 236, 255));
    } else {
      this.setBackground(Color.WHITE);
    }

    // find the physical center of the frame, calculates the cell width, and initializes the
    // hexagons to be centered in the frame. If they have already been initialized and the frame
    // size changes, then updates the cells to fit the new size.
    double x = setPhysicalCoords().getX();
    double y = setPhysicalCoords().getY();
    double cellWidth = this.getCellWidth();
    this.makeRows(x, y, cellWidth);

    // draws the cells and their disks
    for (int row = 0; row < this.model.getNumRows(); row++) {
      for (int cell = 0; cell < this.model.getRowSize(row); cell++) {
        Hexagon currHex = cells[row][cell];
        ReversiCell currCell = this.model.getCellAt(row, cell);

        // draw cells
        if (currHex.filled) {
          g2d.setColor(Color.CYAN);
        } else if (model.allPossibleMoves(this.color).contains(currCell)
                && model.getTurn() == this.color) {
          g2d.setColor(new Color(252, 197, 231));
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
        }

        g2d.fill(currHex);
        g2d.setColor(Color.BLACK);
        g2d.draw(currHex);
        if (currHex.filled && showHint) {
          String hint = "";
          try {
            hint = String.valueOf(numCellsFlipped(currCell));
          } catch (IllegalStateException e) {
            hint = "0";
          }
          int textWidth = metrics.stringWidth(hint);
          int textHeight = metrics.getHeight();

          // Calculate text position to center it within the shape
          int textX = (int) (currHex.center.getX() - textWidth / 2);
          int textY = (int) (currHex.center.getY() + textHeight / 4); // Adjust for centering

          g2d.drawString(hint, textX, textY);
        }

        // draw disks
        if (!this.model.isEmpty(currCell)) {
          DiskColor diskColor = this.model.getColorAt(currCell);
          Shape disk = currHex.addDisk(diskColor);
          g2d.setColor(currHex.diskColor);
          g2d.fill(disk);
        }
      }
    }
  }

  @Override
  protected Double getCellWidth() {
    Point2D logicalPointA = new Point2D.Double(0, 0);
    Point2D logicalPointB = new Point2D.Double(1, 0);
    Point2D physicalPointA = new Point2D.Double();
    Point2D physicalPointB = new Point2D.Double();
    this.transformLogicalToPhysical().transform(logicalPointA, physicalPointA);
    this.transformLogicalToPhysical().transform(logicalPointB, physicalPointB);

    return physicalPointB.getX() - physicalPointA.getX();
  }


  // initializes a row. Calculates physical x and y coordinates for the cells
  protected void makeRow(int rowNum, double x, double y, double cellWidth) {
    int rowSize = this.model.getRowSize(rowNum);
    double cellHeight = (cellWidth / Math.sqrt(3)) * 2;
    int midRowNum;
    if (this.model.getBoardSize() % 2 == 0) {
      midRowNum = this.model.getNumRows() / 2;
      if (rowNum % 2 != 0) {
        x -= (cellWidth / 2) * ((this.model.getRowSize(rowNum) - 1) / 2);
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

    if (this.cells[rowNum] == null) {
      this.cells[rowNum] = new Hexagon[rowSize];
    }
    iterateCells(rowNum, x, y);
    repaint();
  }

  // helper for makeRow. should resize the cells and update coordinates if the frame changes size.
  protected void iterateCells(int rowNum, double x, double y) {
    int rowSize = this.model.getRowSize(rowNum);
    double cellWidth = this.getCellWidth();
    for (int i = 0; i < rowSize; i++) {
      Point2D currPoint = new Point2D.Double(x, y);
      if (this.cells[rowNum][i] == null) {
        Hexagon currHex = new Hexagon(currPoint);
        this.placeHex(currHex, rowNum, i);
      } else {
        Hexagon currHex = this.cells[rowNum][i];
        Hexagon newHex = currHex.updateCenter(currPoint);
        this.placeHex(newHex, rowNum, i);
      }
      x += cellWidth / 2;
    }
  }

  // assigns the appropriate grid location to the given cell.
  private void placeHex(Hexagon hex, int numRow, int numCell) {
    this.cells[numRow][numCell] = hex;
  }

  /**
   * Class Hexagon extends Path2D.Double to create a custom Hexagon shape. Protected so it can be
   * inherited by its parent class and other subclasses within the parent class.
   */
  protected class Hexagon extends Path2D.Double {
    // center of the hexagon
    private Point2D center;
    // size: distance from the center to a vertice
    private double size;
    // if this hexagon has a disk in it.
    protected boolean hasDisk;
    // if this hexagon is currently highlighted
    private boolean filled;
    // the color of this hexagon
    // protected Color fillColor;
    // the color of the disk in this hexagon
    protected Color diskColor;

    // private constructor for the hexagon class initializes fields and creates a new Hexagon shape.
    private Hexagon(Point2D center) {
      this.size = getCellWidth() / Math.sqrt(3);
      this.center = center;
      this.hasDisk = false;
      this.filled = false;
      // this.fillColor = Color.LIGHT_GRAY;

      moveTo(this.center.getX(), this.center.getY() - size / 2);
      for (int i = 0; i < 7; i++) {
        double angle = Math.PI / 6 + i * Math.PI / 3;
        double x = this.center.getX() + size / 2 * Math.cos(angle);
        double y = this.center.getY() - size / 2 * Math.sin(angle);
        lineTo(x, y);
      }
    }

    // makes a copy of this hexagon and returns it.
    protected Hexagon makeCopy() {
      Hexagon copy = new Hexagon(this.center);
      if (this.filled) {
        copy.filled = true;
      }
      if (this.hasDisk) {
        if (this.diskColor.equals(Color.BLACK)) {
          copy.addDisk(DiskColor.Black);
        } else {
          copy.addDisk(DiskColor.White);
        }
      }
      return copy;
    }

    // updates the center of the hexagon if the size of the frame is changed.
    private Hexagon updateCenter(Point2D newCenter) {
      this.center = newCenter;
      this.size = getCellWidth() / Math.sqrt(3);
      return makeCopy();
    }

    // adds an ellipse to represent a disk to the center of this hexagon.
    private Shape addDisk(DiskColor color) {
      this.hasDisk = true;
      if (color.equals(DiskColor.Black)) {
        this.diskColor = Color.BLACK;
      } else {
        this.diskColor = Color.WHITE;
      }
      return new Ellipse2D.Double(this.center.getX() - (size * .25),
              this.center.getY() - (size * .25), (size * .5), (size * .5));
    }

    // unhighlights this hexagon.
    private void unfill() {
      // this.fillColor = Color.LIGHT_GRAY;
      this.filled = false;
      selectedX = Optional.empty();
      selectedY = Optional.empty();
    }

    // highlights this hexagon, possible unhighlighting other hexagons or this hexagon
    // in the process.
    private void fill() {
      if (this.filled) {
        this.unfill();
      } else if (!this.hasDisk) {

        for (int row = 0; row < cells.length; row++) {
          for (int cell = 0; cell < cells[row].length; cell++) {
            if (cells[row][cell].filled) {
              cells[row][cell].unfill();
            }
            if (cells[row][cell].equals(this)) {
              this.filled = true;
            }
          }
        }
      }
    }
  }

  // Private class MouseEventsListener extends MouseInputAdapter to listen for mouse clicks from
  // the user.
  private class MouseEventsListener extends MouseInputAdapter {

    // if the mouse is released, highlight of unhighlight a cell based on the physical position of
    // the mouse event.
    @Override
    public void mouseReleased(MouseEvent e) {
      Point physicalPoint = e.getPoint();
      boolean pointIsInBorder = false;
      for (int row = 0; row < cells.length; row++) {
        for (int cell = 0; cell < cells[row].length; cell++) {
          Hexagon hex = cells[row][cell];
          if (hex.contains(physicalPoint) && model.getTurn() == color) {
            selectedX = Optional.of(row);
            selectedY = Optional.of(cell);
            System.out.printf("HexCell(%d, %d)%n", selectedX.get(), selectedY.get());
            hex.fill();
            repaint();
            pointIsInBorder = true;
          }
        }
      }
      if (!pointIsInBorder) {
        if (selectedX.isPresent() && selectedY.isPresent()) {
          cells[selectedX.get()][selectedY.get()].unfill();
          repaint();
        }
      }
    }
  }
}

