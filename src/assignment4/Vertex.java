package assignment4;

import java.util.HashMap;
import java.util.Map;

class Vertex<T>{
    private T name;

    // T is a vertex, Integer is the weight
    private Map<T, Integer> edges;

    public Vertex(T name) {
        this.name = name;
        this.edges = new HashMap<>();
    }

    public Map<T, Integer> getEdges() {
        return edges;
    }

    public void appendEdge(T nextVertex) {
        if (edges.containsKey(nextVertex)) {
            edges.put(nextVertex, edges.get(nextVertex) + 1);
        }
        else {
            edges.put(nextVertex, 1);
        }
    }

    public int getEdgeWeight(T nextVertex) {
        if (edges.containsKey(nextVertex)) {
            return edges.get(nextVertex);
        }
        else {
            return 0;
        }
    }
}
