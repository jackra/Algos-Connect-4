/**
 * @author Ganesh Ramamoorthy, Deepak Rohan Sekar
 * @version 1.0
 * @date March 21, 2014
 */
public class PlayBoard {
	public int[][] location;
	public int next_player;
	public int[] col;
	public int move_x = 0;
	public int move_y = 0;
	public int winner = 0;
	public boolean out = true;
	public String listOfMoves;
	ConnectFour ru = new ConnectFour(); //Instantiated in order to show the error pop ups

	/** Creates a new instance of SimpleBoard */
	public PlayBoard() {
		next_player = 1;
		location = new int[6][7];
		col = new int[7];
		winner = 0;
		clear();
		listOfMoves = "";
		out = true;
	}
	/**
	 * Parses each move from the String move_lust
	 * @param move_list
	 */
	public void ParseMove(String move_list) {
		for (int i = 0; i < move_list.length(); i++) {
			int tm = Integer.parseInt((new Character(move_list.charAt(i)))
					.toString());
			Move(tm);
		}
	}
	/**
	 * it validates the move if the position is < 0 or >6 then the move is 
	 * considered to be invalid. if the column is full then the move is considered 
	 * to be invalid.
	 * @param pos
	 */
	public void Move(int pos) {
		try {
			if ((pos < 0) || (pos > 6))
				System.out.println("invalid input\n\n");
			else {
				if ((col[pos] == 6) && out)
					System.out.println("Column full");
				else {
					move_y = pos;
					listOfMoves += (new Integer(pos)).toString();
					move_x = 5 - col[pos];
					col[pos]++;
					location[move_x][move_y] = next_player;
					next_player = 3 - next_player;
				}
			}
			/*
			 * Column is full it will trigger the pop up declared in the 
			 * ConnectFour class.
			 */
		} catch (Exception e) {
			ru.errorColumn();
		}
	}

	public int[][] view() {
		return location;
	}

	public int[] viewcol() {
		return col;
	}

	public void clear() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++) {
				location[i][j] = 0;
			}
		for (int j = 0; j < 7; j++)
			col[j] = 0;
	}

	public int next() {
		return next_player;
	}

	public int[] ret_col() {
		return col;
	}
	/**
	 * To check if  the game is over
	 * @return
	 */
	public boolean over() {
		String line_x = "";
		String line_y = "";
		try {
			String line_ld = (new Integer(location[move_x][move_y])).toString();
			String line_rd = (new Integer(location[move_x][move_y])).toString();
			String s = (new Integer(3 - next_player)).toString();
			String sub = s + s + s + s;
			String match = "[012]*" + sub + "[012]*";

			for (int i = 0; i < 7; i++) {
				int cell = location[move_x][i];
				line_x += (new Integer(cell)).toString();
			}
			for (int i = 0; i < 6; i++) {
				int cell = location[i][move_y];
				line_y += (new Integer(cell)).toString();
			}
			int tempx = move_x;
			int tempy = move_y;
			while ((tempx > 0) && (tempy > 0)) {
				tempx--;
				tempy--;
				line_ld = (new Integer(location[tempx][tempy])).toString()
						+ line_ld;
			}
			tempx = move_x;
			tempy = move_y;
			while ((tempx < 5) && (tempy < 6)) {
				tempx++;
				tempy++;
				line_ld = line_ld
						+ (new Integer(location[tempx][tempy])).toString();
			}
			tempx = move_x;
			tempy = move_y;
			while ((tempx > 0) && (tempy < 6)) {
				tempx--;
				tempy++;
				line_rd = (new Integer(location[tempx][tempy])).toString()
						+ line_rd;
			}
			tempx = move_x;
			tempy = move_y;
			while ((tempx < 5) && (tempy > 0)) {
				tempx++;
				tempy--;
				line_rd = line_rd
						+ (new Integer(location[tempx][tempy])).toString();
			}
			if ((line_x.matches(match)) || (line_y.matches(match))
					|| (line_ld.matches(match)) || (line_rd.matches(match))) {
				winner = 3 - next_player;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid Move Column is full");
		}
		int z = 0;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				if (location[i][j] == 0)
					z = 1;

		if (z == 0) {
			return true;
		}
		return false;
	}

	public String toString() {
		String ret = "   0 1 2 3 4 5 6\n";
		for (int i = 0; i < 6; i++) {
			ret += (new Integer(i)).toString() + ": ";
			for (int j = 0; j < 7; j++) {
				ret += (new Integer(location[i][j])).toString();
				ret += " ";
			}
			ret += "\n";
		}
		return ret;
	}
}
