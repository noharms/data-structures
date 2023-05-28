package graph;

import java.util.*;

import static java.util.Collections.emptySet;

/**
 * An unweighted graph that uses a map for storing the nodes of the graph as keys, and the edges of the graph
 * as values for each node.
 * <br>
 * The graph is built iteratively by the client by adding nodes and edges.
 * <br>
 * The graph can be directed or undirected. The client simply has to use the separate APIs when constructing the
 * {@link UnweightedGraph#addDirectedEdge(Object, Object)}
 * {@link UnweightedGraph#addUndirectedEdge(Object, Object)}.
 */
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

    /**
     * Returns a set of all components of the graph, where a component is a set of nodes that are connected to each
     * other but to no other node in the graph.
     */
    public Set<Set<T>> findComponents() {
        Set<Set<T>> components = new HashSet<>();
        Set<T> visited = new HashSet<>();
        for (T node : nodesToEdges.keySet()) {
            if (!visited.contains(node)) {
                Set<T> component = new HashSet<>();
                component.add(node);
                component.addAll(findAllConnected(node));
                components.add(component);
                visited.addAll(component);
            }
        }
        return components;
    }

    /**
     * Returns a set of all components of the graph, where a component is a set of nodes that are connected to each
     * other but to no other node in the graph.
     */
    public Set<T> findAllConnected(T node) {
        if (!contains(node)) {
            return emptySet();
        }
        Set<T> connectedNodes = new HashSet<>();
        connectedNodes.add(node);
        findAllConnected(node, new HashSet<>(), connectedNodes);
        return connectedNodes;
    }

    private void findAllConnected(T node, Set<T> visited, Set<T> result) {
        for (T neighbor : nodesToEdges.get(node)) {
            if (!visited.contains(neighbor)) {
                result.add(neighbor);
                visited.add(neighbor);
                findAllConnected(neighbor, visited, result);
            }
        }
    }

}
