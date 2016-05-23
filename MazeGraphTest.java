import java.util.ArrayList;

/*
 * this file is used to test MazeNode and MazeGraphImplementation, and indirectly Graph classes
 * to run this properly, you need to go into run configurations>vm arguments and add -ea if
 * you are using eclipse. On the command line, use java -ea
 * It also contains some example maze construction and iterations
 */
public class MazeGraphTest {
	public static void main(String args[]){
		MazeGraphTest mazeGraphTests = new MazeGraphTest();
		mazeGraphTests.tests2();
		//mazeGraphTests.tests();
		//mazeGraphTests.exampleIteration1();
		//mazeGraphTests.exampleIteration2();
	}
	public void tests2(){
		MazeGraphImplementation m = exampleConstruction();
		m.printMaze();
		ArrayList<MazeNode> neighbours = m.getPathNeighbours(m.getMazeNode(9,9));
		/*for(MazeNode n: neighbours){
			System.out.println(n.toString());
		}*/
		
		neighbours = m.getPathNeighbours(m.getMazeNode(5,5));
		for(MazeNode n: neighbours){
			System.out.println(n.toString());
		}
		System.out.println();
		System.out.println(m.getMazeNode(5, 6));
		System.out.println(m.getMazeNode(5, 4));
		System.out.println(m.getMazeNode(4, 5));
		System.out.println(m.getMazeNode(6, 5));
		
		neighbours = m.getPathNeighbours(m.getMazeNode(0,0));
		for(MazeNode n: neighbours){
			System.out.println(n.toString());
		}
		System.out.println();
		System.out.println(m.getMazeNode(0, 1));
		System.out.println(m.getMazeNode(1, 0));
		
	}
	public MazeGraphImplementation exampleConstruction(){
		MazeGraphImplementation m = new MazeGraphImplementation();
		int mazeSize = 10;
		for(int x=0; x<mazeSize; x++){
			for(int y=0; y<mazeSize; y++){
				if(x % 2 == 0)
					m.addMazeNode(new MazeNode(false,x,y));
				if(x % 2 == 1)
					m.addMazeNode(new MazeNode(true,x,y));
			}
		}
		return m;
	}
	public void exampleIteration1(){
		MazeGraphImplementation m = exampleConstruction();
		int mazeSize = m.mazeSize();
		for(int x=0; x<mazeSize; x++){
			for(int y=0; y<mazeSize; y++){
				MazeNode node = m.getMazeNode(x, y);
				System.out.print("(" + node.getX() + "," + node.getY() + ") ");
				if(y==mazeSize-1){
					System.out.println();
				}
			}
		}
	}
	public void exampleIteration2(){
		MazeGraphImplementation m = exampleConstruction();
		int mazeSize = m.mazeSize();
		for(int x=0; x<mazeSize; x++){
			for(int y=0; y<mazeSize; y++){
				MazeNode node = m.getMazeNode(x, y);
				if(node.getIsPath()){
					System.out.print("X ");
				} else {
					System.out.print("O ");
				}
				if(y==mazeSize-1){
					System.out.println();
				}
			}
		}
	}
	public void tests(){
		MazeGraphImplementation m = new MazeGraphImplementation();
		m.addMazeNode(new MazeNode(false,0,0));
		m.addMazeNode(new MazeNode(false,0,1));
		m.addMazeNode(new MazeNode(false,0,2));
		m.addMazeNode(new MazeNode(false,0,3));
		
		m.addMazeNode(new MazeNode(true,1,0));
		m.addMazeNode(new MazeNode(false,1,1));
		m.addMazeNode(new MazeNode(true,1,2));
		m.addMazeNode(new MazeNode(false,1,3));
		
		m.addMazeNode(new MazeNode(false,2,0));
		m.addMazeNode(new MazeNode(false,2,1));
		m.addMazeNode(new MazeNode(true,2,2));
		m.addMazeNode(new MazeNode(true,2,3));

		m.addMazeNode(new MazeNode(true,3,0));
		m.addMazeNode(new MazeNode(false,3,1));
		m.addMazeNode(new MazeNode(true,3,2));
		m.addMazeNode(new MazeNode(false,3,3));
		
		m.printMaze();
		
		MazeNode k1 = m.getMazeNode(0, 0);
		assert k1.getX() == 0;
		assert k1.getY() == 0;
		assert k1.getIsPath() == false;
		
		MazeNode k3 = m.getMazeNode(3, 1);
		assert k3.getX() == 3;
		assert k3.getY() == 1;
		assert k3.getIsPath() == false;
		
		MazeNode k2 = m.getMazeNode(5, 5);
		assert k2 == null;
		
		k2 = m.getMazeNode(-1, -1);
		assert k2 == null;
		
		MazeNode k4 = m.getMazeNode(3, 0);
		assert k4 != null;
		assert k4.getX() == 3;
		assert k4.getY() == 0;
		assert k4.getIsPath() == true;
		
		k4 = m.getMazeNode(1, 0);
		assert k4 != null;
		assert k4.getX() == 1;
		assert k4.getY() == 0;
		assert k4.getIsPath() == true;
		
		
		m.updateMazeNode(new MazeNode(true,2,0));
		m.updateMazeNode(new MazeNode(true,2,1));
		m.updateMazeNode(new MazeNode(false,2,2));
		m.updateMazeNode(new MazeNode(false,2,3));
		
		k4 = m.getMazeNode(2, 0);
		assert k4 != null;
		assert k4.getX() == 2;
		assert k4.getY() == 0;
		assert k4.getIsPath() == true;
		
		
		k4 = m.getMazeNode(2, 1);
		assert k4.getX() == 2;
		assert k4.getY() == 1;
		assert k4.getIsPath() == true;
		
		k4 = m.getMazeNode(2, 2);
		assert k4.getX() == 2;
		assert k4.getY() == 2;
		assert k4.getIsPath() == false;
		
		k4 = m.getMazeNode(2, 3);
		assert k4.getX() == 2;
		assert k4.getY() == 3;
		assert k4.getIsPath() == false;
		
		m.updateMazeNode(new MazeNode(true,3,0));
		m.updateMazeNode(new MazeNode(false,3,1));
		m.updateMazeNode(new MazeNode(true,3,2));
		m.updateMazeNode(new MazeNode(false,3,3));
		
		k4 = m.getMazeNode(3, 0);
		assert k4.getX() == 3;
		assert k4.getY() == 0;
		assert k4.getIsPath() == true;
		
		k4 = m.getMazeNode(3, 1);
		assert k4.getX() == 3;
		assert k4.getY() == 1;
		assert k4.getIsPath() == false;
		
		k4 = m.getMazeNode(3, 2);
		assert k4.getX() == 3;
		assert k4.getY() == 2;
		assert k4.getIsPath() == true;
		
		k4 = m.getMazeNode(3, 3);
		assert k4.getX() == 3;
		assert k4.getY() == 3;
		assert k4.getIsPath() == false;
		
		System.out.println();
		m.printMaze();
		
		MazeNode s = m.getMazeNode(1,1);
		assert s != null;
		MazeNode t = m.getEast(s);
		assert t.getX() == 2;
		assert t.getY() == 1;
		
		m.updateMazeNode(new MazeNode(true,t.getX(),t.getY()));
		
		System.out.println();
		m.printMaze();
		System.out.println();
		
		ArrayList<MazeNode> neighbours = m.getNeighbours(m.getMazeNode(3, 1));
		for(MazeNode n: neighbours){
			System.out.println(n.toString());
		}
		assert neighbours.contains(m.getMazeNode(3, 3));
		assert neighbours.contains(m.getMazeNode(1, 1));
		
		neighbours = m.getNeighbours(m.getMazeNode(0, 0));
		assert neighbours.contains(m.getMazeNode(2, 0));
		assert neighbours.contains(m.getMazeNode(0, 2));
		
		neighbours = m.getNeighbours(m.getMazeNode(0, 3));
		assert neighbours.contains(m.getMazeNode(0, 1));
		assert neighbours.contains(m.getMazeNode(2, 3));
		
		neighbours = m.getNeighbours(m.getMazeNode(1, 0));
		assert neighbours.contains(m.getMazeNode(3, 0));
		assert neighbours.contains(m.getMazeNode(1, 2));
		
		neighbours = m.getNeighbours(m.getMazeNode(3, 0));
		assert neighbours.contains(m.getMazeNode(1, 0));
		assert neighbours.contains(m.getMazeNode(3, 2));
		
		k2 = m.getMazeNode(4, 4);
		assert k2 == null;
		
		//neighbours = m.getNeighbours(m.getMazeNode(4, 4));
		//assert neighbours == null;
		
		
		m.addMazeNode(new MazeNode(true,0,0));
		m.addMazeNode(new MazeNode(true,1,0));
		System.out.println();
		m.printMaze();
	}
}
