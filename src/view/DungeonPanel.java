package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.IDungeonController;
import dungeon.CreatureType;
import dungeon.Direction;
import dungeon.Location;
import dungeon.ReadOnlyModel;
import dungeon.SmellType;
import dungeon.SoilQuality;
import dungeon.Treasure;

/**
 * <P>
 *   This class represents the dungeon panel. The panel creates the entire dungeon grid view by
 *   visiting all the nodes and overlaying the images on top of each other based on different
 *   things present at each location.
 * </P>
 */
class DungeonPanel extends JPanel {

  List<JLabel> gridList;
  private ReadOnlyModel model;
  private IView view;
  private JPanel gamePanel;

  /**
   * Constructor to create a blank dungeon panel with white background and yellow border.
   *
   * @param m take the readonly model
   * @param v take the view
   */
  public DungeonPanel(ReadOnlyModel m, IView v) {
    this.model = m;
    this.view = v;
    UIManager.put("OptionPane.background", Color.WHITE);
    setBorder(BorderFactory.createLineBorder(Color.YELLOW));
  }

  /**
   * This method refreshed the dunegon UI by updating the dungeon panel as well as the internal
   * grid panel.
   */
  public void refresh() {
    this.updateUI();
    gamePanel.updateUI();
  }

  /**
   * This method creates the entire dungeon by passing through each node in the dungeon.
   *
   * @param listener takes the controller as an input to use it for mouse clicks
   */
  public void createDungeon(IDungeonController listener) {
    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(model.getRows(), model.getColumns()));
    gamePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    this.add(gamePanel);
    gridList = new ArrayList<>();
    try {
      List<Location> tempList = new ArrayList<>(model.getLocationList());
      for (int i = 0; i < tempList.size(); i++) {
        JLabel jLabel = new JLabel();
        String pathname = "/images/black.png";
        BufferedImage myPicture = ImageIO.read(getClass().getResourceAsStream(pathname));

        if (tempList.get(i).hasPlayerVisited()) {
        myPicture = overlay(myPicture,
                "/images/" + getCorrectImages(tempList.get(i)),
                0);

        //if location contains otyugh
        if (tempList.get(i).hasMonster() &&
                tempList.get(i).getMonster().getMonsterType().equals(CreatureType.OTUYGH)
                && tempList.get(i).getMonster().getHealth() > 0) {
          if (tempList.get(i).getMonster().getHealth() == 100) {
            myPicture = overlay(myPicture,
                    "/images/otyugh.png",
                    2);
          } else if (tempList.get(i).getMonster().getHealth() == 50) {
            myPicture = overlay(myPicture,
                    "/images/otyugh.png",
                    2);
          }
        }

        //if location contains arrow
        if (tempList.get(i).getArrow() > 0) {
          myPicture = overlay(myPicture,
                  "/images/arrow-white.png",
                  2);
        }

        //if location contains ruby
        if (tempList.get(i).getTreasureList().contains(Treasure.RUBY)) {
          myPicture = overlay(myPicture,
                  "/images/ruby.png",
                  2);
        }

        //if contains diamond
        if (tempList.get(i).getTreasureList().contains(Treasure.DIAMOND)) {
          myPicture = overlay(myPicture,
                  "/images/diamond.png",
                  2);
        }

        //if contains sapphire
        if (tempList.get(i).getTreasureList().contains(Treasure.SAPPHIRE)) {
          myPicture = overlay(myPicture,
                  "/images/emerald.png",
                  2);
        }

        //if moving monster
        if (tempList.get(i).hasMonster()
                && tempList.get(i).getMonster().getMonsterType().equals(CreatureType.MOVING_MONSTER)
                && tempList.get(i).getMonster().getHealth() > 0) {
          myPicture = overlay(myPicture,
                  "/images/moving-monster.png",
                  2);
        }

        //if contains pit
        if (tempList.get(i).isContainsPit()) {
          myPicture = overlay(myPicture,
                  "/images/pit.png",
                  0);
        }

        //if contains thief
        if (tempList.get(i).isContainsThief()) {
          myPicture = overlay(myPicture,
                  "/images/thief.png",
                  2);
        }

        //if contains player cave - show stench
        if (tempList.get(i).getId() == model.getPlayer().getCurrentLocation().getId()) {
          if (model.checkSmell() == SmellType.HIGH_PUNGENT) {
            myPicture = overlay(myPicture,
                    "/images/stench02.png", 2);
          } else if (model.checkSmell() == SmellType.LESS_PUNGENT) {
            myPicture = overlay(myPicture,
                    "/images/stench01.png", 2);
          }

          if(model.checkSoilType() == SoilQuality.DENSE) {
            myPicture = overlay(myPicture,
                    "/images/dense.png", 2);
          } else if(model.checkSoilType() == SoilQuality.POROUS) {
            myPicture = overlay(myPicture,
                    "/images/porous.png", 2);
          }
        }

        //player image
        if (tempList.get(i).getId() == model.getPlayer().getCurrentLocation().getId()) {
          myPicture = overlay(myPicture,
                  "/images/player.png", 2);
        }
        }
        jLabel.setIcon(new ImageIcon(myPicture));
        gridList.add(jLabel);
        gamePanel.add(jLabel);
      }

      //click listener
      //Code citation: https://northeastern.instructure.com/courses/90366/files/10995936?wrap=1
      Map<Direction, Location> adjacencyClickMap = new HashMap<>(
              model.getPlayer().getCurrentLocation().getNeighbors());
      Iterator<Map.Entry<Direction, Location>> iterator = adjacencyClickMap.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<Direction, Location> entry = iterator.next();
        gridList.get(entry.getValue().getId()).addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            listener.moveThePlayerByClick(entry.getKey());
          }
        });
      }
      refresh();
      gameOver();
    } catch (IOException i) {
      i.printStackTrace();
    }
  }

  private void gameOver() {

    if (model.getPlayer().getHealth() <= 0) {
      new DialogBox("Player has been killed", "Game Over!!",
              "/images/dead.png");
      this.removePanel();
      this.updateUI();
      this.view.clearDescriptions();
    }

    if (model.getPlayer().getHealth() > 0
            && model.getPlayer().getCurrentLocation().getId() == model.getEndCave().getId()) {
      new DialogBox("HOORAY!! You Won!", "Game Over!!",
              "/images/won.png");
      this.removePanel();
      this.updateUI();
      this.view.clearDescriptions();
    }
  }

  private String getCorrectImages(Location location) {

    String result = "";
    if (location.getNeighbors().size() == 1) {
      if (location.getNeighbors().containsKey(Direction.NORTH)) {
        result = "N.png";
      } else if (location.getNeighbors().containsKey(Direction.SOUTH)) {
        result = "S.png";
      } else if (location.getNeighbors().containsKey(Direction.EAST)) {
        result = "E.png";
      } else if (location.getNeighbors().containsKey(Direction.WEST)) {
        result = "W.png";
      }
    } else if (location.getNeighbors().size() == 2) {
      if (location.getNeighbors().containsKey(Direction.NORTH)
              && location.getNeighbors().containsKey(Direction.SOUTH)) {
        result = "NS.png";
      } else if (location.getNeighbors().containsKey(Direction.NORTH)
              && location.getNeighbors().containsKey(Direction.WEST)) {
        result = "NW.png";
      } else if (location.getNeighbors().containsKey(Direction.NORTH)
              && location.getNeighbors().containsKey(Direction.EAST)) {
        result = "NE.png";
      } else if (location.getNeighbors().containsKey(Direction.SOUTH)
              && location.getNeighbors().containsKey(Direction.EAST)) {
        result = "ES.png";
      } else if (location.getNeighbors().containsKey(Direction.SOUTH)
              && location.getNeighbors().containsKey(Direction.WEST)) {
        result = "SW.png";
      } else if (location.getNeighbors().containsKey(Direction.EAST)
              && location.getNeighbors().containsKey(Direction.WEST)) {
        result = "EW.png";
      }
    } else if (location.getNeighbors().size() == 3) {
      if (location.getNeighbors().containsKey(Direction.EAST)
              && location.getNeighbors().containsKey(Direction.WEST)
              && location.getNeighbors().containsKey(Direction.NORTH)) {
        result = "NEW.png";
      } else if (location.getNeighbors().containsKey(Direction.EAST)
              && location.getNeighbors().containsKey(Direction.WEST)
              && location.getNeighbors().containsKey(Direction.SOUTH)) {
        result = "SEW.png";
      } else if (location.getNeighbors().containsKey(Direction.SOUTH)
              && location.getNeighbors().containsKey(Direction.WEST)
              && location.getNeighbors().containsKey(Direction.NORTH)) {
        result = "NSW.png";
      } else if (location.getNeighbors().containsKey(Direction.SOUTH)
              && location.getNeighbors().containsKey(Direction.EAST)
              && location.getNeighbors().containsKey(Direction.NORTH)) {
        result = "NSE.png";
      }
    } else if (location.getNeighbors().size() == 4) {
      result = "NSEW.png";
    }
    return result;
  }

  //Code citation for buffered image: https://piazza.com/class/kt0jcw0x7h955a?cid=1500
  private BufferedImage overlay(BufferedImage starting, String fpath, int offset)
          throws IOException {
    BufferedImage overlay = ImageIO.read(getClass().getResourceAsStream(fpath));
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }

  /**
   * This method removes the grid panel.
   */
  public void removePanel() {
    this.remove(gamePanel);
  }
}
