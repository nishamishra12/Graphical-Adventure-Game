package dungeon;

import java.io.InputStreamReader;

import controller.CommandController;
import controller.IDungeonController;
import controller.KeyBoardListener;
import randomizer.ActualRandomizer;
import randomizer.FixedRandomizer;
import view.DungeonView;
import view.IView;

/**
 * This a DungeonMain class which acts like a user input for the project.
 */
public class DungeonMain {

  /**
   * This is a main class which will be used to start the DungeonMain class.
   *
   * @param args Args can be provided as any
   */
  public static void main(String[] args) {

    Dungeon dungeon;

    if(args.length == 0) {

      dungeon = new DungeonImpl(5, 7, 2, 25, false,
              5, 1, 1, 1, new ActualRandomizer());

      IView view = new DungeonView(dungeon);
      IDungeonController controller = new CommandController(view, dungeon);
      controller.go();
    } else {
      try {
        if (args.length < 5) {
          throw new IllegalArgumentException("Invalid command line arguments given. "
                  + "Please provide correct arguments");
        }
        int rows = Integer.parseInt(args[0]);
        int cols = Integer.parseInt(args[1]);
        int interConnectivity = Integer.parseInt(args[2]);
        int treasurePercent = Integer.parseInt(args[3]);
        boolean wrapping = Boolean.parseBoolean(args[4]);
        int monsterCount = Integer.parseInt(args[5]);
        int thiefCount = Integer.parseInt(args[6]);
        int pitCount = Integer.parseInt(args[7]);
        int movingMonsterCount = Integer.parseInt(args[8]);

        dungeon = new DungeonImpl(rows, cols, interConnectivity, treasurePercent, wrapping,
                monsterCount, thiefCount, pitCount, movingMonsterCount, new ActualRandomizer());

        System.out.println("*********** Dungeon Created ***************");
        System.out.println(dungeon);
        System.out.println("*************** Game Begins ***************");
        System.out.println("Start Cave: " + dungeon.getStartCave().getId());
        System.out.println("End Cave: " + dungeon.getEndCave().getId());

        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;

        new CommandController(input, output).play(dungeon);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
