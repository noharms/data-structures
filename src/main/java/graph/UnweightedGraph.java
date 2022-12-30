package graph;

import java.util.*;

/**
 * An unweighted graph that uses a map for storing the nodes of the graph as keys, and the edges of the graph
 * as values for each node.
 * <br>
 * The graph is built iteratively by the client by adding nodes and edges.
 * <br>
 * The graph can be directed or undirected. The client simply has to use the separate APIs when constructing the
 * {@link UnweightedGraph#addDirectedEdge(Object, Object)}
 * {@link UnweightedGraph#addUndirectedEdge(Object, Object)}.
 * */
public class UnweightedGraph<T> extends Graph<T> {

    private final Map<T, Set<T>> nodesToEdges = new HashMap<>();

    @Override
    public int size() {
        return nodesToEdges.keySet().size();
    }

    @Override
    public boolean contains(T value) {
        return nodesToEdges.containsKey(value);
    }

    @Override
    public void addNode(T value) {
        throwIfFound(value);
        nodesToEdges.put(value, new HashSet<>());
    }

    /**
     * If the edge already exists, this method will not complain.
     */
    public void addDirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
    }

    /**
     * If the edge already exists, this method will not complain.
     */
    public void addUndirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
        nodesToEdges.get(to).add(from);
    }

    @Override
    public Set<T> allNeighbors(T value) {
        throwIfNotFound(value);
        return nodesToEdges.get(value);
    }

    @Override
    public List<T> shortestPath(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return bfsPath(from, to);
    }

}
