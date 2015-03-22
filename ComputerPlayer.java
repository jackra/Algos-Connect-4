/**
 * @author Deepak Rohan Sekar, Ganesh Ramamoorthy
 * @version 1.0
 * @date March 21, 2014 
 */
import java.util.*;
/**
 * Class ComputerPlayer
 * This is used to implement the way the computer player makes
 * moves on the board. For better design pattern we extended the
 * interface Player. Player interface is used by both ComputerPlayer
 * and HumanPlayer. The ComputerPlayer uses MinMax algorithm to decide
 * the best move at each point.  
 */

public class ComputerPlayer implements PlayerInterface {
	// private int[] location;
	private State current_state;
	private int lookahead = 4;
/**
 * Initialization of the Computer player using the MinMax player
 * If there is a disc already in the place where the MinMax generates output
 * We have used a Math Random function as a back up
 * @param s
 * @return
 */
	private int minMaxState(State s) {
		// System.out.println("MiniMax started...");
		int v = 0;
		// System.out.println(s.next_player);
		if (s.next_player == 1)
			v = maximumValue(s);
		else
			v = minimumValue(s);
		// System.out.print("MiniMax Search Completed. H:");
		// System.out.println(v);

		// System.out.println("MiniMax finished.");

		for (int i = 0; i < s.suc.size(); i++) {
			State cell = (State) s.suc.get(i);
			if (cell.v == v)
				return cell.action;
		}
		/*
		 * Math Random if the MinMax algorithm is not able to find
		 * the position of the disc of the computer player
		 */
		// System.out.println("I don't know...");
		return (int) (Math.random() * 7);
	}
/**
 * It gets the list of moves list for the computerPlayer
 * NodeTest is called to check if the column is not full
 * If the column is not full it calls the placement so that
 * the computer player places the disc on the board
 * This is the maximum values of the MinMax tree
 * @param s
 * @return
 */
	private int maximumValue(State s) {
		// System.out.println("MaxValue get called! State: "+s.movelist);
		if (NodeTest(s)) {
			// System.out.println("MaxValue: Calling Utility");
			int u = Placement(s);
			// System.out.print("MaxValue: The utility of state "+s.movelist+" is ");
			// System.out.println(s.v);
			return u;
		}
		s.v = -500;
		Vector suc = s.suc;
		for (int i = 0; i < suc.size(); i++) {
			State cell = (State) suc.get(i);
			int temp = minimumValue(cell);
			if (temp > s.v) {
				s.v = temp;
			}
		}
		/*
		 * System.out.print("MaxValue: "); for (int i=0;i<suc.size(); i++){
		 * State cell=(State) suc.get(i);
		 * 
		 * System.out.print(cell.v); System.out.print(" "); }
		 * System.out.println();
		 */
		return s.v;
	}
/**
 * It gets the list of moves list for the computerPlayer
 * NodeTest is called to check if the column is not full
 * If the column is not full it calls the placement so that
 * the computer player places the disc on the board
 * This is the minimum values of the MinMax tree 
 * @param s
 * @return
 */
	private int minimumValue(State s) {
		// System.out.println("MinValue get called! State: "+s.movelist);
		if (NodeTest(s)) {
			// System.out.println("MinValue: Calling Utility");
			int u = Placement(s);
			// System.out.print("MinValue: The utility of state "+s.movelist+" is ");
			// System.out.println(s.v);
			return u;
		}
		s.v = 500;
		Vector suc = s.suc;
		for (int i = 0; i < suc.size(); i++) {
			State cell = (State) suc.get(i);
			int temp = maximumValue(cell);

			if (temp < s.v)
				s.v = temp;
		}
		/*
		 * System.out.print("MinValue: "); for (int i=0;i<suc.size(); i++){
		 * State cell=(State) suc.get(i); System.out.print(cell.v);
		 * System.out.print(" "); }
		 */
		// System.out.println("min of max: "+s.v );
		return s.v;
	}
/**
 * This method returns false if the board is full and true if the 
 * board is full based on this the move is calculated by the MinMax
 * @param s
 * @return
 */
	public boolean NodeTest(State s) {
		// System.out.println("Termininal Testing.  State:"+s.movelist);
		if ((s.depth == lookahead) || (s.over()))
			return true;
		return false;
	}
/**
 * Checks if the next player is the computer player and also makes sure
 * the game is not over using an if else condition. Then it analyzes the 
 * position of the computer discs and the opponents disc on the board
 * based on the position of the discs, the computer player calculates the 
 * position where the computer player has to place the disc on the board.
 * @param s
 * @return
 */
	public int Placement(State s) {
		// System.out.println("Utility of State "+s.movelist);
		int me = current_state.next_player;
		int opponent = 3 - me;
		if (s.over()) {
			if (s.winner == me) {
				s.v = 100;

				return s.v;
			}
			if (s.winner == opponent) {
				s.v = -100;
				return s.v;
			}
			if (s.winner == 0) {
				s.v = 0;
				return 0;
			}
		}
		int h = 0;
		int row = s.move_x;
		int col = s.move_y;
		/*
		 * List of moves, this checks where the discs of the computer and 
		 * Opponent are placed on the board based on the analysis it comes
		 * up with a maximum probability move if the disc placed there than
		 * the minimumValue for the tree is calculated. This is similar to 
		 * MinMax tree. Various directions of calculations are listed below.
		 */

		if ((col >= 3) && (s.location[row][col - 1] == me)
				&& (s.location[row][col - 2] == me)
				&& (s.location[row][col - 3] == me))
			h = h + 16;
		// right direction
		if ((col <= 3) && (s.location[row][col + 1] == me)
				&& (s.location[row][col + 2] == me)
				&& (s.location[row][col + 3] == me))
			h = h + 16;
		// y direction
		if ((row <= 2) && (s.location[row + 1][col] == me)
				&& (s.location[row + 2][col] == me)
				&& (s.location[row + 3][col] == me))
			h = h + 16;
		//  left diagonal
		if ((col >= 3) && (row <= 2) && (s.location[row + 1][col - 1] == me)
				&& (s.location[row + 2][col - 2] == me)
				&& (s.location[row + 3][col - 3] == me))
			h = h + 16;

		if ((col <= 3) && (row <= 2) && (s.location[row + 1][col + 1] == me)
				&& (s.location[row + 2][col + 2] == me)
				&& (s.location[row + 3][col + 3] == me))
			h = h + 16;

		if ((col >= 3) && (row >= 3) && (s.location[row - 1][col - 1] == me)
				&& (s.location[row - 2][col - 2] == me)
				&& (s.location[row - 3][col - 3] == me))
			h = h + 16;

		if ((col <= 3) && (row >= 3) && (s.location[row - 1][col + 1] == me)
				&& (s.location[row - 2][col + 2] == me)
				&& (s.location[row - 3][col + 3] == me))
			h = h + 16;

		if ((col >= 2) && (s.location[row][col - 1] == me)
				&& (s.location[row][col - 2] == me))
			h = h + 4;
		// right direction
		if ((col <= 4) && (s.location[row][col + 1] == me)
				&& (s.location[row][col + 2] == me))
			h = h + 4;
		// y direction
		if ((row <= 3) && (s.location[row + 1][col] == me)
				&& (s.location[row + 2][col] == me))
			h = h + 4;
		// left diagonal
		if ((col >= 2) && (row <= 3) && (s.location[row + 1][col - 1] == me)
				&& (s.location[row + 2][col - 2] == me))
			h = h + 4;

		if ((col <= 4) && (row <= 3) && (s.location[row + 1][col + 1] == me)
				&& (s.location[row + 2][col + 2] == me))
			h = h + 4;

		if ((col >= 2) && (row >= 2) && (s.location[row - 1][col - 1] == me)
				&& (s.location[row - 2][col - 2] == me))
			h = h + 4;

		if ((col <= 4) && (row >= 2) && (s.location[row - 1][col + 1] == me)
				&& (s.location[row - 2][col + 2] == me))
			h = h + 4;

		if ((col >= 1) && (s.location[row][col - 1] == me))
			h = h + 2;
		// right direction

		if ((col <= 5) && (s.location[row][col + 1] == me))
			h = h + 2;
		//  y direction
		if ((row <= 4) && (s.location[row + 1][col] == me))
			h = h + 2;
		//  left diagonal
		if ((col >= 1) && (row <= 4) && (s.location[row + 1][col - 1] == me))
			h = h + 2;

		if ((col <= 5) && (row <= 4) && (s.location[row + 1][col + 1] == me))
			h = h + 2;

		if ((col >= 1) && (row >= 1) && (s.location[row - 1][col - 1] == me))
			h = h + 2;

		if ((col <= 5) && (row >= 1) && (s.location[row - 1][col + 1] == me))
			h = h + 2;

		//  x direction.
		// left
		if ((col >= 3) && (s.location[row][col - 1] == opponent)
				&& (s.location[row][col - 2] == opponent)
				&& (s.location[row][col - 3] == opponent))
			h = h + 8;
		// right direction
		if ((col <= 3) && (s.location[row][col + 1] == opponent)
				&& (s.location[row][col + 2] == opponent)
				&& (s.location[row][col + 3] == opponent))
			h = h + 8;
		//  y direction
		if ((row <= 2) && (s.location[row + 1][col] == opponent)
				&& (s.location[row + 2][col] == opponent)
				&& (s.location[row + 3][col] == opponent))
			h = h + 8;
		//  left diagonal
		if ((col >= 3) && (row <= 2)
				&& (s.location[row + 1][col - 1] == opponent)
				&& (s.location[row + 2][col - 2] == opponent)
				&& (s.location[row + 3][col - 3] == opponent))
			h = h + 8;

		if ((col <= 3) && (row <= 2)
				&& (s.location[row + 1][col + 1] == opponent)
				&& (s.location[row + 2][col + 2] == opponent)
				&& (s.location[row + 3][col + 3] == opponent))
			h = h + 8;

		if ((col >= 3) && (row >= 3)
				&& (s.location[row - 1][col - 1] == opponent)
				&& (s.location[row - 2][col - 2] == opponent)
				&& (s.location[row - 3][col - 3] == opponent))
			h = h + 8;

		if ((col <= 3) && (row >= 3)
				&& (s.location[row - 1][col + 1] == opponent)
				&& (s.location[row - 2][col + 2] == opponent)
				&& (s.location[row - 3][col + 3] == opponent))
			h = h + 8;

		if ((col >= 2) && (s.location[row][col - 1] == opponent)
				&& (s.location[row][col - 2] == opponent))
			h = h + 4;
		// right direction
		if ((col <= 4) && (s.location[row][col + 1] == opponent)
				&& (s.location[row][col + 2] == opponent))
			h = h + 4;
		//  y direction
		if ((row <= 3) && (s.location[row + 1][col] == opponent)
				&& (s.location[row + 2][col] == opponent))
			h = h + 4;
		// left diagonal
		if ((col >= 2) && (row <= 3)
				&& (s.location[row + 1][col - 1] == opponent)
				&& (s.location[row + 2][col - 2] == opponent))
			h = h + 4;

		if ((col <= 4) && (row <= 3)
				&& (s.location[row + 1][col + 1] == opponent)
				&& (s.location[row + 2][col + 2] == opponent))
			h = h + 4;

		if ((col >= 2) && (row >= 2)
				&& (s.location[row - 1][col - 1] == opponent)
				&& (s.location[row - 2][col - 2] == opponent))
			h = h + 4;

		if ((col <= 4) && (row >= 2)
				&& (s.location[row - 1][col + 1] == opponent)
				&& (s.location[row - 2][col + 2] == opponent))
			h = h + 4;

		if ((col >= 1) && (s.location[row][col - 1] == opponent))
			h = h + 2;
		// right direction

		if ((col <= 5) && (s.location[row][col + 1] == opponent))
			h = h + 2;
		//  y direction
		if ((row <= 4) && (s.location[row + 1][col] == opponent))
			h = h + 2;
		//  left diagonal
		if ((col >= 1) && (row <= 4)
				&& (s.location[row + 1][col - 1] == opponent))
			h = h + 2;

		if ((col <= 5) && (row <= 4)
				&& (s.location[row + 1][col + 1] == opponent))
			h = h + 2;

		if ((col >= 1) && (row >= 1)
				&& (s.location[row - 1][col - 1] == opponent))
			h = h + 2;

		if ((col <= 5) && (row >= 1)
				&& (s.location[row - 1][col + 1] == opponent))
			h = h + 2;

		s.v = h;
		return h;
	}

	/** 
	 * Instantiation of the new computer player 
	 */
	public ComputerPlayer() {
		System.out.println("ComputerPlayer Initialized.");
		current_state = new State();
	}

	public int getPlayerType() {
		return 2;
	}
	/**
	 * It gets the list of moves and then parses 
	 * here the m initializes the MinMax part and than this comes
	 * up the Maximum and Minimum probability positions of the computer
	 * Maximum move is given higher priority on the board
	 * 
	 * @param b
	 */
	public void go(PlayBoard b) {
		State current_state = new State();

		current_state.ParseMove(b.listOfMoves);
		// System.out.println("MinMaxPlayer thinking...");
		createSuccessor(current_state);
		int m = minMaxState(current_state);
		// System.out.println(m);
		b.Move(m);

	}
	/**
	 * This is a part of implementing the interface, the move for the 
	 * computer player is calculated using the MinMax algorithm so the
	 * below method doesn't return any value.
	 * However in the case of human player it returns the value of the 
	 * column where the disc has to be dropped.
	 */
	public void setMove(int col) {
	}

	public void display_tree() {

	}

	private void createSuccessor(State s) {
		// System.out.print("Generate Successors down to level ");
		// System.out.println(lookahead);
		Vector chd = new Vector();
		for (int i = 0; i < 7; i++) {
			State temp = new State();
			temp.ParseMove(s.listOfMoves);
			if (temp.col[i] != 6) {
				temp.Move(i);
				temp.depth = s.depth + 1;
				temp.action = i;
				temp.out = false;
				chd.add(temp);
				if (temp.depth < lookahead)
					createSuccessor(temp);
			}
		}
		s.suc = chd;
	}

}

class State extends PlayBoard {
	public int v = -1;
	public int depth = 0;
	public int action = 0;
	public boolean out = false;
	public Vector suc = new Vector();

	State() {
		action = -1;
		v = 0;
		depth = 0;
		clear();
		suc = new Vector();
		out = false;
	}

}
