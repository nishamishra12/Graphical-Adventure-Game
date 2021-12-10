package controller;

import java.awt.event.KeyListener;
import java.util.Map;

/**
 * This interface represents a keyboard listener which extends key listener.
 * It is called from the command controller whenever a key is pressed on the keyboard.
 */
public interface IKeyBoardListener extends KeyListener {

  public void setKeyTypedMap(Map<Character, Runnable> map);

  public void setKeyPressedMap(Map<Integer, Runnable> map);

  public void setKeyReleasedMap(Map<Integer, Runnable> map);
}
