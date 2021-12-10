package controller.commands;

import controller.ICommandController;
import dungeon.Dungeon;

//Code reference for command pattern:
// https://northeastern.instructure.com/courses/90366/pages/
// module-8-mvc-controllers?module_item_id=6535605
/**
 * This class represents the pick-up arrow command in the dungeon game and picks up arrows from
 * the current location.
 */
public class PickArrow implements ICommandController {

  /**
   * {@inheritDoc}
   */
  @Override
  public String goCommand(Dungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Dungeon cannot be null");
    }
    return dungeon.pickArrow();
  }
}
