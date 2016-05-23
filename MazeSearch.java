import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSearch {
	
	public MazeSearch(){
		this.solution = new ArrayList<MazeNode>();
	}
	
	public void getSolution (MazeGraphImplementation maze, MazeNode start, MazeNode goal){
		
		this.solution.clear();
		
		/*
		 * makeshift hashmap of nodes and their parents
		 * ie. index 0, 2, 4, etc... -> nodes
		 * index 1, 3, 5, etc... -> parent
		 * 
		 * could change to actual hashmap though
		 */
		ArrayList<MazeNode> path = new ArrayList<MazeNode>();
		
		//if for some reason start is the same as goal
		if(start.equals(goal)){
			path.add(start);
		}
		
		Queue<MazeNode> toVisit = new LinkedList<MazeNode>();
		ArrayList<MazeNode> visited = new ArrayList<MazeNode>();
		toVisit.add(start);
		while(!toVisit.isEmpty()){
			MazeNode current = toVisit.poll();
			visited.add(current);

				for (MazeNode neighbour : maze.getPathNeighbours(current)){
					path.add(neighbour);
					path.add(current);
					
					if (neighbour.equals(goal)){
						makePath(path, start, goal);
					}else{
						if (!visited.contains(neighbour)){
							toVisit.add(neighbour);
						}
					}
					
				}
		}
	}
	
	
	//print maze with solution
	
	public void printSolution(MazeGraphImplementation maze){
		int i,j;
		System.out.println();
		for (i = 0; i < maze.mazeSize(); i++) {	
			for(j = 0; j < maze.mazeSize(); j++) {
				if(maze.getMazeNode(j, i).getIsPath() && maze.getMazeNode(j, i).getIsSolution()) {
					System.out.print("x");
				} else if (maze.getMazeNode(j, i).getIsPath() && !maze.getMazeNode(j, i).getIsSolution()){
					System.out.print(".");
				} else {
					System.out.print("1");	
				}		
			}
			System.out.println();	
		}
		System.out.println();
	}
	
	private void makePath(ArrayList<MazeNode> path, MazeNode start, MazeNode goal){

		//finds the parent of the node
		int i = path.indexOf(goal);
		MazeNode curr = path.get(i+1);
		
		//set this mazenode to part of solution
		curr.setIsSolution(true);
		
		goal.setIsSolution(true);
		
		//adds the goal to the solution array
		this.solution.add(0, goal);
		
		if (curr.equals(start)){
			//start node is found
			this.solution.add(0, start);
		}else{
			/*
			 * trace the path from goal to end
			 * ie. the curr node is now the new destination
			 */
			makePath(path, start, curr);
		}
	}
	/**
	 * add portals that are not on the solution path
	 * @param maze
	 */
	public void addPortals(MazeGraphImplementation maze){
		for (int i = 0; i < maze.mazeSize(); i++) {	
			for(int j = 0; j < maze.mazeSize(); j++) {
				MazeNode currentNode = maze.getMazeNode(j,i);
				MazeNode north = maze.getNorth(currentNode);
				MazeNode west = maze.getWest(currentNode);
				MazeNode east = maze.getEast(currentNode);
				MazeNode south = maze.getSouth(currentNode);
				if(currentNode.getIsPath() && !(currentNode.getIsSolution()) &&
					north!=null && west !=null && east!= null && south!=null){
					if(!north.getHasPowerUp() && !south.getHasPowerUp() &&
					   !east.getHasPowerUp() && !west.getHasPowerUp()){
						currentNode.randomizePowerUps();
					}
				}
			}
		}
	}
	/*private boolean isVisited(ArrayList<MazeNode> visited, MazeNode toCheck){
		boolean result = false;
		
		for (MazeNode curr : visited){
			if (curr.getX() == toCheck.getX() && curr.getY() == toCheck.getY()){
				result = true;
			}
		}
		return result;
	}*/
	
	private ArrayList<MazeNode> solution;
}
