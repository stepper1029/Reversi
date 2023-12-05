package view;

import java.awt.Polygon;
import model.Coordinate;

/**
 * <h3>Hexagon Class</h3>
 * Represents a Hexagon. This Hexagon has a center {@link Coordinate} and an apothem, which
 *     is the distance from the center to the midway point of one edge.
 */
public class Hexagon extends ReversiPolygon {
  // the center of this Hexagon
  private final Coordinate center;
  // the distance from the center to the midway point of an edge of this Hexagon
  int apothem;
  // the length from the center to a corner of this Hexagon
  // this is a double because it is calculated with trigonometry rather than being
  // defined from a constructor argument
  double hypotenuse;

  /**
   * Constructs a new Hexagon with a center and apothem. The constructor calls the
   *     {@code createHexagon()} method in order to calculate the bounds.
   * @param center the center {@link Coordinate} of this Hexagon.
   * @param apothem the apothem of this Hexagon.
   */
  public Hexagon(Coordinate center, int apothem) {
    super(center, apothem, 30);
    this.center = center;
    this.apothem = apothem;
    this.hypotenuse = (apothem / (Math.cos(Math.toRadians(30))));
    this.createHexagon();
  }

  /**
   * Creates a new Hexagon given the center coordinate and apothem. This method works
   *     by adding the six coordinates of each corner of the Hexagon to itself. The Java
   *     {@link Polygon} implementation has a method called addPoint() that adds each
   *     point to an {@code Array} of points.
   */
  private void createHexagon() {
    double degrees = 30.0;
    // loops 6 times - one for each side
    for (int i = 0; i < 6; i++) {
      double x = this.center.getX() + (hypotenuse * Math.cos(Math.toRadians(degrees)));
      double y = this.center.getY() + (hypotenuse * Math.sin(Math.toRadians(degrees)));
      super.addPoint((int) x, (int) y);
      degrees -= 60.0;
    }
  }
}
