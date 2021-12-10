package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Citation for the code: https://northeastern.instructure.com/courses/90366/files/10995936?wrap=1
 * This class represents a button listener event and stores lists of buttons along with its actions.
 */
public class ButtonListener implements ActionListener {

  private Map<String, Runnable> buttonClickedActions;

  /**
   * Empty default constructor.
   */
  public ButtonListener() {
    buttonClickedActions = null;
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are
   * characters.
   *
   * @param map the actions for button click
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}
