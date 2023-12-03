/*
 * AUTHOR: Tanner Jackson
 * FILE: DGraph.java
 * Assignment: PA11 - The Traveling Salesman Problem
 * COURSE: CSc 210; Fall 2021
 * Purpose: This is the DGraph class file which works with
 * the DGraph object. It has many methods within it including
 * search methods which are the main part of this project
 * that search through the graph to find the cost efficient way
 * of going through it. The heuristic and backtracking methods
 * are two different ways of searching a graph, heuristic being
 * the way in which it searches the graph quickly by making decisions
 * at each step. Backtrack that searches every possibilty and is the
 * brute force search for this graph.
 */

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.text.DecimalFormat;


// directed weighted graph represented using adjacency list  
class DGraph
{ 
	class Edge
	{
		int label;
		float weight;
		public Edge(int v, float w)
		{
			label = v;
			weight = w;
		}
	}
	
	private int numVertices;
	private ArrayList<LinkedList<Edge>> adjList = new ArrayList<>();
	private float MAX_WEIGHT = 0;
	private double min_cost = 10000000000000000000.0;
	private List<Integer> visitOrder = new ArrayList<>();
	
	DGraph(int numVertices) { 
		this.numVertices = numVertices; 
		for (int i = 0; i < numVertices; i++)
			adjList.add(new LinkedList<Edge>());
	} 
 
	/*
	 * This method adds an edge to the graph. Helps create
	 * the graph by adding an edge that can then be accessed
	 * later.
	 * 
	 * @param int, integer for the vertices to add an edge to. 
	 * @param int, integer for the edge to be added.
	 * @param float, weight of the edge.
	 */
	void addEdge(int u, int v, float w) { 
		if (w >= MAX_WEIGHT)
			MAX_WEIGHT = w;
		adjList.get(u).add(new Edge(v,w)); 
		//adjList.get(v).add(new Edge(u,w)); 
	} 

	/*
	 * This method returns an edge for a certain vertices
	 * at a certain position. It gets this by searching through
	 * the graph for the correct edge to return.
	 * 
	 * @param int, integer for correct vertex.
	 * @param int, integer for place in vertex list.
	 * @return edge, correct edge returned.
	 */
	Edge getEdge(int u, int v) { 
		for (int i = 0; i < adjList.get(u).size(); i++)
			if (adjList.get(u).get(i).label == v)
				return adjList.get(u).get(i);
		return null;
	} 
	
	/*
	 * This method searches the graph in a heuristic manner
	 * in which it makes a choice at each step to be able
	 * to get the lowest cost. This is a fast search that is
	 * not typically the correct path.
	 * 
	 * @param int, integer for starting location.
	 */
	public void TSP(int start) { 
		boolean visited[] = new boolean[numVertices]; 
		LinkedList<Integer> queue = new LinkedList<>(); 
		queue.add(start); 
		visited[start]=true; 
		Double cost = 0.0;
		int num_check = numVertices;
		ArrayList<Integer> visitOrder = new ArrayList<>();
		while (queue.size() != 0)  { 
			int u = queue.pollFirst(); 
			visitOrder.add(u + 1);
			ArrayList<Integer> path = new ArrayList<>();
			path.add(u);
			float min_wt = MAX_WEIGHT;
			boolean check = false;
			int min_v = 0;
			Edge v_edge = new Edge(0, 0);
			Edge min_v_edge = new Edge(0, 0);
			for (int i = 0; i < adjList.get(u).size(); i++) {
				int v = adjList.get(u).get(i).label;
				v_edge = adjList.get(u).get(i);
				if (!visited[v]) {
					if (v_edge.weight < min_wt) {
						check = true;
						min_wt = v_edge.weight;
						min_v = v;
						min_v_edge = v_edge;
					}
				}
			}
			if (check == true) {
				queue.add(min_v);
				visited[min_v] = true;
				cost += min_v_edge.weight;
			}
			num_check --;
			// Case to get the last vertex to go back to the 
			// 0 vertex to find the cost.
			if (num_check == 0) {
				for (int i = 0; i < adjList.get(u).size(); i++) {
					int v = adjList.get(u).get(i).label;
					v_edge = adjList.get(u).get(i);
					if (v == 0)
						cost += v_edge.weight;
				}
			}
		} 
		Double costDouble = (double)Math.round(cost * 10d) / 10d;
		System.out.println("cost = " + costDouble + ", visitOrder = " + visitOrder);
	}
 
	/*
	 * A method that searches the graph in a brute force method that
	 * searches every possible path to be able to determine which path
	 * is the best to go through. It does this using recursion to find
	 * the correct path using backtracking.
	 * 
	 * @param int, start location.
	 */
    public void hamiltonianCycles(int start)
    {
    	boolean[] visited = new boolean[numVertices];
    	List<Integer> path = new ArrayList<>();
    	path.add(start);
    	hamiltonianCycles(start, visited, path, 0);
		Double costDouble = (double)Math.round(min_cost * 10d) / 10d;
    	System.out.println("cost = " + costDouble + ", visitOrder = " + visitOrder);
    }
    
    /*
     * Recursive helper function that does the same thing as described
     * in the previous method. This is just a helper method to that
     * method.
     * 
     * @param int, vertex.
     * @boolean[], checks if the vertex edge is visited or not.
     * List<Integer>, path it is currently on.
     * double, current cost of the path.
     */
    public void hamiltonianCycles(int u, boolean[] visited, List<Integer> path, double cost)
    {
		visited[u] = true;

    	// if all the vertices are visited, then the Hamiltonian cycle exists
    	if (path.size() == numVertices){
    		for (int i = 0; i < path.size() - 1; i++) {
    			for (int j = 0; j < adjList.get(path.get(i)).size(); j++) {
    	    		Integer v = adjList.get(path.get(i)).get(j).label;
    				Edge v_edge = adjList.get(path.get(i)).get(j);
    				if (v == path.get(i + 1)) {
    					cost += v_edge.weight;
    				}
    			}
    		}
    		int last_one = path.get(path.size() - 1);	
    		for (int c = 0; c < adjList.get(last_one).size(); c++) {
	    		Integer v = adjList.get(last_one).get(c).label;
				Edge v_edge = adjList.get(last_one).get(c);
				if (v == 0) {
					cost += v_edge.weight;
					break;
				}
    		}
    		if (cost < min_cost) {
    			min_cost = cost;
    			visitOrder.clear();
    			for (int k = 0; k < path.size(); k++) {
    				visitOrder.add(path.get(k) + 1);
    			}
    		}
    		return;
    	}

    	// Check if every edge starting from vertex `u` leads to a solution or not
    	for (int i = 0; i < adjList.get(u).size(); i++) {
    		Integer v = adjList.get(u).get(i).label;
    		if (!visited[v]) {
    			path.add(v);
    			hamiltonianCycles(v, visited, path, cost);
    			// backtrack for the path
    			visited[v] = false;  // so v could be used in another path
    			path.remove(path.size() - 1);
    		}
    	}
    }
    
	/*
	 * A method that searches the graph in a brute force method that
	 * searches every possible path to be able to determine which path
	 * is the best to go through. It does this using recursion to find
	 * the correct path using backtracking. Has one small fix in which
	 * it destroys the path if it is over the minimum cost.
	 * 
	 * @param int, start location.
	 */
    public void MINE(int start)
    {
    	boolean[] visited = new boolean[numVertices];
    	List<Integer> path = new ArrayList<>();
    	path.add(start);
    	MINE(start, visited, path, 0);
		Double costDouble = (double)Math.round(min_cost * 10d) / 10d;
    	System.out.println("cost = " + costDouble + ", visitOrder = " + visitOrder);
    }
    
    /*
     * Recursive helper function that does the same thing as described
     * in the previous method. This is just a helper method to that
     * method.
     * 
     * @param int, vertex.
     * @boolean[], checks if the vertex edge is visited or not.
     * List<Integer>, path it is currently on.
     * double, current cost of the path.
     */
    public void MINE(int u, boolean[] visited, List<Integer> path, double cost)
    {
    	// Checks if the current cost is more than the minimum cost.
		if (cost > min_cost)
			return;
		visited[u] = true;

    	// if all the vertices are visited, then the Hamiltonian cycle exists
    	if (path.size() == numVertices){
    		for (int i = 0; i < path.size() - 1; i++) {
    			for (int j = 0; j < adjList.get(path.get(i)).size(); j++) {
    	    		Integer v = adjList.get(path.get(i)).get(j).label;
    				Edge v_edge = adjList.get(path.get(i)).get(j);
    				if (v == path.get(i + 1)) {
    					cost += v_edge.weight;
    				}
    			}
    		}
    		int last_one = path.get(path.size() - 1);	
    		for (int c = 0; c < adjList.get(last_one).size(); c++) {
	    		Integer v = adjList.get(last_one).get(c).label;
				Edge v_edge = adjList.get(last_one).get(c);
				if (v == 0) {
					cost += v_edge.weight;
					break;
				}
    		}
    		if (cost < min_cost) {
    			min_cost = cost;
    			visitOrder.clear();
    			for (int k = 0; k < path.size(); k++) {
    				visitOrder.add(path.get(k) + 1);
    			}
    		}
    		return;
    	}
    	// Check if every edge starting from vertex `u` leads to a solution or not
    	for (int i = 0; i < adjList.get(u).size(); i++) {
    		Integer v = adjList.get(u).get(i).label;
    		if (!visited[v]) {
    			path.add(v);
    			MINE(v, visited, path, cost);
    			// backtrack for the path
    			visited[v] = false;  // so v could be used in another path
    			path.remove(path.size() - 1);
    		}
    	}
    }
    
    /*
     * The toString method that allows the DGraph to
     * be printed back to the user if they want to do 
     * that.
     */
	public String toString()
	{
		String str = "";
		for (int i = 0; i < numVertices; i++) 
		{
			str += i + ": [";
			LinkedList<Edge> list = adjList.get(i);

		    for(int j = 0; j < list.size(); j++)
				str += list.get(j).label + "/"+ list.get(j).weight + ", ";

			str += "]\n";
		}
		return str;		
	}
}
