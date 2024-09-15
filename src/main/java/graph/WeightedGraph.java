package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A weighted graph based on a map whose keys are the nodes of the graph and whose values, if present, are the edges
 * (given by the neighbor and the weight of the connection).
 * <br>
 * Differs from the unweighted version mainly in the {@link Graph#shortestPath(Object, Object)} algorithm.
 * <br>
 * The graph is built iteratively by the client by adding nodes and edges.
 * <br>
 * The graph can be directed or undirected. The client simply has to use the separate APIs when constructing the
 * {@link UnweightedGraph#addDirectedEdge(Object, Object)}
 * {@link UnweightedGraph#addUndirectedEdge(Object, Object)}.
 */
public class WeightedGraph<T> extends Graph<T> {

    private static final Integer INFINITY = Integer.MAX_VALUE;

    private final Map<T, Map<T, Integer>> nodesToEdges = new HashMap<>();

    private Integer weight(T from, T to) {
        return nodesToEdges.get(from).get(to);
    }

    @Override
    public int size() {
        return nodesToEdges.keySet().size();
    }

    @Override
    public Set<T> nodes() {
        return nodesToEdges.keySet();
    }

    @Override
    public boolean contains(T value) {
        return nodesToEdges.containsKey(value);
    }

    @Override
    public void addNode(T value) {
        throwIfFound(value);
        nodesToEdges.put(value, new HashMap<>());
    }

    /**
     * If the edge already exists, the weight is overwritten.
     */
    public void addDirectedEdge(T from, T to, int weight) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        throwIfNegative(weight);
        nodesToEdges.get(from).put(to, weight);
    }

    /**
     * If the edge already exists, the weight is overwritten.
     */
    public void addUndirectedEdge(T from, T to, int weight) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        throwIfNegative(weight);
        addDirectedEdge(from, to, weight);
        addDirectedEdge(to, from, weight);
    }

    private void throwIfNegative(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Negative weights are not allowed - %s.".formatted(weight));
        }
    }

    @Override
    public Set<T> allNeighbors(T value) {
        throwIfNotFound(value);
        return nodesToEdges.get(value).keySet();
    }

    @Override
    public Set<T> allUpstreamNeighbors(T value) {
        return nodesToEdges
            .entrySet()
            .stream()
            .filter(e -> e.getValue().containsKey(value))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    @Override
    public void addDirectedEdge(Edge<T> edge) {
        if (edge instanceof Edge.WeightedEdge<T> e) {
            addDirectedEdge(e.from(), e.to(), e.weight());
        } else {
            throw new IllegalArgumentException("Can only add weighted edges to weighted graph.");
        }
    }

    @Override
    public Set<Edge<T>> edges() {
        return nodesToEdges.entrySet()
            .stream()
            .flatMap(nodeToNeighborsWithWeights -> nodeToNeighborsWithWeights
                .getValue()
                .entrySet()
                .stream()
                .map(edge -> new Edge.WeightedEdge<>(nodeToNeighborsWithWeights.getKey(), edge.getKey(), edge.getValue()))
            )
            .collect(Collectors.toSet());
    }

    public Map<T, Integer> allEdges(T from) {
        return Collections.unmodifiableMap(nodesToEdges.getOrDefault(from, new HashMap<>()));
    }

    public int getEdge(T from, T to) {
        return nodesToEdges.get(from).get(to);
    }

    /**
     * If an edge (with any weight) exists, it will be removed. If not, this method has no effect.
     */
    public void removeEdgeIfExisting(T from, T to) {
        if (nodesToEdges.containsKey(from)) {
            nodesToEdges.get(from).remove(to);
        }
    }

    public Set<Integer> allWeights() {
        return nodes().stream()
            .map(this::allEdges)
            .map(Map::values)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public List<T> shortestPath(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return dijkstra(from, to);
    }

    @Override
    public WeightedGraph<T> copyWithoutEdges() {
        final WeightedGraph<T> copy = new WeightedGraph<>();
        copy.addNodes(nodes());
        return copy;
    }

    /**
     * A bit simpler implementation than the original algorithm by Djikstra:
     * instead of using a min priority queue to find the next cheapest
     * unvisited node to consider, we simply search the whole graph each time again. As a consequence, this algorithm
     * is O(n^2) instead of O((n+m)*log(n)), where n is the number of nodes and m the number of edges.
     */
    // note: could be generalised to return the distances to all nodes by not stopping once a target is found
    private List<T> dijkstra(T from, T to) {
        Set<T> visited = new HashSet<>();
        Map<T, Integer> nodeToDistance = initialNodeToDistanceMap(from);
        Map<T, T> nodeToParent = new HashMap<>();

        Optional<T> nextNode = Optional.of(from);
        while (nextNode.isPresent() && !nextNode.get().equals(to)) {
            T current = nextNode.get();
            for (T neighbor : unvisitedNeighbors(current, visited)) {
                int oldDistanceToNeighbor = nodeToDistance.get(neighbor);
                int newDistanceToNeighbor = nodeToDistance.get(current) + weight(current, neighbor);
                if (newDistanceToNeighbor < oldDistanceToNeighbor) {
                    nodeToDistance.put(neighbor, newDistanceToNeighbor);
                    nodeToParent.put(neighbor, current);
                }
            }
            visited.add(current);
            nextNode = nearestUnvisitedNode(visited, nodeToDistance);
        }
        return nextNode.isPresent() ? reconstructPath(to, nodeToParent) : new LinkedList<>();
    }

    private Map<T, Integer> initialNodeToDistanceMap(T startNode) {
        return nodesToEdges.keySet().stream()
            .collect(Collectors.toMap(
                node -> node,
                node -> node.equals(startNode) ? 0 : INFINITY,
                (key, val) -> val,
                HashMap::new
            ));
    }

    private Optional<T> nearestUnvisitedNode(Set<T> visited, Map<T, Integer> nodeToMinimalDistance) {
        return nodesToEdges.keySet().stream()
            .filter(node -> !visited.contains(node))
            .filter(node -> nodeToMinimalDistance.get(node) < INFINITY)
            .min(Comparator.comparingInt(nodeToMinimalDistance::get));
    }

    /**
     * Differs from {@link WeightedGraph#shortestPath(Object, Object)} in that it uses the original algorithm
     * with a min priority queue as an optimisation. This should be O((n+m) * log(n)), n number nodes, m number edges
     */
    public List<T> shortestPathUsingMinQueue(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return dijkstraUsingMinQueue(from, to);
    }

    private List<T> dijkstraUsingMinQueue(T from, T to) {
        Set<T> visited = new HashSet<>();
        Map<T, Integer> nodeToDistance = initialNodeToDistanceMap(from);
        Map<T, T> nodeToParent = new HashMap<>();
        PriorityQueue<NodeWithPriority<T>> searchQueue = new PriorityQueue<>();
        searchQueue.add(new NodeWithPriority<>(from, 0));

        while (!searchQueue.isEmpty() && !searchQueue.peek().node().equals(to)) {
            T current = searchQueue.poll().node();
            for (T neighbor : unvisitedNeighbors(current, visited)) {
                int oldDistanceToNeighbor = nodeToDistance.get(neighbor);
                int newDistanceToNeighbor = nodeToDistance.get(current) + weight(current, neighbor);
                if (newDistanceToNeighbor < oldDistanceToNeighbor) {
                    nodeToDistance.put(neighbor, newDistanceToNeighbor);
                    nodeToParent.put(neighbor, current);
                }
                searchQueue.remove(new NodeWithPriority<>(neighbor, oldDistanceToNeighbor)); // O(log(n))
                searchQueue.add(new NodeWithPriority<>(neighbor, newDistanceToNeighbor)); // O(log(n))
            }
            visited.add(current);
        }
        return searchQueue.isEmpty() ? new LinkedList<>() : reconstructPath(to, nodeToParent);
    }

    private record NodeWithPriority<V>(V node, int priority) implements Comparable<NodeWithPriority<V>> {
        @Override
        public int compareTo(WeightedGraph.NodeWithPriority<V> o) {
            return priority - o.priority;
        }
    }
}

