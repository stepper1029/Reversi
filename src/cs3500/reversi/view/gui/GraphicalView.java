package cs3500.reversi.view.gui;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Objects;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;


import cs3500.reversi.controller.PlayerActions;
import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;

/**
 * Graphical view represents the view require for assignment 7. This class extends
 * the class JFrame and implements ReversiView.
 */
public class GraphicalView extends JFrame implements ReversiView {

  // Private JPanel to hold the scores. Gets updated after each move.
  private final JPanel scorePanel;
  private final JLabel blackScore;
  private final JLabel whiteScore;
  //Private ReadOnlyModel so the view can observe the model, but not mutate it. Does not need to
  //be visible outside the class.
  private final ReadOnlyModel model;
  //Private final custom JPanel class to hold the rendering of the board.
  private final AbstractPanel boardPanel;
  // private panel to display a message when the game is over
  private final JPanel gameOverPanel;
  // color of the player who this view belongs to
  private final DiskColor color;

  /**
   * Constructor for the Frame, initializes the parameters as well as other features of the frame
   * such as the title and size.
   *
   * @param model Is passed in as a Mutable Model but the field type is a ReadOnlyModel
   */
  public GraphicalView(ReadOnlyModel model, DiskColor color, AbstractPanel boardPanel) {
    super();
    this.color = color;
    this.model = Objects.requireNonNull(model);
    this.setTitle("Reversi - " + this.color);
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // game over panel
    this.gameOverPanel = new JPanel();

    // score labels
    scorePanel = new JPanel();
    scorePanel.setBackground(Color.LIGHT_GRAY);
    scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
    scorePanel.add(Box.createVerticalStrut(25));
    this.add(scorePanel, BorderLayout.NORTH);
    // JLabels to hold each player's score. Gets updated after each move.
    blackScore = new JLabel("Black: " + this.model.getScore(DiskColor.Black));
    scorePanel.add(blackScore);
    scorePanel.add(Box.createHorizontalStrut(150));
    whiteScore = new JLabel("White: " + this.model.getScore(DiskColor.White));
    scorePanel.add(whiteScore);
    scorePanel.add(Box.createHorizontalGlue());

    // board panel
    this.boardPanel = boardPanel;
    this.add(boardPanel, BorderLayout.CENTER);

    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(PlayerActions playerActions) {
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        update();
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
          playerActions.receivePass();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          if (getSelectedX().isPresent() && getSelectedY().isPresent()) {
            playerActions.receivePlace(
                    model.getCellAt(getSelectedX().get(), getSelectedY().get()));
          }
        } else if (e.getKeyCode() == KeyEvent.VK_H) {
          boardPanel.toggleHint();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // no events need to happen when the key is released
      }
    });
  }

  // initializes and updates the scores on the GUI
  private void updateScoreLabels() {
    blackScore.setText("Black: " + this.model.getScore(DiskColor.Black));
    whiteScore.setText("White: " + this.model.getScore(DiskColor.White));
  }

  @Override
  public void update() {
    this.updateScoreLabels();
    this.scorePanel.revalidate();
    this.scorePanel.repaint();
    this.boardPanel.update();
  }

  @Override
  public void place(DiskColor color) {
    this.boardPanel.place(color);
    this.update();
  }

  @Override
  public Optional<Integer> getSelectedX() {
    return this.boardPanel.getSelectedX();
  }

  @Override
  public Optional<Integer> getSelectedY() {
    return this.boardPanel.getSelectedY();
  }

  @Override
  public void popUpError(String message) {
    JOptionPane.showMessageDialog(this.boardPanel,
            message, "Notification", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void gameOver() {
    String message = "";
    if (this.model.getWinner().equals(Optional.empty())) {
      message = "Game over. You tied!";
    } else if (this.model.getWinner().isPresent() &&
            this.model.getWinner().get().equals(this.color)) {
      message = "Game over. You win!";
    } else {
      message = "Game Over. You lose :(";
    }

    message += " Your score: " + this.model.getScore(this.color);
    JLabel gameOverLabel = new JLabel(message);
    gameOverLabel.setForeground(Color.RED);
    gameOverPanel.setLayout(new FlowLayout());
    gameOverPanel.add(gameOverLabel);
    gameOverPanel.setBackground(Color.yellow);
    this.add(gameOverPanel, BorderLayout.SOUTH);
    this.update();
  }
}
