package graph;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

/**
 * An abstract graph: nodes are values of a generic type T (say integer nodes); edges can be directed or undirected
 * <br><br>
 * Since our nodes are pure values (and since there is no order in a graph) we cannot allow duplicate values - we need
 * something to uniquely identify any given node of the graph. If a client would want to have a graph with duplicate
 * values, that would still be possible because they could wrap each value and decorate them with some other identifier.
 * <br><br>
 * Caveat: T must implement {@link Object#hashCode()} and {@link Object#equals(Object)} for this class to work correctly
 */
public abstract class Graph<T> {

    /**
     * based on the equals method of the generic type T
     */
    abstract public boolean contains(T value);

    abstract public void addNode(T value);

    public void addNodes(Collection<T> values) {
        for (T node : values) {
            addNode(node);
        }
    }

    abstract public int size();

    abstract public Set<T> nodes();

    abstract public Set<T> neighbors(T value);

    abstract public Set<T> allUpstreamNeighbors(T value);

    private Map<T, Integer> countUpstreamNeighbors() {
        return nodes().stream().collect(Collectors.toMap(node -> node, node -> this.allUpstreamNeighbors(node).size()));
    }

    abstract public void addDirectedEdge(Edge<T> edge);

    public void addDirectedEdges(Collection<Edge<T>> edges) {
        for (Edge<T> e : edges) {
            addDirectedEdge(e);
        }
    }

    public void addUndirectedEdge(Edge<T> edge) {
        addDirectedEdge(edge);
        addDirectedEdge(edge.opposite());
    }

    // adding an undirected is just a convenience to avoid two calls of addDirectedEdge
    public void addUndirectedEdges(Collection<Edge<T>> edges) {
        for (Edge<T> e : edges) {
            addUndirectedEdge(e);
        }
    }

    /**
     * Note: we represent undirected edges as two opposing edges and, if existing, both will be returned here
     */
    abstract public Set<Edge<T>> edges();

    abstract public List<T> shortestPath(T from, T to);

    abstract public Graph<T> copyWithoutEdges();

    public Graph<T> copy() {
        final Graph<T> copy = copyWithoutEdges();
        copy.addDirectedEdges(edges());
        return copy;
    }

    public boolean isDirected() {
        final Set<Edge<T>> allEdges = edges();
        return allEdges.stream().anyMatch(edge -> !allEdges.contains(edge.opposite()));
    }

    public boolean isUndirected() {
        final Set<Edge<T>> allEdges = edges();
        return allEdges.stream().allMatch(edge -> allEdges.contains(edge.opposite()));
    }

    void throwIfFound(T value) {
        if (contains(value)) {
            String msg = "Duplicate nodes are not allowed - %s already exists in graph".formatted(value);
            throw new IllegalArgumentException(msg);
        }
    }

    void throwIfNotFound(T value) {
        if (!contains(value)) {
            throw new IllegalArgumentException("Node %s is not found in graph".formatted(value));
        }
    }

    public Graph<T> transpose() {
        final Graph<T> transposed = copyWithoutEdges();
        final List<Edge<T>> oppositeEdges = edges().stream().map(Edge::opposite).toList();
        transposed.addDirectedEdges(oppositeEdges);
        return transposed;
    }

    /**
     * Finds a breadth-first path from node {@code from} to node {@code to}.
     * <br>
     * Note: this works both on a weighted and an unweighted graph -however, only on a unweighted graph does the bfs
     * path correspond to the shortest path between the two nodes.
     *
     * @return a list of nodes starting with {@code from} and ending with {@code to} that describes a path connecting
     * the two nodes; the path is minimal in terms of the number of nodes contained; if no connection exists an
     * empty list is returned
     */
    public List<T> bfsPath(T from, T to) {
        Queue<T> searchQueue = new LinkedList<>(List.of(from));
        Set<T> processed = new HashSet<>(Set.of(from));
        Map<T, T> nodeToParent = new HashMap<>();

        T current = null;
        while (!searchQueue.isEmpty() && !to.equals(current)) {
            current = searchQueue.remove();
            for (T neighbor : neighbors(current)) {
                // need to check that the neighbor was not yet added to the search queue
                // imagine e.g. a graph of cells for a 3x3 matrix
                // 000
                // 000
                // 000
                // when (0,0) is processed, (1,0) and (0,1) are in the search queue
                // when (1,0) is processed  (0,1), (2,0), (1, 1) are in the search queue
                // when (0,1) is processed  (2,0), (1,1) are still in the queue and (0,2), (1, 1) are the neighbors
                // but we dont want to add (1,1) again to the search queue
                if (!processed.contains(neighbor)) {
                    processed.add(neighbor);
                    searchQueue.add(neighbor);
                    nodeToParent.put(neighbor, current);
                }
            }
        }

        boolean connectionFound = to.equals(current);
        return connectionFound ? reconstructPath(to, nodeToParent) : new LinkedList<>();
    }

    Set<T> unvisitedNeighbors(T current, Set<T> visited) {
        return neighbors(current).stream()
            .filter(neighbor -> !visited.contains(neighbor))
            .collect(Collectors.toSet());
    }

    static <U> List<U> reconstructPath(U endNode, Map<U, U> nodeToParent) {
        List<U> path = new LinkedList<>();
        path.add(endNode);
        U current = endNode;
        while (nodeToParent.get(current) != null) {
            current = nodeToParent.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    public static <V> Set<V> dfsTraversal(V startNode, Graph<V> g) {
        if (!g.contains(startNode)) {
            return emptySet();
        }
        final Set<V> result = new HashSet<>();
        dfsRecurse(startNode, g, new HashSet<>(), result);
        return result;
    }

    public static <V> void dfsRecurse(V node, Graph<V> g, Set<V> visited, Set<V> result) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        result.add(node);
        for (V neighbor : g.neighbors(node)) {
            dfsRecurse(neighbor, g, visited, result);
        }
    }

    /**
     * Checks by a depth-first-search if two nodes {@code from} and {@code to} are connected.
     */
    public boolean dfsIsConnected(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return dfsUntilEqualNodes(from, to, new HashSet<>());
    }

    private boolean dfsUntilEqualNodes(T candidate, T target, HashSet<T> visited) {
        if (candidate.equals(target)) {
            return true;
        }
        visited.add(candidate);
        for (T neighbor : unvisitedNeighbors(candidate, visited)) {
            if (dfsUntilEqualNodes(neighbor, target, visited)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses Kahn's algorithm to return a list of all nodes contained in the graph such that the parent-child relations
     * of the graph are respected in the order of nodes. That is, if in the graph nodeY is a child of nodeX, nodeY will
     * appear after nodeX in the returned list. Note that this ordering is non-unique and we just return some order.
     * <br><br>
     * O(V + E) in space and time, V number of vertices (=nodes), E number of edges
     * <br><br>
     * @throws if the graph is not a directed, acyclic graph
     */
    public List<T> topologicalSort() {
        if (isUndirected()) {
            throw new IllegalStateException("Topological sort can only be applied on a directed graph.");
        }
        final Map<T, Integer> nodeToDependencyCount = countUpstreamNeighbors();
        final Set<T> sources = nodeToDependencyCount
            .entrySet()
            .stream()
            .filter(e -> e.getValue() == 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
        // a queue for the nodes without missing dependencies; initially, it contains only sources but as we work off
        // nodes, other nodes can become dependency-free; if the q is empty but we have not yet visited the whole graph
        // it means there must be a cyclic dependency in the graph, so we have to stop and throw an error
        final Queue<T> q = new LinkedList<>(sources);
        final Set<T> visited = new HashSet<>();
        final List<T> result = new ArrayList<>();
        while (!q.isEmpty()) {
            final T node = q.remove();
            if (!visited.contains(node)) {
                visited.add(node);
                result.add(node);
                for (T child : neighbors(node)) {
                    final int newDependencyCount = nodeToDependencyCount.get(child) - 1;
                    nodeToDependencyCount.put(child, newDependencyCount);
                    if (newDependencyCount == 0) {
                        q.add(child);
                    }
                }
            }
        }
        if (visited.size() == nodes().size()) {
            return result;
        } else {
            throw new IllegalStateException("The graph has cyclic dependencies, topological sort impossible. First find SCCs, then condense SCCs to supernodes, then use this API.");
        }
    }

    /**
     * A component of a graph is a maximal subset of nodes and edges in which there is a path from any single node to
     * any other node, i.e. all nodes are pairwise connected. Maximal means that the component is defined to contain all
     * connected nodes (one cannot leave out a connected node for a set of nodes to be called a component).
     * <br><br>
     * In undirected graphs the algorithm is simple: traverse all nodes of the graph and start a DFS from each node to
     * find the component of the node; keep track of all visited nodes to not traverse the same component twice; this
     * is O(n) in time and space.
     */
    public Set<Set<T>> weaklyConnectedComponents() {
        if (isDirected()) {
            throw new IllegalStateException("Turn your graph to the equivalent undirected graph to use this API.");
        }
        final Set<Set<T>> components = new HashSet<>();
        final Set<T> visited = new HashSet<>();
        for (T node : nodes()) {
            if (!visited.contains(node)) {
                final Set<T> newComponent = dfsTraversal(node, this);
                components.add(newComponent);
                visited.addAll(newComponent);
            }
        }
        return components;
    }

    /**
     * A component of a graph is a maximal subset of nodes and edges in which there is a path from any single node to
     * any other node, i.e. all nodes are pairwise connected. Maximal means that the component is defined to contain all
     * connected nodes (one cannot leave out a connected node for a set of nodes to be called a component).
     * <br><br>
     * While the term is intuitive for undirected graphs, for directed graphs, there is a distinction between weakly
     * connected (WCC) and strongly connected components (SCC): weakly means to define components as if the edges were
     * undirected; strongly means to actually respect the direction of the edges (note that this means a source node
     * with only outgoing directions is ALWAYS its own component because no path leads back to it).
     */
    public Set<Set<T>> stronglyConnectedComponents() {
        final Set<Set<T>> result = new HashSet<>();

        final Set<T> alreadyInComponents = new HashSet<>();
        final List<T> byDFSFinishingTime = sortByDFSFinishingTime();
        final Graph<T> transposed = transpose();

        while (!byDFSFinishingTime.isEmpty()) {
            final T node = byDFSFinishingTime.removeLast();
            if (!alreadyInComponents.contains(node)) {
                final Set<T> newComponent = new HashSet<>();
                dfsRecurse(node, transposed, alreadyInComponents, newComponent);
                result.add(newComponent);
            }
        }
        return result;
    }

    /**
     * The nodes which finish first will be at the start of the returned list
     */
    private List<T> sortByDFSFinishingTime() {
        final List<T> sorted = new LinkedList<>();
        final HashSet<T> visited = new HashSet<>();
        for (T node : nodes()) {
            dfsStoreByFinishTime(node, visited, sorted);
        }
        return sorted;
    }

    private void dfsStoreByFinishTime(T node, Set<T> visited, List<T> result) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        for (T neighbor : neighbors(node)) {
            dfsStoreByFinishTime(neighbor, visited, result);
        }
        result.addLast(node);
    }

    @Override
    public int hashCode() {
        // this works, assuming T implements hashcode
        return Objects.hash(nodes(), edges());
    }

    @Override
    public boolean equals(Object other) {
        // this works, assuming T implements equals
        return other instanceof Graph<?> otherGraph
            && this.nodes().equals(otherGraph.nodes())
            && this.edges().equals(otherGraph.edges());
    }

    @Override
    public String toString() {
        return "Graph(" + nodes().stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }
}
