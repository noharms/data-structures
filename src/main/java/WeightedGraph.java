import java.util.*;
import java.util.stream.Collectors;

/**
 * A weighted graph based on a map whose keys are the nodes of the graph and whose values, if present, are the edges
 * (given by the neighbor and the weight of the connection).
 * <br>
 * Differs from the unweighted version mainly in the {@link Graph#shortestPath(Object, Object)} algorithm.
 */
public class WeightedGraph<T> extends Graph<T> {

    private static final Integer INFINITY = Integer.MAX_VALUE;

    private final Map<T, Map<T, Integer>> nodesToEdges = new HashMap<>();

    private Integer weight(T from, T to) {
        return nodesToEdges.get(from).get(to);
    }

    @Override
    public boolean contains(T value) {
        return nodesToEdges.containsKey(value);
    }

    @Override
    void addNode(T value) {
        throwIfFound(value);
        nodesToEdges.put(value, new HashMap<>());
    }

    public void addDirectedEdge(T from, T to, int weight) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        throwIfNegative(weight);
        nodesToEdges.get(from).put(to, weight);
    }

    public void addUndirectedEdge(T from, T to, int weight) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        throwIfNegative(weight);
        nodesToEdges.get(from).put(to, weight);
        nodesToEdges.get(to).put(from, weight);
    }


    private void throwIfNegative(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Negative weights are not allowed - %s.".formatted(weight));
        }
    }

    @Override
    Set<T> allNeighbors(T value) {
        throwIfNotFound(value);
        return nodesToEdges.get(value).keySet();
    }

    @Override
    public List<T> shortestPath(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return dijkstra(from, to);
    }

    private List<T> dijkstra(T from, T to) {
        Set<T> visited = new HashSet<>();
        Map<T, Integer> nodeToDistance = initializeDistances(from);
        Map<T, T> nodeToParent = new HashMap<>();

        Optional<T> nextNode = Optional.of(from);
        while (nextNode.isPresent() && !nextNode.get().equals(to)) {
            T current = nextNode.get();
            Set<T> unvisitedNeighbors = unvisitedNeighbors(visited, current);
            for (T neighbor : unvisitedNeighbors) {
                int newDistanceToNeighbor = nodeToDistance.get(current) + weight(current, neighbor);
                if (newDistanceToNeighbor < nodeToDistance.get(neighbor)) {
                    nodeToDistance.put(neighbor, newDistanceToNeighbor);
                    nodeToParent.put(neighbor, current);
                }
            }
            visited.add(current);
            nextNode = nearestUnvisitedNode(visited, nodeToDistance);
        }
        return nextNode.isPresent() ? reconstructPath(to, nodeToParent) : new LinkedList<>();
    }

    private Set<T> unvisitedNeighbors(Set<T> visited, T current) {
        return allNeighbors(current).stream()
                                    .filter(neighbor -> !visited.contains(neighbor))
                                    .collect(Collectors.toSet());
    }

    private Map<T, Integer> initializeDistances(T startNode) {
        Set<T> directNeighbors = allNeighbors(startNode);
        return nodesToEdges.keySet().stream()
                           .collect(Collectors.toMap(
                                   node -> node,
                                   node -> directNeighbors.contains(node) ? weight(startNode, node) : INFINITY,
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

}

