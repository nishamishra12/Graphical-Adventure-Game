package dungeontest;

import java.util.List;
import java.util.Map;

import dungeon.Cave;
import dungeon.Dungeon;
import dungeon.Location;
import dungeon.Player;
import dungeon.PlayerImpl;
import dungeon.SmellType;
import dungeon.SoilQuality;
import randomizer.Randomizer;

public class MockModel implements Dungeon {

  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  /**
   * This method moves the player from one location to the other based on the location value that
   * is passed.
   *
   * @param val this parameter takes the location value of the direction
   *            in which the player has to move
   * @return the result of the move
   * @throws IllegalArgumentException when the move entered is invalid
   */
  @Override
  public String nextMove(String val) throws IllegalArgumentException {
    log.append("\nCalling Next Move method, direction provided is -> " + val);
    return "Move successful";
  }

  /**
   * This method checks if the player has reached end of the dungeon.
   *
   * @return true or false depending on if the player has reached end or not
   */
  @Override
  public boolean hasReachedEnd() {
    log.append("\nCalling Has reached end method to check if the player has reached end cave");
    return false;
  }

  /**
   * This method updates the treasure list of the location and the player once the player decides
   * to pick up the treasure at the current location.
   *
   * @return the string if the treasure has been picked up or not
   */
  @Override
  public String pickTreasure() {
    log.append("\nCalling pick treasure method to pick treasures from players current "
            + "location");
    return "Treasure pickedup";
  }

  /**
   * This method is used to apply the BFS algorithm on the dungeon where the root node is set as
   * the src provided in the parameters and after creating the BFS path, it will provide the list of
   * all the nodes and their levels in the BFS tree.
   *
   * @param src Source that will be used as the root node of the BFS path.
   * @return the map that contains all the nodes and their levels in the created BFS tree.
   * @throws IllegalArgumentException if the src is null.
   */
  @Override
  public Map<Location, Integer> dfs(Location src) throws IllegalArgumentException {
    return null;
  }

  /**
   * This method picks up arrow from a location and updates the number of arrows the player has
   * along with removing arrows from the current location.
   *
   * @return the string is arrow is picked up or not
   */
  @Override
  public String pickArrow() {
    log.append("\nCalling pick arrow method to pick arrows from players current "
            + "location");
    return "Arrow picked up";
  }

  /**
   * This method shoots an arrow in the given direction and the required distance specified by the
   * player. The method shoots and kills the Otyugh if it is present at specified parameter, else
   * it misses the monster.
   *
   * @param distance  this parameter takes the distance till which the arrow should travel
   * @param direction this parameter takes the direction in which the arrow should travel
   * @return true or false based on if the arrow is shot or not.
   */
  @Override
  public String shootArrow(int distance, String direction) {
    log.append("\nCalling shoot arrow method, to shoot the monster. "
            + "\nThe direction provided for shoot is " + direction
            + " and the distance is " + distance);
    return "Shoot arrow successful";
  }

  /**
   * This method provides the all the locations present in the dungeon.
   *
   * @return list of location objects present in the dungeon
   */
  @Override
  public List<Location> getLocationList() {
    return null;
  }

  /**
   * This method provides the end cave of the dungeon.
   *
   * @return the end cave
   */
  @Override
  public Location getEndCave() {
    return null;
  }

  /**
   * This method provides the start cave of the dungeon.
   *
   * @return the start cave.
   */
  @Override
  public Location getStartCave() {
    return null;
  }

  @Override
  public int getRows() {
    return 0;
  }

  @Override
  public int getColumns() {
    return 0;
  }

  /**
   * This method provides the player playing in the dungeon.
   *
   * @return the player
   */
  @Override
  public Player getPlayer() {
    return null;
  }

  /**
   * This method provides the type of smell in the dungeon coming from the nearby locations of the
   * current location the player is in. The smell is determined by the monsters present at each
   * location.
   *
   * @return the type of Smell
   */
  @Override
  public SmellType checkSmell() {
    return null;
  }

  /**
   * This method provides the description of the player consisting of the current locations,
   * treasures and arrows collected by the player.
   *
   * @return the string representation of the player description
   */
  @Override
  public String getPlayerDescription() {
    log.append("\nCalling method Get Player Description to get the player's description");
    return null;
  }

  /**
   * This method provides the location description, along with the treasures and arrows present at
   * the location.
   *
   * @return the string representation of the location description
   */
  @Override
  public String getLocationDescription() {
    log.append("\nCalling method location description to get the description of players current"
            + "location");
    return null;
  }

  /**
   * This method provides the description of next possible moves the player can go to from the
   * current location.
   *
   * @return the string representation next possible moves
   */
  @Override
  public String getNextPossibleDescription() {
    log.append("\nCalling method to get next possible directions in which the player "
            + "can proceed in");
    return null;
  }

  @Override
  public int getInterConnectivity() {
    return 0;
  }

  @Override
  public boolean getWrapping() {
    return false;
  }

  @Override
  public int getTreasurePercent() {
    return 0;
  }

  @Override
  public int getMonsterCount() {
    return 0;
  }

  @Override
  public List<Location> getFinalLocationList() {
    return null;
  }

  @Override
  public Randomizer getRandomizer() {
    return null;
  }

  @Override
  public SoilQuality checkSoilType() {
    return null;
  }

  /**
   * This method gives the no of thief in the dungeon.
   *
   * @return the thief count
   */
  @Override
  public int getThiefCount() {
    return 0;
  }

  /**
   * This method gives the no of pits in te dungeon.
   *
   * @return the pit count
   */
  @Override
  public int getPitCount() {
    return 0;
  }

  /**
   * This method gives the no of moving monster in the dungeon.
   *
   * @return the moving monster count
   */
  @Override
  public int getMovingMonsterCount() {
    return 0;
  }
}
