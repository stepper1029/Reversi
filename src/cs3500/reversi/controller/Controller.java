package cs3500.reversi.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.reversi.model.DiskColor;
import cs3500.reversi.model.ReversiCell;
import cs3500.reversi.model.MutableModel;
import cs3500.reversi.view.KeyboardListener;
import cs3500.reversi.view.ReversiView;

/**
 * Controller class to facilitate interactions between the model and the view. THIS IS A
 * STUB OF THE CONTROLLER FOR THE PURPOSES OF CREATING FUNCTIONING KEY HANDLERS. There are many
 * things that this controller does not yet implement, like a game over method, keeping track
 * of whose turn it is, check if a move is valid, etc. The only functionality it currently supports
 * is for the keys 'p' and 'enter' to pass and place cells, respectively.
 */
public class Controller {

  // private final: one model for the game, it should never be reassigned. Only the controller
  // should have access to this model.
  private final MutableModel model;
  // private final: for now, one view for the model, it does not need to be reassigned. Only the
  // controller should have access to this view. In the future, may want to include two views,
  // one for each player.
  private final ReversiView view;

  /**
   * Constructor for the controller class initializes the model and view, configures the
   * keyboard listener, and makes the view visible to the user.
   * @param model model of the current game
   * @param view view for the current game
   */
  public Controller(MutableModel model, ReversiView view) {
    this.model = model;
    this.view = view;
    view.makeVisible();
    configureKeyBoardListener();
  }


  /**
   * Creates and sets a keyboard listener for the view. In effect, it creates snippets of code
   * as Runnable object, one for each time a key is typed, pressed and released, only for those
   * that the program needs.
   *
   * So far, we need to place a disk when the ENTER key is released and pass the current
   * players turn when the P key is released. These listeners are created and put in the
   * appropriate map.
   *
   * Last we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<Character, Runnable>();
    Map<Integer, Runnable> keyPresses = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> keyReleases = new HashMap<Integer, Runnable>();

    keyReleases.put(KeyEvent.VK_P, () -> {
            model.pass(this.model.getTurn());
            System.out.println("passed");
            view.update();
            }
    );
    keyPresses.put(KeyEvent.VK_ENTER, () -> {
              if (view.getSelectedX().isPresent() && view.getSelectedY().isPresent()) {
                ReversiCell cell = this.model.getCellAt(
                        view.getSelectedX().get(), view.getSelectedY().get());
                this.model.place(cell, this.model.getTurn());
                this.view.place(this.model.getTurn());
                System.out.println("placed");
                view.update();
              }
            }
    );

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }
}
