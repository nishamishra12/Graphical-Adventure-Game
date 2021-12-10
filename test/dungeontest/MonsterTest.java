package dungeontest;

import org.junit.Before;
import org.junit.Test;

import dungeon.Creature;
import dungeon.CreatureType;
import dungeon.MonsterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Test class to test all the implementation of CreatureAbs class.
 */
public class MonsterTest {

  private Creature otyugh;
  private Creature movingMonster;

  @Before
  public void setUp() throws Exception {
    otyugh = MonsterFactory.createMonster(CreatureType.OTUYGH);
    movingMonster = MonsterFactory.createMonster(CreatureType.MOVING_MONSTER);
  }

  @Test
  public void getHealth() {
    assertEquals(100, otyugh.getHealth());
    assertEquals(100, movingMonster.getHealth());
  }

  @Test
  public void hit() {
    assertEquals(100, otyugh.getHealth());
    otyugh.hit(50);
    assertEquals(50, otyugh.getHealth());

    assertEquals(100, movingMonster.getHealth());
    movingMonster.hit(50);
    assertEquals(50, movingMonster.getHealth());
  }
}