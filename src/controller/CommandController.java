package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

import controller.commands.Move;
import controller.commands.PickArrow;
import controller.commands.PickTreasure;
import controller.commands.Shoot;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randomizer.ActualRandomizer;
import randomizer.FixedRandomizer;
import view.IView;

//Code reference for command pattern:
// https://northeastern.instructure.com/courses/90366/pages/
// module-8-mvc-controllers?module_item_id=6535605

/**
 * This class represents the controller of the entire dungeon. It is used to play the dungeon
 * game by moving the player, picking arrows, treasure, and slaying the monsters. The game is over
 * when the player is eaten by the monster or falling into the pit
 * or wins by reaching the destination.
 */
public class CommandController implements IDungeonController, ActionListener {

  private Appendable out;
  private Scanner sc;
  private IView view;
  private Dungeon model;
  private int shootDist;
  private String shootArrowVariable;

  public CommandController(IView v, Dungeon m) {
    this.view = v;
    this.model = m;
    view.addActionListener(this);
    view.makeVisible();
    this.shootArrowVariable = "No";
  }

  /**
   * Constructs the output message and sets the input parameter.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public CommandController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    sc = new Scanner(in);
  }

  @Override
  public void go() {
    configureKeyBoardListener();
    configureButtonListener();
  }

  //Code reference for command pattern:
  // https://northeastern.instructure.com/courses/90366/pages/
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_UP, () -> {
      String text;
      if (!this.shootArrowVariable.equals("Shoot")) {
        text = new Move("N").goCommand(model);
      } else {
        text = new Shoot(shootDist, "N").goCommand(model);
        this.shootArrowVariable = "No";
      }
      view.showMessage(text);
      view.renderDungeon(this);
    });

    keyPresses.put(KeyEvent.VK_DOWN, () -> {
      String text;
      if (!this.shootArrowVariable.equals("Shoot")) {
        text = new Move("S").goCommand(model);
      } else {
        text = new Shoot(shootDist, "S").goCommand(model);
        this.shootArrowVariable = "No";
      }
      view.showMessage(text);
      view.renderDungeon(this);
    });

    keyPresses.put(KeyEvent.VK_LEFT, () -> {
      String text;
      if (!this.shootArrowVariable.equals("Shoot")) {
        text = new Move("W").goCommand(model);
      } else {
        text = new Shoot(shootDist, "W").goCommand(model);
        this.shootArrowVariable = "No";
      }
      view.showMessage(text);
      view.renderDungeon(this);
    });

    keyPresses.put(KeyEvent.VK_RIGHT, () -> {
      String text;
      if (!this.shootArrowVariable.equals("Shoot")) {
        text = new Move("E").goCommand(model);
      } else {
        text = new Shoot(shootDist, "E").goCommand(model);
        this.shootArrowVariable = "No";
      }
      view.showMessage(text);
      view.renderDungeon(this);
    });

    keyPresses.put(KeyEvent.VK_A, () -> {
      String text = new PickArrow().goCommand(model);
      view.renderDungeon(this);
      view.showMessage(text);
      this.shootArrowVariable = "No";
    });

    keyPresses.put(KeyEvent.VK_T, () -> {
      String text = new PickTreasure().goCommand(model);
      view.renderDungeon(this);
      view.showMessage(text);
      this.shootArrowVariable = "No";
    });

    keyPresses.put(KeyEvent.VK_1, () -> {
        this.shootDist = 1;
        this.shootArrowVariable = "Shoot";
        view.showMessage("Enter direction by using arrow keys for shooting");
    });

    keyPresses.put(KeyEvent.VK_2, () -> {
      this.shootDist = 2;
      this.shootArrowVariable = "Shoot";
      view.showMessage("Enter direction by using arrow keys for shooting");
    });

    keyPresses.put(KeyEvent.VK_3, () -> {
      this.shootDist = 3;
      this.shootArrowVariable = "Shoot";
      view.showMessage("Enter direction by using arrow keys for shooting");
    });

    keyPresses.put(KeyEvent.VK_4, () -> {
      this.shootDist = 4;
      this.shootArrowVariable = "Shoot";
      view.showMessage("Enter direction by using arrow keys for shooting");
    });

    keyPresses.put(KeyEvent.VK_5, () -> {
      this.shootDist = 5;
      this.shootArrowVariable = "Shoot";
      view.showMessage("Enter direction by using arrow keys for shooting");
    });

    keyReleases.put(KeyEvent.VK_UP, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_DOWN, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_LEFT, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_RIGHT, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_A, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_T, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_1, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_2, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_3, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_4, () -> {
      view.refresh();
    });
    keyReleases.put(KeyEvent.VK_5, () -> {
      view.refresh();
    });

    KeyBoardListener keyBoardListener = new KeyBoardListener();
    keyBoardListener.setKeyTypedMap(keyTypes);
    keyBoardListener.setKeyPressedMap(keyPresses);
    keyBoardListener.setKeyReleasedMap(keyReleases);

    view.addKeyListener(keyBoardListener);
  }

  @Override
  public void movePlayerOnKeyPress(String str) {
    String text = new Move(str).goCommand(model);
    view.showMessage(text);
    view.renderDungeon(this);
  }

  @Override
  public void shootOtyughOnKeyPress(int shootDist, String str) {
    String text = new Shoot(shootDist, str).goCommand(model);
    view.showMessage(text);
    view.renderDungeon(this);
  }

  @Override
  public void pickTreasureOnKeyPress() {
    String text = new PickTreasure().goCommand(model);
    view.showMessage(text);
    view.renderDungeon(this);
  }

  @Override
  public void pickArrowOnKeyPress() {
    String text = new PickArrow().goCommand(model);
    view.showMessage(text);
    view.renderDungeon(this);
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("Move North", () -> {
      String text = new Move("N").goCommand(model);
      view.renderDungeon(this);
      view.showMessage(text);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Move South", () -> {
      String text = new Move("S").goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Move East", () -> {
      String text = new Move("E").goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Move South", () -> {
      String text = new Move("S").goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Move West", () -> {
      String text = new Move("W").goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Pick Arrow", () -> {
      String text = new PickArrow().goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Pick Treasure", () -> {
      String text = new PickTreasure().goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Shoot Arrow", () -> {
      String text = new Shoot(view.getShootDistance(), view.getShootDirection()).goCommand(model);
      view.showMessage(text);
      view.renderDungeon(this);
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonClickedMap.put("Player Description", () -> {
      view.showPlayerDesc(model.getPlayerDescription());
      // set focus back to main frame so that keyboard events work
      view.resetFocus();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void play(Dungeon dungeon) throws IllegalArgumentException, IllegalStateException {

    if (dungeon == null) {
      throw new IllegalArgumentException("The Dungeon model cannot be null");
    }

    try {
      ICommandController cmd = null;
      while (!dungeon.hasReachedEnd() && dungeon.getPlayer().getHealth() > 0) {
        try {
          out.append("\n" + dungeon.getPlayerDescription());
          out.append(dungeon.getLocationDescription());
          out.append(dungeon.getNextPossibleDescription());
          out.append("\nSmell: " + dungeon.checkSmell());
          out.append("\nSoil Quality: " + dungeon.checkSoilType());
          out.append("\nWhat do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n");
          if (!sc.hasNext()) {
            break;
          }
          String in = sc.next();
          switch (in) {
            case "M":
            case "m":
              out.append("\nPlease select your next move.");
              out.append(" Enter N: North, Enter S: South, Enter E: East, Enter W: West\n");
              String input = sc.next();
              cmd = new Move(input);
              break;

            case "P":
            case "p":
              out.append("\nWhat? A/T\n");
              String pick = sc.next();
              if (pick.equalsIgnoreCase("A")) {
                cmd = new PickArrow();
              } else if (pick.equalsIgnoreCase("T")) {
                cmd = new PickTreasure();
              } else {
                out.append("\nInvalid entry");
              }
              break;

            case "S":
            case "s":
              out.append("\nNo. of caves (1-5)?\n");
              int dist = parseInt(sc.next());
              out.append("Where to?\n");
              String dir = sc.next();
              cmd = new Shoot(dist, dir);
              break;

            case "q":
            case "Q":
              out.append("Game Quit!");
              return;

            default:
              out.append("\nInvalid entry.");
              break;
          }
          if (cmd != null) {
            out.append(cmd.goCommand(dungeon)).append("\n");
            cmd = null;
          }
        } catch (IllegalArgumentException a) {
          out.append(a.getMessage());
        }
      }

      if (dungeon.hasReachedEnd()) {
        out.append("\nPlayer has reached the destination location "
                + dungeon.getPlayer().getCurrentLocation().getId() + ". Game Over!!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }

  private int parseInt(String s) throws IllegalArgumentException {
    int a = 0;
    try {
      a = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid input for dist");
    }
    return a;
  }

  private void createDungeonModel() {
    try {
      model = new DungeonImpl(view.getRows(), view.getColumns(), view.getInterconnectivity(),
              view.getTreasurePercent(), view.getWrapping(), view.getMonsterCount(),
              view.getThiefCount(), view.getPitCount(), view.getMovingMonsterCount(),
              new ActualRandomizer());
      this.view.setNewModel(model, this);
    } catch (IllegalArgumentException e) {
      this.view.showErrorMessage(e.getMessage());
    }
  }

  @Override
  public void moveThePlayerByClick(Direction direction) {
    String text = new Move(parseDirectionEnumToString(direction)).goCommand(model);
    view.showMessage(text);
    view.renderDungeon(this);
    // set focus back to main frame so that keyboard events work
    view.resetFocus();
  }

  private String parseDirectionEnumToString(Direction direction) {
    String dir = "";
    switch (direction) {
      case EAST:
        dir = "E";
        break;
      case WEST:
        dir = "W";
        break;
      case SOUTH:
        dir = "S";
        break;
      case NORTH:
        dir = "N";
        break;
    }
    return dir;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getActionCommand() == "Enter dungeon parameters") {
        int result = view.setDungeonParameters();
        if (result == JOptionPane.OK_OPTION) {
          createDungeonModel();
        } else if (result == JOptionPane.CANCEL_OPTION) {
          //do nothing if parameter box is cancelled
        }
      } else if (e.getActionCommand() == "Quit Game") {
        System.exit(0);
      } else if (e.getActionCommand() == "Restart Game") {
        this.model = new DungeonImpl(model);
        this.view.setNewModel(model, this);
        this.view.refresh();
      }
    } catch (Exception ex) {
      view.showErrorMessage(ex.getMessage());
    }
  }
}


