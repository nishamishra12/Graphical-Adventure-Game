package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import controller.IDungeonController;
import dungeon.Dungeon;
import dungeon.ReadOnlyModel;

/**
 * This is a view interface for the entire dungeon GUI. The view consists of the header, footer,
 * j-menus and their respective listeners.
 */
public interface IView {

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Key listener to capture and act on every key press.
   *
   * @param listener takes the key listener
   */
  void addKeyListener(KeyListener listener);

  /**
   * Action listener for every button and menus present in the view screen.
   *
   * @param listener takes the action listener
   */
  void addActionListener(ActionListener listener);

  /**
   * This method is used to render the dungeon after every action the player takes.
   *
   * @param listener takes controller
   */
  void renderDungeon(IDungeonController listener);

  /**
   * This method shows the error message to the screen in case of any exceptions.
   *
   * @param error
   */
  void showErrorMessage(String error);

  /**
   * This method rewrites all the text messages which are displayed to the user during the game.
   *
   * @param text the text to be displayed after every action
   */
  void showMessage(String text);

  /**
   * Refreshes the view panel
   */
  void refresh();

  /**
   * Reset focus from keyboards to mouse and mouse to keyboard
   */
  void resetFocus();

  /**
   * This method is used to create a new model every time the user enters new values or restarts
   * the game.
   *
   * @param model the new model
   * @param listener the controller
   */
  public void setNewModel(ReadOnlyModel model, IDungeonController listener);

  /**
   * This method gives the shoot direction.
   *
   * @return the shoot direction
   */
  public String getShootDirection();

  /**
   * The method gives the shoot distance.
   *
   * @return the shoot distance
   */
  public int getShootDistance();

  /**
   * this method is used to clear all descriptions once the game is over.
   */
  public void clearDescriptions();

  /**
   * This method shows the player description to the user.
   *
   * @param desc the player description string
   */
  public void showPlayerDesc(String desc);

  /**
   * This method is used to render the view again with fresh parameters.
   *
   * @return the value of the settings
   */
  public int setDungeonParameters();

  /**
   * Gives the number of rows.
   *
   * @return row
   */
  public int getRows();

  /**
   * Gives the number of columns.
   *
   * @return column
   */
  public int getColumns();

  /**
   * Gives the interconnectivity.
   *
   * @return interconnectivity
   */
  public int getInterconnectivity();

  /**
   * Gives the treasure percentage.
   *
   * @return treasure percent
   */
  public int getTreasurePercent();

  /**
   * Gives the wrapping condition.
   *
   * @return wrapping
   */
  public boolean getWrapping();

  /**
   * Gives the number of monsters.
   *
   * @return no of monsters
   */
  public int getMonsterCount();

  /**
   * Gives the number of thieves.
   *
   * @return no of thieves
   */
  public int getThiefCount();

  /**
   * Gives the number of pits.
   *
   * @return no of pits
   */
  public int getPitCount();

  /**
   * Gives the number of moving monsters.
   *
   * @return no of moving monsters
   */
  public int getMovingMonsterCount();
}
