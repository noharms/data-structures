package graph.adjacencylist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Adjacency list based graph of integers: each node in the graph must be uniquely identified by the numbers from 0 to
 * n - 1, where n is the number of nodes. Duplicates are not allowed. The graph is unweighted.
 */
public class IntegerGraph {

    // contains for each node the outgoing edges; e.g. adjacencyList[0] = [3, 4] means node 0 has an edge to 3 and 4
    private final List<List<Integer>> adjacencyList;

    public IntegerGraph(List<List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public List<Integer> nodes() {
        return IntStream.range(0, numberNodes()).boxed().toList();
    }

    public List<Integer> neighbors(int nodeId) {
        return adjacencyList.get(nodeId);
    }

    public int numberNodes() {
        return adjacencyList.size();
    }

    public List<Integer> dfs() {
        final List<Integer> result = new ArrayList<>();
        final Set<Integer> visited = new HashSet<>();
        for (int id : nodes()) {
            if (!visited.contains(id)) {
                dfsRecursive(id, visited, result);
            }
        }
        return result;
    }

    private void dfsRecursive(int id, Set<Integer> visited, List<Integer> result) {
        if (visited.contains(id)) {
            return;
        }
        visited.add(id);
        result.add(id);
        for (int neighborId : neighbors(id)) {
            dfsRecursive(neighborId, visited, result);
        }
    }

}
