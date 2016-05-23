import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MazeGUI extends JFrame implements ActionListener{

	private JLabel showTime;
	private int totalTime = 0;
	private int min = 0;
	private int sec = 0;

	private Timer timer;
	JPanel mazeState;
	
	private final int HARDMSIZE = 20;
	private final int HARDCSIZE = 17;
	private final int MEDMSIZE = 17;
	private final int MEDCSIZE = 20;
	private final int EASYMSIZE = 15;
	private final int EASYCSIZE = 23;
	
	public MazeGUI() {
		initUI(); 
	} 
	
	private void initUI() {
		//create JFrame border layout
		JFrame frame = new JFrame("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(910,715));//bottom part of maze is cut off
						//increase vertical and hor size by 10
		frame.setResizable(false);
		//create one main jpanel to store everything
		JPanel mainContainer = new JPanel();
		mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));
		
		//create box layout for LHS, START WITH MEDIUM
		final JPanel LHS = new JPanel();
		mazeState = new DrawMaze(MEDMSIZE, MEDCSIZE);

		//create box layout for RHS
		//to have even spacing between all jbuttons/jlabels, 
		// used flowLayouts
		JPanel RHS = new JPanel();
		RHS.setLayout(new BoxLayout(RHS, BoxLayout.Y_AXIS));
		RHS.setPreferredSize(new Dimension(190,700));
		
		//Flow layout 1: new Game
		JPanel newGame = new JPanel();
		newGame.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton newGameButton = new JButton("NEW GAME");
		newGameButton.setPreferredSize(new Dimension(140, 35));
		newGameButton.setFont(new Font("SansSerif", Font.BOLD, 18));
		newGameButton.setFocusPainted(false);
		newGameButton.setBackground(Color.BLACK);
		newGameButton.setForeground(Color.WHITE);
		
		newGameButton.setFocusable(false);
		newGameButton.setActionCommand("newGame");
		newGameButton.addActionListener(this);
		newGame.add(newGameButton);
		
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("STARTING NEW GAME");
				((DrawMaze) mazeState).newGame();
			}
			
		});
		
		//FlowLayout 2: restart
		JPanel restart = new JPanel();
		restart.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton restartButton = new JButton("RESTART");
		restartButton.setPreferredSize(new Dimension(140, 35));
		restartButton.setFont(new Font("SansSerif", Font.BOLD, 18));
		restartButton.setBackground(Color.BLACK);
		restartButton.setForeground(Color.WHITE);
		
		restartButton.setFocusable(false);
		restartButton.setActionCommand("restart");
		restartButton.addActionListener(this);
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DrawMaze) mazeState).restart();	
			}
		});
		restart.add(restartButton);
		
		//FlowLayout 3: solution
		JPanel solution = new JPanel();
		solution.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton solveButton = new JButton("SOLVE");
		solveButton.setPreferredSize(new Dimension(140, 35));
		solveButton.setFont(new Font("SansSerif", Font.BOLD, 18));
		solveButton.setBackground(Color.BLACK);
		solveButton.setForeground(Color.WHITE);
		
		solveButton.setFocusable(false);
		solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DrawMaze) mazeState).solution();	
			}
		});
		solution.add(solveButton);
		
		//Flow Layout 4: choose difficulty: easy  medium hard
		JPanel difficulty = new JPanel();
		difficulty.setLayout(new FlowLayout(FlowLayout.CENTER));
		difficulty.setPreferredSize(new Dimension(120,180));
		((FlowLayout)difficulty.getLayout()).setVgap(20);		
		
		//create difficulty JLabel and white border to highlight
		//difficulty level
		JLabel diffLabel= new JLabel("Difficulty: "); 
		diffLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		final Border border = new LineBorder(Color.cyan, 3);
		final Border noBorder = new LineBorder(Color.BLACK, 3);
		
		//create easy med hard JButton
		final JButton easy = new JButton("EASY");
		final JButton medium = new JButton("MEDIUM");
		medium.setBorder(border); //start with  medium difficulty
		final JButton hard = new JButton("HARD");
		
		//set up easy JButton
		easy.setPreferredSize(new Dimension(115, 30));
		easy.setFont(new Font("SansSerif", Font.BOLD, 18));
		easy.setFocusPainted(false);
		easy.setBackground(Color.BLACK);
		easy.setForeground(Color.WHITE);
		easy.setActionCommand("newGame");
		easy.addActionListener(this);
		
		easy.setFocusable(false);
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clicking on button again same as clicking new game
				((DrawMaze) mazeState).setMazeSize(EASYMSIZE);
				((DrawMaze) mazeState).setCellSize(EASYCSIZE);
				((DrawMaze) mazeState).newGame();
				
				easy.setBorder(border);
				medium.setBorder(noBorder);
				hard.setBorder(noBorder);
			}
		});
		
		//set up medium JButton
		medium.setPreferredSize(new Dimension(115, 30));
		medium.setFont(new Font("SansSerif", Font.BOLD, 18));
		medium.setBackground(Color.BLACK);
		medium.setForeground(Color.WHITE);
		medium.setActionCommand("newGame");
		medium.addActionListener(this);
		
		medium.setFocusable(false);
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clicking on button again same as clicking new game
				((DrawMaze) mazeState).setMazeSize(MEDMSIZE);
				((DrawMaze) mazeState).setCellSize(MEDCSIZE);
				((DrawMaze) mazeState).newGame();
				
				easy.setBorder(noBorder);
				medium.setBorder(border);
				hard.setBorder(noBorder);
			}
		});
		
		//set up hard JButton
		hard.setPreferredSize(new Dimension(115, 30));
		hard.setFont(new Font("SansSerif", Font.BOLD, 18));
		hard.setBackground(Color.BLACK);
		hard.setForeground(Color.WHITE);
		hard.setActionCommand("newGame");
		hard.addActionListener(this);
		
		hard.setFocusable(false);
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clicking on button again same as clicking new game
				((DrawMaze) mazeState).setMazeSize(HARDMSIZE);
				((DrawMaze) mazeState).setCellSize(HARDCSIZE);
				((DrawMaze) mazeState).newGame();
				
				easy.setBorder(noBorder);
				medium.setBorder(noBorder);
				hard.setBorder(border);
			}
		});
		
		difficulty.add(diffLabel);
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		
		//Flow Layout 4: symbols
		JPanel symbols = new JPanel();
		JLabel symbol = new JLabel("Symbols: ");
		symbol.setFont(new Font("SansSerif", Font.BOLD, 16));

		symbols.setLayout(new FlowLayout(FlowLayout.CENTER));
		symbols.setPreferredSize(new Dimension(120,180));
		((FlowLayout)symbols.getLayout()).setVgap(10);
		
		ImageIcon player1 = new ImageIcon("images/redCarN.PNG");		
		JLabel player1Label = new JLabel("Player 1: arrow keys", player1, JLabel.CENTER);
		player1Label.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		ImageIcon player2 = new ImageIcon("images/yellowCarN.PNG");		
		JLabel player2Label = new JLabel("Player 2: WASD keys", player2, JLabel.CENTER);
		player2Label.setFont(new Font("SansSerif", Font.BOLD, 14));

		ImageIcon portal = new ImageIcon("images/portal.PNG");		
		JLabel portalLabel = new JLabel("Portal: random teleport", portal, JLabel.CENTER);
		portalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		ImageIcon goalIcon = new ImageIcon("images/finish.png");	
		JLabel goal = new JLabel("Goal", goalIcon, JLabel.CENTER);
		goal.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		symbols.add(symbol);
		symbols.add(player1Label);
		symbols.add(player2Label);
		symbols.add(portalLabel);
		symbols.add(goal);
		
		//Flow Layout 6: timer
		JPanel displayTimer = new JPanel();
		displayTimer.setLayout(new FlowLayout(FlowLayout.CENTER));
		showTime = new JLabel();

		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				totalTime++;
				min = totalTime/60;
				sec = totalTime % 60;
				showTime.setText("Time: " + min + " mins " + sec + " secs");
				showTime.setFont(new Font("SansSerif", Font.BOLD, 16));
				
				if ( ((DrawMaze) mazeState).getStopTimer() == true ) {
					timer.stop();
					if( ((DrawMaze) mazeState).getWinner() == 1 ) {
						JOptionPane.showMessageDialog(null, "Player 1 Wins!\n "
						+ "Finished in: " + min + " mins and " + sec + " secs");
					} else if( ((DrawMaze) mazeState).getWinner() == 2 ) {
						JOptionPane.showMessageDialog(null, "Player 2 Wins!\n "
						+ "Finished in: " + min + " mins and " + sec + " secs");
					}
				}
			}

		};
		timer = new Timer(1000,timerListener);
		timer.setInitialDelay(0);
		timer.start(); 
		displayTimer.add(showTime);
		
		// ADD EVERYTHING TOGETHER
		LHS.add(mazeState);
		
		RHS.add(newGame);
		RHS.add(restart);
		RHS.add(solution);
		RHS.add(difficulty);
		RHS.add(symbols);
		RHS.add(displayTimer);
		
		mainContainer.add(LHS);
		mainContainer.add(RHS);
		
		frame.add(mainContainer); 

		frame.pack(); //The pack() method resizes the window so that 
			      //the label component is shown in its preferred size.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}									
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
            		public void run() {
                		new MazeGUI();
            		}
        	});
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("restart")||actionCommand.equals("newGame")){
			((DrawMaze) mazeState).setStopTimer(false);
			timer.restart();
			totalTime = 0;
			min = 0;
			sec = 0;
			System.out.println("restarting timer");
			
		}
		
	}
}
