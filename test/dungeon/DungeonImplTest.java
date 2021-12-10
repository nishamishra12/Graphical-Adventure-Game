package dungeon;

import org.junit.Before;
import org.junit.Test;

import randomizer.FixedRandomizer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class DungeonImplTest {

  private Dungeon dungeon;
  private Dungeon dungeonW;

  @Before
  public void setUp() throws Exception {
    dungeon = new DungeonImpl(5, 7, 4, 20,
            false, 5, new FixedRandomizer(2));
    dungeonW = new DungeonImpl(5, 7, 4, 20,
            true, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRows() {
    new DungeonImpl(-1, 7, 4, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidColumns() {
    new DungeonImpl(5, -1, 4, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterConnectivity() {
    new DungeonImpl(5, 7, -1, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void largeInterConnectivity() {
    new DungeonImpl(5, 7, 55, 20,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighTreasurePercent() {
    new DungeonImpl(5, 7, 4, 110,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowTreasurePercent() {
    new DungeonImpl(5, 7, 4, -10,
            false, 5, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowMonsterCount() {
    new DungeonImpl(5, 7, 4, 10,
            false, -1, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighMonsterCount() {
    new DungeonImpl(5, 7, 4, 10,
            false, 20, new FixedRandomizer(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullRandomizer() {
    new DungeonImpl(5, 7, 4, 20,
            false, 5, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void smallDungeon() {
    new DungeonImpl(2, 2, 1, 20,
            false, 5, new FixedRandomizer(1));
  }

  @Test
  public void testForPlayerHasNoTreasureEncountersThief() {
    assertEquals("\n"
            + "Thief in the cave.\n"
            + "Player has no treasure to steal\n"
            + "Player moved successfully to location 1, 1", dungeon.nextMove("S"));
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
            false, 5, new FixedRandomizer(25));

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
            false, 5, new FixedRandomizer(48));

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
}