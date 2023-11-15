package cs3500.reversi.view;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;

/**
 * Graphical view represents the requirements of the model for assignment 6. This class extends
 * the class JFrame and implements ReversiView.
 */
public class GraphicalView extends JFrame implements ReversiView {

  // Private JPanel to hold the scores. Gets updated after each move.
  private JPanel scorePanel;
  // Private JLabels to hold each player's score. Gets updated after each move.
  private JLabel blackScore, whiteScore;
  //Private ReadOnlyModel so the view can observe the model, but not mutate it. Does not need to
  //be visible outside the class.
  private final ReadOnlyModel model;
  //Private final custom JPanel class to hold the rendering of the board.
  private final SimpleReversiBoard boardPanel;

  /**
   * Constructor for the Frame, initializes the parameters as well as other features of the frame
   * such as the title and size.
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

  // initializes and updates the scores on the GUI
  private void setScoreLabels(int black, int white) {
    scorePanel = new JPanel();
    scorePanel.setBackground(Color.GRAY);
    scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
    scorePanel.add(Box.createVerticalStrut(25));
    this.add(scorePanel, BorderLayout.NORTH);
    this.blackScore = new JLabel("Black: " + black);
    scorePanel.add(blackScore);
    scorePanel.add(Box.createHorizontalStrut(150));
    whiteScore = new JLabel("White: " + white);
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
  public Optional<Integer> getSelectedX(){
    return this.boardPanel.getSelectedX();
  }

  @Override
  public Optional<Integer> getSelectedY(){
    return this.boardPanel.getSelectedY();
  }
}
