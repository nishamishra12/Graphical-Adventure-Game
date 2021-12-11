package dungeon;

import java.util.List;

import randomizer.Randomizer;

public interface ReadOnlyModel {

  /**
   * This method provides the all the locations present in the dungeon.
   *
   * @return list of location objects present in the dungeon
   */
  public List<Location> getLocationList();

  /**
   * This method provides the end cave of the dungeon.
   *
   * @return the end cave
   */
  public Location getEndCave();

  /**
   * This method provides the start cave of the dungeon.
   *
   * @return the start cave.
   */
  public Location getStartCave();

  /**
   * This method provides the no of rows in the dungeon.
   *
   * @return the rows
   */
  public int getRows();

  /**
   * This method provides the no of columns in the dungeon.
   *
   * @return the columns
   */
  public int getColumns();

  /**
   * This method provides the player playing in the dungeon.
   *
   * @return the player
   */
  public Player getPlayer();

  /**
   * This method provides the type of smell in the dungeon coming from the nearby locations of the
   * current location the player is in. The smell is determined by the monsters present at each
   * location.
   *
   * @return the type of Smell
   */
  public SmellType checkSmell();

  /**
   * This method provides the description of the player consisting of the current locations,
   * treasures and arrows collected by the player.
   *
   * @return the string representation of the player description
   */
  public String getPlayerDescription();

  /**
   * This method provides the location description, along with the treasures and arrows present at
   * the location.
   *
   * @return the string representation of the location description
   */
  public String getLocationDescription();

  /**
   * This method provides the description of next possible moves the player can go to from the
   * current location.
   *
   * @return the string representation next possible moves
   */
  public String getNextPossibleDescription();

  /**
   * This method provides the interconnectivity of the dungeon.
   *
   * @return the interconnectivity.
   */
  public int getInterConnectivity();

  /**
   * This method provides the wrapping value of the dungeon.
   *
   * @return the wrapping
   */
  public boolean getWrapping();

  /**
   * This method provides the treasure percent.
   *
   * @return the treasure percentage
   */
  public int getTreasurePercent();

  /**
   * This method return the number of monsters in the dungeon.
   *
   * @return the monster count
   */
  public int getMonsterCount();

  /**This method provides location list to be used during reset method.
   *
   * @return the list of all locations
   */
  public List<Location> getFinalLocationList();

  /**
   * This method provides the randomizer used in the dungeon.
   *
   * @return the randomizer
   */
  public Randomizer getRandomizer();

  /**
   * This method provides the soil quality of the dungeon when a pit is nearby.
   *
   * @return the soil quality of the dungeon
   */
  public SoilQuality checkSoilType();

  /**
   * This method gives the no of thief in the dungeon.
   *
   * @return the thief count
   */
  public int getThiefCount();

  /**
   * This method gives the no of pits in te dungeon.
   *
   * @return the pit count
   */
  public int getPitCount();

  /**
   * This method gives the no of moving monster in the dungeon.
   *
   * @return the moving monster count
   */
  public int getMovingMonsterCount();
}
