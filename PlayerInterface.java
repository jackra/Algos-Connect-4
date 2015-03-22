/**
 * @author Deepak Rohan Sekar, Ganesh Ramamoorthy
 * @version 1.0
 * @date March 21, 2014
 * interface PlayerInterface
 * Here we have three methods go, setMove and getPlayerType 
 * All these methods are implemented in the ComputerPlayer and 
 * HumanPlayer class.
 */
public interface PlayerInterface {
	/*
	 * go is used to get the list of moves and parse it 
	 * and make a move on the board b
	 * @param b
	 */
	public void go(PlayBoard b);
	/*
	 * setMove is used to actually to get the position of the move
	 * in the case of computer, there is no implementation of setMove
	 * However in the case of user it gets the column where the disc
	 * has to be dropped on the board
	 * @param col
	 */
	public void setMove(int col);
	/*
	 * It just returns who plays next on the board both the HumanPlayer
	 * class and ComputerPlayer implements this interface so that they
	 * know who goes for the next move.
	 */
	public int getPlayerType();
}
