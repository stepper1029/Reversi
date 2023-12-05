package cs3500.reversi.provider.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import cs3500.reversi.provider.model.Coordinate;
import cs3500.reversi.provider.model.GamePieceColor;
import cs3500.reversi.provider.model.Piece;

/**
 * <h3>ReversiPanel Class</h3>
 * Represents a Reversi Panel. This panel extends Java's {@link JPanel} and is responsible for
 *     painting in all the components. This includes each ReversiPolygon with its correct color
 *     and each game piece present on the board.
 * @see Hexagon
 * @see ReversiPolygon
 * @see GamePieceColor
 */
public class ReversiPanel extends JPanel implements IReversiPanel {

  // A Map relating each ReversiPolygon to its color
  Map<ReversiPolygon, Color> hexagonColors;
  // The list of all pieces that are in play on the board
  List<Piece> playerList;
  // The apothem (length from center to side) of each Hexagon
  int apothem;

  /**
   * Creates a new Reversi Panel with the given parameters.
   * @param hexagonColors the Map of each ReversiPolygon to its color
   * @param playerList the list of all game pieces in play on the board
   * @param apothem the apothem of each Hexagon on the board
   */
  ReversiPanel(Map<ReversiPolygon, Color> hexagonColors, List<Piece> playerList, int apothem) {
    this.setBackground(new Color(130, 184, 218));
    this.playerList = playerList;
    this.apothem = apothem;
    this.hexagonColors = hexagonColors;
  }

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    // translates the origin to the middle of the board
    g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
    // this.setBorder(BorderFactory.createLineBorder(Color.black));
    // Paints each ReversiPolygon with its correct color by looping through the Map
    for (Map.Entry<ReversiPolygon, Color> hexagon : this.hexagonColors.entrySet()) {
      // for the fill
      g2d.setColor(hexagon.getValue());
      g2d.fill(hexagon.getKey());
      // for the outline
      g2d.setColor(new Color(130, 184, 218));
      g2d.drawPolygon(hexagon.getKey());
    }

    // Adds each game piece onto the board by looping through the model's board
    for (Piece gp : this.playerList) {
      GamePieceColor gpc = gp.getColor();
      // ensures the game piece is either black or white
      if (!gpc.equals(GamePieceColor.Empty)) {
        Coordinate center = gp.getCoordinate();
        g2d.setColor(gpc.getColor());

        // creates the game piece and fills it in
        Shape oval = new Ellipse2D.Double(
                (center.getX() * this.apothem) - (double) (this.apothem / 2),
                center.getY() * (int) Math.sqrt(3 * (Math.pow(this.apothem, 2)))
                        - ((double) this.apothem / 2),
                this.apothem, this.apothem);
        g2d.fill(oval);
      }
    }
  }
}
