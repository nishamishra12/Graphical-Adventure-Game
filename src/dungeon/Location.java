package dungeon;

import java.util.List;
import java.util.Map;

import randomizer.Randomizer;

/**
 * This interface represents a location or node in the dungeon along with possible neighbors
 * of the current location, the treasures, arrows, and monster present at the current location
 * and a check if the node has been visited.
 */
public interface Location {

  /**
   * This method provides the id of the cave.
   *
   * @return the cave id
   */
  public int getId();

  /**
   * This method adds the given adjacency map to the cave neighbor list.
   *
   * @throws IllegalArgumentException when the map passed is null
   */
  public void addNeighbors(Map<Direction, Location> map) throws IllegalArgumentException;

  /**
   * This method provides all the neighbor location in different direction of the current
   * location.
   *
   * @return the map of direction and location of all the neighbors
   */
  public Map<Direction, Location> getNeighbors();

  /**
   * This method adds the treasures to the list of treasures in the location.
   *
   * @throws IllegalArgumentException when the treasure passed is null
   */
  public void addTreasureList() throws IllegalArgumentException;

  /**
   * This method provides all the treasures present at the location.
   *
   * @return the list of treasures
   */
  public List<Treasure> getTreasureList();

  /**
   * This method provides the location type of the location.
   *
   * @return the location type
   */
  public LocationType getLocationType();

  /**
   * This method sets the type of the location.
   *
   * @param locationType this parameter takes the type of location that needs to be set
   * @throws IllegalArgumentException when the type of location passed is null
   */
  public void setLocationType(LocationType locationType) throws IllegalArgumentException;

  /**
   * This method removes the treasures from the location.
   */
  public void removeTreasure();

  /**
   * This method gives the count of arrows at the location.
   *
   * @return the number of arrows
   */
  public int getArrow();

  /**
   * This method adds the number of arrows to the location.
   *
   * @param arrowCount this parameter takes the number of arrows to be added
   */
  public void addArrow(int arrowCount);

  /**
   * This method removes arrows from the location, after the player has picked it up.
   */
  public void removeArrow();

  /**
   * This method adds monsters (Otyughs) to the location.
   */
  public void addMonster(CreatureType monsterType);

  /**
   * This method provides the monster object present at the location.
   *
   * @return the monster object
   */
  public Creature getMonster();

  /**
   * This method checks if the location has monsters.
   *
   * @return true or false based on if the location has monsters or not.
   */
  public boolean hasMonster();

  /**
   * This method hits the monsters when the arrow has been shot by the player.
   */
  public void hitMonster(int val);

  /**
   * This method checks if a thief is present or not in the cave.
   *
   * @return true or false based on if a thief is present or not
   */
  public boolean isContainsThief();

  /**
   * This method sets if a thief is there or not.
   *
   * @param containsThief take true or false based on the thief presence
   */
  public void setContainsThief(boolean containsThief);

  /**
   * This method adds pit to the cave.
   */
  public void setPit();

  /**
   * This method checks if a pit is present in the cave or not.
   *
   * @return if there is a pit or not
   */
  public boolean isContainsPit();

  /**
   * This method gives the row id of the cave.
   *
   * @return the row number
   */
  public int getRow();

  /**
   * This method gives the column value of the cave.
   *
   * @return the column number
   */
  public int getColumn();

  /**
   * This method updates the visited status of the location.
   *
   * @param value takes the boolean value
   */
  public void updatePlayerVisited(boolean value);

  /**
   * This method gives the visit status of the location whether true or false depending on if the
   * location has been visited or not.
   *
   * @return the visit status
   */
  public boolean hasPlayerVisited();

  /**
   * This method removes monster when the health is 0.
   */
  public void removeMonster();
}
