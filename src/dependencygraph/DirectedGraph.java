package dependencygraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * DirectedGraph
 * 
 * @author matthew.towles
 * @param <T>
 * @date Mar 1, 2019
 */
public class DirectedGraph<T> {

    /**
     * Keep track of number of vertices in graph
     * Increment with each added vertex
     */
    private int vertexIndex = 0;
    
    /**
     * Map vertex T to the index it has in ArrayList
     */
    private HashMap<T, Integer> vertexMap;
    private HashMap<Integer, T> vertexMapReverse;
    
    /**
     * ArrayList of adjacency lists
     * vertexMap.get(T) to get the key for
     * each adjacency list
     */
    private ArrayList<LinkedList<Integer>> vertexLists;
    
    
    private ArrayList<T> discoveredVertices;
    
    
    private Stack<T> vertexStack;
    
    /**
     * Constructor
     * @param file
     * @throws java.io.FileNotFoundException
     */
    public DirectedGraph(File file) throws FileNotFoundException {
        vertexMap = new HashMap<>();
        vertexMapReverse = new HashMap<>();
        vertexLists = new ArrayList<>();
        discoveredVertices = new ArrayList<>();
        vertexStack = new Stack<>();
        
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            T[] vertices = (T[]) scanner.nextLine().split(" ");
            LinkedList<Integer> adjList = new LinkedList<>();
            for (int i = 0; i < vertices.length; i++) {
                // catch everything for adjacency list
                // belonging to first entry
                if (i != 0) {
                    // if vertex already mapped, get that index as
                    // key for next addition to adjList
                    if (vertexMap.containsKey(vertices[i])) {
                        adjList.add(vertexMap.get(vertices[i]));
                    } else {
                        // otherwise just add where we are
                        adjList.add(vertexIndex);
                    }
                }
                // avoid duplications in map and array list
                if (!vertexMap.containsKey(vertices[i])) {
                    vertexMap.put(vertices[i], vertexIndex);
                    vertexMapReverse.put(vertexIndex, vertices[i]);
                    // add to array list too
                    // then update first item in this list
                    // with all items from adjList
                    vertexLists.add(vertexMap.get(vertices[i]), new LinkedList<>());
                    vertexIndex++;
                } 
            }
            // add the adjList to the first vertex on current line
            vertexLists.get(vertexMap.get(vertices[0])).addAll(adjList);
        }
    }
    
    
    /**
     * Generates a reverse topological order on a stack
     * Iterating over stack and popping will give results in
     * topological order
     * 
     * @param vertex - starting point to sort from
     * @throws InvalidClassException 
     * @throws dependencygraph.CycleException 
     */
    public void topologicalSort(T vertex) 
            throws InvalidClassException, CycleException {
        if (!vertexMap.containsKey(vertex)) {
            throw new InvalidClassException(vertex + " is not in file given.");
        }
        
        if (discoveredVertices.contains(vertex)) {
            throw new CycleException("Invalid class list. Cycle detected.");
        }
        
        // base case
        if (vertexStack.search(vertex) > -1) {
            return;
        }
        
        discoveredVertices.add(vertex);
        
        LinkedList<Integer> adjList = vertexLists.get(vertexMap.get(vertex));
        
        for (Integer vert : adjList) {            
            topologicalSort(vertexMapReverse.get(vert));
        }
        
        // mark vertex as finished? or is it being in the stack good enough?
        
        vertexStack.push(vertex);
    }

    public String getRecompilationOrder() throws Exception {
        if (vertexStack.empty()) {
            throw new Exception("Classes are not in order.");
        }
        String order = "";
        
        while (!vertexStack.empty()) {
            order += vertexStack.pop() + " ";
        }
        return order;
    }
    
    // getters:
    
    public int getVertexIndex() {
        return vertexIndex;
    }

    public HashMap<T, Integer> getVertexMap() {
        return vertexMap;
    }

    public ArrayList<LinkedList<Integer>> getVertexLists() {
        return vertexLists;
    }
}
