package controller;

import dungeon.Direction;
import dungeon.Dungeon;

//Code reference for command pattern:
// https://northeastern.instructure.com/courses/90366/pages/
// module-8-mvc-controllers?module_item_id=6535605
/**
 * This interface represents the controller of the entire dungeon. It is used to play the dungeon
 * game by moving the player, picking arrows, treasure, and slaying the monsters. The game is over
 * when the player is eaten by the monster or by falling in to the pit
 * or wins by reaching the destination.
 */
public interface IDungeonController {

  /**
   * This method plays the dungeon game with the help of user inputs and moves the player,
   * picks up treasures, arrows, and slays monster.
   *
   * @param dungeon this parameter takes the dungeon model
   * @throws IllegalArgumentException when the dungeon model is null
   * @throws IllegalStateException    when append to the output stream fails.
   */
  public void play(Dungeon dungeon) throws IllegalArgumentException, IllegalStateException;

  public void go();

  public void moveThePlayerByClick(Direction direction);

  public void movePlayerOnKeyPress(String str);

  public void shootOtyughOnKeyPress(int shootDist, String str);

  public void pickTreasureOnKeyPress();

  public void pickArrowOnKeyPress();
}
