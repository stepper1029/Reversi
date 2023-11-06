package cs3500.reversi.view;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class HexButton extends JButton {

  private Hexagon hex = new Hexagon();

  public HexButton() {
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
    setOpaque(false);
    setPreferredSize(new Dimension(110, 100));
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.setColor(Color.BLUE);
    g2d.fill(hex);
    super.paintComponent(g);
  }

  public boolean contains(int x, int y) {
    return hex.contains(x, y);
  }

  private static class Hexagon extends Path2D.Double {
    private Hexagon() {
      int size = 100; // Adjust the size as needed
      int centerX = size / 2;
      int centerY = size / 2;

      moveTo(centerX + size / 2, centerY);
      for (int i = 1; i < 6; i++) {
        double angle = 2 * Math.PI / 6 * i;
        double x = centerX + size / 2 * Math.cos(angle);
        double y = centerY + size / 2 * Math.sin(angle);
        lineTo(x, y);
      }
      closePath();
    }
  }
}
