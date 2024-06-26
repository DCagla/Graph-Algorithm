//Title: Graph class
 // Author: Dilara Cagla Sarisu
// Student ID: 202128201
// Description: This Graph class creates a graph representing the paths a knight can move on a board and finds 
//the shortest path from a given starting point of the horse to a given ending point. At the same time, main is in this class.

import java.io.BufferedReader;  //used to read chessboard data from a file
import java.io.FileReader;  //used to read streams of characters from files
import java.io.IOException; //to handle errors that may occur during check-in/check-out operations
import java.util.*;

public class Graph {
    private final int V; // stores the number of vertices (or nodes) in the graph. final so that it does not change after the chart is created.
    private int E; // stores the number of edges in the graph
    private List<Integer>[] adj; // an array of lists that stores the list of adjacent nodes for each node
    // stores the start and end node of the graph. the default is -1 because it does not assign a value
    private int startNode = -1;
    private int endNode = -1;
    private boolean[] obstacles; // an array that indicates whether each node is an tree

    public Graph(int V) { // constructor
        this.V = V; // assigns the number of vertices passed as a parameter to the class's V property
        this.E = 0; // initializes the number of edges to 0
        adj = (List<Integer>[]) new List[V]; // initializes the adj array to create a list of adjacent nodes for each node

        for (int v = 0; v < V; v++) { 
            adj[v] = new ArrayList<>();
        }
        obstacles = new boolean[V]; //initially, all values are set to false.
    }

    //adds an edge between two nodes v and w if neither of these nodes is a tree
    public void addEdge(int v, int w) {
        if (!obstacles[v] && !obstacles[w]) {
            adj[v].add(w);
            adj[w].add(v);
            E++;
        }
    }
    //returns the adjacency list
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public static void main(String[] args) {
        String inputFileName = "PA1_test.txt";
        //reads the dimensios from the file and creates a graph
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            Graph graph = new Graph(rows * cols);
            // determines the start, end and barrier nodes
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                for (int j = 0; j < cols; j++) {
                    char cell = line.charAt(j);
                    int nodeIndex = i * cols + j;
                    if (cell == 'K') {
                        graph.startNode = nodeIndex;
                    } else if (cell == 'G') {
                        graph.endNode = nodeIndex;
                    } else if (cell == 'T') {
                        graph.obstacles[nodeIndex] = true;
                    }
                }
            }
                //adds edges for each non-tree node based on the horse's movements
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (!graph.obstacles[i * cols + j]) {
                        addEdgesFromKnight(graph, i, j, rows, cols);
                    }
                }
            }
            
            PathFinder pathFinder = new PathFinder(graph);
            pathFinder.findAndPrintShortestPath();
            
        } catch (IOException e) {
            System.out.println("dosya okuma hatası: " + e.getMessage());
        }
    }

    // adds all edges to the graph that the knight can move from a given position
    private static void addEdgesFromKnight(Graph graph, int i, int j, int rows, int cols) {
        int[] dRow = {-2, -1, 1, 2, 2, 1, -1, -2}; //direction vectors
        int[] dCol = {1, 2, 2, 1, -1, -2, -2, -1}; 
        
        for (int k = 0; k < 8; k++) {
            int newRow = i + dRow[k];
            int newCol = j + dCol[k];
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) { //checks if the new location is within the boundaries of the board
                graph.addEdge(i * cols + j, newRow * cols + newCol);
            }
        }
    }
}