package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumFlowTest {

    @Test
    void two_nodes_one_edge_gives_obvious_max_flow() {
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addDirectedEdge(0, 1, 42);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(42, maximumFlow.computeMaxFlow(0, 1));
    }

    @Test
    void two_nodes_one_cyclic_edge_gives_obvious_max_flow() {
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addDirectedEdge(0, 1, 42);
        graph.addDirectedEdge(1, 0, 3);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(42, maximumFlow.computeMaxFlow(0, 1));
    }

    @Test
    void three_linear_nodes_gives_obvious_max_flow() {
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addDirectedEdge(0, 1, 42);
        graph.addDirectedEdge(1, 2, 20);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(20, maximumFlow.computeMaxFlow(0, 2));
    }

}