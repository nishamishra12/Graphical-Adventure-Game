# Readme Documentation - Graphical Adventure Game

## About/Overview
The game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them.

The project creates a dungeon which will allow a player to enter in to the dungeon, move from one
location to another location via any of the possible directions - North, South, East, and West.
Pickup treasure or arrows from the cave if they are present. The dungeon also consists of Otyugh
which are type of Monster, the player can slay or kill the Otyugh.
The dungeon also has other creatures like thief who can steal all treasures from the player, pit - in which the player could fall and die, or moving monster which roams around the dungeon and when encountered needs to be battled.
The dungeon created can be wrapped or unwrapped along with different degree of interconnectivity. The player can lose if eaten by an Otyugh, or if the player falls into the cave, or looses in hand-to-hand combat with the moving monster. 
The player wins when player reaches the destination.

## List of Features
1. The dungeon starts by taking rows, columns, treasure percentage, wrapping status, number of monsters, and interconnectivity, no of thieves, no of pits, no of moving monsters of the dungeon.
2. Treasures and arrows are randomly added to specific percentage of the caves.
3. Dungeon can wrap or not from one side to another.
4. A new dungeon is created randomly every time resulting in a different network each time the game begins.
5. Every location in the dungeon is connected to every other location. Increasing the degree of inter-connectivity increases number of paths from one location to another.
6. Player can move from one location to another in any of the 4 directions - North, South, East, West.
7. Player can decide to pick treasure or arrows located in the dungeon.
8. Player has an initial full health which is decreased when fighting with the moving monster.
9. A list is maintained with the player of all the treasures.
10. A count is maintained with the player of all the arrows collected.
11. Otyughs are present in the dungeon, and they have health which is reduced when the Otyugh gets shot.
12. While moving in the dungeon, player can decide to slay a monster.
13. If monster is present in the cave and player moves to that cave, the player gets eaten by the Otyugh.
14. If the Otyugh is injured and player moves to that cave, the player has 50% chance to excape alive.
15. The moving monsters roam around the dungeon, and whenever encountered with the player, they are supposed to be fought in a hand-to-hand combat.
16. The dungeon has pits, pits can be sensed by the quality of sand in the dungeon that when a pit is nearby, falling into the pit will lead to death of the player.
17. There are thieves in the dungeon, the thief has the ability to steal all the treasures of the player.

## How to Run
1. Open Terminal
2. Navigate to the location where you have stored the jar file.
3. You need to provide the below-mentioned parameters as command line arguments in the order:
    1. number of rows in the dungeon 2D matrix
    2. number of columns in the dungeon 2D matrix
    3. The degree of interconnectivity
    4. The percentage of caves which should contain treasure
    5. Boolean value showing if the dungeon is wrapping or non-wrapping
        1. Wrapping Dungeon : true
        2. Non-wrapping Dungeon : false
    6. The number of monsters that should be present in the dungeon
    7. The number of thieves that should be present in the dungeon
    8. The number of pits that should be present in the dungeon
    9. The number of moving monsters that should be present in the dungeon
4. Run the jar file using the
   command java - jar Adventure_Game.jar rows cols interConnectivity treasurePercent wrapping monsterCount thiefCount pitCount movingMonsterCount

## How to use the program
Text Based
1. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, wrapping status, monster count, thiefCount, pitCount, movingMonsterCount.
2. Dumping the dungeon
3. Displaying the description of the player, which includes the current location the player is in and the treasures and arrows that it holds.
4. Displaying the location description of the current cave the player is in.
5. Displaying the next possible moves the player can make from the current location.
6. Displaying the smell the treasure can sense.
7. Enter whether the player wants to Move, Shoot, or Pick
8. If Move, enter the direction in which to move
9. If Pick, enter whether to pickup Treasure or Arrow
10. If Shoot, enter the distance and direction in which to shoot
11. The player has to continue giving M, P, S in each iteration till the player reaches the end or the player gets eaten by the Otyugh.
12. The program will terminate if incorrect values is given in the command line.
GUI
13. A UI swing view will open up, the view will be blank initially.
14. Click on 'Create Dungeon' from the JMenu Bar to create a new dungeon.
15. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, wrapping status, monster count, thiefCount, pitCount, movingMonsterCount in the dialog box.
16. A new dungeon will be created; only the parts visited by the player will be shown on the view.
17. Move using any of the arrow keys (Up-North, Down-South, Left-West, Right-East) or by clicking on the neighboring cells of the players current location. Move can also be performed using respective buttons on the screen.
18. Press 'A' for Picking up arrows and 'T' for picking up treasures, same action can be performed using the buttons
19. Press 'S' to shoot, then enter the distance, and later the direction of shoot. Shoot can also be performed by the buttons - select shoot dircetion and distance and press the 'Shoot Arrow' button.
20. Players description at any point can be seen by clicking 'Player Description' button.
21. The location description and next possible directions are shown on the right hand side panel.
22. For restarting the game go to - Game Options -> 'Restart Game'
23. For quiting the game go to - Game Options -> 'Quit Game'

## Design/Model Changes
1. A GUI view has been added with multiple panels like dungeon grid view panel, setting panels, header panel, foot panel etc.
2. Button listener and key listener events are configured which were not there in the text based version.
3. The dungeon now has the capability to hold thief, moving monster, and pit. Initially only a single type of creature -Otyugh was present.
4. Otyugh was a single class in the old design, for new design since moving monster was also added enumeration for monster type, abstract class for monsters -  which both Otyugh and Moving Monster extends is added. Additionally, a monster factory class to provide the different monster object is implemented.
5. A general factory pattern class is also added which is common to the entire dungeon, which helps in creating various object and reduces repetitive code.

## Assumptions
1. Each cave can have upto three treasures of one type each
2. Each cave can have upto three arrows
3. Treasure percentage is an integer
4. The no of thieves that can be added is 1, 2
5. The no of pits that can be added is 1, 2
6. The no of moving monsters that can be added is 1, 2
7. Player dies by falling into the pit
8. When player encounters thief, all the treasures the player has collected gets stolen
9. When player encounters a moving monster, a random hand-to-hand battle takes place without the need of user entering the value, the out of which is either the player is dead or the moving monster.

## Limitations
1. The player will pick up all the treasure at once from a location
2. The player will pick up all the arrows at once from a location

## Citations
1. https://www.baeldung.com/java-spanning-trees-kruskal
2. https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
3. https://northeastern.instructure.com/courses/90366/pages/8-dot-6-command-design-pattern?module_item_id=6535611
4. https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm
5. https://northeastern.instructure.com/courses/90366/files/10995936?wrap=1
6. https://northeastern.instructure.com/courses/90366/pages/
7. https://www.techiedelight.com/kruskals-algorithm-for-finding-minimum-spanning-tree/
8. https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
9. https://piazza.com/class/kt0jcw0x7h955a?cid=1500
10. https://northeastern.instructure.com/courses/90366/files/10995936?wrap=1

