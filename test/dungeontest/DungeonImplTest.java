package dungeontest;

import org.junit.Before;
import org.junit.Test;

import dungeon.CreatureType;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.Location;
import dungeon.SmellType;
import dungeon.SoilQuality;
import randomizer.FixedRandomizer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class DungeonImplTest {

  private Dungeon dungeon;
  private Dungeon dungeonW;

  @Before
  public void setUp() throws Exception {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
    dungeonW = new DungeonImpl(5, 7, 4, 20,
            true, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRows() {
    new DungeonImpl(-1, 7, 4, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidColumns() {
    new DungeonImpl(5, -1, 4, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterConnectivity() {
    new DungeonImpl(5, 7, -1, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void largeInterConnectivity() {
    new DungeonImpl(5, 7, 55, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighTreasurePercent() {
    new DungeonImpl(5, 7, 4, 110,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowTreasurePercent() {
    new DungeonImpl(5, 7, 4, -10,
            false, 5, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowMonsterCount() {
    new DungeonImpl(5, 7, 4, 10,
            false, -1, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighMonsterCount() {
    new DungeonImpl(5, 7, 4, 10,
            false, 20, 1, 1, 1,
            new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRandomizer() {
    new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 1, 1, null);
  }

  @Test
  public void nextMoveNonWrapping() {

    //check current cave
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(1).getNeighbors().get(Direction.EAST).getId());
    assertEquals(8, dungeon.getLocationList().get(1).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(0, dungeon.getLocationList().get(1).getNeighbors().get(Direction.WEST).getId());

    dungeon.nextMove("S");
    assertEquals(8, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(7, dungeon.getLocationList().get(8).getNeighbors().get(Direction.WEST).getId());
    assertEquals(9, dungeon.getLocationList().get(8).getNeighbors().get(Direction.EAST).getId());
    assertEquals(1, dungeon.getLocationList().get(8).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(15, dungeon.getLocationList().get(8).getNeighbors().get(Direction.SOUTH).getId());

    dungeon.nextMove("E");
    assertEquals(9, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(2, dungeon.getLocationList().get(9).getNeighbors().get(Direction.NORTH).getId());
    assertEquals(10, dungeon.getLocationList().get(9).getNeighbors().get(Direction.EAST).getId());
    assertEquals(8, dungeon.getLocationList().get(9).getNeighbors().get(Direction.WEST).getId());
    assertEquals(16, dungeon.getLocationList().get(9).getNeighbors().get(Direction.SOUTH).getId());
    dungeon.shootArrow(1, "N");
    dungeon.shootArrow(1, "N");
    dungeon.nextMove("N");
    assertEquals(2, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(1, dungeon.getLocationList().get(2).getNeighbors().get(Direction.WEST).getId());
    assertEquals(9, dungeon.getLocationList().get(2).getNeighbors().get(Direction.SOUTH).getId());
    assertEquals(3, dungeon.getLocationList().get(2).getNeighbors().get(Direction.EAST).getId());

    dungeon.nextMove("W");
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
  }

  @Test
  public void dumpDungeonWrapping() {
    assertEquals("\n"
            + "CAVE: 0\n"
            + "-->NORTH Neighbor: CAVE: 28\n"
            + "-->SOUTH Neighbor: CAVE: 7\n"
            + "-->EAST Neighbor: CAVE: 1\n"
            + "-->WEST Neighbor: CAVE: 6\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 1\n"
            + "-->NORTH Neighbor: CAVE: 29\n"
            + "-->SOUTH Neighbor: CAVE: 8\n"
            + "-->EAST Neighbor: CAVE: 2\n"
            + "-->WEST Neighbor: CAVE: 7\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 2\n"
            + "-->NORTH Neighbor: CAVE: 30\n"
            + "-->SOUTH Neighbor: CAVE: 9\n"
            + "-->EAST Neighbor: CAVE: 3\n"
            + "-->WEST Neighbor: CAVE: 8\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 3\n"
            + "-->NORTH Neighbor: CAVE: 31\n"
            + "-->SOUTH Neighbor: CAVE: 10\n"
            + "-->EAST Neighbor: CAVE: 4\n"
            + "-->WEST Neighbor: CAVE: 9\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 4\n"
            + "-->NORTH Neighbor: CAVE: 32\n"
            + "-->SOUTH Neighbor: TUNNEL: 11\n"
            + "-->EAST Neighbor: CAVE: 5\n"
            + "-->WEST Neighbor: CAVE: 10\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 5\n"
            + "-->NORTH Neighbor: CAVE: 33\n"
            + "-->SOUTH Neighbor: TUNNEL: 12\n"
            + "-->WEST Neighbor: CAVE: 4\n"
            + "Arrow Count: 2_____PIT_____\n"
            + "\n"
            + "CAVE: 6\n"
            + "-->NORTH Neighbor: CAVE: 34\n"
            + "-->SOUTH Neighbor: TUNNEL: 13\n"
            + "-->EAST Neighbor: CAVE: 0\n"
            + "###MovingMonster###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 7\n"
            + "-->NORTH Neighbor: CAVE: 0\n"
            + "-->SOUTH Neighbor: TUNNEL: 14\n"
            + "-->EAST Neighbor: CAVE: 1^^^^^THIEF^^^^^\n"
            + "\n"
            + "CAVE: 8\n"
            + "-->NORTH Neighbor: CAVE: 1\n"
            + "-->SOUTH Neighbor: TUNNEL: 15\n"
            + "-->EAST Neighbor: CAVE: 2\n"
            + "\n"
            + "CAVE: 9\n"
            + "-->NORTH Neighbor: CAVE: 2\n"
            + "-->SOUTH Neighbor: TUNNEL: 16\n"
            + "-->EAST Neighbor: CAVE: 3\n"
            + "\n"
            + "CAVE: 10\n"
            + "-->NORTH Neighbor: CAVE: 3\n"
            + "-->SOUTH Neighbor: TUNNEL: 17\n"
            + "-->EAST Neighbor: CAVE: 4\n"
            + "\n"
            + "TUNNEL: 11\n"
            + "-->NORTH Neighbor: CAVE: 4\n"
            + "-->SOUTH Neighbor: TUNNEL: 18\n"
            + "\n"
            + "TUNNEL: 12\n"
            + "-->NORTH Neighbor: CAVE: 5\n"
            + "-->SOUTH Neighbor: TUNNEL: 19\n"
            + "\n"
            + "TUNNEL: 13\n"
            + "-->NORTH Neighbor: CAVE: 6\n"
            + "-->SOUTH Neighbor: TUNNEL: 20\n"
            + "\n"
            + "TUNNEL: 14\n"
            + "-->NORTH Neighbor: CAVE: 7\n"
            + "-->SOUTH Neighbor: CAVE: 21\n"
            + "\n"
            + "TUNNEL: 15\n"
            + "-->NORTH Neighbor: CAVE: 8\n"
            + "-->SOUTH Neighbor: CAVE: 22\n"
            + "\n"
            + "TUNNEL: 16\n"
            + "-->NORTH Neighbor: CAVE: 9\n"
            + "-->SOUTH Neighbor: CAVE: 23\n"
            + "\n"
            + "TUNNEL: 17\n"
            + "-->NORTH Neighbor: CAVE: 10\n"
            + "-->SOUTH Neighbor: CAVE: 24\n"
            + "\n"
            + "TUNNEL: 18\n"
            + "-->NORTH Neighbor: TUNNEL: 11\n"
            + "-->SOUTH Neighbor: CAVE: 25\n"
            + "\n"
            + "TUNNEL: 19\n"
            + "-->NORTH Neighbor: TUNNEL: 12\n"
            + "-->SOUTH Neighbor: CAVE: 26\n"
            + "\n"
            + "TUNNEL: 20\n"
            + "-->NORTH Neighbor: TUNNEL: 13\n"
            + "-->SOUTH Neighbor: CAVE: 27\n"
            + "\n"
            + "CAVE: 21\n"
            + "-->NORTH Neighbor: TUNNEL: 14\n"
            + "\n"
            + "CAVE: 22\n"
            + "-->NORTH Neighbor: TUNNEL: 15\n"
            + "\n"
            + "CAVE: 23\n"
            + "-->NORTH Neighbor: TUNNEL: 16\n"
            + "\n"
            + "CAVE: 24\n"
            + "-->NORTH Neighbor: TUNNEL: 17\n"
            + "\n"
            + "CAVE: 25\n"
            + "-->NORTH Neighbor: TUNNEL: 18\n"
            + "\n"
            + "CAVE: 26\n"
            + "-->NORTH Neighbor: TUNNEL: 19\n"
            + "###Otyugh###\n"
            + "\n"
            + "CAVE: 27\n"
            + "-->NORTH Neighbor: TUNNEL: 20\n"
            + "\n"
            + "CAVE: 28\n"
            + "-->SOUTH Neighbor: CAVE: 0\n"
            + "\n"
            + "CAVE: 29\n"
            + "-->SOUTH Neighbor: CAVE: 1\n"
            + "\n"
            + "CAVE: 30\n"
            + "-->SOUTH Neighbor: CAVE: 2\n"
            + "\n"
            + "CAVE: 31\n"
            + "-->SOUTH Neighbor: CAVE: 3\n"
            + "\n"
            + "CAVE: 32\n"
            + "-->SOUTH Neighbor: CAVE: 4\n"
            + "\n"
            + "CAVE: 33\n"
            + "-->SOUTH Neighbor: CAVE: 5\n"
            + "\n"
            + "CAVE: 34\n"
            + "-->SOUTH Neighbor: CAVE: 6\n", dungeonW.toString());
  }

  @Test
  public void testForDumpDungeonNonWrapping() {
    assertEquals("\n"
            + "TUNNEL: 0\n"
            + "-->SOUTH Neighbor: CAVE: 7\n"
            + "-->EAST Neighbor: CAVE: 1\n"
            + "Arrow Count: 2_____PIT_____\n"
            + "\n"
            + "CAVE: 1\n"
            + "-->SOUTH Neighbor: CAVE: 8\n"
            + "-->EAST Neighbor: CAVE: 2\n"
            + "-->WEST Neighbor: TUNNEL: 0\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 2\n"
            + "-->SOUTH Neighbor: CAVE: 9\n"
            + "-->EAST Neighbor: CAVE: 3\n"
            + "-->WEST Neighbor: CAVE: 1\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 3\n"
            + "-->SOUTH Neighbor: CAVE: 10\n"
            + "-->EAST Neighbor: CAVE: 4\n"
            + "-->WEST Neighbor: CAVE: 2\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 4\n"
            + "-->SOUTH Neighbor: CAVE: 11\n"
            + "-->EAST Neighbor: CAVE: 5\n"
            + "-->WEST Neighbor: CAVE: 3\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 5\n"
            + "-->SOUTH Neighbor: TUNNEL: 12\n"
            + "-->EAST Neighbor: TUNNEL: 6\n"
            + "-->WEST Neighbor: CAVE: 4\n"
            + "###Otyugh###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "TUNNEL: 6\n"
            + "-->SOUTH Neighbor: TUNNEL: 13\n"
            + "-->WEST Neighbor: CAVE: 5\n"
            + "###MovingMonster###\n"
            + "Arrow Count: 2\n"
            + "\n"
            + "CAVE: 7\n"
            + "-->NORTH Neighbor: TUNNEL: 0\n"
            + "-->SOUTH Neighbor: TUNNEL: 14\n"
            + "-->EAST Neighbor: CAVE: 8^^^^^THIEF^^^^^\n"
            + "\n"
            + "CAVE: 8\n"
            + "-->NORTH Neighbor: CAVE: 1\n"
            + "-->SOUTH Neighbor: TUNNEL: 15\n"
            + "-->EAST Neighbor: CAVE: 9\n"
            + "-->WEST Neighbor: CAVE: 7\n"
            + "\n"
            + "CAVE: 9\n"
            + "-->NORTH Neighbor: CAVE: 2\n"
            + "-->SOUTH Neighbor: TUNNEL: 16\n"
            + "-->EAST Neighbor: CAVE: 10\n"
            + "-->WEST Neighbor: CAVE: 8\n"
            + "\n"
            + "CAVE: 10\n"
            + "-->NORTH Neighbor: CAVE: 3\n"
            + "-->SOUTH Neighbor: TUNNEL: 17\n"
            + "-->EAST Neighbor: CAVE: 11\n"
            + "-->WEST Neighbor: CAVE: 9\n"
            + "\n"
            + "CAVE: 11\n"
            + "-->NORTH Neighbor: CAVE: 4\n"
            + "-->SOUTH Neighbor: TUNNEL: 18\n"
            + "-->WEST Neighbor: CAVE: 10\n"
            + "\n"
            + "TUNNEL: 12\n"
            + "-->NORTH Neighbor: CAVE: 5\n"
            + "-->SOUTH Neighbor: TUNNEL: 19\n"
            + "\n"
            + "TUNNEL: 13\n"
            + "-->NORTH Neighbor: TUNNEL: 6\n"
            + "-->SOUTH Neighbor: TUNNEL: 20\n"
            + "\n"
            + "TUNNEL: 14\n"
            + "-->NORTH Neighbor: CAVE: 7\n"
            + "-->SOUTH Neighbor: TUNNEL: 21\n"
            + "\n"
            + "TUNNEL: 15\n"
            + "-->NORTH Neighbor: CAVE: 8\n"
            + "-->SOUTH Neighbor: TUNNEL: 22\n"
            + "\n"
            + "TUNNEL: 16\n"
            + "-->NORTH Neighbor: CAVE: 9\n"
            + "-->SOUTH Neighbor: TUNNEL: 23\n"
            + "\n"
            + "TUNNEL: 17\n"
            + "-->NORTH Neighbor: CAVE: 10\n"
            + "-->SOUTH Neighbor: TUNNEL: 24\n"
            + "\n"
            + "TUNNEL: 18\n"
            + "-->NORTH Neighbor: CAVE: 11\n"
            + "-->SOUTH Neighbor: TUNNEL: 25\n"
            + "\n"
            + "TUNNEL: 19\n"
            + "-->NORTH Neighbor: TUNNEL: 12\n"
            + "-->SOUTH Neighbor: TUNNEL: 26\n"
            + "\n"
            + "TUNNEL: 20\n"
            + "-->NORTH Neighbor: TUNNEL: 13\n"
            + "-->SOUTH Neighbor: TUNNEL: 27\n"
            + "\n"
            + "TUNNEL: 21\n"
            + "-->NORTH Neighbor: TUNNEL: 14\n"
            + "-->SOUTH Neighbor: CAVE: 28\n"
            + "\n"
            + "TUNNEL: 22\n"
            + "-->NORTH Neighbor: TUNNEL: 15\n"
            + "-->SOUTH Neighbor: CAVE: 29\n"
            + "\n"
            + "TUNNEL: 23\n"
            + "-->NORTH Neighbor: TUNNEL: 16\n"
            + "-->SOUTH Neighbor: CAVE: 30\n"
            + "\n"
            + "TUNNEL: 24\n"
            + "-->NORTH Neighbor: TUNNEL: 17\n"
            + "-->SOUTH Neighbor: CAVE: 31\n"
            + "\n"
            + "TUNNEL: 25\n"
            + "-->NORTH Neighbor: TUNNEL: 18\n"
            + "-->SOUTH Neighbor: CAVE: 32\n"
            + "\n"
            + "TUNNEL: 26\n"
            + "-->NORTH Neighbor: TUNNEL: 19\n"
            + "-->SOUTH Neighbor: CAVE: 33\n"
            + "\n"
            + "TUNNEL: 27\n"
            + "-->NORTH Neighbor: TUNNEL: 20\n"
            + "-->SOUTH Neighbor: CAVE: 34\n"
            + "\n"
            + "CAVE: 28\n"
            + "-->NORTH Neighbor: TUNNEL: 21\n"
            + "\n"
            + "CAVE: 29\n"
            + "-->NORTH Neighbor: TUNNEL: 22\n"
            + "\n"
            + "CAVE: 30\n"
            + "-->NORTH Neighbor: TUNNEL: 23\n"
            + "\n"
            + "CAVE: 31\n"
            + "-->NORTH Neighbor: TUNNEL: 24\n"
            + "\n"
            + "CAVE: 32\n"
            + "-->NORTH Neighbor: TUNNEL: 25\n"
            + "\n"
            + "CAVE: 33\n"
            + "-->NORTH Neighbor: TUNNEL: 26\n"
            + "\n"
            + "CAVE: 34\n"
            + "-->NORTH Neighbor: TUNNEL: 27\n"
            + "###Otyugh###\n", dungeon.toString());
  }

    @Test
  public void getLocationDescriptionNonWrapping() {
    assertEquals("\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 2", dungeon.getLocationDescription());
  }

  @Test
  public void getLocationDescriptionWrapping() {
    assertEquals("\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 2", dungeonW.getLocationDescription());
  }

  @Test
  public void getPlayerDescriptionWrapping() {
    assertEquals("\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3", dungeon.getPlayer().toString());
  }

  @Test
  public void getPlayerDescription() {
    assertEquals("\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 0\n"
            + "Player has no treasure\n"
            + "Arrows: 3", dungeonW.getPlayer().toString());
  }

  @Test
  public void testForPlayerHasNoTreasureEncountersThief() {
    dungeon.nextMove("S");
    assertEquals("\n"
            + "Thief in the cave.\n"
            + "Player has no treasure to steal\n"
            + "Player moved successfully to location 1, 0", dungeon.nextMove("W"));
  }

  @Test
  public void testForPlayerWithTreasureEncountersThief() {
    assertEquals(0, dungeon.getPlayer().getTreasureList().size());
    dungeon.pickTreasure();
    assertEquals(3, dungeon.getPlayer().getTreasureList().size());
    assertEquals("\n"
            + "Thief in the cave.\n"
            + "Thief stole all treasures of the player, player has no treasure\n"
            + "Player moved successfully to location 1, 1", dungeon.nextMove("S"));
  }

  @Test
  public void testForPlayerEncountersMovingMonsterAndWins() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(25));

    dungeon.shootArrow(1, "E");
    dungeon.shootArrow(1, "E");
    dungeon.nextMove("E");

    dungeon.pickArrow();
    dungeon.shootArrow(1, "E");
    dungeon.shootArrow(1, "E");
    dungeon.nextMove("E");

    assertEquals("\n"
            + "Thief in the cave.\n"
            + "Player has no treasure to steal\n"
            + "Moving CreatureAbs encountered in the cave, player has to fight.\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 25\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 25\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 25\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 25\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 25\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 25\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 25\n"
            + "Player killed the moving monster\n"
            + "Player moved successfully to location 2", dungeon.nextMove("W"));
  }

  @Test
  public void testForPlayerEncountersMovingMonsterAndLooses() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 1, 1,
            new FixedRandomizer(48));

    dungeon.shootArrow(1, "E");
    dungeon.shootArrow(1, "E");
    dungeon.nextMove("E");

    dungeon.pickArrow();
    dungeon.shootArrow(1, "E");
    dungeon.shootArrow(1, "E");
    dungeon.nextMove("E");

    assertEquals("\n"
            + "Thief in the cave.\n"
            + "Player has no treasure to steal\n"
            + "Moving CreatureAbs encountered in the cave, player has to fight.\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 48\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 48\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 48\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 48\n"
            + "Monster gets the turn\n"
            + "Monster hits player, damage by: 48\n"
            + "Player gets the turn\n"
            + "Player hits monster, damage by: 48\n"
            + "Game Over!! Player got Killed", dungeon.nextMove("W"));
  }

  @Test
  public void testForOtyughHighSmell() {
    //player at location 1, location 2 has monster thus the smell should be HIGH
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    assertTrue(dungeon.getLocationList().get(2).hasMonster());
    assertEquals(SmellType.HIGH_PUNGENT, dungeon.checkSmell());
  }

  @Test
  public void testForOtyughLowSmell() {
    //player at location 1, location 2 has monster thus the smell should be HIGH
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    assertTrue(dungeon.getLocationList().get(2).hasMonster());
    assertEquals(SmellType.HIGH_PUNGENT, dungeon.checkSmell());

    //move monster to position 0
    dungeon.nextMove("W");
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getId());
    assertEquals(SmellType.LESS_PUNGENT, dungeon.checkSmell());
  }

  @Test
  public void testForOtyughNoneSmell() {
    //player at location 1, nearby only location 2 has monster thus the smell should be HIGH
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    assertEquals(100, dungeon.getLocationList().get(2).getMonster().getHealth());
    assertEquals(SmellType.HIGH_PUNGENT, dungeon.checkSmell());

    dungeon.nextMove("S");
    dungeon.nextMove("S");
    //smell should be none
    assertEquals(SmellType.ODOURLESS, dungeon.checkSmell());
  }

  @Test
  public void testForPlayerDiesByFallingIntoPit() {
    assertTrue(dungeon.getLocationList().get(0).isContainsPit());
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());

    //move into pit
    assertEquals("\nPit in the cave! Ohhh nooo, "
            + "player died by falling into the pit", dungeon.nextMove("W"));
  }

  @Test
  public void testForPitNearby() {
    assertTrue(dungeon.getLocationList().get(0).isContainsPit());
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());

    assertEquals(SoilQuality.DENSE, dungeon.checkSoilType());
  }

  @Test
  public void testForPitTwoDistanceFromPlayer() {
    assertTrue(dungeon.getLocationList().get(0).isContainsPit());
    assertEquals(1, dungeon.getPlayer().getCurrentLocation().getId());
    dungeon.nextMove("S");
    assertEquals(SoilQuality.POROUS, dungeon.checkSoilType());
  }

  @Test
  public void testNoOfThiefInCave() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 2, 1, 1,
            new FixedRandomizer(2));
    int thiefCount = 0;
    for (Location location : dungeon.getLocationList()) {
      if (location.isContainsThief()) {
        thiefCount++;
      }
    }
    assertEquals(2, thiefCount);
  }

  @Test
  public void testNoOfPitsInCave() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 2, 1,
            new FixedRandomizer(2));
    int pitCount = 0;
    for (Location location : dungeon.getLocationList()) {
      if (location.isContainsPit()) {
        pitCount++;
      }
    }
    assertEquals(2, pitCount);
  }

  @Test
  public void testNoOfMovingMonsterInCave() {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, 1, 1, 2,
            new FixedRandomizer(2));
    int mvCount = 0;
    for (Location location : dungeon.getLocationList()) {
      if (location.hasMonster()
              && location.getMonster().getMonsterType() == CreatureType.MOVING_MONSTER
              && location.getMonster().getHealth() > 0) {
        mvCount++;
      }
    }
    assertEquals(2, mvCount);
  }
}