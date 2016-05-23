import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
/*
 * ANY OBJECTS THAT USE THIS GRAPH MUST OVERRIDE THEIR HASHCODE OR
 * ELSE EQUALITY COMPARISONS WILL FAIL
 */
/**
 * 
 * @param <E> any object type to be stored in a graph
 */

public class Graph<E> {

	public Graph(){
		graph = new HashMap<E,ArrayList<E>>();
	}
	/**
	 * checks to see if a node exists
	 * @param e node to be checked
	 * @return return true if the node is in the graph, false if not
	 */
	public boolean nodeExists(E e){
		for(E node: graph.keySet()){
			if(node.equals(e))
				return true;
		}
		return false;
	}
	/**
	 * adds a node to the graph if it not already in there
	 * @param e a node to be added
	 */
	public void addVertex(E e){
		if(!nodeExists(e)){
			ArrayList<E> neighbours = new ArrayList<E>();
			graph.put(e, neighbours);
		}
	}
	/**
	 * adds a edge between nodes e and f only, and only if they exist
	 * @param e first node 
	 * @param f second node
	 */
	public void addEdge(E e, E f){
		if (nodeExists(e)){
			for(E node : graph.keySet()){
				if (node.equals(e)){
					ArrayList<E> neighbours =graph.get(node);
					neighbours.add(f);
				}
			}
		} 
	}
	/**
	 * adds a edge at a specified location in the arrayList of edges it belongs too
	 * @param e
	 * @param f
	 * @param index
	 */
	public void addEdgeAtIndex(E e, E f,int index){
		if (nodeExists(e)){
			for(E node : graph.keySet()){
				if (node.equals(e)){
					ArrayList<E> neighbours =graph.get(node);
					neighbours.add(index,f);
				}
			}
		} 
	}
	/**
	 * print the graph in a human readable way
	 */
	public void printGraph(){
		for (E node : graph.keySet()){
			System.out.print("node " + node.toString() + " : ");
			for(E neighbour : graph.get(node)){
				System.out.print("["+neighbour.toString()+"]" + "->");
			}
			System.out.println("\n");
		}
	}
	/**
	 * get all the nodes in the graph
	 * @return a list of all the nodes in the graph
	 */
	public ArrayList<E> getAllVertices(){
		ArrayList<E> allNodes = new ArrayList<E>();
		for (E node : graph.keySet()){
			allNodes.add(node);
		}
		return allNodes;
	}
	/**
	 * get the neighbours of node e
	 * @param e the node to get neighbours for
	 * @return a list containing neighbours of e
	 */
	public ArrayList<E> getNeighbours(E e){
		if(!nodeExists(e)){
			System.out.println("node not found");
			return null;
		}
		ArrayList<E> neighbours = new ArrayList<E>();
		for (Entry<E, ArrayList<E>> entry : graph.entrySet()) {
		    E key = entry.getKey();
		    ArrayList<E> neighbourList = entry.getValue();
		    if(key.equals(e)){
		    	neighbours = neighbourList;
			    break;
		    }
		}

		return neighbours;
	}
	/**
	 * replace the node oldE with newE
	 * @param oldE
	 * @param newE
	 */
	public void replace(E oldE, E newE){
		boolean isNodeReplaced = false;
		for (E vertex : graph.keySet()){
			if(isNodeReplaced){
				break;
			}
			for(E neighbour : graph.get(vertex)){
				if(neighbour.equals(oldE)){
					int index = graph.get(vertex).indexOf(neighbour);
					graph.get(vertex).set(index, newE);
					isNodeReplaced=true;
					break;
				}
			}
		}
	}
	private HashMap<E,ArrayList<E>> graph;
	
}
