package dependencygraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

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
    
    /**
     * ArrayList of adjacency lists
     * vertexMap.get(T) to get the key for
     * each adjacency list
     */
    private ArrayList<LinkedList<Integer>> vertexLists;
    
    /**
     * Constructor
     * @param file
     * @throws java.io.FileNotFoundException
     */
    public DirectedGraph(File file) throws FileNotFoundException {
        vertexMap = new HashMap<>();
        vertexLists = new ArrayList<>();
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
     * 
     * @param vertex - starting point to sort from
     * @throws InvalidClassException 
     */
    public void topologicalSort(T vertex) throws InvalidClassException {
        if (!vertexMap.containsKey(vertex)) {
            throw new InvalidClassException(vertex + " is not in file given.");
        }
        
        // add discovered vertices here
        ArrayList<T> discovered = new ArrayList<>();
        
        /*
        generate a reverse topological order 
        so after it completes
        the forward order gotten by popping vertices from stack

        depth_first_search(vertex s) 
        if s is discovered 
            throw cycle detected exception 
        if s is finished 
            return 
        mark s as discovered 
        for all adjacent vertices v 
            depth_first_search(v) 
        mark s as finished 
        push s onto the stack
        */
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
