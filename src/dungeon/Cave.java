package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import randomizer.Randomizer;

/**
 * This class represents a location or node in the dungeon along with possible neighbors
 * of the current location, the treasures, arrows, and monster present at the current location
 * and a check if the node has been visited.
 */
public class Cave implements Location {

  private int id;
  private Map<Direction, Location> directionCaveMap;
  private List<Treasure> treasureList;
  private LocationType locationType;
  private boolean isVisited;
  private int arrow;
  private Creature monster;
  private boolean containsThief;
  private boolean containsPit;
  private int row;
  private int column;
  private boolean playerVisited;

  /**
   * Constructs a location node with its id.
   *
   * @param id this parameter takes the id of the location
   * @param row this parameter takes the row value of the location
   * @param col this parameter takes the column value of the location
   * @throws IllegalArgumentException when then id, row, column passed is invalid
   */
  public Cave(int id, int row, int col) throws IllegalArgumentException {

    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative");
    }
    if (row < 0) {
      throw new IllegalArgumentException("Row cannot be negative");
    }
    if (col < 0) {
      throw new IllegalArgumentException("Column cannot be negative");
    }
    directionCaveMap = new HashMap<Direction, Location>();
    treasureList = new ArrayList<Treasure>();
    this.id = id;
    this.isVisited = false;
    this.monster = null;
    this.arrow = 0;
    this.containsThief = false;
    this.containsPit = false;
    this.row = row;
    this.column = col;
    this.playerVisited = false;
  }

  /**
   * Copy constructor of the Cave class to maintain a defensive copy.
   *
   * @param location for which the copy is constructed
   * @throws IllegalArgumentException when the location entered is null
   */
  public Cave(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("The location entered is null");
    }
    directionCaveMap = location.getNeighbors();
    treasureList = location.getTreasureList();
    this.id = location.getId();
    this.locationType = location.getLocationType();
    this.arrow = location.getArrow();
    this.monster = location.getMonster();
    this.containsThief = location.isContainsThief();
    this.containsPit = location.isContainsPit();
    this.row = location.getRow();
    this.column = location.getColumn();
    this.playerVisited = location.hasPlayerVisited();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addNeighbors(Map<Direction, Location> map) throws IllegalArgumentException {
    if (map == null) {
      throw new IllegalArgumentException("List of neighbors cannot be null");
    }
    this.directionCaveMap.putAll(map);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Direction, Location> getNeighbors() {
    Map<Direction, Location> directionLocationMapCopy =
            new HashMap<Direction, Location>(this.directionCaveMap);
    return directionLocationMapCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addTreasureList() throws IllegalArgumentException {
    this.treasureList = new ArrayList<>(Arrays.asList(Treasure.values()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<Treasure>(this.treasureList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocationType getLocationType() {
    LocationType locationTypeCopy = this.locationType;
    return locationTypeCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocationType(LocationType locationType) throws IllegalArgumentException {
    if (locationType == null) {
      throw new IllegalArgumentException("Location Type argument is invalid");
    }
    this.locationType = locationType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeTreasure() {
    this.treasureList.removeAll(this.treasureList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getArrow() {
    return arrow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArrow(int arrowCount) {
    this.arrow = arrowCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArrow() {
    this.arrow = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMonster(CreatureType monsterType) {
    this.monster = MonsterFactory.createMonster(monsterType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Creature getMonster() {
    if (this.hasMonster()) {
      Creature monsterCopy = monster;
      return monsterCopy;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hitMonster(int val) {
    monster.hit(val);
    if (monster.getMonsterType() == CreatureType.MOVING_MONSTER && monster.getHealth() <= 0) {
      this.monster = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasMonster() {
    return this.monster != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMonster() {
    this.monster = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isContainsThief() {
    return containsThief;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContainsThief(boolean containsThief) {
    this.containsThief = containsThief;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isContainsPit() {
    return containsPit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRow() {
    return row;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumn() {
    return column;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updatePlayerVisited(boolean value) {
    this.playerVisited = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasPlayerVisited() {
    return this.playerVisited;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPit() {
    this.containsPit = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n - Location Description - ");
    if (treasureList.size() > 0) {
      sb.append("\n\nTreasures:");
      for (int i = 0; i < treasureList.size(); i++) {
        sb.append("\n" + treasureList.get(i).getTreasure());
      }
    } else {
      sb.append("\nThe cave has no treasures");
    }

    if (arrow > 0) {
      sb.append("\nArrows: " + arrow);
    } else {
      sb.append("\nThere are no arrows at the current location");
    }
    return sb.toString();
  }
}