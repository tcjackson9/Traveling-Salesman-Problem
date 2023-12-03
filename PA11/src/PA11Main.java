/*
 * AUTHOR: Tanner Jackson
 * FILE: PA11Main.java
 * Assignment: PA11 - The Traveling Salesman Problem
 * COURSE: CSc 210; Fall 2021
 * Purpose: This is the main file that takes in a file and 
 * a command. It creates a graph based on the contents of the file
 * and computes either a heuristic, backtrack, backtrack improved,
 * or timed working of the graph. Meaning that it searches through 
 * graph to find the least costly path. In the heuristic approach
 * it does this by choosing the best option at each stop, while the
 * backtrack approach searches through all of the options to find the 
 * best path. The timing command times these three other commands to see
 * how long they take to be computed.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;

public class PA11Main {

	public static void main(String[] args) {
        Scanner file = null;
        try {
        	file = new Scanner(new File(args[0]));} 
        catch (FileNotFoundException e) {
            System.out.println("File not found");;
            return;}
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("Out of bounds");;
        	return;
        }
        DGraph g = createGraph(file);
        String command = null;
        try {
        	command = args[1];}
        catch (ArrayIndexOutOfBoundsException e) {
        	System.out.println("No Command");
        	return;
        }
	    if (command.equals("HEURISTIC"))
	    	g.TSP(0);
	    else if (command.equals("BACKTRACK"))
	    	g.hamiltonianCycles(0);
	    else if (command.equals("MINE"))
	    	g.MINE(0);
	    else if (command.equals("TIME"))
	    	time(g);
	}
	
	/*
	 * A method that computes how long each of the commands takes
	 * to compute. It then gives the time back to the user so that
	 * it can be seen which methods work faster. Uses nanoTime to 
	 * be able to make these calculations.
	 * 
	 * @param g, a DGraph object with all of the graph contents.
	 */
	public static void time(DGraph g) {
		long startTimeTSP = System.nanoTime();
		g.TSP(0);
		long endTimeTSP = System.nanoTime();
		long durationTSP = endTimeTSP - startTimeTSP;
		System.out.println("heuristic: " + 
		durationTSP / 1000000 + " milliseconds");
		long startTimeBACKTRACK = System.nanoTime();
		g.hamiltonianCycles(0);
		long endTimeBACKTRACK = System.nanoTime();
		long durationBACKTRACK = endTimeBACKTRACK - startTimeBACKTRACK;
		System.out.println("backtrack: " + 
		durationBACKTRACK / 1000000 + " milliseconds");
		long startTimeMINE = System.nanoTime();
		g.MINE(0);
		long endTimeMINE = System.nanoTime();
		long durationMINE = endTimeMINE - startTimeMINE;
		System.out.println("mine: " + durationMINE / 1000000 + " milliseconds");
		
	}
	
	/*
	 * A method that creates the DGraph object from the file that
	 * is passed in the argument by the user. It does this by reading
	 * in the file and then putting each line into the graph object
	 * so that it has all of the connections.
	 * 
	 * @param Scanner, a file that was passed in the argument for the
	 * run configurations.
	 * @return DGraph, returns the DGraph object that was 
	 * created by this method.
	 */
    public static DGraph createGraph(Scanner file) 
    {
    	//---- read past comments
        String startLine = null;
        while (file.hasNextLine()) {
        	startLine = file.nextLine();
            if (!startLine.startsWith("%"))
                break;
        }
        
        //---- read the number of vertices and create a MyGraph
        String[] startLineSplit = startLine.split(" ");
        int numVertices = Integer.parseInt(startLineSplit[0]);
        DGraph graph = new DGraph(numVertices);
        
        //---- read the edge info and add the edges to the graph
        while (file.hasNextLine()) {
            String[] s = file.nextLine().split("( )+");
            graph.addEdge(Integer.parseInt(s[0])-1, Integer.parseInt(s[1])-1,
            		Float.parseFloat(s[2]));
        }
        return graph;
    }	
}
