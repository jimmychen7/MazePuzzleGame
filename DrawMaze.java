import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;

/**
 * currently this class only makes a JPanel that draws a orange and red player that can be controlled
 * by arrow keys and WASD. This class can be renamed and have methods to draw maze added to it.
 *
 */
public class DrawMaze extends JPanel implements KeyListener{
	
	/** 
	 * creates a JFrame with a paintComponent which are squares representing the
	 * players current location, and implements keyListener so that the user
	 * can move this square with the arrow keys
	 * @param x starting x position for player
	 * @param y starting y position for player
	 */
	public DrawMaze(int mSize,int cSize){
		//set panel preferred size
		mazeSize = mSize; 
		panelSize = 700;
		setPreferredSize(new Dimension(panelSize,panelSize));
		cellSize = cSize;
		showSolution = false;
		
		//add and enable key listener, disable traversal keys(?)
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//initialise maze
		MazeGeneration mazeGen = new MazeGeneration(mazeSize);
		maze = mazeGen.getMaze();
		//find maze solution and add portals
		MazeSearch search = new MazeSearch();
		search.getSolution(maze, maze.getMazeNode(0,0), maze.getMazeNode(maze.mazeSize()-1, maze.mazeSize()-1));
		search.addPortals(maze);
		//set player start positions
		x = 0;
		y = 0;
		x2 = (maze.mazeSize()-1)*cellSize;
		y2 = (maze.mazeSize()-1)*cellSize;
		dirn1 = SOUTH;
		dirn2 = NORTH;
		loadImages();
		

	}
	private void loadImages(){
		//Loading the PNG files to be used for drawing the maze.
		try {
			vertical = ImageIO.read(this.getClass().getResource("vertical.png"));
			horizontal = ImageIO.read(this.getClass().getResource("horizontal.png"));			
			threeWayNorth = ImageIO.read(this.getClass().getResource("3wayNorth.png"));
			threeWaySouth = ImageIO.read(this.getClass().getResource("3waySouth.png"));
			threeWayEast = ImageIO.read(this.getClass().getResource("3wayEast.png"));
			threeWayWest = ImageIO.read(this.getClass().getResource("3wayWest.png"));
			fourWay = ImageIO.read(this.getClass().getResource("4way.png"));
			nw = ImageIO.read(this.getClass().getResource("nw.png"));
			ne = ImageIO.read(this.getClass().getResource("ne.png"));
			sw = ImageIO.read(this.getClass().getResource("sw.png"));
			se = ImageIO.read(this.getClass().getResource("se.png"));
			wall = ImageIO.read(this.getClass().getResource("grass.png"));
			finish = ImageIO.read(this.getClass().getResource("finish.png"));
			
			yellowCarN = ImageIO.read(this.getClass().getResource("yellowCarN.png"));
			yellowCarS = ImageIO.read(this.getClass().getResource("yellowCarS.png"));
			yellowCarE = ImageIO.read(this.getClass().getResource("yellowCarE.png"));
			yellowCarW = ImageIO.read(this.getClass().getResource("yellowCarW.png"));
			redCarN = ImageIO.read(this.getClass().getResource("redCarN.png"));
			redCarS = ImageIO.read(this.getClass().getResource("redCarS.png"));
			redCarE = ImageIO.read(this.getClass().getResource("redCarE.png"));
			redCarW = ImageIO.read(this.getClass().getResource("redCarW.png"));
			
			portal = ImageIO.read(this.getClass().getResource("portal.png"));
			
		} catch (IOException e) {
		}
		
		verticaltp = new TexturePaint(vertical, new Rectangle(0,0,cellSize,cellSize));
		horizontaltp = new TexturePaint(horizontal, new Rectangle(0,0,cellSize,cellSize));
		threeWayNorthtp = new TexturePaint(threeWayNorth, new Rectangle(0,0,cellSize,cellSize));
		threeWaySouthtp = new TexturePaint(threeWaySouth, new Rectangle(0,0,cellSize,cellSize));
		threeWayEasttp = new TexturePaint(threeWayEast, new Rectangle(0,0,cellSize,cellSize));
		threeWayWesttp = new TexturePaint(threeWayWest, new Rectangle(0,0,cellSize,cellSize));
		fourWaytp = new TexturePaint(fourWay, new Rectangle(0,0,cellSize,cellSize));
		nwtp = new TexturePaint(nw, new Rectangle(0,0,cellSize,cellSize));
		netp = new TexturePaint(ne, new Rectangle(0,0,cellSize,cellSize));
		swtp = new TexturePaint(sw, new Rectangle(0,0,cellSize,cellSize));
		setp = new TexturePaint(se, new Rectangle(0,0,cellSize,cellSize));
		walltp = new TexturePaint(wall, new Rectangle(0,0,cellSize,cellSize));
		finishtp = new TexturePaint(finish, new Rectangle(0,0,cellSize,cellSize));
		
		yellowCarNtp = new TexturePaint(yellowCarN, new Rectangle(0,0,cellSize,cellSize));
		yellowCarStp = new TexturePaint(yellowCarS, new Rectangle(0,0,cellSize,cellSize));
		yellowCarEtp = new TexturePaint(yellowCarE, new Rectangle(0,0,cellSize,cellSize));
		yellowCarWtp = new TexturePaint(yellowCarW, new Rectangle(0,0,cellSize,cellSize));
		redCarNtp = new TexturePaint(redCarN, new Rectangle(0,0,cellSize,cellSize));
		redCarStp = new TexturePaint(redCarS, new Rectangle(0,0,cellSize,cellSize));
		redCarEtp = new TexturePaint(redCarE, new Rectangle(0,0,cellSize,cellSize));
		redCarWtp = new TexturePaint(redCarW, new Rectangle(0,0,cellSize,cellSize));
		
		portaltp = new TexturePaint(portal, new Rectangle(0,0,cellSize,cellSize));
	}
	
	public int getMazeSize() {
		return mazeSize;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	public void setMazeSize(int mSize) {
		mazeSize = mSize;
	}
	
	public void setCellSize(int cSize) {
		cellSize = cSize;
	}
	public boolean getStopTimer() {
		return stopTimer;
	}
	
	public int getWinner() {
		return winner;
	}
	
	public void setStopTimer(boolean value) {
		stopTimer = value;
	}

	//resets the player position but players can't move anymore
	public void restart() {
		System.out.println("RESTARTING");
		x = 0;
		y = 0;
		y2 = (maze.mazeSize()-1)*cellSize;
		/*if(maze.getMazeNode(1, 0).getIsPath()){
			x2 = x2+cellSize;
		}
		if(maze.getMazeNode(0, 1).getIsPath()){
			y2 = y2+cellSize;
		}*/
		repaint();
		return;
	}
	
	//Generates a new maze but the players can't move anymore
	public void newGame() {
		loadImages();
		MazeGeneration mazeGen = new MazeGeneration(mazeSize);
		maze = mazeGen.getMaze();
		MazeSearch search = new MazeSearch();
		search.getSolution(maze, maze.getMazeNode(0,0), maze.getMazeNode(maze.mazeSize()-1, maze.mazeSize()-1));
		search.addPortals(maze);
		//mazeGen.printMazeGeneration();
		//set player start positions
		x = 0;
		y = 0;
		x2 = (maze.mazeSize()-1)*cellSize;
		y2 = (maze.mazeSize()-1)*cellSize;
		/*if(maze.getMazeNode(1, 0).getIsPath()){
			x2 = x2+cellSize;
		}
		if(maze.getMazeNode(0, 1).getIsPath()){
			y2 = y2+cellSize;
		}*/
		showSolution = false;
		repaint();
		return;
	}
	
	public void solution(){
		//toggle show solution
		if (this.showSolution == false){
			this.showSolution = true;
		}else{
			this.showSolution = false;
		}
		repaint();
		return;
	}
	
	/**
	 * draws a orange square and yellow square at position x,y for player 1 and x2,y2 for player 2
	 * draws the maze with roads: start is a blue block and end is a red block
	 * draws the solution as a green path
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		MazeNode mazeNode;

			
		for (int i = 0; i < maze.mazeSize(); i++) {			
			for(int j = 0; j < maze.mazeSize(); j++) {
				Shape mazeCell = new Rectangle2D.Float(i*cellSize, j*cellSize, cellSize, cellSize);
				mazeNode = maze.getMazeNode(i, j);
				
				if((mazeNode.getIsPath())){	
					//PATH
					if(maze.getIsPath(i, j-1) && maze.getIsPath(i, j+1)
				    && maze.getIsPath(i-1, j) && maze.getIsPath(i+1, j)) {
						g2d.setPaint(fourWaytp);			
						g2d.fill(mazeCell);			
					} else if (maze.getIsPath(i, j-1) && maze.getIsPath(i, j+1)
						    && maze.getIsPath(i+1, j)) {
						g2d.setPaint(threeWayEasttp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j-1) && maze.getIsPath(i, j+1)
						    && maze.getIsPath(i-1, j)) {
						g2d.setPaint(threeWayWesttp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j+1) && maze.getIsPath(i-1, j) 
							&& maze.getIsPath(i+1, j)) {
						g2d.setPaint(threeWaySouthtp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j-1) && maze.getIsPath(i-1, j)
							&& maze.getIsPath(i+1, j)) {
						g2d.setPaint(threeWayNorthtp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j-1) && maze.getIsPath(i+1, j)) {
						g2d.setPaint(netp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j-1) && maze.getIsPath(i-1, j)) {
						g2d.setPaint(nwtp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j+1) && maze.getIsPath(i+1, j) ) {
						g2d.setPaint(setp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j+1) && maze.getIsPath(i-1, j)) {
						g2d.setPaint(swtp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i, j-1)) {
						g2d.setPaint(verticaltp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i-1, j)) {
						g2d.setPaint(horizontaltp);			
						g2d.fill(mazeCell);			
					} else if (maze.getIsPath(i, j+1)) {
						g2d.setPaint(verticaltp);			
						g2d.fill(mazeCell);		
					} else if (maze.getIsPath(i+1, j)) {
						g2d.setPaint(horizontaltp);			
						g2d.fill(mazeCell);		
					} else {
						g2d.setPaint(walltp);
						g2d.fill(mazeCell);
					}
					if(mazeNode.getHasPowerUp()){
						g2d.setPaint(portaltp);
						g2d.fill(mazeCell);
					}
					if(maze.getMazeNode(i, j).equals(maze.getMazeNode(0, 0))) {
						//START
						g2d.setPaint(finishtp);
						g2d.fill(mazeCell);
					}else if(maze.getMazeNode(i, j).equals(maze.getMazeNode(maze.mazeSize()-1, maze.mazeSize()-1))){
						//END
						g2d.setPaint(finishtp);
						g2d.fill(mazeCell);
					}
					
				} else {
					//WALL
					g2d.setPaint(walltp);
					g2d.fill(mazeCell);
				}
			}
		}	
		
		//PLAYER1 
		Shape player = new Rectangle2D.Float(x, y, cellSize, cellSize);
		if(dirn1 == EAST) {
			g2d.setPaint(redCarEtp);	
		} else if(dirn1 == WEST) {
			g2d.setPaint(redCarWtp);
		} else if(dirn1 == SOUTH) {
			g2d.setPaint(redCarStp);
		} else if(dirn1 == NORTH) {
			g2d.setPaint(redCarNtp);
		} else {
			
		}
	
		g2d.fill(player);		
		
		//PLAYER2
		Shape player2 = new Rectangle2D.Float(x2, y2, cellSize, cellSize);
		if(dirn2 == EAST) {
			g2d.setPaint(yellowCarEtp);	
		} else if(dirn2 == WEST) {
			g2d.setPaint(yellowCarWtp);
		} else if(dirn2 == SOUTH) {
			g2d.setPaint(yellowCarStp);
		} else if(dirn2 == NORTH) {
			g2d.setPaint(yellowCarNtp);
		}
		g2d.fill(player2);		

		//drawing solution path
		if(this.showSolution){
			//setting transparency values
			float alpha = 0.75f;
			int type = AlphaComposite.SRC_OVER;
			AlphaComposite composite = AlphaComposite.getInstance(type, alpha);
			
			//find which cells are part of the solution
			for (int i = 0; i < maze.mazeSize(); i++) {			
				for(int j = 0; j < maze.mazeSize(); j++) {
					Shape solutionCell = new Rectangle2D.Float(i*cellSize, j*cellSize, cellSize, cellSize);
					mazeNode = maze.getMazeNode(i, j);
					//Color color = new Color(0,1,1,alpha);
					Composite original = g2d.getComposite();
					g2d.setComposite(composite);
					g2d.setPaint(Color.ORANGE);
					if(mazeNode.getIsSolution()){
						g2d.fill(solutionCell);
					}
					g2d.setComposite(original);
				}
			}
		}
	}

	/**
	 * determine what happens next when a key is pressed. Before the move is made,
	 * it is checked to see if is a wall. If it is, do not allow this move.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//upon key press, the next move is considered but not made. 
		//first check if the next move hits any walls
		
		
		int key = e.getKeyCode();
		int rowPlayer1ToGo = y / cellSize;
		int colPlayer1ToGo = x / cellSize;
		int rowPlayer2ToGo = y2 / cellSize;
		int colPlayer2ToGo = x2 / cellSize;
		
		System.out.println("player1: "+rowPlayer1ToGo+","+colPlayer1ToGo);
		System.out.println("player1 pixel position: "+x+","+y);
		
		if(key == KeyEvent.VK_UP && rowPlayer1ToGo>0){
			rowPlayer1ToGo --;
			dirn1 = NORTH;
		} else if (key == KeyEvent.VK_LEFT && colPlayer1ToGo>0){
			colPlayer1ToGo --;
			dirn1 = WEST;
		} else if (key == KeyEvent.VK_RIGHT && colPlayer1ToGo < maze.mazeSize()-1){
			colPlayer1ToGo ++;
			dirn1 = EAST;
		} else if (key == KeyEvent.VK_DOWN && rowPlayer1ToGo < maze.mazeSize()-1){
			rowPlayer1ToGo	++;
			dirn1 = SOUTH;
		} else if(key == KeyEvent.VK_W && rowPlayer2ToGo>0){
			rowPlayer2ToGo --;
			dirn2 = NORTH;
		} else if (key == KeyEvent.VK_A && colPlayer2ToGo>0){
			colPlayer2ToGo --;
			dirn2 = WEST;
		} else if (key == KeyEvent.VK_D && colPlayer2ToGo < maze.mazeSize()-1){
			colPlayer2ToGo ++;
			dirn2 = EAST;
		} else if (key == KeyEvent.VK_S && rowPlayer2ToGo < maze.mazeSize()-1){
			rowPlayer2ToGo ++;
			dirn2 = SOUTH;
		}
		
		//if next move is a path, allow this move
		
		MazeNode player1MazeNode = maze.getMazeNode(colPlayer1ToGo,rowPlayer1ToGo);
		MazeNode player2MazeNode = maze.getMazeNode(colPlayer2ToGo,rowPlayer2ToGo);
		
		if(player1MazeNode.getIsPath()){
			if(player1MazeNode.getHasPowerUp()){
				System.out.println("player 1 entered portal at "+colPlayer1ToGo+","+rowPlayer1ToGo);
				MazeNode teleport = teleport(player1MazeNode);
				y = teleport.getY() * cellSize;
				x = teleport.getX() * cellSize;
			} else {
				y = rowPlayer1ToGo * cellSize;
				x = colPlayer1ToGo * cellSize;
			}
		}
		if(player2MazeNode.getIsPath()){
			if(player2MazeNode.getHasPowerUp()){
				System.out.println("player 1 entered portal at "+colPlayer2ToGo+","+rowPlayer2ToGo);
				MazeNode teleport = teleport(player2MazeNode);
				y2 = teleport.getY() * cellSize;
				x2 = teleport.getX() * cellSize;
			} else {
				y2 = rowPlayer2ToGo * cellSize;
				x2 = colPlayer2ToGo * cellSize;
			}
		}
		

		//keep players within the frame
		if (x<0)
			x = 0;
		if (x>panelSize-cellSize)
			x = panelSize-cellSize;
		if (y<0)
			y = 0;
		if (y>panelSize-cellSize)
			y = panelSize-cellSize;
		if (x2<0)
			x2 = 0;
		if (x2>panelSize-cellSize)
			x2 = panelSize-cellSize;
		if (y2<0)
			y2 = 0;
		if (y2>panelSize-cellSize)
			y2 = panelSize-cellSize;
		
		repaint();
		
		//Print message if player has reached the end
		if(rowPlayer1ToGo == maze.mazeSize() - 1 && colPlayer1ToGo == maze.mazeSize() - 1) {
			stopTimer = true;
			winner = 1;
			// JOptionPane.showMessageDialog(null, "Player 1 Wins!");
		} else if(rowPlayer2ToGo == 0 && colPlayer2ToGo == 0) {
			stopTimer = true;
			winner = 2;
			// JOptionPane.showMessageDialog(null, "Player 2 Wins!");
		}
		
	}
	private MazeNode teleport(MazeNode start){
		MazeNode portalExit = start;
		int count = 0;;
		while(count < maze.mazeSize()){
			int exitX = (int )(Math.random() * maze.mazeSize());
			int exitY = (int )(Math.random() * maze.mazeSize());
			portalExit = maze.getMazeNode(exitX, exitY);
			if(!portalExit.equals(start)&&portalExit.getIsPath()){
				break;
			}
			count ++;
		}
		return portalExit;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	private int x, y;
	private int x2, y2;
	private int dirn1;
	private int dirn2;

	final static int EAST = 0;
	final static int NORTH = 1;
	final static int WEST = 2;
	final static int SOUTH = 3;

	private int cellSize;
	private MazeGraphImplementation maze;
	private int panelSize;
	private static final long serialVersionUID = -7733698667719513148L;
	BufferedImage vertical, horizontal, threeWayNorth, threeWaySouth, threeWayEast, threeWayWest, fourWay, nw, ne, sw, se, wall, finish, portal;
	BufferedImage yellowCarN, yellowCarS, yellowCarE, yellowCarW, redCarN, redCarS, redCarE, redCarW;
	TexturePaint verticaltp, horizontaltp, threeWayNorthtp, threeWaySouthtp, threeWayEasttp, threeWayWesttp, fourWaytp, nwtp, netp, swtp, setp, walltp, finishtp,portaltp;
	TexturePaint yellowCarNtp, yellowCarStp, yellowCarEtp, yellowCarWtp, redCarNtp, redCarStp, redCarEtp, redCarWtp;
	private int mazeSize;
	private boolean showSolution;
	private boolean stopTimer = false;
	private int winner = 0;
}