package dungeontest;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Map;

import controller.CommandController;
import controller.ICommandController;
import controller.IDungeonController;
import controller.commands.Move;
import controller.commands.PickArrow;
import controller.commands.PickTreasure;
import controller.commands.Shoot;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import randomizer.FixedRandomizer;
import view.DungeonView;
import view.IView;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for the dungeon command controller to test all the implementation of the controller
 * using the dungeon model.
 */
public class CommandControllerTest {

  private IDungeonController commandController;
  private Dungeon dungeon;

  @Before
  public void setUp() throws Exception {
    dungeon = new DungeonImpl(5, 4, 2, 20,
            false, 2, 1, 1, 1,
            new FixedRandomizer(3));
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {

    StringReader input = new StringReader("P A P T");
    Appendable gameLog = new FailingAppendable();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullOutput() {
    StringReader input = new StringReader("P A M W");

    commandController = new CommandController(input, null);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullInput() {
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(null, gameLog);
    commandController.play(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullModel() {

    StringReader input = new StringReader("P A M W");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonMove() {
    ICommandController cmd = new Move("N");
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonShoot() {
    ICommandController cmd = new Shoot(1, "N");
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonPickArrow() {
    ICommandController cmd = new PickArrow();
    cmd.goCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNullDungeonPickTreasure() {
    ICommandController cmd = new PickTreasure();
    cmd.goCommand(null);
  }

  @Test
  public void testForWrongMPS() {
    StringReader input = new StringReader("T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Invalid entry.\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForMoveM() {
    StringReader input = new StringReader("M S M E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 2\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPickUpP() {
    StringReader input = new StringReader("P A P T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "Arrow picked up\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 6\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "Treasure picked up\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Treasure: \n"
            + " DIAMOND: 1\n"
            + " RUBY: 1\n"
            + " SAPPHIRE: 1\n"
            + "Arrows: 6\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForShootS() {
    StringReader input = new StringReader("S 1 E S 1 E M E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster has been killed\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 0, 2\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidMove() {
    StringReader input = new StringReader("M B");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "Invalid Direction\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidPick() {
    StringReader input = new StringReader("P V");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "\n"
            + "Invalid entry\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidShootDirection() {
    StringReader input = new StringReader("S 1 A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "Invalid Direction\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidShootDistance() {
    StringReader input = new StringReader("S -1 N S 0 N");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "Distance should be at least 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "Distance should be at least 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerShotIntoDarkness() {
    StringReader input = new StringReader("S 4 S");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "You shot an arrow into the darkness\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidTreasurePickUp() {
    StringReader input = new StringReader("M S P T");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "No treasure present at the location\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForInvalidArrowPickUp() {
    StringReader input = new StringReader("M S P A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "No arrow present at the location\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerEscaped() {
    StringReader input = new StringReader("S 1 E M E");
    Appendable gameLog = new StringBuilder();

    dungeon = new DungeonImpl(5, 4, 2, 20
            , false, 2, 1,1,1,
            new FixedRandomizer(2));

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 2\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 2\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player escaped successfully from an injured Otyugh, and moved to location 2\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 2\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerReachedEnd() {
    StringReader input = new StringReader("P A S 1 E S 1 E M E M S M S M S S 1 S S 1 S M S");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "Arrow picked up\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 6\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 5\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster has been killed\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 4\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 2\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 4\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 6\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 4\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 10\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: TUNNEL: 2, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 4\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 14\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: TUNNEL: 3, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 4\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: TUNNEL: 3, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster has been killed\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: TUNNEL: 3, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: NONE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 18\n"
            + "\n"
            + "Player has reached the destination location 18. Game Over!!", gameLog.toString());
  }

  @Test
  public void testForShootWhenNoArrows() {
    StringReader input = new StringReader("S 1 S S 1 W S 1 E S 1 E");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot an arrow into the darkness\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot an arrow into the darkness\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Player has no arrows\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "You are out of arrows, explore to find more\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Player has no arrows\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForQuit() {
    StringReader input = new StringReader("S 1 S q");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot an arrow into the darkness\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "Game Quit!", gameLog.toString());
  }

  @Test
  public void testForCommandAfterGameQuit() {
    StringReader input = new StringReader("S 1 S q M N");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot an arrow into the darkness\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "Game Quit!", gameLog.toString());
  }


  @Test
  public void testForCommandAfterGameEnds() {
    StringReader input = new StringReader("M E P A");
    Appendable gameLog = new StringBuilder();

    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);

    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Otyugh in cave! Chomp, chomp, chomp, player got eaten by an Otyugh!\n"
            + "Better luck next time\n", gameLog.toString());
  }

  @Test
  public void testMoveAllDirection() {
    StringReader input = new StringReader("S 1 E S 1 E M E M W M S M N");
    Appendable gameLog = new StringBuilder();
    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster is injured\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 2\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "No. of caves (1-5)?\n"
            + "Where to?\n"
            + "\n"
            + "Player shot the monster, monster has been killed\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 0, 2\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 2\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 0, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 0, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 1\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

  @Test
  public void testForPlayerDiedByFallingIntoPit() {
    StringReader input = new StringReader("M W");
    Appendable gameLog = new StringBuilder();
    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Pit in the cave! Ohhh nooo, player died by falling into the pit\n", gameLog.toString());
  }

  @Test
  public void testForThiefStoleAllTreasure() {
    StringReader input = new StringReader("P T M S M W");
    Appendable gameLog = new StringBuilder();
    commandController = new CommandController(input, gameLog);
    commandController.play(dungeon);
    assertEquals("\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "What? A/T\n"
            + "Treasure picked up\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 0, 1\n"
            + "Treasure: \n"
            + " DIAMOND: 1\n"
            + " RUBY: 1\n"
            + " SAPPHIRE: 1\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: HIGH_PUNGENT\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Player moved successfully to location 1, 1\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 1\n"
            + "Treasure: \n"
            + " DIAMOND: 1\n"
            + " RUBY: 1\n"
            + " SAPPHIRE: 1\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "The cave has no treasures\n"
            + "There are no arrows at the current location\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "West\n"
            + "Smell: LESS_PUNGENT\n"
            + "Soil Quality: POROUS\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n"
            + "\n"
            + "Please select your next move. Enter N: North, Enter S: South, Enter E: East, Enter W: West\n"
            + "\n"
            + "Thief in the cave.\n"
            + "Thief stole all treasures of the player, player has no treasure\n"
            + "Player moved successfully to location 1, 0\n"
            + "\n"
            + "\n"
            + " - Player Description - \n"
            + "\n"
            + "Current Location: CAVE: 1, 0\n"
            + "Player has no treasure\n"
            + "Arrows: 3\n"
            + " - Location Description - \n"
            + "\n"
            + "Treasures:\n"
            + "Diamond\n"
            + "Ruby\n"
            + "Sapphire\n"
            + "Arrows: 3\n"
            + "Next possible moves: \n"
            + "North\n"
            + "South\n"
            + "East\n"
            + "Smell: ODOURLESS\n"
            + "Soil Quality: DENSE\n"
            + "What do you want to do? Move, Pickup, Shoot, Quit (M-P-S-Q)?\n", gameLog.toString());
  }

//  @Test
//  public void testForHandToHandCombatWithMovingMonster() {
//    StringReader input = new StringReader("S 1 E S 1 E M E M E");
//    Appendable gameLog = new StringBuilder();
//    commandController = new CommandController(input, gameLog);
//    Dungeon dungeon = new DungeonImpl(5, 4, 2, 20,
//            false, 2, new FixedRandomizer(3));
//    System.out.println(dungeon);
//    commandController.play(dungeon);
//    System.out.println(dungeon);
//    assertEquals("", gameLog.toString());
//  }

  //Test using mock view
  @Test
  public void testForMockMove() {
    StringBuilder viewLog = new StringBuilder();
    IView mock = new MockView(viewLog);
    IDungeonController controller = new CommandController(mock, dungeon);
    controller.go();
    controller.movePlayerOnKeyPress("S");
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Called key listener function\n"
            + "Called action listener function\n"
            + "Description: \n"
            + "Player moved successfully to location 1, 1\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", viewLog.toString());
  }

  @Test
  public void testForMockMoveInWrongDirection() {
    StringBuilder viewLog = new StringBuilder();
    IView mock = new MockView(viewLog);
    IDungeonController controller = new CommandController(mock, dungeon);
    controller.go();
    controller.movePlayerOnKeyPress("N");
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Called key listener function\n"
            + "Called action listener function\n"
            + "Description: \n"
            + "Move N not possible \n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", viewLog.toString());
  }


  @Test
  public void testForMockShoot() {
    StringBuilder viewLog = new StringBuilder();
    IView mock = new MockView(viewLog);
    IDungeonController controller = new CommandController(mock, dungeon);
    controller.go();
    controller.movePlayerOnKeyPress("N");
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Called key listener function\n"
            + "Called action listener function\n"
            + "Description: \n"
            + "Move N not possible \n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", viewLog.toString());
  }

  @Test
  public void testForMockPickArrow() {
    StringBuilder viewLog = new StringBuilder();
    IView mock = new MockView(viewLog);
    IDungeonController controller = new CommandController(mock, dungeon);
    controller.go();
    controller.pickArrowOnKeyPress();
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Called key listener function\n"
            + "Called action listener function\n"
            + "Description: Arrow picked up\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", viewLog.toString());
  }

  @Test
  public void testForMockPickTreasure() {
    StringBuilder viewLog = new StringBuilder();
    IView mock = new MockView(viewLog);
    IDungeonController controller = new CommandController(mock, dungeon);
    controller.go();
    controller.pickTreasureOnKeyPress();
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Called key listener function\n"
            + "Called action listener function\n"
            + "Description: Treasure picked up\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", viewLog.toString());
  }

  //Test using mock model
  @Test
  public void testPickArrowUsingMockModel() {
    StringBuilder modelLog = new StringBuilder();

    Dungeon mockModel = new MockModel(modelLog);
    IView mockView = new MockView(modelLog);

    IDungeonController controller = new CommandController(mockView, mockModel);
    controller.pickArrowOnKeyPress();
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Calling pick arrow method to pick arrows from players current location\n"
            + "Description: Arrow picked up\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", modelLog.toString());
  }

  @Test
  public void testPickTreasureUsingMockModel() {
    StringBuilder modelLog = new StringBuilder();

    Dungeon mockModel = new MockModel(modelLog);
    IView mockView = new MockView(modelLog);

    IDungeonController controller = new CommandController(mockView, mockModel);
    controller.pickTreasureOnKeyPress();
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Calling pick treasure method to pick treasures from players current location\n"
            + "Description: Treasure pickedup\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", modelLog.toString());
  }

  @Test
  public void testShootArrowUsingMockModel() {
    StringBuilder modelLog = new StringBuilder();

    Dungeon mockModel = new MockModel(modelLog);
    IView mockView = new MockView(modelLog);

    IDungeonController controller = new CommandController(mockView, mockModel);
    controller.shootOtyughOnKeyPress(1, "N");
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Calling shoot arrow method, to shoot the monster. \n"
            + "The direction provided for shoot is N and the distance is 1\n"
            + "Description: Shoot arrow successful\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", modelLog.toString());
  }

  @Test
  public void testNextMoveUsingMockModel() {
    StringBuilder modelLog = new StringBuilder();

    Dungeon mockModel = new MockModel(modelLog);
    IView mockView = new MockView(modelLog);

    IDungeonController controller = new CommandController(mockView, mockModel);
    controller.movePlayerOnKeyPress("N");
    assertEquals("\n"
            + "Called action listener function\n"
            + "Called make visible method\n"
            + "Calling Next Move method, direction provided is -> N\n"
            + "Description: Move successful\n"
            + "Give Next move description\n"
            + "Give location description\n"
            + " Calling render dungeon", modelLog.toString());
  }
}