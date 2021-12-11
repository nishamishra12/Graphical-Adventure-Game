package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import randomizer.Randomizer;

import static java.util.stream.Collectors.toMap;

/**
 * The class represents a dungeon in which the player will be moving from one direction
 * to another, along with the various location types, treasures, arrows, and Otyugh present in the
 * dungeon, and the paths from start to end of the dungeon. The player can shoot the Otyugh, escape
 * from the Otyugh or get killed by it.
 */
public class DungeonImpl implements Dungeon {

  private int rows;
  private int columns;
  private Randomizer randomizer;
  private List<Edge> edges;
  private List<Edge> mazeList;
  private int interconnectivity;
  private List<Location> locationList;
  private List<Location> finalLocationList;
  private List<Location> caveList;
  private Location startCave;
  private Location endCave;
  private Player player;
  private boolean wrapping;
  private int percent;
  private int monsterCount;
  private int thiefCount;
  private int pitCount;
  private int movingMonsterCount;
  private int countOfMV;
  private List<Location> monsterList;
  private FactoryPattern factoryPattern;

  /**
   * Constructs a new dungeon where the player can move.
   *
   * @param rows              this parameter takes the no of rows the dungeon can have
   * @param columns           this parameter takes the no of columns the dungeon can have
   * @param interconnectivity this parameter takes the interconnectivity value of the dungeon
   * @param treasurePercent   this parameter takes the treasure percent of the dungeon
   * @param wrapping          this parameter takes the wrapping status of the dungeon
   * @param monsterCount      this parameter takes the number of monsters in the dungeon
   * @param randomizer        this parameter takes the randomizer
   * @throws IllegalArgumentException when the values entered are invalid or null
   */
  public DungeonImpl(int rows, int columns, int interconnectivity, int treasurePercent,
                     boolean wrapping, int monsterCount,
                     int thiefCount, int pitCount, int movingMonsterCount,
                     Randomizer randomizer)
          throws IllegalArgumentException {

    validatorHelper(rows, 1, "Rows");
    validatorHelper(columns, 1, "Columns");
    validatorHelper(interconnectivity, 0, "Inter-Connectivity");
    validatorHelper(monsterCount, 1, "Monster Count");

    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Treasure percent is invalid. Should be between 0-100");
    }

    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer entered is null. Enter correct randomizer");
    }
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.randomizer = randomizer;
    this.factoryPattern = new FactoryPattern(randomizer);
    this.wrapping = wrapping;
    this.monsterCount = monsterCount;
    this.percent = calculateTreasurePercent(treasurePercent);
    this.thiefCount = thiefCount;
    this.pitCount = pitCount;
    this.movingMonsterCount = movingMonsterCount;
    this.countOfMV = movingMonsterCount;
    this.monsterList = new ArrayList<>();
    this.caveList = new ArrayList<>();
    this.edges = new ArrayList<>();
    KruskalAlgo maze = new KruskalAlgo();
    createMaze();
    mazeList = maze.kruskalAlgo(edges, rows * columns);
    setBFSAndLocations();
    player = new PlayerImpl("Shadow", startCave);
    addDungeonCreatures();
    caveListCopy();
    locationList.get(startCave.getId()).updatePlayerVisited(true);
  }

  /**
   * This is a copy constructor used the dungeon class. This copy constructor is used for
   * creating the same dungeon again when the user requests for restart game.
   *
   * @param dungeon takes the readonly dungeon model.
   */
  public DungeonImpl(ReadOnlyModel dungeon) {
    this.rows = dungeon.getRows();
    this.columns = dungeon.getColumns();
    this.interconnectivity = dungeon.getInterConnectivity();
    this.percent = dungeon.getTreasurePercent();
    this.wrapping = dungeon.getWrapping();
    this.monsterCount = dungeon.getMonsterCount();
    this.finalLocationList = dungeon.getFinalLocationList();
    this.locationList = dungeon.getLocationList();
    this.randomizer = dungeon.getRandomizer();
    this.monsterList = new ArrayList<>();
    this.thiefCount = dungeon.getThiefCount();
    this.movingMonsterCount = dungeon.getMovingMonsterCount();
    this.pitCount = dungeon.getPitCount();
    reuseDungeonWithOldProperties(dungeon);
    for (Location location : getLocationList()) {
      location.updatePlayerVisited(false);
    }
    this.player = new PlayerImpl("Shadow", startCave);
    locationList.get(startCave.getId()).updatePlayerVisited(true);
  }

  private void validatorHelper(int variable, int minVal, String text)
          throws IllegalArgumentException {
    if (variable < minVal) {
      throw new IllegalArgumentException(text + " entered is not correct. Kindly change it");
    }
  }

  private int calculateTreasurePercent(int treasurePercent) {
    return (treasurePercent + randomizer.getNextInt(0, (100 - treasurePercent)));
  }

  private void setBFSAndLocations() {
    createMazeList();
    createCaves();
    addNeighbors();
    setLocationType();
    createOnlyCaveList();
    findMinPath();
  }

  private void addDungeonCreatures() {
    addTreasureToCave();
    addArrowsToCave();
    addMonster();
    addPit();
    addMovingMonster();
    addThiefToDungeon();
  }

  private void reuseDungeonWithOldProperties(ReadOnlyModel dungeon) {
    int i = 0;
    for (Iterator<Location> iterator = finalLocationList.iterator(); iterator.hasNext(); ) {
      Location location = iterator.next();
      //add Arrow if present
      if (location.getArrow() > 0) {
        this.locationList.get(i).removeArrow();
        this.locationList.get(i).addArrow(location.getArrow());
      }

      //add treasure if present
      if (location.getTreasureList().size() > 0) {
        this.locationList.get(i).removeTreasure();
        this.locationList.get(i).addTreasureList();
      }

      //if monster present
      if (location.hasMonster() && location.getMonster().getMonsterType() == CreatureType.OTUYGH) {
        this.locationList.get(i).addMonster(CreatureType.OTUYGH);
      }

      //if moving monster present
      if (location.hasMonster() && location.getMonster().getMonsterType()
              == CreatureType.MOVING_MONSTER) {
        this.locationList.get(i).addMonster(CreatureType.MOVING_MONSTER);
      }

      //if thief present
      if (location.isContainsThief()) {
        this.locationList.get(i).setContainsThief(true);
      }

      //if pit present
      if (location.isContainsPit()) {
        this.locationList.get(i).setPit();
      }
      i++;
    }

    this.startCave = dungeon.getStartCave();
    this.endCave = dungeon.getEndCave();
  }

  private void createMaze() {

    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < rows * columns; i++) {
      map.put(i, i);
    }

    //first row map
    Map<Integer, Integer> firstRowMap = new HashMap<>();
    for (int i = 0; i < columns; i++) {
      firstRowMap.put(i, i);
    }

    //first column map
    Map<Integer, Integer> firstColumnMap = new HashMap<>();
    for (int i = 0; i < rows; i++) {
      firstColumnMap.put(i, i);
    }

    //create edges without wrapping
    for (Map.Entry<Integer, Integer> m : map.entrySet()) {
      if (((m.getKey() + 1) % columns) != 0) {
        edges.add(factoryPattern.createEdge(map.get(m.getKey()), map.get(m.getKey() + 1)));
      }
      if (m.getKey() < map.size() - columns) {
        edges.add(factoryPattern.createEdge(map.get(m.getKey()), map.get(m.getKey() + columns)));
      }

      //if wrapping
      if (wrapping) {
        if (firstRowMap.containsKey(m.getKey())) {
          edges.add(factoryPattern.createEdge(map.get(m.getKey()),
                  map.get(m.getKey() + map.size() - columns)));
        }

        if (firstColumnMap.containsKey(m.getKey())) {
          edges.add(factoryPattern.createEdge(map.get(m.getKey()),
                  map.get(m.getKey() + columns - 1)));
        }
      }
    }
  }

  private void createMazeList() {
    List<Edge> extraList = new ArrayList<>(edges);
    extraList.removeAll(mazeList);
    extraList = randomizer.randomizedValueList(extraList);

    validatorHelper(extraList.size(), interconnectivity, "Interconnectivity");

    for (int i = 0; i < interconnectivity; i++) {
      mazeList.add(extraList.get(i));
    }
  }

  private void createCaves() {
    locationList = new ArrayList<>();
    for (int i = 0; i < rows * columns; i++) {
      locationList.add(factoryPattern.createLocation(i, i / columns, i % columns));
    }
  }

  private void addNeighbors() {

    //creating map of all edges with their differences
    Map<Integer, Map<Integer, Integer>> sdMap = new HashMap<>();
    for (Edge edge : mazeList) {
      if (sdMap.containsKey(edge.getSrc())) {
        sdMap.get(edge.getSrc()).put(edge.getDest(), edge.getDest() - edge.getSrc());
      } else {
        Map<Integer, Integer> innerMap = new HashMap<>();
        innerMap.put(edge.getDest(), edge.getDest() - edge.getSrc());
        sdMap.put(edge.getSrc(), innerMap);
      }
    }
    //creating map of location id, location
    Map<Integer, Location> locationMap = new HashMap<>();
    for (Location l : locationList) {
      locationMap.put(l.getId(), l);
    }

    for (Map.Entry<Integer, Location> l : locationMap.entrySet()) {
      if (sdMap.containsKey(l.getKey())) {
        for (Map.Entry<Integer, Integer> m : sdMap.get(l.getKey()).entrySet()) {
          if (m.getValue() == 1) {
            addToMap(l.getKey(), Direction.EAST, locationMap.get(m.getKey()));
            addToMap(m.getKey(), Direction.WEST, locationMap.get(l.getKey()));
          } else if (m.getValue() == (columns - 1) ||
                  (m.getValue() == 1 - columns)) {
            addToMap(l.getKey(), Direction.WEST, locationMap.get(m.getKey()));
            addToMap(m.getKey(), Direction.EAST, locationMap.get(l.getKey()));
          } else if (m.getValue() == columns) {
            addToMap(l.getKey(), Direction.SOUTH, locationMap.get(m.getKey()));
            addToMap(m.getKey(), Direction.NORTH, locationMap.get(l.getKey()));
          } else if (m.getValue() > columns) {
            addToMap(l.getKey(), Direction.NORTH, locationMap.get(m.getKey()));
            addToMap(m.getKey(), Direction.SOUTH, locationMap.get(l.getKey()));
          }
        }
      }
    }
  }

  private void addToMap(int index, Direction key, Location val) {
    Map<Direction, Location> newMap = new HashMap<>(locationList.get(index).getNeighbors());
    newMap.put(key, val);
    locationList.get(index).addNeighbors(newMap);
  }

  //setLocationType
  private void setLocationType() {
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getNeighbors().size() == 2) {
        locationList.get(i).setLocationType(LocationType.TUNNEL);
      } else {
        locationList.get(i).setLocationType(LocationType.CAVE);
      }
    }
  }

  private void createOnlyCaveList() {
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getLocationType().equals(LocationType.CAVE)) {
        caveList.add(locationList.get(i));
      }
    }
  }

  //give treasure to cave
  private void addTreasureToCave() {
    List<Location> exclusiveCaveList = new ArrayList<>(caveList);

    exclusiveCaveList = randomizer.randomizedValueList(exclusiveCaveList);

    int noOfCavesWithTreasure = Math.toIntExact(
            Math.round((percent * exclusiveCaveList.size()) / 100));

    for (int i = 0; i < noOfCavesWithTreasure; i++) {
      exclusiveCaveList.get(i).addTreasureList();
    }
  }

  //add Arrows to the cave
  private void addArrowsToCave() {
    int noOfCavesWithArrows = Math.toIntExact(Math.round((percent * locationList.size()) / 100));

    List<Location> locationListCopy = new ArrayList<>(locationList);
    locationListCopy = randomizer.randomizedValueList(locationListCopy);

    for (int i = 0; i < noOfCavesWithArrows; i++) {
      locationListCopy.get(i).addArrow(randomizer
              .getNextInt(1, 4));
    }
  }

  private Direction convert(String input) {
    Direction direction = null;
    if (input.equalsIgnoreCase("E")) {
      direction = Direction.EAST;
    } else if (input.equalsIgnoreCase("W")) {
      direction = Direction.WEST;
    } else if (input.equalsIgnoreCase("S")) {
      direction = Direction.SOUTH;
    } else if (input.equalsIgnoreCase("N")) {
      direction = Direction.NORTH;
    }
    return direction;
  }

  //check if neighbor exist
  private boolean checkNeighborExist(Location l, Direction d) {
    return l.getNeighbors().containsKey(d);
  }

  //shoot arrow
  @Override
  public String shootArrow(int dist, String dir) throws IllegalArgumentException {

    if ((!dir.equalsIgnoreCase("N") && !dir.equalsIgnoreCase("S")
            && !dir.equalsIgnoreCase("E") && !dir.equalsIgnoreCase("W"))
            || dir == null) {
      throw new IllegalArgumentException("Invalid Direction");
    }

    if (dist < 1) {
      throw new IllegalArgumentException("Distance should be at least 1");
    }

    StringBuilder sb = new StringBuilder();

    if (player.getArrowCount() <= 0) {
      sb.append("\nYou are out of arrows, explore to find more");
      return sb.toString();
    }
    Location currArrowLoc = player.getCurrentLocation();
    Direction direction = convert(dir);


    while (dist > 0) {
      if (!(checkNeighborExist(currArrowLoc, direction))) {
        sb.append("\nYou shot an arrow into the darkness");
        return sb.toString();
      } else {
        currArrowLoc = currArrowLoc.getNeighbors().get(direction);
        if (currArrowLoc.getLocationType() == LocationType.CAVE) {
          dist--;
        } else {
          for (Map.Entry<Direction, Location> map : currArrowLoc.getNeighbors().entrySet()) {
            if (map.getKey() != direction) {
              direction = map.getKey();
              break;
            }
          }
        }
      }
    }

    if (dist == 0) {
      player.decreaseArrow();
      if (currArrowLoc.hasMonster() && currArrowLoc.getMonster().getHealth() > 0) {
        currArrowLoc.hitMonster(50);
        if (currArrowLoc.getMonster().getHealth() == 50) {
          sb.append("\nPlayer shot the monster, monster is injured");
        } else if (currArrowLoc.getMonster().getHealth() == 0) {
          sb.append("\nPlayer shot the monster, monster has been killed");
        }
      } else {
        sb.append("\nPlayer shot an arrow into the darkness");
      }
      return sb.toString();
    }
    sb.append("\nYou shot an arrow into the darkness");
    return sb.toString();
  }

  //add monster to the cave
  private void addMonster() throws IllegalArgumentException {
    List<Location> caveListCopy = new ArrayList<>(caveList);

    if (monsterCount > caveListCopy.size()) {
      throw new IllegalArgumentException("Monster count is greater than no of caves");
    }

    caveListCopy.remove(startCave);
    endCave.addMonster(CreatureType.OTUYGH);
    caveListCopy.remove(endCave);

    //shuffle
    caveListCopy = randomizer.randomizedValueList(caveListCopy);

    for (int i = 0; i < monsterCount - 1; i++) {
      caveListCopy.get(i).addMonster(CreatureType.OTUYGH);
    }
  }

  //add pit to a cave randomly
  private void addPit() {
    List<Location> pitList = new ArrayList<>();
    for (int i = 0; i < caveList.size(); i++) {
      if (!locationList.get(i).hasMonster() || locationList.get(i).getMonster().getHealth() <= 0) {
        pitList.add(locationList.get(i));
      }
    }

    //pit not in the start or end cave
    pitList.remove(startCave);
    pitList.remove(endCave);

    pitList = randomizer.randomizedValueList(pitList);
    for (int i = 0; i < pitCount; i++) {
      pitList.get(i).setPit();
    }
  }

  private void addMovingMonster() {
    List<Location> noMonsterList = new ArrayList<>();
    //first remove monster from everywhere
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).hasMonster() && locationList.get(i).getMonster().getMonsterType()
              .equals(CreatureType.MOVING_MONSTER)) {
        locationList.get(i).removeMonster();
      }
    }
    for (int i = 0; i < locationList.size(); i++) {
      if ((!locationList.get(i).hasMonster() || locationList.get(i).getMonster().getHealth() <= 0)
              && !locationList.get(i).isContainsPit()
              && locationList.get(i).getId() != startCave.getId()
              && locationList.get(i).getId() != this.player.getCurrentLocation().getId()) {
        noMonsterList.add(locationList.get(i));
      }
    }
    noMonsterList = randomizer.randomizedValueList(noMonsterList);
    for (int i = 0; i < movingMonsterCount; i++) {
      noMonsterList.get(i).addMonster(CreatureType.MOVING_MONSTER);
    }
  }

  private void addThiefToDungeon() {

    //thief and monster cannot be together
    List<Location> dungeonWithoutMonsterPitList = new ArrayList<>();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).isContainsThief()) {
        locationList.get(i).setContainsThief(false);
      }
      if ((!locationList.get(i).hasMonster() || locationList.get(i).getMonster().getHealth() <= 0)
              && !locationList.get(i).isContainsPit()) {
        dungeonWithoutMonsterPitList.add(locationList.get(i));
      }
    }
    dungeonWithoutMonsterPitList.remove(startCave);
    dungeonWithoutMonsterPitList = randomizer.randomizedValueList(dungeonWithoutMonsterPitList);
    for (int i = 0; i < thiefCount; i++) {
      dungeonWithoutMonsterPitList.get(i).setContainsThief(true);
    }
  }

  private int checkTheDistance(Location currentLoc, String str) {
    int count = 0;
    if (str == "SMELL") {
      for (Map.Entry<Direction, Location> m : currentLoc.getNeighbors().entrySet()) {
        if ((!monsterList.contains(m.getValue())) && m.getValue().hasMonster() &&
                m.getValue().getMonster().getMonsterType() == CreatureType.OTUYGH
                && m.getValue().getMonster().getHealth() > 0) {
          monsterList.add(m.getValue());
          count++;
        }
      }
    } else if (str == "PIT") {
      for (Map.Entry<Direction, Location> m : currentLoc.getNeighbors().entrySet()) {
        if (m.getValue().isContainsPit()) {
          return 1;
        }
      }
    }
    return count;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SoilQuality checkSoilType() {
    if (checkTheDistance(this.player.getCurrentLocation(), "PIT") > 0) {
      return SoilQuality.DENSE;
    }
    for (Map.Entry<Direction, Location> neigh :
            this.player.getCurrentLocation().getNeighbors().entrySet()) {
      if (checkTheDistance(neigh.getValue(), "PIT") == 1) {
        return SoilQuality.POROUS;
      }
    }
    return SoilQuality.NONE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SmellType checkSmell() {
    monsterList.clear();
    int monsterCount = 0;
    monsterCount = checkTheDistance(this.player.getCurrentLocation(), "SMELL");
    if (monsterCount > 0) {
      return SmellType.HIGH_PUNGENT;
    }
    monsterCount = 0;
    for (Map.Entry<Direction, Location> neigh :
            this.player.getCurrentLocation().getNeighbors().entrySet()) {
      monsterCount += checkTheDistance(neigh.getValue(), "SMELL");
    }
    if (monsterCount == 1) {
      return SmellType.LESS_PUNGENT;
    } else if (monsterCount > 1) {
      return SmellType.HIGH_PUNGENT;
    } else {
      return SmellType.ODOURLESS;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getInterConnectivity() {
    return this.interconnectivity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getWrapping() {
    return this.wrapping;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTreasurePercent() {
    return this.percent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMonsterCount() {
    return this.monsterCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRows() {
    return rows;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumns() {
    return columns;
  }

  //start and end cave
  private void findMinPath() {
    List<Location> possibleStart = new ArrayList<Location>(caveList);
    boolean foundEnd = false;

    while (!foundEnd) {
      possibleStart = randomizer.randomizedValueList(possibleStart);
      this.startCave = possibleStart.get(0);
      Map<Location, Integer> m = new HashMap<>();
      Map<Location, Integer> sortedMap = new HashMap<>();

      for (Map.Entry<Location, Integer> map : dfs(this.startCave).entrySet()) {
        if (map.getKey().getLocationType() == LocationType.CAVE
                && map.getValue() >= 5) {
          m.put(map.getKey(), map.getValue());
        }
      }

      //Citation for below code:
      //https://www.javacodegeeks.com/2017/09/j
      // ava-8-sorting-hashmap-values-ascending-descending-order.html
      if (!m.isEmpty()) {
        sortedMap = m.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Map.Entry<Location, Integer> et = sortedMap.entrySet().iterator().next();
        this.endCave = et.getKey();
        foundEnd = true;
      } else {
        possibleStart.remove(this.startCave);
      }
    }
  }

  /**
   * {@inheritDoc}
   */

  @Override
  public Map<Location, Integer> dfs(Location src) {
    if (src == null) {
      throw new IllegalArgumentException("Location for start cave cannot be null");
    }

    Map<Location, Integer> locationLevelMap = new HashMap<>();
    Set<Location> visited = new HashSet<Location>();

    for (Location loc : locationList) {
      locationLevelMap.put(loc, Integer.MAX_VALUE);
    }
    DFS(src, locationLevelMap, 0, visited);
    return new HashMap<>(locationLevelMap);
  }

  private void DFS(Location src, Map<Location, Integer> locationLevelMap,
                   int level, Set<Location> visited) {
    try {
      if (src == null) {
        return;
      }
      locationLevelMap.put(src, Math.min(locationLevelMap.get(src), level));
      visited.add(src);

      for (Map.Entry<Direction, Location> neigh : src.getNeighbors().entrySet()) {
        if (visited.contains(neigh.getValue())) {
          continue;
        }
        visited.add(neigh.getValue());
        DFS(neigh.getValue(), locationLevelMap, level + 1, visited);
      }
      visited.remove(src);
    } catch (Exception e) {
      //do nothing if this occurs
    }
  }

  private void caveListCopy() {
    finalLocationList = new ArrayList<>();
    for (Iterator<Location> iterator = locationList.iterator(); iterator.hasNext(); ) {
      Location location = iterator.next();
      finalLocationList.add(factoryPattern.createCopyLocation(location));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String nextMove(String val) throws IllegalArgumentException {
    if ((!val.equalsIgnoreCase("N") && !val.equalsIgnoreCase("S")
            && !val.equalsIgnoreCase("E") && !val.equalsIgnoreCase("W"))
            || val == null) {
      throw new IllegalArgumentException("Invalid Direction");
    }

    String result = player.move(convert(val));
    StringBuilder sb = new StringBuilder();

    if (result.equals("Moved")) {
      player.getCurrentLocation().updatePlayerVisited(true);
      //thief
      if (player.getCurrentLocation().isContainsThief()) {
        sb.append("\nThief in the cave.");
        if (player.getTreasureList().size() > 0) {
          player.emptyTreasureList();
          sb.append("\nThief stole all treasures of the player, player has no treasure");
        } else {
          sb.append("\nPlayer has no treasure to steal");
        }
      }

      //pit and monster
      if (player.getCurrentLocation().isContainsPit()) {
        player.hitPlayer(100);
        sb.append("\nPit in the cave! Ohhh nooo, player died by falling into the pit");
      } else if (player.getCurrentLocation().hasMonster()
              && player.getCurrentLocation().getMonster().getMonsterType()
              == CreatureType.MOVING_MONSTER) {
        sb.append(handToHandCombat());
      } else if (player.getCurrentLocation().hasMonster()
              && player.getCurrentLocation().getMonster().getMonsterType() == CreatureType.OTUYGH) {
        int escape = randomizer.getNextInt(0, 2);
        if ((player.getCurrentLocation().getMonster().getHealth() == 100)
                || (player.getCurrentLocation().getMonster().getHealth() == 50
                && escape % 2 == 1)) {
          player.hitPlayer(100);
          sb.append("\nOtyugh in cave! Chomp, chomp, chomp, player got eaten by an Otyugh!");
          sb.append("\nBetter luck next time");
        } else if ((player.getCurrentLocation().getMonster().getHealth() == 50
                && escape % 2 == 0)) {
          sb.append("\nPlayer escaped successfully from an injured Otyugh, and moved to location "
                  + player.getCurrentLocation().getId());
        } else if (player.getCurrentLocation().getMonster().getHealth() == 0) {
          sb.append("\nPlayer moved successfully to location "
                  + player.getCurrentLocation().getRow() + ", "
                  + player.getCurrentLocation().getColumn());
        }
      } else {
        sb.append("\nPlayer moved successfully to location "
                + player.getCurrentLocation().getRow() + ", "
                + player.getCurrentLocation().getColumn());
      }
      addThiefToDungeon();
      if (countOfMV > 0) {
        addMovingMonster();
      }
    } else {
      sb.append("\nMove " + val + " not possible ");
    }
    return sb.toString();
  }

  private String monsterTurn() {
    StringBuilder sb = new StringBuilder();
    if (player.getCurrentLocation().hasMonster() &&
            player.getCurrentLocation().getMonster().getHealth() > 0) {
      sb.append("\nMonster gets the turn");
      int playerDamage = randomizer.getNextInt(30, 100);
      player.hitPlayer(playerDamage);
      sb.append("\nMonster hits player, damage by: " + playerDamage);
    }
    return sb.toString();
  }

  private String playerTurn() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nPlayer gets the turn");
    int monsterDamage = randomizer.getNextInt(30, 100);
    player.getCurrentLocation().hitMonster(monsterDamage);
    sb.append("\nPlayer hits monster, damage by: " + monsterDamage);
    return sb.toString();
  }

  private String handToHandCombat() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nMoving CreatureAbs encountered in the cave, player has to fight.");
    int turnVal = randomizer.getNextInt(20, 30);
    while (player.getHealth() > 0 && player.getCurrentLocation().hasMonster()) {
      //if positive, monster hits first
      if (turnVal % 2 == 0) {
        sb.append(monsterTurn());
        sb.append(playerTurn());
      } else {
        sb.append(playerTurn());
        sb.append(monsterTurn());
      }
    }

    if (player.getHealth() <= 0) {
      sb.append("\nGame Over!! Player got Killed");
    } else {
      countOfMV--;
      sb.append("\nPlayer killed the moving monster");
      sb.append("\nPlayer moved successfully to location " + player.getCurrentLocation().getId());
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < locationList.size(); i++) {
      sb.append("\n" + locationList.get(i).getLocationType() + ": " + locationList.get(i).getId());
      Map<Direction, Location> treeMap = new TreeMap<Direction, Location>(
              locationList.get(i).getNeighbors());
      for (Map.Entry<Direction, Location> map : treeMap.entrySet()) {
        sb.append("\n-->" + map.getKey() + " Neighbor: " + map.getValue().getLocationType()
                + ": " + map.getValue().getId());
      }
      if (locationList.get(i).hasMonster()) {
        sb.append("\n###" + locationList.get(i).getMonster().getClass().getSimpleName() + "###");
      }
      if (locationList.get(i).getArrow() > 0) {
        sb.append("\nArrow Count: " + locationList.get(i).getArrow());
      }
      if (locationList.get(i).isContainsPit()) {
        sb.append("_____PIT_____");
      }
      if (locationList.get(i).isContainsThief()) {
        sb.append("^^^^^THIEF^^^^^");
      }

      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String pickTreasure() {
    StringBuilder sb = new StringBuilder();
    if (player.getCurrentLocation().getTreasureList().size() > 0) {
      player.updateTreasureList();
      sb.append("Treasure picked up");
    } else {
      sb.append("No treasure present at the location");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String pickArrow() {
    StringBuilder sb = new StringBuilder();
    if (player.getCurrentLocation().getArrow() > 0) {
      player.pickUpArrow();
      sb.append("Arrow picked up");
    } else {
      sb.append("No arrow present at the location");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasReachedEnd() {
    return player.getCurrentLocation().getId() != endCave.getId() ? false : true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getNextPossibleDescription() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\nNext possible moves: ");
    Map<Direction, Location> treeMap = new TreeMap<>(player.getCurrentLocation().getNeighbors());
    for (Map.Entry<Direction, Location> set : treeMap.entrySet()) {
      stringBuilder.append("\n" + set.getKey().getDirection());
    }
    return stringBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocationDescription() {
    return player.getCurrentLocation().toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPlayerDescription() {
    return player.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Location> getLocationList() {
    return new ArrayList<>(this.locationList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Player getPlayer() {
    Player playerCopy = new PlayerImpl(this.player);
    return playerCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getEndCave() {
    Location endCaveCopy = new Cave(this.endCave);
    return endCaveCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getStartCave() {
    Location startCaveCopy = factoryPattern.createCopyLocation(this.startCave);
    return startCaveCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Location> getFinalLocationList() {
    return new ArrayList<>(finalLocationList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Randomizer getRandomizer() {
    return this.randomizer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getThiefCount() {
    return thiefCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPitCount() {
    return pitCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMovingMonsterCount() {
    return movingMonsterCount;
  }
}