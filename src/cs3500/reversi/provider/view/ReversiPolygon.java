package cs3500.reversi.provider.view;

import java.awt.Polygon;

import cs3500.reversi.provider.model.Coordinate;

/**
 * <h3>ReversiPolygon Abstract Class</h3>
 * This class extends Java's {@link Polygon} class to create a Polygon specific to
 *     this Reversi game. This class has methods to get the center {@link Coordinate} of
 *     this ReversiPolygon and to get the apothem (distance from the center coordinate to
 *     the midpoint of an edge) of this ReversiPolygon.
 */
abstract class ReversiPolygon extends Polygon {

  // the center coordinate of this ReversiPolygon
  private final Coordinate center;
  // represents the distance from the center coordinate to the midpoint
  // of this ReversiPolygon's edge
  private final int apothem;
  // the degrees to travel from 0 to this ReversiPolygon's first corner.
  private final int degreesToCorner;

  /**
   * Creates a new ReversiPolygon with a center, apothem, and the degrees from
   *     0 to the top-right corner.
   * @param center the center {@link Coordinate}
   * @param apothem the apothem of this ReversiPolygon
   * @param degreesToCorner the degrees from 0 to the top-right corner of this ReversiPolygon
   */
  public ReversiPolygon(Coordinate center, int apothem, int degreesToCorner) {
    this.center = center;
    this.apothem = apothem;
    this.degreesToCorner = degreesToCorner;
  }

  /**
   * Returns the center of this ReversiPolygon.
   * @return the center {@link Coordinate} of this ReversiPolygon
   */
  public Coordinate getCenter() {
    return this.center;
  }

  /**
   * Returns the apothem of this ReversiPolygon.
   * @return the {@code int} value of this ReversiPolygon's apothem
   */
  public int getApothem() {
    return this.apothem;
  }

  /**
   * Gets this ReversiPolygon's degrees from 0 to its top-rightmost corner.
   * @return the {@code int} value of this ReversiPolygon's degrees
   */
  public int getDegrees() {
    return this.degreesToCorner;
  }
}
