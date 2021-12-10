package dungeon;

import java.util.List;
import java.util.Map;

/**
 * The interface represents a dungeon in which the player will be moving from one direction
 * to another, along with the various location types, treasures, arrows, and Otyugh present in the
 * dungeon, and the paths from start to end of the dungeon. The player can shoot the Otyugh, escape
 * from the Otyugh or get killed by it.
 */
public interface Dungeon extends ReadOnlyModel {

  /**
   * This method moves the player from one location to the other based on the location value that
   * is passed.
   *
   * @param val this parameter takes the location value of the direction
   *            in which the player has to move
   * @return the result of the move
   * @throws IllegalArgumentException when the move entered is invalid
   */
  public String nextMove(String val) throws IllegalArgumentException;

  /**
   * This method checks if the player has reached end of the dungeon.
   *
   * @return true or false depending on if the player has reached end or not
   */
  public boolean hasReachedEnd();

  /**
   * This method updates the treasure list of the location and the player once the player decides
   * to pick up the treasure at the current location.
   *
   * @return the string if the treasure has been picked up or not
   */
  public String pickTreasure();

  /**
   * This method is used to apply the DFS algorithm on the dungeon where the root node is set as
   * the src provided in the parameters and after creating the BFS path, it will provide the list of
   * all the nodes and their levels in the DFS tree.
   *
   * @param src Source that will be used as the root node of the DFS path.
   * @return the map that contains all the nodes and their levels in the created DFS tree.
   * @throws IllegalArgumentException if the src is null.
   */
  public Map<Location, Integer> dfs(Location src) throws IllegalArgumentException;

  /**
   * This method picks up arrow from a location and updates the number of arrows the player has
   * along with removing arrows from the current location.
   *
   * @return the string is arrow is picked up or not
   */
  public String pickArrow();

  /**
   * This method shoots an arrow in the given direction and the required distance specified by the
   * player. The method shoots and kills the Otyugh if it is present at specified parameter, else
   * it misses the monster.
   *
   * @param distance this parameter takes the distance till which the arrow should travel
   * @param direction this parameter takes the direction in which the arrow should travel
   * @return true or false based on if the arrow is shot or not.
   */
  public String shootArrow(int distance, String direction);
}
