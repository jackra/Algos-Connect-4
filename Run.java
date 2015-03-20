/**
 * @author Ron Adams (eclip5e@ccs.neu.edu)
 * @date Created November 28, 2003
 * @see ConnectFour
 * This class its the GUI application for the ConnectFour AI
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Run implements ActionListener, ItemListener {
	// create objects
	static int counter=0;
	static String playerColor="None";
	static SimpleBoard board = new SimpleBoard();
	static JFrame frameMainWindow;
	static JFrame frameWin;
	static JFrame frameErr;
	static JFrame frameCredits;

	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;

	private static Player p1 = new HumanPlayer();
	private static Player p2 = new HumanPlayer();
	private static int p1score = 0;
	private static int p2score = 0;
	private static int p1TypeFlag = 0;
	private static int p2TypeFlag = 0;
	static JPanel statusPanel = new JPanel();
	 static JLabel statusLabel1 = new JLabel("Moves so far :"+counter);
	 static JLabel statusLabel2 = new JLabel("Last Player :"+playerColor);
		
	 static JLabel statusLabel3 = new JLabel("   ( Red First )   ");
	 static JLabel statusLabel4 = new JLabel("          Scores"+p1score+"-"+p2score);
	 public JMenuBar createMenuBar() {
			JMenuBar menuBar;
			JMenu menu, submenu, subsubmenu;
			JMenuItem menuItem;
			JRadioButtonMenuItem rbMenuItem;
		//	int[][] boardView;

			// create and build first menu
			menuBar = new JMenuBar();
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			menuBar.add(menu);
			// add items to menu
			menuItem = new JMenuItem("New game", KeyEvent.VK_N);
			menuItem.setName("NewGame");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			menu.addSeparator();
			menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
			menuItem.setName("QuitGame");
			menuItem.addActionListener(this);
			menu.add(menuItem);

			// create and build second menu
			menu = new JMenu("Players");
			menu.setMnemonic(KeyEvent.VK_P);
			menuBar.add(menu);
			// add items to menu
			// Submenu one
			submenu = new JMenu("Red Player");
				ButtonGroup groupPlayers1 = new ButtonGroup();
				
				rbMenuItem = new JRadioButtonMenuItem("Human");
				if (p1.getType() == 0) rbMenuItem.setSelected(true);
				rbMenuItem.setName("P1Human");
				rbMenuItem.addItemListener(this);
				groupPlayers1.add(rbMenuItem);
				
				submenu.add(rbMenuItem);
				rbMenuItem = new JRadioButtonMenuItem("Computer");
				if (p1.getType() == 4) rbMenuItem.setSelected(true);
				rbMenuItem.setName("P1MinMax");
				rbMenuItem.addItemListener(this);
				groupPlayers1.add(rbMenuItem);
				submenu.add(rbMenuItem);
				menu.add(submenu);
	/*			
				submenu.add(rbMenuItem);
				subsubmenu = new JMenu("Computer");
					rbMenuItem = new JRadioButtonMenuItem("MinMax Player");
					if (p1.getType() == 4) rbMenuItem.setSelected(true);
					rbMenuItem.setName("P1MinMax");
					rbMenuItem.addItemListener(this);
					groupPlayers1.add(rbMenuItem);
					subsubmenu.add(rbMenuItem);
				submenu.add(subsubmenu);
			menu.add(submenu);*/
				
			// submenu 2
			submenu = new JMenu("Green Player");
				ButtonGroup groupPlayers2 = new ButtonGroup();
//				subsubmenu = new JMenu("Computer");
					rbMenuItem = new JRadioButtonMenuItem("Computer");
					if (p2.getType() == 4) rbMenuItem.setSelected(true);
					rbMenuItem.setName("P2MinMax");
					rbMenuItem.addItemListener(this);
					groupPlayers2.add(rbMenuItem);
					submenu.add(rbMenuItem);
					menu.add(submenu);
//					subsubmenu.add(rbMenuItem);
//				submenu.add(subsubmenu);
				rbMenuItem = new JRadioButtonMenuItem("Human");
				if (p1.getType() == 0) rbMenuItem.setSelected(true);
				rbMenuItem.setName("P2Human");
				rbMenuItem.setSelected(true);
				rbMenuItem.setMnemonic(KeyEvent.VK_H);
				groupPlayers2.add(rbMenuItem);
				rbMenuItem.addItemListener(this);
				submenu.add(rbMenuItem);
			menu.add(submenu);
			menuBar.add(Box.createHorizontalGlue());
			// create Help menu
			menu = new JMenu("Help");
			menuBar.add(menu);
			// add items to menu
			menuItem = new JMenuItem("Display");
			menuItem.setName("Display");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			menu.addSeparator();
			menuItem = new JMenuItem("Report");
			menuItem.setName("Report");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			menu.addSeparator();
			menuItem = new JMenuItem("About");
			menuItem.setName("About");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			return menuBar;
		}

	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 490));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Max Connect 4 Game"));
		ImageIcon imageBoard = new ImageIcon("images/Board.jpg");
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

		return layeredGameBoard;
	}

	private static String playerColor(String color) {
		
		if(color.equals("red"))
		{
			System.out.println("Last Move : Red(1)");
			return "red";
		}
		else if(color.equals("green")){
			System.out.println("Last Move : Green(2)");
			return "green";
		}
		else
		{
			System.out.println("Last Move : None");
		}
		
return null;		
	}
	
	public static void createNewGame() {
		board = new SimpleBoard();
                board.out=false;
                
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Max Connect Four");
		frameMainWindow.setBounds(100, 100, 400, 300);
		Run conFour = new Run();
		frameMainWindow.setJMenuBar( conFour.createMenuBar() );
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);
		// create the status bar panel and shove it down the bottom of the frame
		

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});

		// show window
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);
		if (p1TypeFlag == 1) {
			p1.go(board);
			redrawBoard();
		}

		if ( (p1TypeFlag == 1) && (p2TypeFlag == 1) ) {
                    panelBoardNumbers.setEnabled(false);
                    panelBoardNumbers.repaint();
			while (!board.over() ){
			   if (board.next() == 1) p1.go(board);
			   else p2.go(board);
			   redrawBoard();
			}
			panelBoardNumbers.setVisible(false);
			panelBoardNumbers.repaint();
		}
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		createStatusPanel();
		//statusPanel.setPreferredSize(new Dimension(frameMainWindow.getWidth(), 16));
	}

	private static void createStatusPanel() {
		
		
		/*statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		statusLabel2.setHorizontalAlignment(SwingConstants.LEFT);*/
		statusLabel1.setText("         Moves so far :"+counter);
		statusLabel2.setText("Last Player :"+playerColor);
		 statusLabel4.setText("        Scores"+p1score+"-"+p2score);
		//statusPanel.add(statusLabel);
		//statusPanel.add(statusLabel2);
		//statusPanel.add(statusLabel3);
		statusPanel.add(statusLabel2);
		statusPanel.add(statusLabel4);
		statusPanel.add(statusLabel3);
		//statusPanel.add(Box.createHorizontalGlue());
		statusPanel.add(statusLabel1);
		frameMainWindow.add(statusPanel, BorderLayout.SOUTH);
	}

	public static void paintRed(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon redIcon = new ImageIcon("images/Red.jpg");
		JLabel redIconLabel = new JLabel(redIcon);
		redIconLabel.setBounds(27 + xOffset, 27 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
		layeredGameBoard.add(redIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
		
	}

	public static void paintBlack(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon blackIcon = new ImageIcon("images/Green.jpg");
		JLabel blackIconLabel = new JLabel(blackIcon);
		blackIconLabel.setBounds(27 + xOffset, 27 + yOffset, blackIcon.getIconWidth(),blackIcon.getIconHeight());
		layeredGameBoard.add(blackIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}

	public static void redrawBoard() {
		
		int[][] boardView = board.view();
		int r = board.m_x;
		int c = board.m_y;

		int playerPos = boardView[r][c];
		if (playerPos == 1) {
			// paint red at [r][c]
			paintRed(r, c);
			playerColor("red");
			playerColor="red";
			System.out.println("Current Board View");
            System.out.println(board);
            counter=counter+1;
		} else if (playerPos == 2) {
			// paint green at [r][c]
			paintBlack(r, c);
			playerColor("green");
			playerColor="green";
			System.out.println("Current Board View");
            System.out.println(board);
            counter=counter+1;
		}
		System.out.println("(Red First)");
		 System.out.println("         Moves so Far::"+counter);
		 System.out.println("***********************");
		 p1score=board.player1;
		 p2score=board.player2;
		 createStatusPanel();
		/* JPanel statusPanel = new JPanel();
		 JLabel statusLabel1 = new JLabel("Moves so far :"+counter);
	      statusPanel.add(new JLabel("Score (in the Applet):  "));
	      statusPanel.add(statusLabel1);
	  	frameMainWindow.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frameMainWindow.getWidth(), 16));*/  
		/*if (board.over() == true) {
			gameOver();
		}*/

	}


	public static void game(){

		if (board.next() == 1) p1.go(board);
		else p2.go(board);
		redrawBoard();
                if (!board.over()) {
		int nextTypeFlag = 0;
		if (board.next() == 1) nextTypeFlag = p1TypeFlag;
		else nextTypeFlag = p2TypeFlag;
                if (nextTypeFlag == 1) {
			if (board.next() == 1) p1.go(board);
			else p2.go(board);
			redrawBoard();
                }
               
        }
                p1score=board.player1;
          		 p2score=board.player2;
          		
          		 createStatusPanel();
          		 System.out.println("Red player "+p1score+""+" Green player "+p2score);  
                

                
	}

	/**
	 * @return Component - Returns a component to be drawn by main window
	 * @see main()
	 * This function creates the main window components.
	 */
	public static Component createContentComponents() {

		// create panels to hold and organize board numbers
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 4, 4));
		JButton buttonCol0 = new JButton("1");
		buttonCol0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(0);
				else p2.setMove(0);
				game();
			}
		});
		JButton buttonCol1 = new JButton("2");
		buttonCol1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(1);
				else p2.setMove(1);
				game();
			}
		});
		
		JButton buttonCol2 = new JButton("3");
		buttonCol2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(2);
				else p2.setMove(2);
				game();
			}
		});
		JButton buttonCol3 = new JButton("4");
		buttonCol3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(3);
				else p2.setMove(3);
				game();
			}
		});
		JButton buttonCol4 = new JButton("5");
		buttonCol4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(4);
				else p2.setMove(4);
				game();
			}
		});
		JButton buttonCol5 = new JButton("6");
		buttonCol5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(5);
				else p2.setMove(5);
				game();
			}
		});
		JButton buttonCol6 = new JButton("7");
		buttonCol6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(6);
				else p2.setMove(6);
				game();
			}
		});
		panelBoardNumbers.add(buttonCol0);
		panelBoardNumbers.add(buttonCol1);
		panelBoardNumbers.add(buttonCol2);
		panelBoardNumbers.add(buttonCol3);
		panelBoardNumbers.add(buttonCol4);
		panelBoardNumbers.add(buttonCol5);
		panelBoardNumbers.add(buttonCol6);

		// create game board with pieces
		layeredGameBoard = createLayeredBoard();

		// create panel to hold all of above
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		//panelMain.setLayout(new GridLayout(1, 2, 4, 4));

		// add objects to pane
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		return panelMain;
	}

	// Returns just the class name -- no package info.
	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex+1);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String s = source.getName();

		if ( s.equals("NewGame") ) {
			// create new game
			playerColor="none";
			counter=0;
			p1score=0;
			p2score=0;
			createStatusPanel();
			createNewGame();
		} else if ( s.equals("QuitGame")) {
			System.exit(0);
		} else if (s.equals("Display")) {
			showContents();
		}else if (s.equals("Report")) {
				showReports();
		} else if (s.equals("About")) {
			showCredits();
		}

	}


	public void itemStateChanged(ItemEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String s = source.getName() +
				   ((e.getStateChange() == ItemEvent.SELECTED) ?
					 "-selected":"-unselected");

		
			if (s.equals("P1Human-selected")) {
			p1 = new HumanPlayer();
			p1TypeFlag = 0;
		} else if (s.equals("P1MinMax-selected")) {
			p1 = new MinMaxPlayer();
			p1TypeFlag = 1;
		} else if (s.equals("P2Human-selected")) {
			p2 = new HumanPlayer();
			p2TypeFlag = 0;
		} else if (s.equals("P2MinMax-selected")) {
			p2 = new MinMaxPlayer();
			p2TypeFlag = 1;
		}

	}

	public static void gameOver() {
        panelBoardNumbers.setVisible(false);
		frameWin = new JFrame("Result !!");
		frameWin.setBounds(300, 300, 220, 120);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		JLabel winLabel;
		if (board.winner == 3) {
			winLabel = new JLabel("Red Player wins !!");
			System.out.println("Red Player wins !!");
			winPanel.add(winLabel);
		} else if (board.winner == 4) {
			winLabel = new JLabel("Green Player wins !!");
			winPanel.add(winLabel);
			System.out.println("Green Player wins !!");
		} else {
			winLabel = new JLabel("Nobody Win! - You both loose!");
			winPanel.add(winLabel);
			System.out.println("Nobody Win! - You both loose!");
		}
		winPanel.add(winLabel, BorderLayout.NORTH);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameWin.setVisible(false);
			}
		});
		winPanel.add(okButton, BorderLayout.SOUTH);
		frameWin.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameWin.setVisible(true);
	}
	public static void errorColumn() {
		frameErr = new JFrame("Error !!");
		frameErr.setBounds(300, 300, 220, 120);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		JLabel winLabel;
			winLabel = new JLabel("Move Invalid!!");
			winPanel.add(winLabel);
		winPanel.add(winLabel, BorderLayout.NORTH);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameErr.setVisible(false);
			}
		});
		winPanel.add(okButton, BorderLayout.SOUTH);
		frameErr.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameErr.setVisible(true);
	}
	public static void showCredits() {
		frameCredits = new JFrame("Credits");
		frameCredits.setBounds(300, 300, 480, 320);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		JLabel winLabel = new JLabel("<HTML><strong>Max Connect-Four</strong><br>" +
		"<pre>Programmers:          Deepak Rohan<br>" +
		"                      Ganesh Ramamoorthy<br><br></pre></HTML>");

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frameCredits.setVisible(false);
			}
		});

		winPanel.add(winLabel);
		frameCredits.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameCredits.setVisible(true);
	}

	private static void showContents() {
		frameCredits = new JFrame("Display");
		frameCredits.setBounds(300, 300, 680, 320);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		JLabel winLabel = new JLabel("<HTML><strong>How To Play</strong><br>" +
		"<pre>Red Player and Green player randomly drops the red <br>" +
				"and green discs into the board.<br>" +
		" The player who first creates a quadruple of four consecutive positions<br>" +" on board, either in the horizontal, vertical, or each of the<br>" +" two diagonal directions wins the game<br><br></pre></HTML>");

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frameCredits.setVisible(false);
			}
		});

		winPanel.add(winLabel);
		frameCredits.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameCredits.setVisible(true);

	}
	private static void showReports() {
		JFrame f = new JFrame("Reports");
		 JTextArea textArea = new JTextArea();
     	Document doc = textArea.getDocument();
     	PrintStream out = new PrintStream(new DocumentOutputStream(doc));
         out.println("Current Board View");
         out.println(board);
         WindowListener l = new WindowAdapter() {
     	    public void windowClosing(WindowEvent e) {
     	    	f.setVisible(false);
     	    }
     	};
     
     	f.addWindowListener(l); 
     	f.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
     	f.setSize(640, 480);
     	f.setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			 /* try {
					PrintStream out = new PrintStream(new FileOutputStream("C:/DELL/answer3.txt"));
					System.setOut(out);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) { }
		createNewGame();
	    }
	}


