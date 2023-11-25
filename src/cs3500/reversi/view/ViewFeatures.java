package cs3500.reversi.view;

import cs3500.reversi.model.DiskColor;

public interface ViewFeatures {

  /**
   * Places a disk of the given color on the highlighted cell. The location of the highlighted cell
   * is known locally within the view, so it doesn't need to be passed in.
   * @param color the color of the disk to be placed based on the current player.
   */
  void place(DiskColor color);

  void pass();
}
