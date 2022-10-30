package graph.adjacencymatrix;

import graph.WeightedGraph;

import java.util.Arrays;
import java.util.List;

/**
 * A converter from the adjacency matrix representation of a matrix to the abstract {@link WeightedGraph} datastructure.
 * <br>
 * A graph can be represented as an adjacency matrix, in which matrix[i][j] holds the weight from node i to node j.
 */
public class AdjacencyConverter {

    private final int[][] adjacency;
    private final int nNodes;

    public AdjacencyConverter(int[][] adjacency) {
        throwIfNonQuadratic(adjacency);
        throwIfNonZeroSelfWeight(adjacency);
        this.adjacency = adjacency;
        this.nNodes = adjacency.length;
    }

    private void throwIfNonZeroSelfWeight(int[][] adjacency) {
        int nNodes = adjacency.length;
        for (int i = 0; i < nNodes; i++) {
            if (adjacency[i][i] != 0) {
                throw new IllegalArgumentException("The connection from a node to itself must be 0.");
            }
        }
    }

    private void throwIfNonQuadratic(int[][] adjacency) {
        int nNodes = adjacency.length;
        List<Integer> rowLengths = Arrays.stream(adjacency).map(arr -> arr.length).toList();
        if (rowLengths.stream().anyMatch(length -> length != nNodes)) {
            throw new IllegalArgumentException("The adjacency matrix must be quadratic.");
        }
    }

    public WeightedGraph<Integer> convert() {
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        addNodes(graph);
        addEdges(graph);
        return graph;
    }

    private void addEdges(WeightedGraph<Integer> graph) {
        for (int from = 0; from < nNodes; from++) {
            for (int to = 0; to < nNodes; to++) {
                if (from == to) {
                    continue;
                }
                graph.addDirectedEdge(from, to, adjacency[from][to]);
            }
        }
    }

    private void addNodes(WeightedGraph<Integer> graph) {
        for (int nodeId = 0; nodeId < nNodes; nodeId++) {
            graph.addNode(nodeId);
        }
    }
}
