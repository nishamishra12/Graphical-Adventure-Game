package dungeon;

/**
 * This class represents an Otyugh and inherits all properties of the abstract creature
 * class.
 */
public class Otyugh extends CreatureAbs {

  @Override
  public CreatureType getMonsterType() {
    return CreatureType.OTUYGH;
  }
}
