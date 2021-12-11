package dungeontest;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import controller.IDungeonController;
import dungeon.ReadOnlyModel;
import view.IView;

public class MockView implements IView {

  private final StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  @Override
  public void makeVisible() {
    log.append("\nCalled make visible method");
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    log.append("\nCalled key listener function");
  }

  @Override
  public void addActionListener(ActionListener listener) {
    log.append("\nCalled action listener function");
  }

  @Override
  public void renderDungeon(IDungeonController listener) {
    log.append("\n Calling render dungeon");
  }

  @Override
  public void showErrorMessage(String error) {
    log.append("\nError: " + error);
  }

  @Override
  public void showMessage(String text) {
    log.append("\nDescription: " + text);
    log.append("\nGive Next move description");
    log.append("\nGive location description");
  }

  @Override
  public void refresh() {
    log.append("\nRefresh UI");
  }

  @Override
  public void resetFocus() {
    log.append("\nFocus Reset");
  }

  @Override
  public void setNewModel(ReadOnlyModel model, IDungeonController listener) {
    log.append("\nCalling set new model");
  }

  @Override
  public String getShootDirection() {
    log.append("\nCalling method to get distance of shoot");
    return null;
  }

  @Override
  public int getShootDistance() {
    log.append("\nCalling method to get distance of shoot");
    return 0;
  }

  @Override
  public void clearDescriptions() {
    log.append("\nDescription cleared for ");
  }

  @Override
  public void showPlayerDesc(String desc) {
    log.append("\nPlayer Description: " + desc);
  }

  @Override
  public int setDungeonParameters() {
    log.append("\nMethod called to set dungeon parameters for new dungeon");
    return 0;
  }

  @Override
  public int getRows() {
    log.append("\nMethod called to get no of rows");
    return 0;
  }

  @Override
  public int getColumns() {
    log.append("\nMethod called to get no of columns");
    return 0;
  }

  @Override
  public int getInterconnectivity() {
    log.append("\nMethod called to get interconnectivity");
    return 0;
  }

  @Override
  public int getTreasurePercent() {
    log.append("\nMethod called to get treasure percent");
    return 0;
  }

  @Override
  public boolean getWrapping() {
    log.append("\nMethod called to get wrapping");
    return false;
  }

  @Override
  public int getMonsterCount() {
    log.append("\nMethod called to get no of monsters");
    return 0;
  }

  @Override
  public int getThiefCount() {
    log.append("\nMethod called to get thief count");
    return 0;
  }

  @Override
  public int getPitCount() {
    log.append("\nMethod called to get pit count");
    return 0;
  }

  @Override
  public int getMovingMonsterCount() {
    log.append("\nMethod called to get moving monster count");
    return 0;
  }
}