import java.util.*;
import java.util.stream.Collectors;

/**
 * A weighted graph based on a map whose keys are the nodes of the graph and whose values, if present, are the edges
 * (given by the neighbor and the weight of the connection).
 * <br>
 * Differs from the unweighted version mainly in the {@link Graph#shortestPath(Object, Object)} algorithm.
 */
public class WeightedGraph<T> extends Graph<T> {

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

    public void addConnection(T from, T to, int weight) {
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
        return djikstra(from, to);
    }

    private List<T> djikstra(T from, T to) {
        Set<T> visited = new HashSet<>();
        Map<T, Integer> nodeToDistance = initializeDistances(from); // is O(n) space from beginning
        Map<T, T> nodeToParent = new HashMap<>(); // will fill up to O(n) space

        Optional<T> nextNode = nearestUnvisitedNode(visited, nodeToDistance);
        while (nextNode.isPresent() && !nextNode.get().equals(to)) {
            T current = nextNode.get();
            int distance = nodeToDistance.get(current);
            Set<T> neighbors = allNeighbors(current);
            for (T neighbor : neighbors) {
                int newDistanceToNeighbor = distance + weight(current, neighbor);
                if (newDistanceToNeighbor < nodeToDistance.get(neighbor)) {
                    nodeToDistance.put(neighbor, newDistanceToNeighbor);
                    nodeToParent.put(neighbor, current);
                }
            }
            nextNode = nearestUnvisitedNode(visited, nodeToDistance);
        }

        List<T> path = new LinkedList<>();
        if (nextNode.isPresent()) {
            path = backTrace(nextNode.get(), nodeToParent);
            Collections.reverse(path);
        }
        return path;
    }

    private List<T> backTrace(T node, Map<T, T> nodeToParent) {
        List<T> pathToRoot = new LinkedList<>();
        pathToRoot.add(node);
        while (nodeToParent.get(node) != null) {
            node = nodeToParent.get(node);
            pathToRoot.add(node);
        }
        return pathToRoot;
    }

    private Map<T, Integer> initializeDistances(T startNode) {
        Set<T> directNeighbors = allNeighbors(startNode);
        return nodesToEdges.keySet().stream().collect(Collectors.toMap(node -> node,
                                                                       node -> directNeighbors.contains(node) ?
                                                                           weight(startNode, node) :
                                                                           Integer.MAX_VALUE,
                                                                       (key, val) -> val,
                                                                       HashMap::new));
    }

    private Optional<T> nearestUnvisitedNode(Set<T> visited, Map<T, Integer> nodeToMinimalDistance) {
        return nodesToEdges.keySet().stream()
                           .filter(node -> !visited.contains(node))
                           .min(Comparator.comparingInt(nodeToMinimalDistance::get));
    }

}

