package cs3500.reversi.view;

//import java.awt.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;
// import javax.swing.*;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReadOnlyModel;

/**
 * Graphical view represents the requirements of the model for assignment 6. This class extends
 * the class JFrame and implements ReversiView.
 */
public class GraphicalView extends JFrame implements ReversiView {

  // Private JPanel to hold the scores. Gets updated after each move.
  private JPanel scorePanel;
  //Private ReadOnlyModel so the view can observe the model, but not mutate it. Does not need to
  //be visible outside the class.
  private final ReadOnlyModel model;
  //Private final custom JPanel class to hold the rendering of the board.
  private final SimpleReversiBoard boardPanel;

  /**
   * Constructor for the Frame, initializes the parameters as well as other features of the frame
   * such as the title and size.
   *
   * @param model Is passed in as a Mutable Model but the field type is a ReadOnlyModel
   */
  public GraphicalView(ReadOnlyModel model) {
    super();
    this.model = Objects.requireNonNull(model);
    this.setTitle("Reversi");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // score labels
    this.setScoreLabels(this.model.getScore(DiskColor.Black), this.model.getScore(DiskColor.White));

    boardPanel = new SimpleReversiBoard(model);
    this.add(boardPanel, BorderLayout.CENTER);

    this.pack();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(ViewFeatures viewFeatures) {
//    echoButton.addActionListener(evt -> features.echoOutput(input.getText()));
//    toggleButton.addActionListener(evt -> features.toggleColor());
//    exitButton.addActionListener(evt -> features.exitProgram());
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_P:
            viewFeatures.receivePass();
            break;

          case KeyEvent.VK_ENTER:
            viewFeatures.receivePlace(model.getTurn(),
                    model.getCellAt(getSelectedX().get(), getSelectedY().get()));
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {}
    });
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  // initializes and updates the scores on the GUI
  private void setScoreLabels(int black, int white) {
    scorePanel = new JPanel();
    scorePanel.setBackground(Color.GRAY);
    scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
    scorePanel.add(Box.createVerticalStrut(25));
    this.add(scorePanel, BorderLayout.NORTH);
    // JLabels to hold each player's score. Gets updated after each move.
    JLabel blackScore = new JLabel("Black: " + black);
    scorePanel.add(blackScore);
    scorePanel.add(Box.createHorizontalStrut(150));
    JLabel whiteScore = new JLabel("White: " + white);
    scorePanel.add(whiteScore);
    scorePanel.add(Box.createHorizontalGlue());
  }

  @Override
  public void update() {
    this.setScoreLabels(this.model.getScore(DiskColor.Black), this.model.getScore(DiskColor.White));
    this.scorePanel.revalidate();
    this.scorePanel.repaint();
    this.boardPanel.update();
  }

  @Override
  public void place(DiskColor color) {
    this.boardPanel.place(color);
  }

  @Override
  public Optional<Integer> getSelectedX() {
    return this.boardPanel.getSelectedX();
  }

  @Override
  public Optional<Integer> getSelectedY() {
    return this.boardPanel.getSelectedY();
  }
}
