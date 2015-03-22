@author: Deepak Rohan Sekar, Ganesh Ramamoorthy
@date: 21 March, 2014
@version: 1.0

Overview
--------

This is a program, which has been designed with an GUI to run the most
popular game Max Connect 4. For the working of the game refer to the 
GamePlan document in the folder. To be brief Connect four disc either 
horizantally, vertically or diagonally and earn a point.

Requirements
------------

We have used java 1.8.x version to run and design the game. However
the game is compatible to work with 1.7.x version of Java

Platform: java
Compiler: java compiler

Version Details:

java version "1.8.0_40-b27"
javac javac 1.8.0_40

Installation
------------

Once java is added to the path variable run the below commands

The game can be run in two ways. One can either run the bash or batch
files in the folder game.sh and game.bat based on the operating systems
The execution of the bash and batch script will trigger the java compilation
and running the java class. 

$ ./game.sh

The other way around is to run the Java file "ConnectFour.java" as such 
from the command window using the below commands.

$ javac connectFour.java

$ java connectFour

Once the program starts to run it should generate GUI(JPanel) from which
you have several options like selecting a new game selecting color of the 
player, generating a report, view the instructions and a status panel which
keeps track of the scores as well as the last move. 
Once the board is full you will have the winner declared and the scores 
updated. A report will be generated after the completion of the game in
the same folder.

Implementation
--------------

The game of connect four has been implemented using the MinMax algorithm. The
computer player uses the MinMax to calculate the winning move(Max) if not the 
to stop us from winning and the alternate would be placing it on some place on 
the board(Min). The ConnecFour uses the JPanel to provide the GUI. All the options
and menu have been updated so that you can create three types of games
	1. Human Vs Human
	2. Computer(first) Vs Human
	3. Human(first) Vs Computer
In any of the above cases red player will always be first and that means green
will be always be second. 

Finding the Winner
------------------

In the program we have implemented the pattern recognition technique, which will
identify the patterns in the horizantal, vertical and diagonal directions. After 
each move we have called this method so that we have the updated scores on the 
status panel as well as in the generated report.

Breakdown of files
------------------
├── ComputerPlayer.java
|			Class:
|				ComputerPlayer
|				State
|			
|			Methods:
|				minMaxState
|				maximumValue
|				minimumValue
|				NodeTest
|				Placement
|				ComputerPlayer
|				getPlayerType
|				go
|				setMove
|				createSuccessor
|				State(Constructor)
|				
├── ConnectFour.java
|			Class:
|				ConnectFour
|			Methods:
|				createMenus
|				createGameBoard
|				designNewGame	
|				createStatusPanel
|				colorRed
|				colorGreen
|				redrawGameBoard
|				gameOverPanelUpdate
|				playGame
|				createButtons
|				actionPerformed
|				itemStateChanged
|				theEndGame
|				checkPlayerScore
|				softwareVersionDetails
|				howToPlay
|				errorColumn
|				generateReport
|				main		
├── DocumentStream.java
|			Class:
|				DocumentStream
|			Methods:
|				DocumentStream
|				write
├── HumanPlayer.java
|			Class:
|				HumanPlayer
|			Methods:
|				setMove
|				HumanPlayer
|				getPlayerType
|				go
├── PlayBoard.java
|			Class:
|				PlayBoard
|			Methods:
|				ParseMove
|				Move
|				view
|				viewcol
|				clear
|				next
|				ret_col
├── PlayerInterface.java
|			Methods:
|				PlayerInterface
|				go
|				setMove
|				getPlayerType
├── game.bat
├── game.sh
├── images
│   ├── Board.jpg
│   ├── Red.jpg
│   └── green.jpg
└── report.txt
			
What goes well
--------------

Has a GUI which has been designed using the JPanel. Once the board is filled up
you can always view the report, which will show you the detailed board view at each
step so that you can analyze your moves.

Limitations
--------------

The report.txt which lists the detailed view of all steps can be best viewed using 
NotePad++, Sublime2, TextMate or Eclipse inbuilt editor. Notepad view is not so good.



References
----------

T. Cormen, C Leiserson, R. Rivest, C. Stein: Introduction to Algorithms, Third Edition
