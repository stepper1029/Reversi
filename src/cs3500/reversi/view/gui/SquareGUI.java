package cs3500.reversi.view.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;
import cs3500.reversi.model.ReversiCell;

public class SquareGUI extends AbstractPanel {

  // Protected final cells to hold the rendered Hexagons in the same coordinate grid as the Board
  // holds ReversiCells. The subclasses Hexagon and MouseEventsListener need access.
  // outer array holds rows (Hexagon[]), inner array holds Hexagons
  protected Square[][] cells;

  public SquareGUI(ReadOnlyModel model, DiskColor color) {
    super(model, color);
    this.cells = new Square[this.model.getNumRows()][];
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
  }

  @Override
  protected Dimension getPreferredLogicalSize() {
    return new Dimension(this.model.getBoardSize(), this.model.getBoardSize());
  }

  @Override
  void place(DiskColor color) {
    if (selectedX.isPresent() && selectedY.isPresent()) {
      Square square = this.cells[selectedX.get()][selectedY.get()];
      square.addDisk(color);
      update();
      repaint();
    }
  }

  @Override
  void update() {
    if (this.selectedX.isPresent() && this.selectedY.isPresent()) {
      Square selectedSquare = cells[this.selectedX.get()][this.selectedY.get()];
      selectedSquare.unfill();
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
        Square currSquare = cells[row][cell];
        ReversiCell currCell = this.model.getCellAt(row, cell);

        // draw cells
        if (currSquare.filled) {
          g2d.setColor(Color.CYAN);
//          System.out.println("squarex: " + row);
//          System.out.println("squarey: " + cell);
//          System.out.println("selectedX: " + selectedX);
//          System.out.println("selectedy: " + selectedY);
        } else if (model.allPossibleMoves(this.color).contains(currCell)
                && model.getTurn() == this.color) {
          g2d.setColor(new Color(252, 197, 231));
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
        }

        g2d.fill(currSquare);
        g2d.setColor(Color.BLACK);
        g2d.draw(currSquare);
        if (currSquare.filled && showHint) {
          try {
            String hint = String.valueOf(numCellsFlipped(currCell));
            int textWidth = metrics.stringWidth(hint);
            int textHeight = metrics.getHeight();
            // Calculate text position to center it within the shape
            int textX = (int) (currSquare.center.getX() - textWidth / 2);
            int textY = (int) (currSquare.center.getY() + cellWidth + textHeight / 4);

            g2d.drawString(hint, textX, textY);
          } catch(IllegalStateException e) {
          }
        }

        // draw disks
        if (!this.model.isEmpty(currCell)) {
          DiskColor diskColor = this.model.getColorAt(currCell);
          Shape disk = currSquare.addDisk(diskColor);
          g2d.setColor(currSquare.diskColor);
          g2d.fill(disk);
        }
      }
    }
  }

  @Override
  protected Double getCellWidth() {
    return (this.getSize().width / this.model.getNumRows()) * .75;
  }

  @Override
  protected void makeRow(int rowNum, double x, double y, double cellWidth) {
    int rowSize = this.model.getRowSize(rowNum);
    int middle = this.model.getNumRows() / 2;
    if (rowNum < middle) {
      y -= (cellWidth * (middle - rowNum)) + (cellWidth  / 2);
    } else {
      y += (cellWidth * (rowNum - middle)) - (cellWidth / 2);
    }

    x -= (cellWidth * (this.model.getNumRows() / 2)) - (cellWidth / 2);

    if (this.cells[rowNum] == null) {
      this.cells[rowNum] = new Square[rowSize];
    }

    if (y != this.setPhysicalCoords().getY()) {
      iterateCells(rowNum, x, y);
      repaint();
    }
  }

  @Override
  protected void iterateCells(int rowNum, double x, double y) {
    int rowSize = this.model.getRowSize(rowNum);
    double cellWidth = this.getCellWidth();
    for (int i = 0; i < rowSize; i++) {
      Point2D currPoint = new Point2D.Double(x, y);
      if (this.cells[rowNum][i] == null) {
        Square currSquare = new Square(currPoint);
        this.placeSquare(currSquare, rowNum, i);
      } else {
        Square currSquare = this.cells[rowNum][i];
        Square newSquare = currSquare.updateCenter(currPoint);
        this.placeSquare(newSquare, rowNum, i);
      }
      x += cellWidth;
    }
  }

  // assigns the appropriate grid location to the given cell.
  private void placeSquare(Square square, int numRow, int numCell) {
    this.cells[numRow][numCell] = square;
  }


  /**
   * Class Hexagon extends Path2D.Double to create a custom Hexagon shape. Protected so it can be
   * inherited by its parent class and other subclasses within the parent class.
   */
  protected class Square extends Path2D.Double {
    // center of the hexagon
    private Point2D center;
    // size: width
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
    private Square(Point2D center) {
      this.size = getCellWidth();
      this.center = center;
      this.hasDisk = false;
      this.filled = false;

      double x = center.getX() - (size / 2); // x-coordinate of the square's top-left corner
      double y = center.getY() + (size / 2); // y-coordinate of the square's top-left corner
      moveTo(x, y);
      lineTo(x + size, y);
      lineTo(x + size, y + size);
      lineTo(x, y + size);
      closePath(); // Close the square path
    }

    // makes a copy of this hexagon and returns it.
    protected Square makeCopy() {
      Square copy = new Square(this.center);
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
    private Square updateCenter(Point2D newCenter) {
      this.center = newCenter;
      this.size = getCellWidth();
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
              this.center.getY() - (size * .25) + getCellWidth(), (size * .5), (size * .5));
    }

    // unhighlights this square
    private void unfill() {
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
              selectedX = Optional.of(row);
              selectedY = Optional.of(cell);

            }
          }
        }
      }
    }
  }

  private class MouseEventsListener extends MouseInputAdapter {

    // if the mouse is released, highlight of unhighlight a cell based on the physical position of
    // the mouse event.
    @Override
    public void mouseReleased(MouseEvent e) {
      Point physicalPoint = e.getPoint();
      boolean pointIsInBorder = false;
      for (int row = 0; row < cells.length; row++) {
        for (int cell = 0; cell < cells[row].length; cell++) {
          Square square = cells[row][cell];
          if (square.contains(physicalPoint) && model.getTurn() == color) {
            selectedX = Optional.of(row);
            selectedY = Optional.of(cell);
            square.fill();
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
