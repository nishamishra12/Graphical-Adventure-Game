package dungeon;

/**
 * This is a factory class of creature to provide type of creature.
 */
public class MonsterFactory {

  /**
   * This method takes the type of creature and provides the required object.
   *
   * @param monsterType take the creature type
   * @return the creature object
   * @throws IllegalArgumentException when the type entered is invalid
   */
  public static Creature createMonster(CreatureType monsterType) throws IllegalArgumentException {
    switch (monsterType) {
      case MOVING_MONSTER:
        return new MovingMonster();
      case OTUYGH:
        return new Otyugh();
      default:
        throw new IllegalArgumentException("Unexpected value of the monster type: ");
    }
  }
}
