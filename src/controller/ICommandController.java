package controller;

import dungeon.Dungeon;

//Code reference for command pattern:
// https://northeastern.instructure.com/courses/90366/pages/
// module-8-mvc-controllers?module_item_id=6535605
/**
 * This interface represents the different types of commands the player uses to navigate and play
 * the dungeon game.
 */
public interface ICommandController {

  /**
   * This method executes the different command given by the dungeon model.
   *
   * @param dungeon this parameter takes the dungeon model
   * @return the string representation of the executed command
   */
  String goCommand(Dungeon dungeon);
}
