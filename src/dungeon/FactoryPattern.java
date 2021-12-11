package dungeon;

import randomizer.Randomizer;

/**
 * This is a general factory class to provide different objects.
 */
public class FactoryPattern {

  private Randomizer randomizer;

  /**
   * Construct a general factory.
   *
   * @param r take the randomizer
   */
  public FactoryPattern(Randomizer r) {
    this.randomizer = r;
  }

  /**
   * This method creates a new edge object.
   *
   * @param source the source of the edge
   * @param destination the destination of the edge
   * @return the new Edge object
   */
  public Edge createEdge(int source, int destination) {
    return new Edge(source, destination, randomizer.getNextInt(3, 9));
  }

  public Location createLocation(int id, int rows, int columns) {
    return new Cave(id, rows, columns);
  }

  public Location createCopyLocation(Location location) {
    return new Cave(location);
  }
}
