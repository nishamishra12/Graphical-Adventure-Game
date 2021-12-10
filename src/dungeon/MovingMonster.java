package dungeon;

/**
 * This class represents the moving monster and inherits all properties of the abstract creature
 * class.
 */
public class MovingMonster extends CreatureAbs {

  @Override
  public CreatureType getMonsterType() {
    return CreatureType.MOVING_MONSTER;
  }
}
