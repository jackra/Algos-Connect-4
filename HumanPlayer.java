/**
 * @author Ganesh Ramamoorthy, Deepak Rohan Sekar
 * @version 1.0
 * @date March 21, 2014
 */
import java.io.*;
/**
 * The class HumanPlayer which inherits the interface Player
 * It just reads the input from the player and puts the disc
 * in the appropriate position. 
 */
public class HumanPlayer implements PlayerInterface {
	private int m = -1;
/**
 * Receives the input of the col so that the disc can put
 * into the corresponding column of the board
 * @param col
 */
	public void setMove(int col) {
		m = col;
	}

	/** Creates a new instance of HumanPlayer */
	public HumanPlayer() {
	}

	public int getPlayerType() {
		return 0;
	}
	/**
	 * The go method reads the input from the user using the Input Stream
	 * Parse it to integer and uses it to move on the GUI
	 * @param b
	 */
	public void go(PlayBoard b) {
		int n = 0;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = null;
		if (m == -2) {
			try {
				s = br.readLine();
			} catch (IOException ioe) {
				//Does happen if the IO isn't connected
			}
			//integer to get the input position
			n = Integer.parseInt(s);
		}

		if ((m != -1) && (m != -2)) {
			b.Move(m);
		} else {
			b.Move(n);
		}
	}

}
