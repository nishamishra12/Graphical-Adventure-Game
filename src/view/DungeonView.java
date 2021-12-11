package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.IDungeonController;
import dungeon.ReadOnlyModel;

//Referenced code and syntax from:
// https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
/**
 * This is a view class for the entire dungeon GUI. The view consists of the header, footer,
 * j-menus and their respective listeners.
 */
public class DungeonView extends JFrame implements IView {

  private ReadOnlyModel model;
  private DungeonPanel dungeonPanel;
  private SettingPanel settingPanel;
  private JMenuBar jMenuBar;
  private JMenu createDungeonMenu, settingMenu;
  private JMenuItem dungeonSettings;
  private JMenuItem quitItem, restartItem;
  private JScrollPane jScrollPane;
  private JScrollPane jScrollPaneDesc;
  private JPanel playGamePanel;
  private JPanel footerPanel;
  private JPanel headerPanel;
  private JPanel nextMovePanel;
  private JPanel locationDescPanel;
  private JSplitPane jSplitPane;

  private JButton moveNorth;
  private JButton moveSouth;
  private JButton moveWest;
  private JButton moveEast;
  private JButton shootArrow;
  private JButton pickArrow;
  private JButton pickTreasure;
  private JButton playerDescButton;
  private JComboBox<Integer> shootDist;
  private JComboBox<String> shootDir;
  private JTextArea message;
  private JTextArea nextMoveDesc;
  private JTextArea locationDesc;

  /**
   * Constructs a dungeon view with the help of a readonly model.
   *
   * @param dungeon takes the readonly model
   */
  public DungeonView(ReadOnlyModel dungeon) {
    super();
    this.setTitle("Dungeon!");
    this.model = dungeon;
    this.setSize(500, 500);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    settingPanel = new SettingPanel();
    message = new JTextArea();
    jMenuBar = new JMenuBar();
    this.setJMenuBar(jMenuBar);
    AddMenuToMenuBar();

    headerPanel = new JPanel();
    headerPanel.setBackground(Color.GRAY);
    JLabel fontLabel = new JLabel("****** Welcome to the Dungeon! ******");
    fontLabel.setForeground(Color.WHITE);
    fontLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    headerPanel.add(BorderLayout.CENTER, fontLabel);
    jScrollPane = new JScrollPane(headerPanel);
    this.add(BorderLayout.PAGE_START, headerPanel);

    footerPanel = new JPanel();
    footerPanel.setBackground(Color.GRAY);
    message.setText("Click on 'Create Dungeon' from the menu to create a new Dungeon");
    message.setSize(new Dimension(700, 50));
    message.setEditable(false);
    message.setWrapStyleWord(true);
    message.setLineWrap(true);
    message.setBackground(Color.GRAY);
    message.setForeground(Color.WHITE);
    message.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    footerPanel.add(BorderLayout.CENTER, message);
    jScrollPane = new JScrollPane(footerPanel);
    this.add(BorderLayout.PAGE_END, footerPanel);

    createPlayGamePanel();
    playGamePanel.setPreferredSize(new Dimension(160, 400));
    jScrollPane = new JScrollPane(playGamePanel);
    this.add(BorderLayout.LINE_START, jScrollPane);
    setAllPanels();
  }

  private void setAllPanels() {
    //player desc
    nextMovePanel = new JPanel();
    nextMoveDesc = new JTextArea();
    nextMoveDesc.setSize(new Dimension(250, 250));
    nextMoveDesc.setEditable(false);
    nextMoveDesc.setWrapStyleWord(true);
    nextMoveDesc.setLineWrap(true);
    nextMoveDesc.setBackground(UIManager.getColor("Label.background"));
    nextMovePanel.add(BorderLayout.CENTER, nextMoveDesc);

    //location desc
    locationDescPanel = new JPanel();
    locationDesc = new JTextArea();
    locationDesc.setSize(new Dimension(250, 250));
    locationDesc.setEditable(false);
    locationDesc.setWrapStyleWord(true);
    locationDesc.setLineWrap(true);
    locationDesc.setBackground(UIManager.getColor("Label.background"));
    locationDescPanel.add(BorderLayout.CENTER, locationDesc);

    jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, nextMovePanel,
            locationDescPanel);
    jSplitPane.setPreferredSize(new Dimension(250, 500));
    jSplitPane.setResizeWeight(0.5);
    jScrollPaneDesc = new JScrollPane(jSplitPane);
    this.add(BorderLayout.LINE_END, jScrollPaneDesc);

    dungeonPanel = new DungeonPanel(model, this);
    dungeonPanel.setPreferredSize(new Dimension(500, 500));
    jScrollPane = new JScrollPane(dungeonPanel);
    this.add(BorderLayout.CENTER, jScrollPane);

    resetFocus();
    pack();
  }

  private void createPlayGamePanel() {
    playGamePanel = new JPanel();
    playGamePanel.setBorder(BorderFactory.createTitledBorder("Play Game"));
    String[] directions = { "North", "South", "East", "West"};
    Integer[] distance = { 1, 2, 3, 4, 5};
    moveNorth = new JButton("Move North");
    moveSouth = new JButton("Move South");
    moveEast = new JButton("Move East");
    moveWest = new JButton("Move West");
    pickArrow = new JButton("Pick Arrow");
    pickTreasure = new JButton("Pick Treasure");
    shootDist = new JComboBox<>(distance);
    shootDir = new JComboBox<>(directions);
    shootArrow = new JButton("Shoot Arrow");
    playerDescButton = new JButton("Player Description");

    //set equal size for all buttons
    for (JButton jButton : Arrays.asList(moveNorth, moveSouth, moveEast, moveWest, pickTreasure,
            pickArrow, shootArrow, playerDescButton)) {
      jButton.setPreferredSize(new Dimension(150, 26));
    }
    shootDir.setPreferredSize(new Dimension(150, 26));
    shootDist.setPreferredSize(new Dimension(150, 26));

    //action listener
    moveEast.setActionCommand("Move East");
    moveWest.setActionCommand("Move West");
    moveSouth.setActionCommand("Move South");
    moveNorth.setActionCommand("Move North");
    pickArrow.setActionCommand("Pick Arrow");
    pickTreasure.setActionCommand("Pick Treasure");
    shootArrow.setActionCommand("Shoot Arrow");
    playerDescButton.setActionCommand("Player Description");

    playGamePanel.add(moveNorth);
    playGamePanel.add(moveSouth);
    playGamePanel.add(moveEast);
    playGamePanel.add(moveWest);
    playGamePanel.add(pickArrow);
    playGamePanel.add(pickTreasure);
    playGamePanel.add(new Label("Enter Distance"));
    playGamePanel.add(shootDist);
    playGamePanel.add(new Label("Enter Direction"));
    playGamePanel.add(shootDir);
    playGamePanel.add(shootArrow);
    playGamePanel.add(playerDescButton);
  }

  private void AddMenuToMenuBar() {
    //1 - Create Dungeon
    createDungeonMenu = new JMenu("Create Dungeon");
    createDungeonMenu.setBorder(new EmptyBorder(1, 0, 1, 0));
    jMenuBar.add(createDungeonMenu);
    dungeonSettings = new JMenuItem("Enter dungeon parameters");

    //add to menu
    createDungeonMenu.add(dungeonSettings);

    //2 - other Options
    settingMenu = new JMenu("Game Options");
    settingMenu.setBorder(new EmptyBorder(1, 0, 1, 0));
    jMenuBar.add(settingMenu);
    quitItem = new JMenuItem("Quit Game");
    restartItem = new JMenuItem("Restart Game");

    //add to menu
    settingMenu.add(quitItem);
    settingMenu.add(restartItem);
  }

  /**
   * Make the view visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Action listener for every button and menus present in the view screen.
   *
   * @param listener takes the action listener
   */
  @Override
  public void addActionListener(ActionListener listener) {
    moveNorth.addActionListener(listener);
    moveSouth.addActionListener(listener);
    moveEast.addActionListener(listener);
    moveWest.addActionListener(listener);
    pickArrow.addActionListener(listener);
    pickTreasure.addActionListener(listener);
    shootArrow.addActionListener(listener);
    playerDescButton.addActionListener(listener);
    restartItem.addActionListener(listener);
    dungeonSettings.addActionListener(listener);
    quitItem.addActionListener(listener);
  }

  /**
   * The method gives the shoot distance.
   *
   * @return the shoot distance
   */
  @Override
  public int getShootDistance() {
    resetFocus();
    return shootDist.getSelectedIndex() + 1;
  }

  /**
   * This method gives the shoot direction.
   *
   * @return the shoot direction
   */
  @Override
  public String getShootDirection() {
    resetFocus();
    String s = "";
    switch (shootDir.getSelectedIndex()) {
      case 0: s = "N";
      break;
      case 1: s = "S";
      break;
      case 2: s = "E";
      break;
      case 3: s = "W";
    }
    return s;
  }

  /**
   * This method is used to render the dungeon after every action the player takes.
   *
   * @param listener takes controller
   */
  @Override
  public void renderDungeon(IDungeonController listener) {
    dungeonPanel.removePanel();
    dungeonPanel.createDungeon(listener);
  }

  /**
   * This method shows the error message to the screen in case of any exceptions.
   *
   * @param error
   */
  @Override
  public void showErrorMessage(String error) {
    new DialogBox(error);
  }

  /**
   * This method shows the player description to the user.
   *
   * @param desc the player description string
   */
  @Override
  public void showPlayerDesc(String desc) {
    new DialogBox(desc, "Player Description", "res/images/player.png");
  }

  /**
   * This method rewrites all the text messages which are displayed to the user during the game.
   *
   * @param text the text to be displayed after every action
   */
  @Override
  public void showMessage(String text) {
    message.setText(text);
    footerPanel.add(message);
    footerPanel.updateUI();

    //nextMove desc
    nextMoveDesc.setText(model.getNextPossibleDescription());
    nextMovePanel.add(nextMoveDesc);

    //location desc
    locationDesc.setText(model.getLocationDescription());
    locationDesc.append("\nSmell: " + model.checkSmell());
    locationDesc.append("\nSoil Quality: " + model.checkSoilType());
    locationDescPanel.add(locationDesc);
  }

  /**
   * Refreshes the view panel
   */
  @Override
  public void refresh() {
    dungeonPanel.refresh();
  }

  /**
   * Reset focus from keyboards to mouse and mouse to keyboard
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRows() {
    return settingPanel.getRows();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumns() {
    return settingPanel.getColumns();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getInterconnectivity() {
    return settingPanel.getInterconnectivity();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTreasurePercent() {
    return settingPanel.getTreasurePercent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean getWrapping() {
    return settingPanel.getWrapping();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMonsterCount() {
    return settingPanel.getMonsterCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getThiefCount() {
    return settingPanel.getThiefCount() + 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPitCount() {
    return settingPanel.getPitCount() + 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMovingMonsterCount() {
    return settingPanel.getMovingMonsterCount() + 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int setDungeonParameters() {
    return settingPanel.addAllComponents();
  }



  /**
   * {@inheritDoc}
   */
  @Override
  public void clearDescriptions() {
    message.setText("");
    footerPanel.add(message);
    footerPanel.updateUI();
    getContentPane().remove(jSplitPane);
    getContentPane().remove(jScrollPaneDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNewModel(ReadOnlyModel m, IDungeonController listener) {
    this.model = m;
    clearDescriptions();
    getContentPane().remove(jScrollPane);
    setAllPanels();
    dungeonPanel.createDungeon(listener);
    showMessage("New Dungeon Created. Use arrow keys to move, 'S' - Shoot, 'A' - pick arrows,"
            + "'T' - pick treasure.");
    refresh();
  }
}

