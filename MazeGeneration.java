import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGeneration {
	public MazeGeneration(int m) {
		mazeSize = m;
		mazeSize = 2 * mazeSize - 1;	
		mazeGeneration = new MazeGraphImplementation();
		
		for(int x=0; x<mazeSize; x++){
			for(int y=0; y<mazeSize; y++){
				if(x % 2 == 0 && y % 2 == 0)
					mazeGeneration.addMazeNode(new MazeNode(true,x,y));
				else
					mazeGeneration.addMazeNode(new MazeNode(false,x,y));
			}
		}
		//mazeGeneration.printMaze();
		
		System.out.println("Doing dfs Generation..");
		dfsGeneration(mazeGeneration.getMazeNode(0,0));		
	}
	
	//get start node
	public MazeNode getStart(){
		return mazeGeneration.getMazeNode(0, 0);
	}
	//get end node
	public MazeNode getEnd(){
		return mazeGeneration.getMazeNode(mazeSize-1, mazeSize-1);
	}
	public MazeGraphImplementation getMaze(){
		return this.mazeGeneration;
	}
	
	private void dfsGeneration(MazeNode mazeNode) {
		mazeNode.setVisited(true);
		ArrayList<MazeNode> neighbours = mazeGeneration.getNeighbours(mazeNode);	
		//printMazeGeneration();
		Collections.shuffle(neighbours);
		for(MazeNode neighbourNode: neighbours) {
			Random rand = new Random();
			int  randomNum = rand.nextInt(10) + 1;

			if(!neighbourNode.isVisited()){
				//System.out.println("Neighbor to go to: (" + neighbourNode.getX() + "," + neighbourNode.getY() + ")" );
				removeWall(mazeNode, neighbourNode);
				if ((neighbourNode.getX()/2)%3==0 && randomNum<3){
					continue;
				}
				dfsGeneration(neighbourNode);
			}
		}	
	}
	

	
	private void removeWall(MazeNode m1, MazeNode m2) {
		int x1 = m1.getX();
		int y1 = m1.getY();
		int x2 = m2.getX();
		int y2 = m2.getY();
		
		//System.out.println("m1:" +x1 + " "+ y1 + " m2:" + x2 + " " + y2);
		
		int xGate, yGate;
		
		if(x1 == x2) {
			//System.out.println("X equal: setting path");
			yGate = (y1 + y2)/2;
			xGate = x1;
			//System.out.println("gateNode:" + xGate + " " + yGate);
			MazeNode gateNode = mazeGeneration.getMazeNode(xGate, yGate);
			if(gateNode != null){
				gateNode.setIsPath(true);

			}
			
		} else if (y1 == y2) {
			//System.out.println("Y equal: setting path");
			xGate = (x1 + x2)/2;
			yGate = y1;
			//System.out.println("gateNode:" + xGate + " " + yGate);
			MazeNode gateNode = mazeGeneration.getMazeNode(xGate, yGate);
			if(gateNode != null)
				gateNode.setIsPath(true);
		}
	}
	
	
	public void printMazeGeneration() {
		int i,j;
		System.out.println();
		for (i = 0; i < mazeGeneration.mazeSize(); i++) {	
			for(j = 0; j < mazeGeneration.mazeSize(); j++) {
				//System.out.println("("+ j + "," + i + ")");
				if(mazeGeneration.getMazeNode(j, i).getIsPath()) {
					System.out.print(" ");	
				} else {
					System.out.print("1");	
				}		
			}
			System.out.println();	
		}
		System.out.println();
	}
	
	private MazeGraphImplementation mazeGeneration;
	private int mazeSize;
}
