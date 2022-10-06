package graph;

import java.util.*;

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
    void addNode(T value) {
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

        // contains the same elements that are in the searchQueue but as a set, in order to get contains() in O(1)
        Set<T> currentlyInSearchQueue = new HashSet<>(Set.of(from));

        boolean connectionFound = false;
        while (!searchQueue.isEmpty()) {
            T current = searchQueue.remove();
            visited.add(current);
            if (current.equals(to)) {
                connectionFound = true;
                break;
            } else {
                for (T neighbor : unvisitedNeighbors(current, visited)) {
                    // need to check that the neighbor was not yet added to the search queue
                    // imagine e.g. a 3x3 matrix
                    // 000
                    // 000
                    // 000
                    // when (0,0) is processed, (1,0) and (0,1) are in the search queue
                    // when (1,0) is processed  (0,1), (2,0), (1, 1) are in the search queue
                    // when (0,1) is processed  (2,0), (1,1) are still in the queue and (0,2), (1, 1) are the neighbors
                    // but we dont want to add (1,1) again to the search queue
                    if (!currentlyInSearchQueue.contains(neighbor)) {
                        nodeToParent.put(neighbor, current);
                        currentlyInSearchQueue.add(neighbor);
                        searchQueue.add(neighbor);
                    }
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
