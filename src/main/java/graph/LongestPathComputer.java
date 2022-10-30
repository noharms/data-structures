package graph;

import java.util.List;

/**
 * A simple algorithm to compute the longest path. The idea is to take the largest weight present in the graph as the
 * "optimum" (in the sense that it is the longest connection possible) and build an inverted graph whose weights
 * are build by subtracting the actual weight from the optimum. Edges that were previously costly are now cheap
 * and vice versa. Thus, the shortest path in the inverted graph should be the longest path in the original graph.
 * <br>
 * Caveat: we might get problems if the largest original weight is too large. Then, all previously small weights
 * will now be huge, and the sum of those large weights might overflow.
 */
public class LongestPathComputer<T> {

    private final WeightedGraph<T> originalGraph;
    private final WeightedGraph<T> invertedGraph;

    public LongestPathComputer(WeightedGraph<T> originalGraph) {
        this.originalGraph = originalGraph;
        if (originalGraph.allNodes().stream().allMatch(node -> originalGraph.allEdges(node).isEmpty())) {
            throw new IllegalArgumentException("For this algorithm to work we need edges in the graph.");
        }
        this.invertedGraph = invert(originalGraph);
    }

    /**
     * Computes the longest path by computing the shortest path on the inverted graph.
     */
    public List<T> longestPath(T from, T to) {
        return invertedGraph.shortestPath(from, to);
    }

    private WeightedGraph<T> invert(WeightedGraph<T> originalGraph) {
        int maxWeight = maxWeight(originalGraph);
        WeightedGraph<T> newGraph = new WeightedGraph<>();
        for (T node : originalGraph.allNodes()) {
            newGraph.addNode(node);
        }
        for (T node : originalGraph.allNodes()) {
            for (var edge : originalGraph.allEdges(node).entrySet()) {
                T to = edge.getKey();
                int weight = edge.getValue();
                int deviationFromOptimum = maxWeight - weight;
                newGraph.addDirectedEdge(node, to, deviationFromOptimum);
            }
        }
        return newGraph;
    }

    private int maxWeight(WeightedGraph<T> originalGraph) {
        return originalGraph.allWeights().stream()
                            .mapToInt(i -> i)
                            .max()
                            .orElseThrow();

    }

}
