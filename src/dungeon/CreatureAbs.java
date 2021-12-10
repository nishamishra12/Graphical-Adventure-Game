package dungeon;

/**
 * Abstract class which represents both the Otyugh and the moving monster along with their
 * health and type.
 */
public abstract class CreatureAbs implements Creature {

  private int health;

  /**
   * Constructs a creature with health 100.
   */
  public CreatureAbs() {
    this.health = 100;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void hit(int hitValue) {
    this.health -= hitValue;
  }
}
