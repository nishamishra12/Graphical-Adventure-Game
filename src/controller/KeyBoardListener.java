package controller;

import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * This class represents a keyboard listener. It is called from the command controller
 * whenever a key is pressed on the keyboard.
 */
public class KeyBoardListener implements IKeyBoardListener {

  private Map<Character, Runnable> keyTypedMap;
  private Map<Integer, Runnable> keyPressedMap;
  private Map<Integer, Runnable> keyReleasedMap;

  /**
   * Constructor to initialize keypress, keyreleased, and keytyped
   */
  public KeyBoardListener() {
    keyTypedMap = null;
    keyPressedMap = null;
    keyReleasedMap = null;
  }

  /**
   * This method stores the typed key in the key type map.
   *
   * @param map takes the runnable value of the typed key.
   */
  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * This method stores the pressed key in the key press map.
   *
   * @param map takes the runnable value of the pressed key.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * This method stores the release key in the key release map.
   *
   * @param map takes the runnable value of the release key.
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar())) {
      keyTypedMap.get(e.getKeyChar()).run();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode())) {
      keyPressedMap.get(e.getKeyCode()).run();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode())) {
      keyReleasedMap.get(e.getKeyCode()).run();
    }
  }
}
