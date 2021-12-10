package view;

import javax.swing.*;

//Referenced code and syntax from:
// https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
/**
 * This is a dialog box class to provide different joption panes on various actions.
 */
class DialogBox {

  /**
   * Constructor to create a Plain message for general actions.
   *
   * @param text take the text to be displayed
   * @param title the title to be displayed
   * @param fileName the filename to be referred for the icon
   */
  public DialogBox(String text, String title, String fileName) {
    JOptionPane.showMessageDialog(null, text, title,
            JOptionPane.PLAIN_MESSAGE, new ImageIcon(fileName));
  }

  /**
   * Constructor to create an error message or exceptions.
   *
   * @param text the error text to be displayed to the user
   */
  public DialogBox(String text) {
    JOptionPane.showMessageDialog(null, text, "Error",
            JOptionPane.ERROR_MESSAGE);
  }
}
