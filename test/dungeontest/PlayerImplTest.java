package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import dungeon.Cave;
import dungeon.Direction;
import dungeon.Location;
import dungeon.Player;
import dungeon.PlayerImpl;

import static org.junit.Assert.assertEquals;

public class PlayerImplTest {

  private Player player;
  private Location startCave;

  @Before
  public void setUp() throws Exception {
    startCave = new Cave(1, 1, 1);
    player = new PlayerImpl("ABC", startCave);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullName() {
    new PlayerImpl(null, startCave);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullCave() {
    new PlayerImpl("ABC", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullPlayer() {
    new PlayerImpl(null);
  }

  @Test
  public void getTreasureList() {
    startCave.addTreasureList();
    player.updateTreasureList();
    assertEquals(3, player.getTreasureList().size());
  }

  @Test
  public void getCurrentLocation() {
    //current location and start cave is same as player hasn't moved anywhere
    assertEquals(startCave, player.getCurrentLocation());
  }

  @Test
  public void move() {
    Map<Direction, Location> map = new HashMap<>();
    map.put(Direction.EAST, new Cave(2,2,2));
    map.put(Direction.WEST, new Cave(6,2,3));
    map.put(Direction.NORTH, new Cave(7,0,1));
    map.put(Direction.SOUTH, new Cave(10,3,1));
    startCave.addNeighbors(map);
    //move to one of the neighbors
    player.move(Direction.EAST);
    //assert the current cave id after player movement
    assertEquals(2, player.getCurrentLocation().getId());
  }

  @Test
  public void updateTreasureList() {
    startCave.addTreasureList();
    assertEquals(3, startCave.getTreasureList().size());

    //call update treasure
    player.updateTreasureList();
    assertEquals(3, player.getTreasureList().size());
    assertEquals(0, startCave.getTreasureList().size());
  }

  @Test
  public void testGetArrowCount() {
    assertEquals(3, player.getArrowCount());
  }

  @Test
  public void testDecreaseArrow() {
    assertEquals(3, player.getArrowCount());
    player.decreaseArrow();
    assertEquals(2, player.getArrowCount());
  }

  @Test
  public void testPickUpArrow() {
    startCave.addArrow(5);
    assertEquals(3, player.getArrowCount());
    player.pickUpArrow();
    assertEquals(8, player.getArrowCount());
  }

  @Test
  public void testEmptyTreasureList() {
    startCave.addTreasureList();
    player.updateTreasureList();
    assertEquals(3, player.getTreasureList().size());

    player.emptyTreasureList();
    assertEquals(0, player.getTreasureList().size());
  }

  @Test
  public void testTestToString() {
    assertEquals("\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: null: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3", player.toString());
  }

  @Test
  public void testGetHealth() {
    assertEquals(100, player.getHealth());
  }

  @Test
  public void testHitPlayer() {
    assertEquals(100, player.getHealth());
    player.hitPlayer(22);
    assertEquals(78, player.getHealth());
  }
}