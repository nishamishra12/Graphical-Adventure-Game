package view;

import java.awt.*;

import javax.swing.*;

//Referenced code and syntax from:
// https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
/**
 * This class represents the setting panel in which the user can enter the dungeon parameters.
 */
class SettingPanel {

  private JTextField rows;
  private JTextField columns;
  private JTextField interconnectivity;
  private JTextField treasurePercent;
  private JCheckBox wrapping;
  private JTextField monsterCount;
  private JLabel rowLabel, colLabel, interconnLabel, percentLabel, monsterCountLabel;
  private JPanel dialogPanel;

  /**
   * Constructs a new setting panel for user to enter dungeon values.
   */
  public SettingPanel() {
    dialogPanel = new JPanel();
    dialogPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    rowLabel = new JLabel("Enter no. of Row");
    colLabel = new JLabel("Enter no. of Column");
    interconnLabel = new JLabel("Enter Inter-Connectivity");
    percentLabel = new JLabel("Enter treasure percentage");
    monsterCountLabel = new JLabel("Enter no of monsters");

    rows = new JTextField("", 2);
    columns = new JTextField("", 2);
    interconnectivity = new JTextField("", 2);
    treasurePercent = new JTextField("", 2);
    wrapping = new JCheckBox("Wrapping");
    monsterCount = new JTextField("", 2);
  }

  /**
   * This method adds all the components to the setting panel.
   *
   * @return the value of the rendered JOption pane
   */
  public int addAllComponents() {
    dialogPanel.add(rowLabel);
    dialogPanel.add(rows);
    dialogPanel.add(colLabel);
    dialogPanel.add(columns);
    dialogPanel.add(interconnLabel);
    dialogPanel.add(interconnectivity);
    dialogPanel.add(percentLabel);
    dialogPanel.add(treasurePercent);
    dialogPanel.add(wrapping);
    dialogPanel.add(monsterCountLabel);
    dialogPanel.add(monsterCount);

    return JOptionPane.showConfirmDialog(null, dialogPanel,
            "Please parameters to create the dungeon", JOptionPane.OK_CANCEL_OPTION);
  }

  /**
   * Gives the number of rows.
   *
   * @return row
   */
  public int getRows() {
    return validateInteger(rows);
  }

  /**
   * Gives the number of columns.
   *
   * @return column
   */
  public int getColumns() {
    return validateInteger(columns);
  }

  /**
   * Gives the interconnectivity.
   *
   * @return interconnectivity
   */
  public int getInterconnectivity() {
    return validateInteger(interconnectivity);
  }

  /**
   * Gives the treasure percentage.
   *
   * @return treasure percent
   */
  public int getTreasurePercent() {
    return validateInteger(treasurePercent);
  }

  /**
   * Gives the wrapping condition.
   *
   * @return wrapping
   */
  public boolean getWrapping() {
    return wrapping.isSelected();
  }

  /**
   * Gives the number of monsters.
   *
   * @return no of monsters
   */
  public int getMonsterCount() {
    return validateInteger(monsterCount);
  }

  private int validateInteger(JTextField value) throws IllegalArgumentException {
    int a = 0;
    try {
      a = Integer.parseInt(value.getText());
    } catch (NumberFormatException e) {
      new DialogBox("Invalid input parameter");
    }
    return a;
  }
}
