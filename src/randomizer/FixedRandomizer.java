package randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the fixed random generator class which implements all the methods
 * of the interface and is used to calculate fixed random numbers.
 */
public class FixedRandomizer<T> implements Randomizer<T> {

  private int val;

  /**
   * Constructs a fixed random generator by taking an integer as the input.
   *
   * @param arr this parameter takes single integer
   */
  public FixedRandomizer(int arr) {
    this.val = arr;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getNextInt(int min, int max) {
    return val;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> randomizedValueList(List<T> list) {
    return new ArrayList<T>(list);
  }
}
