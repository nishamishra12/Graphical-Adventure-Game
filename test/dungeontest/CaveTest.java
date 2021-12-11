package dungeontest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeon.Cave;
import dungeon.CreatureType;
import dungeon.Direction;
import dungeon.Location;
import dungeon.LocationType;
import dungeon.Otyugh;
import dungeon.Treasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CaveTest {

  private Location location;

  @Before
  public void setUp() throws Exception {
    location = new Cave(1, 4, 5);
  }

  @Test
  public void testForGetId() {
    assertEquals(1, location.getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeId() {
    new Cave(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeRow() {
    new Cave(0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeCol() {
    new Cave(6, 3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullLocationConstruction() {
    new Cave(null);
  }

  @Test
  public void testForAddNeighbors() {
    Map<Direction, Location> neighborList = new HashMap<Direction, Location>();
    neighborList.put(Direction.NORTH, location);
    location.addNeighbors(neighborList);
    assertEquals(neighborList, location.getNeighbors());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighborWrongDirection() {
    location.addNeighbors(null);
  }

  @Test
  public void testForAddTreasureList() {
    List<Treasure> treasureList = new ArrayList<Treasure>();
    treasureList.add(Treasure.DIAMOND);
    treasureList.add(Treasure.RUBY);
    treasureList.add(Treasure.SAPPHIRE);
    location.addTreasureList();
    assertEquals(treasureList, location.getTreasureList());
  }

  @Test
  public void testForGetTreasureList() {
    location.addTreasureList();
    assertEquals(3, location.getTreasureList().size());
  }

  @Test
  public void testForSetLocationType() {
    location.setLocationType(LocationType.TUNNEL);
    assertEquals(LocationType.TUNNEL, location.getLocationType());
    location.setLocationType(LocationType.CAVE);
    assertEquals(LocationType.CAVE, location.getLocationType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullLocation() {
    location.setLocationType(null);
  }

  @Test
  public void testForGetLocationType() {
    location.setLocationType(LocationType.TUNNEL);
    assertEquals(LocationType.TUNNEL, location.getLocationType());
    location.setLocationType(LocationType.CAVE);
    assertEquals(LocationType.CAVE, location.getLocationType());
  }

  @Test
  public void testForRemoveTreasure() {
    location.addTreasureList();
    assertEquals(3, location.getTreasureList().size());

    //now remove treasure
    location.removeTreasure();
    assertEquals(0, location.getTreasureList().size());
  }

  @Test
  public void testForGetArrow() {
    location.addArrow(7);
    assertEquals(7, location.getArrow());
  }

  @Test
  public void testAddArrow() {
    assertEquals(0, location.getArrow());
    location.addArrow(7);
    assertEquals(7, location.getArrow());
  }

  @Test
  public void testRemoveArrow() {
    location.addArrow(7);
    assertEquals(7, location.getArrow());
    location.removeArrow();
    assertEquals(0, location.getArrow());
  }

  @Test
  public void testAddMonster() {
    location.addMonster(CreatureType.OTUYGH);
    assertTrue(location.hasMonster());
  }

  @Test
  public void testGetMonster() {
    location.addMonster(CreatureType.OTUYGH);
    assertEquals(CreatureType.OTUYGH, location.getMonster().getMonsterType());
  }

  @Test
  public void testHitMonster() {
    location.addMonster(CreatureType.OTUYGH);
    assertEquals(100, location.getMonster().getHealth());
    location.hitMonster(30);
    assertEquals(70, location.getMonster().getHealth());
  }

  @Test
  public void testHasMonster() {
    location.addMonster(CreatureType.OTUYGH);
    assertTrue(location.hasMonster());
  }

  @Test
  public void testRemoveMonster() {
    location.addMonster(CreatureType.OTUYGH);
    assertTrue(location.hasMonster());
    location.removeMonster();
    assertFalse(location.hasMonster());
  }

  @Test
  public void testIsContainsThief() {
    location.setContainsThief(true);
    assertTrue(location.isContainsThief());
  }

  @Test
  public void testSetContainsThief() {
    location.setContainsThief(true);
    assertTrue(location.isContainsThief());
  }

  @Test
  public void testIsContainsPit() {
    location.setPit();
    assertTrue(location.isContainsPit());
  }

  @Test
  public void testGetRow() {
    assertEquals(4, location.getRow());
  }

  @Test
  public void testGetColumn() {
    assertEquals(4, location.getRow());
  }

  @Test
  public void testUpdatePlayerVisited() {
    assertEquals(false, location.hasPlayerVisited());
    location.updatePlayerVisited(true);
    assertEquals(true, location.hasPlayerVisited());
  }

  @Test
  public void testHasPlayerVisited() {
    location.updatePlayerVisited(true);
    assertEquals(true, location.hasPlayerVisited());
  }

  @Test
  public void testSetPit() {
    location.setPit();
    assertTrue(location.isContainsPit());
  }

  @Test
  public void testTestToString() {
    assertEquals("\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location", location.toString());
  }
}