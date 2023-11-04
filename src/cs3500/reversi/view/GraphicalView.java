package cs3500.reversi.view;

import java.awt.*;
import java.util.List;
import java.util.Objects;

import javax.sound.sampled.Line;
import javax.swing.*;

import cs3500.reversi.model.DiscColor;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.model.ReadOnlyModel;

public class GraphicalView extends JFrame implements ReversiView {

  private JPanel scorePanel;
  private JLabel whiteScoreLabel, blackScoreLabel;
  private ReadOnlyModel model;
  private BoardPanel boardPanel;

  public GraphicalView(MutableModel model) {
    super();
    this.model = Objects.requireNonNull(model);
    this.setTitle("Reversi");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // score labels
    this.setScoreLabels();

    boardPanel = new BoardPanel(model);
    this.add(boardPanel, BorderLayout.CENTER);


    // this.pack();

  }

  public void makeVisible() {
    this.setVisible(true);
  }

  public void setScoreLabels() {
    scorePanel = new JPanel();
    scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
    scorePanel.add(Box.createVerticalStrut(25));
    this.add(scorePanel, BorderLayout.NORTH);
    //scorePanel.add(Box.createHorizontalGlue());
    blackScoreLabel = new JLabel("Black: " + this.model.getScore(DiscColor.Black));
    scorePanel.add(blackScoreLabel);
    scorePanel.add(Box.createHorizontalStrut(150));
    whiteScoreLabel = new JLabel("White: " + this.model.getScore(DiscColor.White));
    scorePanel.add(whiteScoreLabel);
    scorePanel.add(Box.createHorizontalGlue());
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
