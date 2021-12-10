package dungeontest;

import java.awt.event.KeyEvent;
import java.util.Map;

import controller.IKeyBoardListener;

public class MockKeyBoardListener implements IKeyBoardListener {

  public Map<Character, Runnable> getKeyTypedMap() {
    return keyTypedMap;
  }

  public Map<Integer, Runnable> getKeyPressedMap() {
    return keyPressedMap;
  }

  public Map<Integer, Runnable> getKeyReleasedMap() {
    return keyReleasedMap;
  }

  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Runnable> keyReleasedMap;

  /**
   * Default constructor.
   */
  public MockKeyBoardListener() {
    // fields get set by their mutators
    keyTypedMap = null;
    keyPressedMap = null;
    keyReleasedMap = null;
  }


  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {

  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent e) {

  }
}
