//Title: PathFinder class
 // Author: Dilara Cagla Sarisu
// Student ID: 202128201
// Description: This PathFinder class finds the shortest path in the graph and prints it

import java.util.LinkedList;
import java.util.Queue;

class PathFinder {
    private Graph graph;
    private int[] edgeTo; // array holding edge information for each node
    private boolean[] marked;   //array that holds whether nodes have been visited or not

    public PathFinder(Graph graph) { // constructor
        this.graph = graph;
        this.edgeTo = new int[graph.V()];
        this.marked = new boolean[graph.V()];
    }
    
    //finds the shortest path andprints it
    public void findAndPrintShortestPath() {
        bfs(graph.getStartNode()); // finds the path using breadth-first search (BFS)
         // if the end node cannot be reached, prints a message
        if (!marked[graph.getEndNode()]) {
            System.out.println("No path to the target.");
            return;
        }
        // constructs and prints the shortest path
        LinkedList<Integer> path = new LinkedList<>();
        for (int x = graph.getEndNode(); x != graph.getStartNode(); x = edgeTo[x]) {
            path.addFirst(x); // adds each node to the beginning of the path starting from the end node
        }
        path.addFirst(graph.getStartNode()); // adds the start node to the beginning of the path

        int rows = (int) Math.sqrt(graph.V()); // calculates the number of rows in the chessboard
        System.out.println(path.size() - 1 + " steps"); // prints the number of steps in the path
        // prints the path from start to end coordinates
        System.out.print("path from c" + (path.getFirst() / rows + 1) + "," + (path.getFirst() % rows + 1) + // calculates the row and column of the start cell
                " to c" + (path.getLast() / rows + 1) + "," + (path.getLast() % rows + 1) + ": "); // calculates the row and column of the end cell
        for (int step : path) { // prints each step in the path with coordinates
            System.out.print("c" + (step / rows + 1) + "," + (step % rows + 1) + " -> ");
        }
        System.out.println();
    }

// breadth-first search (BFS) algorithm
    private void bfs(int s) {
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true; // marks the start node as visited
        queue.add(s); // adds the start node to the queue
        while (!queue.isEmpty()) { // continues until the queue is empty
            int v = queue.remove(); // removes a node from the queue
            for (int w : graph.adj(v)) { // iterates over theadjacents of the removed node
                if (!marked[w]) { // if the neighbor node has not been visited,
                    edgeTo[w] = v; // records how we reached this node
                    marked[w] = true; // marks the adjacent node as visited
                    queue.add(w); // adds the adjacent node to the queue
                }
            }
        }
    }

}