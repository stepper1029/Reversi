package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Coordinate;
import model.Piece;
import model.ReadonlyReversiModel;

/**
 * <h3>ReversiGUIView Class</h3>
 * Represents a graphical view for this Reversi game. This view contains a {@link JFrame} and a
 *     {@link ReversiPanel} that are each responsible for creating each component and action for
 *     the board.
 */
public class ReversiGUIView extends JFrame implements ReversiView, IReversiFrame {

  // represents the model this ReversiGUIView pulls information from
  private final ReadonlyReversiModel model;
  // A Map of each ReversiPolygon to its color in the GUI view
  private final Map<ReversiPolygon, Color> hexColors;
  // A list of all cells that are clicked by the user
  List<ReversiPolygon> clickedCells;
  // A list of all the pieces from both players of this Reversi game
  private final List<Piece> playerList;
  // The ReversiPanel that handles painting all components onto the GUI view
  private final ReversiPanel panel;
  // The apothem (distance from the center to the midpoint of an edge)
  // of a ReversiPolygon
  private final int apothem;
  private ViewFeatures observer;
  private Coordinate curCoor = null;
  private final int playerNumber;

  /**
   * Constructs a new ReversiGUIView with the given model and player number.
   * @param model the {@link ReadonlyReversiModel}
   * @param playerNumber the player number to be displayed in the title bar
   */
  public ReversiGUIView(ReadonlyReversiModel model, int playerNumber) {

    // responsible for initial board creation
    super();
    this.model = model;
    this.playerNumber = playerNumber;
    this.setTitle("Reversi Player " + this.playerNumber);
    this.setSize(500, 487);

    // initializes all Maps and Lists, along with the apothem
    this.hexColors = new HashMap<>();
    this.playerList = new ArrayList<>();
    this.clickedCells = new ArrayList<>();
    this.apothem = this.getWidth() / (this.model.getMaxSize() * 2);

    // resets the size of the board given the apothem
    this.setSize(this.apothem * 2 * model.getMaxSize(), (int) this.getYHeight());

    // sets the preferred size and creates all the ReversiPolygons to be displayed
    this.panel = new ReversiPanel(this.hexColors, this.playerList, this.apothem);
    this.panel.setPreferredSize(
            new Dimension(this.apothem * 2 * model.getMaxSize(), (int) this.getYHeight()));
    this.createBoard();
    this.initializeBoard();

    // adds a new mouse listener that checks for mouse clicks
    this.addMouseListener(new MouseAdapter() {

      /**
       * Handles mouse clicks by a user. Each click should set the color of the clicked cell
       *     and de-select any previously clicked cells.
       * @param e the event to be processed
       */
      @Override
      public void mouseClicked(MouseEvent e) {
        // sets a map of each ReversiPolygon to its Color to be displayed in the GUI view
        Map<ReversiPolygon, Color> hexColors = panel.hexagonColors;

        // The x-value of the mouse click coordinate, translated by the origin of the center-point
        // of this game board
        int x = e.getX() - (getWidth() / 2);

        // The y-value of the mouse click coordinate, translated by the origin of the center-point
        // of this game board

        int y = e.getY() - (getHeight() / 2) - 15;

        // the model's coordinate for the clicked cell
        // the model's coordinates are axial and start at (0, 0), which is the center of the
        // game board. Moving a cell to the right goes in the positive x-direction
        // and moving downwards goes in the positive y-direction
        int modelx = 0;
        int modely = 0;

        // loops through each entry in the Map of all ReversiPolygons in the board
        for (Map.Entry<ReversiPolygon, Color> entry : hexColors.entrySet()) {

          // sets this local variable to be the current ReversiPolygon from the map
          ReversiPolygon polygon = entry.getKey();

          // checks if the cell contains the mouseclick and the cell isn't already clicked
          if (polygon.contains(x, y) && !(clickedCells.contains(polygon))) {
            clickedCells.add(polygon);

            // sets the color to highlight the cell
            entry.setValue(new Color(220, 240, 255));

            // translate the view's coordinates to the coordinates of the model
            modelx = polygon.getCenter().getX() / polygon.getApothem();
            modely = polygon.getCenter().getY()
                    / ((int) (polygon.getApothem()
                    * Math.cos(Math.toRadians(polygon.getDegrees())) * 2));

            // Note: Our Coordinate class just takes in an x and y coordinate.
            // Coordinate has an overridden toString() method that returns a String
            // in the following format: "Coordinate: (this.x, this.y)"
            curCoor = new Coordinate(modelx, modely);
          }
          else {
            // removes the previously-clicked polygon from
            // the list of clicked polygons if applicable
            clickedCells.remove(polygon);
            // sets the value of each ReversiPolygon component to the default color
            entry.setValue(new Color(195, 220, 250));
          }
        }
        // repaints the GUI view
        repaint();
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (e.getKeyChar() == 'm') {
          observer.moveTurn(curCoor);
        }
        else if (e.getKeyChar() == 'p') {
          observer.passTurn(playerNumber);
        }
      }
    });

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.makeVisible();
  }

  /**
   * Gets the y-height of this board, calculated from the apothem of the {@link ReversiPolygon}s.
   * @return the {@code double} of the height of the board
   * @see Hexagon
   * @see ReversiPolygon
   */
  private double getYHeight() {
    double sidelength = this.apothem * Math.tan(Math.toRadians(30)) * 2;
    double yheight = Math.sqrt((Math.pow(sidelength, 2) - Math.pow(apothem, 2)));
    return (this.model.getMaxSize() * sidelength) + ((this.model.getMaxSize() + 1) * yheight) + 25;
  }

  /**
   * Creates the board with all the components necessary.
   * @implNote The board is build row-by-row, with the origin being the direct center of an
   *     axial coordinate system. There are two helpers which each assist by constructing the
   *     board in the positive y-direction and the negative y-direction respectively.
   * @see Hexagon
   */
  private void createBoard() {

    // accumulator is set to be the leftmost x-value of the Reversi hexagon tiles
    int acc = this.apothem - (this.getWidth() / 2);

    // creates only the middle row
    for (int i = 0; i < this.model.getMaxSize(); i++) {
      Coordinate center = new Coordinate(acc, 0);
      ReversiPolygon h = new Hexagon(center, apothem);
      this.hexColors.put(h, new Color(195, 220, 250));
      acc += 2 * apothem;
    }

    // resets the x and y coordinates
    int centerX = this.apothem - (this.getWidth() / 2);
    int centerY = 0;

    // sizeToBuild updates as it progresses through building each row
    int sizeToBuild = this.model.getMaxSize() - 1;

    // continues building downwards until an IllegalArgumentException is thrown
    while (true) {
      try {
        // calls the helper and updates the x and y coordinates when a row is complete
        this.buildPositive(new Coordinate(centerX, centerY), sizeToBuild);
        centerX += this.apothem;
        centerY += (int) Math.sqrt(3 * (Math.pow(this.apothem, 2)));
        sizeToBuild--;
      } catch (IllegalArgumentException e) {
        break;
      }
    }

    // resets the x and y coordinates, along with the apothem
    centerX = this.apothem - (this.getWidth() / 2);
    centerY = 0;
    sizeToBuild = this.model.getMaxSize() - 1;

    // continues building upwards until an IllegalArgumentException is thrown
    while (true) {
      try {
        // calls the helper and updates the x and y coordinates when a row is complete
        this.buildNegative(new Coordinate(centerX, centerY), sizeToBuild);
        centerX += this.apothem;
        centerY -= (int) Math.sqrt(3 * (Math.pow(this.apothem, 2)));
        sizeToBuild--;
      } catch (IllegalArgumentException e) {
        break;
      }
    }
  }

  /**
   * Helps the createBoard() method by creating all the cells in the negative y-direction.
   * @param initCoor the initial coordinate from where the board is built out
   * @param numToBuild the number of cells to build
   * @implNote {@code model.getMaxSize()} returns the size of the row with the most cells
   * @throws IllegalArgumentException if no more rows can be created according to the model
   */
  private void buildNegative(Coordinate initCoor, int numToBuild) throws IllegalArgumentException {
    if (numToBuild < (this.model.getMaxSize() / 2 + 1)) {
      throw new IllegalArgumentException("Cannot build more Hexagons");
    }

    int yDistance = (int) Math.sqrt(3 * (Math.pow(this.apothem, 2)));

    int centerX = initCoor.getX() + this.apothem;
    int centerY = initCoor.getY() - yDistance;

    // builds out the row according to how many cells are required
    buildHexagons(numToBuild, centerX, centerY);
  }

  /**
   * Helps the createBoard() method by creating all the cells in the positive y-direction.
   * @param initCoor the initial coordinate from where the board is built out
   * @param numToBuild the number of cells to build
   * @implNote {@code model.getMaxSize()} returns the size of the row with the most cells
   * @throws IllegalArgumentException if no more rows can be created according to the model
   */
  private void buildPositive(Coordinate initCoor, int numToBuild) throws IllegalArgumentException {
    if (numToBuild < (this.model.getMaxSize() / 2 + 1)) {
      throw new IllegalArgumentException("Cannot build more Hexagons");
    }

    int yDistance = (int) Math.sqrt(3 * (Math.pow(this.apothem, 2)));

    int centerX = initCoor.getX() + this.apothem;
    int centerY = initCoor.getY() + yDistance;

    // builds out the row according to how many cells are required
    buildHexagons(numToBuild, centerX, centerY);
  }

  /**
   * Builds a single row of {@link Hexagon}.
   * @param numToBuild the number of Hexagons to build
   * @param centerX the center x-value of this Hexagon
   * @param centerY the center y-value of thies Hexagon
   */
  private void buildHexagons(int numToBuild, int centerX, int centerY) {
    for (int i = 0; i < numToBuild; i++) {
      Coordinate center = new Coordinate(centerX, centerY);
      ReversiPolygon p = new Hexagon(center, apothem);
      this.hexColors.put(p, new Color(195, 220, 250));
      centerX += (2 * this.apothem);
    }
  }

  /**
   * Initializes the board by setting the list of filled cells and adding the panel.
   * @implNote model.getBoard() returns a List of all Pieces on the board. Every single cell
   *     in the board is a Piece, and a Piece has a Coordinate accessible by the
   *     getCoordinate() method and a GamePieceColor, which is an enum accessible
   *     with the getColor() method. A GamePieceColor can be Empty if there are no players in
   *     a particular cell.
   * @see ReversiPanel
   */
  private void initializeBoard() {
    List<Piece> logp = this.model.getBoard();
    this.playerList.addAll(logp);
    this.add(this.panel);
  }

  /**
   * Makes the GUIView visible if called.
   */
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void displayPanel(Message message) {
    JOptionPane.showMessageDialog(this, message.returnMessage(this.playerNumber));
  }

  @Override
  public void render() {
    this.repaint();
  }

  @Override
  public void addFeatures(ViewFeatures listener) {
    this.observer = listener;
  }
}
