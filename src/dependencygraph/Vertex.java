package dependencygraph;

import java.util.LinkedList;

/**
 * Vertex
 * 
 * @author matthew.towles
 * @param <T>
 * @date Mar 1, 2019
 */
public class Vertex<T> {

    private boolean discovered;
    private LinkedList<Integer> connections;
    private String name;
    
    public Vertex(String name) {
        discovered = false;
        this.name = name;
    }
    
    public boolean isDiscovered() {
        return discovered;
    }
    
    public void discover() {
        this.discovered = true;
    }
    
    public void unDiscover() {
        this.discovered = false;
    }
}
