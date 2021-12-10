package dungeon;

import java.util.List;

/**
 * The interface represents the player playing in the dungeon, along with the treasures that
 * it possess, the count of arrows, the current node that it is in the dungeon,
 * and the move that the player takes inside the dungeon.
 */
public interface Player {

  /**
   * This method provides the list of treasure that the player possess.
   *
   * @return the treasure list of the player
   */
  public List<Treasure> getTreasureList();

  /**
   * This method gives the current location (cave or tunnel) that the player is in the dungeon.
   *
   * @return the current location of the player
   */
  public Location getCurrentLocation();

  /**
   * This method moves the player in the direction given. The direction is valid when it is on of
   * the direction from the neighbor list of the current location of the player.
   *
   * @param direction this parameter takes the direction in which the player should move
   * @return the true if the player has successfully moved and false when the player hasn't
   * @throws IllegalArgumentException when the direction passed is invalid
   */
  public String move(Direction direction) throws IllegalArgumentException;

  /**
   * This method updates the list of treasure present with the player.
   * Adds treasure to the list when the player picks up treasure from the dungeon.
   */
  public void updateTreasureList();

  /**
   * This method provides the number of arrows the player has.
   *
   * @return the count of arrows
   */
  public int getArrowCount();

  /**
   * This method decreases the count of arrow when the player shoot the arrow.
   */
  public void decreaseArrow();

  /**
   * This method updates the count of arrows by the number of arrows present in the current
   * location of the player.
   */
  public void pickUpArrow();

  /**
   * This method empties all the treasures of the player.
   */
  public void emptyTreasureList();

  /**
   * This method gives the player health.
   *
   * @return the health of the player
   */
  public int getHealth();

  /**
   * This method reduces the health of the player on hit by the moving monster.
   *
   * @param health takes health value to be reduced
   */
  public void hitPlayer(int health);
}
