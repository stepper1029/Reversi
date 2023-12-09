package cs3500.reversi.view.gui;

import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;

public class AbstractPanel extends JPanel {
  // Private final ReadOnlyModel so the model can be observed but not mutated. Does not need to
  // reassigned or visible outside of this class.
  protected ReadOnlyModel model;

  // Protected final cells to hold the rendered Hexagons in the same coordinate grid as the Board
  // holds ReversiCells. The subclasses Hexagon and MouseEventsListener need access.
  // outer array holds rows (Hexagon[]), inner array holds Hexagons
  protected HexGUI.Hexagon[][] cells;

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
    HexGUI.MouseEventsListener listener = new HexGUI.MouseEventsListener();
    this.addMouseListener(listener);
    this.cells = new HexGUI.Hexagon[this.model.getNumRows()][];
    this.selectedX = Optional.empty();
    this.selectedY = Optional.empty();
    this.showHint = false;
  }

}
