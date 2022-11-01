package graph;

import graph.adjacencymatrix.AdjacencyConverter;
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

    @Test
    void wikipedia_example() {
        /*
         *     --------------------> node1 -----------------
         *     |       10              |          5        |
         *     |                       |                   v
         *    source (node0)           | 15               sink (node3)
         *     |                       |                   ^
         *     |       5               v         10        |
         *     --------------------> node2 ----------------
         *
         *  Maximum flow:
         *
         *    0 -> 2 -> 3       - 5
         *    0 -> 1 -> 3       - 5
         *    0 -> 1 -> 2 -> 3  - 5
         *
         */
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addDirectedEdge(0, 1, 10);
        graph.addDirectedEdge(0, 2, 5);
        graph.addDirectedEdge(1, 2, 15);
        graph.addDirectedEdge(1, 3, 5);
        graph.addDirectedEdge(2, 3, 10);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(15, maximumFlow.computeMaxFlow(0, 3));
    }

    @Test
    void geeks_for_geeks_example() {
        /*
         *     --------------------> node1 ----------------> node3 -----------
         *     |       16             ^|          12         / ^      20     |
         *     |                      ||                    /  |             v
         *    source (node0)        4 || 10         9      /   | 7        sink (node3)
         *     |                      ||     <------------/    |             ^
         *     |       13             |v   /        14         |       4     |
         *     --------------------> node2 ----------------> node4 -----------
         *
         * Maximum flow is achieved by the following configuration
         *
         *
         *     --------------------> node1 ----------------> node3 -----------
         *     |     11/16            ^|         12/12       / ^     19/20   |
         *     |                      ||                    /  |             v
         *    source (node0)      1/4 || 0/10     0/9      /   | 7/7      sink (node3)
         *     |                      ||     <------------/    |             ^
         *     |     12/13            |v   /      11/14        |     4/4     |
         *     --------------------> node2 ----------------> node4 -----------
         *
         */
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addDirectedEdge(0, 1, 16);
        graph.addDirectedEdge(0, 2, 13);
        graph.addDirectedEdge(1, 2, 10);
        graph.addDirectedEdge(1, 3, 12);
        graph.addDirectedEdge(2, 1, 4);
        graph.addDirectedEdge(2, 4, 14);
        graph.addDirectedEdge(3, 2, 9);
        graph.addDirectedEdge(3, 5, 20);
        graph.addDirectedEdge(4, 3, 7);
        graph.addDirectedEdge(4, 5, 4);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(23, maximumFlow.computeMaxFlow(0, 5));
    }


    @Test
    void illinois_uni_example() {
        /*                                        4
         *     --------------------> node1 -------------> node2
         *     |       4              \                 /   | 3
         *     |                      2 --> node3 ---> 1    v
         *    source (node0)                  |   \ -- 1 -> sink (node5)
         *     |                         ---> 1             ^
         *     |       2                /                   |
         *     --------------------> node4 --------------- 3
         *
         *  The following flows will form the maximum flow:
         *
         *    0 -> 4 -> 3 -> 5              -  1
         *    0 -> 1 -> 2 -> 5              -  3
         *    0 -> 4 -> 5                   -  1
         *    0 -> 1 -> 3 -> 4 -> 5         -  1
         *
         *  Note: this case is especially interesting because the flow from
         *        node3 to node4 is only opened up due to the other flow
         *        from node4 to node3; initially, there would be no capacity
         *        from node3 to node4
         */
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addDirectedEdge(0, 1, 4);
        graph.addDirectedEdge(0, 4, 2);
        graph.addDirectedEdge(1, 2, 4);
        graph.addDirectedEdge(1, 3, 2);
        graph.addDirectedEdge(2, 5, 3);
        graph.addDirectedEdge(3, 2, 1);
        graph.addDirectedEdge(3, 5, 1);
        graph.addDirectedEdge(4, 3, 1);
        graph.addDirectedEdge(4, 5, 3);

        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(6, maximumFlow.computeMaxFlow(0, 5));
    }

    @Test
    void google_foobar_example() {
        /*
         *             inf                            6                  4
         *     --------------------> node0 ---------------> node2 -------------> node4 ----------
         *     |                          \                /     \                 ^             | inf
         *     |                        -- \-------------->       \     4          |             v
         *    source (node6)           |    \                      ---------->     | 6          sink (node7)
         *     |                     5 |     ------- 6 ----->    ----------->-\----              ^
         *     |                       |                    |   |              \                 | inf
         *     --------------------> node1 ---------------> node3 -------------> node5------------
         *             inf                           2                   6
         *
         *   Maximum flow:
         *
         *    0 -> 2 -> 4             -  4
         *    0 -> 3 -> 5             -  6
         *    0 -> 2 -> 5             -  2
         *    1 -> 3 -> 4             -  2
         *    1 -> 2 -> 5             -  2
         *
         *   ----> total of 16
         */
        int[][] adjacencies = new int[][]{
                {0, 0, 4, 6, 0, 0, 0, 0},
                {0, 0, 5, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 4, 4, 0, 0},
                {0, 0, 0, 0, 6, 6, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, Integer.MAX_VALUE},
                {0, 0, 0, 0, 0, 0, 0, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacencies).connectExcept(0);
        MaximumFlow maximumFlow = new MaximumFlow(graph);

        assertEquals(16, maximumFlow.computeMaxFlow(6, 7));
    }
}
