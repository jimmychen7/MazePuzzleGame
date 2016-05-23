import java.util.ArrayList;
import java.util.Collections;
/*
 * precondition: Coordinates that have y=0 like (0,0), (1,0), (2,0) must be 
 * added before coordinates with y values not equal to zero, because the hashmap is dependent on them. 
 * 
 */
public class MazeGraphImplementation{

	public MazeGraphImplementation(){
		maze = new Graph<MazeNode>();

	}


	/*
	 * Vertices are defined as MazeNodes on the x axis, for example (1,0), (2,0), (3,0) etc
	 * Edges are defined as so: edges of (1,0) would be (1,0), (1,1) (1,2), (1,3) etc
	 * only inserts if the node at the co-ordinate doesn't exist.
	 */
	public void addMazeNode(MazeNode c){
		if(!mazeNodeExists(c)){
			if(c.getY() == 0){
				maze.addVertex(c);
				maze.addEdgeAtIndex(c, c,c.getY());
			} else {
				MazeNode vertex = getMazeNode(c.getX(),0);
				if(vertex != null){
					maze.addEdgeAtIndex(vertex, c,c.getY());
				} 
			}
		}
	}

	/*
	 * xAxisMazeNodes are (1,0) (2,0) (3,0) etc which are defined as vertices.
	 * yMazeNodes are neighbours of xAxisMazeNodes (as in they have the same x value), also defined as edges.
	 * getMazeNode returns null if not found, which includes if x and y co-ords given
	 * are out of bounds
	 */
	public MazeNode getMazeNode(int x, int y){
		ArrayList<MazeNode> xAxisMazeNodes  = maze.getAllVertices();
		if(x<mazeSize() && y<mazeSize() && x>=0 && y>=0 && xAxisMazeNodes != null){
			for(MazeNode xMazeNode: xAxisMazeNodes){
				if(xMazeNode.getX()==x){
					ArrayList<MazeNode> yMazeNodes = maze.getNeighbours(xMazeNode);
					if(y<yMazeNodes.size())
						return yMazeNodes.get(y);
				}
			}
		}
		return null;
	}
	public int mazeSize(){
		return maze.getAllVertices().size();
	}
	
	public boolean getIsPath(int x, int y) {
		ArrayList<MazeNode> xAxisMazeNodes  = maze.getAllVertices();
		if(x<mazeSize() && y<mazeSize() && x>=0 && y>=0 && xAxisMazeNodes != null){
			for(MazeNode xMazeNode: xAxisMazeNodes){
				if(xMazeNode.getX()==x){
					ArrayList<MazeNode> yMazeNodes = maze.getNeighbours(xMazeNode);
					if(y<yMazeNodes.size()) {
						if(yMazeNodes.get(y) != null)
							return yMazeNodes.get(y).getIsPath();
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * the get north/south/west/east methods rely on getMazeNode which will return null
	 * if out of bounds
	 */
	public MazeNode getNorth(MazeNode c){
		MazeNode northMazeNode = null;
		northMazeNode = getMazeNode(c.getX(),(c.getY()-1));
		return northMazeNode;
	}
	
	public MazeNode getSouth(MazeNode c){
		MazeNode southMazeNode = null;
		southMazeNode = getMazeNode(c.getX(),(c.getY()+1));
		return southMazeNode;
	}
	
	public MazeNode getEast(MazeNode c){
		MazeNode eastMazeNode = null;
		eastMazeNode = getMazeNode((c.getX()+1),c.getY());
		return eastMazeNode;
	}
	
	public MazeNode getWest(MazeNode c){
		MazeNode westMazeNode = null;
		westMazeNode = getMazeNode((c.getX()-1),c.getY());
		return westMazeNode;
	}
	/*
	 * update the MazeNode at the co-ordinate specified in the new MazeNode
	 */
	public void updateMazeNode(MazeNode newMazeNode){
		MazeNode oldMazeNode = getMazeNode(newMazeNode.getX(), newMazeNode.getY());
		maze.replace(oldMazeNode, newMazeNode);
	}
	public boolean mazeNodeExists(MazeNode m){
		if(getMazeNode(m.getX(),m.getY())==null){
			return false;
		} else {
			return true;
		}
	}
	/*
	 * get neighbours that are paths only
	 */
	public ArrayList<MazeNode> getPathNeighbours(MazeNode m){
		ArrayList<MazeNode> neighbours = new ArrayList<MazeNode>();
		if(getNorth(m) != null && getNorth(m).getIsPath()){
			neighbours.add(getNorth(m));
		}
		if(getSouth(m) != null && getSouth(m).getIsPath()){
			neighbours.add(getSouth(m));
		}
		if(getWest(m) != null && getWest(m).getIsPath()){
			neighbours.add(getWest(m));
		}
		if(getEast(m) != null && getEast(m).getIsPath()){
			neighbours.add(getEast(m));
		}
		return neighbours;
	}
	/*
	 * gets neighbours that are two co-ordinate spaces away
	 * return null if node given doesn't exist
	 */
	public ArrayList<MazeNode> getNeighbours(MazeNode m){
		ArrayList<MazeNode> xAxisMazeNodes  = maze.getAllVertices();
		if(!mazeNodeExists(m)){
			System.out.println("node given to getNeighbour doesn't exist!");
			return null;
		}
		ArrayList<MazeNode> neighbours = new ArrayList<MazeNode>();
		for(MazeNode xMazeNode: xAxisMazeNodes){
			if(xMazeNode.getX()==m.getX()+2 || xMazeNode.getX()==m.getX()-2){
				ArrayList<MazeNode> yMazeNodes = maze.getNeighbours(xMazeNode);
				neighbours.add(yMazeNodes.get(m.getY()));
			}
			if (xMazeNode.getX()==m.getX()){
				ArrayList<MazeNode> yMazeNodes = maze.getNeighbours(xMazeNode);
				if(m.getY()-2>=0)
					neighbours.add(yMazeNodes.get(m.getY()-2));
				if(m.getY()+2<mazeSize())
					neighbours.add(yMazeNodes.get(m.getY()+2));
			}

		}
		return neighbours;
		
	}

	public void printMaze(){
		ArrayList<MazeNode> xAxisMazeNodes  = maze.getAllVertices();
		ArrayList<MazeNode> allMazeNodes = new ArrayList<MazeNode>();
		for(MazeNode xMazeNode: xAxisMazeNodes){
			ArrayList<MazeNode> yMazeNodes = maze.getNeighbours(xMazeNode);
			for(MazeNode yMazeNode : yMazeNodes){
				allMazeNodes.add(yMazeNode);
			}	
		}
		Collections.sort(allMazeNodes);
		for(MazeNode c: allMazeNodes){
			if(c.getIsPath()){
				System.out.print("X");
			} else {
				System.out.print("O");
			}	
			if(c.getX() == mazeSize()-1){
				System.out.print("\n");
			}
		}
	}
	private Graph<MazeNode> maze;

	
}
