import java.util.Queue;
import java.util.*;

public class UnweightedGraph<T> extends Graph<T> {

    private final Map<T, Set<T>> nodesToEdges = new HashMap<>();

    @Override
    public boolean contains(T value) {
        return nodesToEdges.containsKey(value);
    }

    @Override
    void addNode(T value) {
        throwIfFound(value);
        nodesToEdges.put(value, new HashSet<>());
    }

    public void addDirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
    }

    public void addUndirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
        nodesToEdges.get(to).add(from);
    }

    @Override
    Set<T> allNeighbors(T value) {
        throwIfNotFound(value);
        return nodesToEdges.get(value);
    }

    @Override
    public List<T> shortestPath(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return bfsPath(from, to);
    }

    private List<T> bfsPath(T from, T to) {
        Queue<T> searchQueue = new LinkedList<>(List.of(from));
        Set<T> visited = new HashSet<>();
        Map<T, T> nodeToParent = new HashMap<>();

        boolean connectionFound = false;
        while (!searchQueue.isEmpty()) {
            T current = searchQueue.remove();
            visited.add(current);
            if (current.equals(to)) {
                connectionFound = true;
                break;
            } else {
                for (T neighbor : unvisitedNeighbors(current, visited)) {
                    nodeToParent.put(neighbor, current);
                    searchQueue.add(neighbor);
                }
            }
        }
        return connectionFound ? reconstructPath(to, nodeToParent) : new LinkedList<>();
    }

    public boolean dfsIsConnected(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return dfsRecursive(from, to, new HashSet<>());
    }

    private boolean dfsRecursive(T candidate, T target, HashSet<T> visited) {
        if (candidate.equals(target)) {
            return true;
        }
        visited.add(candidate);
        for (T neighbor : unvisitedNeighbors(candidate, visited)) {
            if (dfsRecursive(neighbor, target, visited)) {
                return true;
            }
        }
        return false;
    }

}
