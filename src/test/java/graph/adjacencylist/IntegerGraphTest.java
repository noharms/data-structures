package graph.adjacencylist;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerGraphTest {

    @Test
    void empty_graph_contains_no_nodes() {
        assertEquals(emptyList(), new IntegerGraph(emptyList()).nodes());
    }

    @Test
    void single_node_graph_contains_only_node_0() {
        List<List<Integer>> adjacencyList = Collections.nCopies(1, emptyList());
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0), g.nodes());
    }

    @Test
    void multiple_nodes_graph_contains_nodes_from_0_to_n_minus_1() {
        List<List<Integer>> adjacencyList = Collections.nCopies(5, emptyList());
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 1, 2, 3, 4), g.nodes());
    }

    @Test
    void empty_graph_dfs_gives_empty_list() {
        assertEquals(emptyList(), new IntegerGraph(emptyList()).dfs());
    }

    @Test
    void single_node_graph_dfs_gives_list_with_single_node() {
        List<List<Integer>> adjacencyList = Collections.nCopies(1, emptyList());
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0), g.dfs());
    }

    @Test
    void multiple_unconnected_nodes_dfs_goes_from_0_to_n_minus_1() {
        List<List<Integer>> adjacencyList = Collections.nCopies(5, emptyList());
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 1, 2, 3, 4), g.dfs());
    }

    @Test
    void multiple_nodes_with_linear_connections_dfs_follows_connection() {
        /* 0 -> 4 -> 2 -> 1 -> 3 */
        List<List<Integer>> adjacencyList = List.of(
            List.of(4),
            List.of(3),
            List.of(1),
            List.of(),
            List.of(2)
        );
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 4, 2, 1, 3), g.dfs());
    }

    @Test
    void multiple_nodes_with_non_trivial_connections_dfs() {
        /*
            0 -> 4 -> 1
            |
            ---> 2 --> 3
        */
        List<List<Integer>> adjacencyList = List.of(
            List.of(4, 2),
            List.of(),
            List.of(3),
            List.of(),
            List.of(1)
        );
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 4, 1, 2, 3), g.dfs());
    }

    @Test
    void two_nodes_with_forth_and_back_cycle_dfs() {
        /*
            0 <-> 1
        */
        List<List<Integer>> adjacencyList = List.of(
            List.of(1),
            List.of(0)
        );
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 1), g.dfs());
    }


    @Test
    void multiple_nodes_with_cycle_dfs() {
        /*

          3 <- 0 -> 2 -> 1
               ^    |
               |    v
                --- 4
        */
        List<List<Integer>> adjacencyList = List.of(
            List.of(2, 3),
            List.of(),
            List.of(4, 1),
            List.of(),
            List.of(0)
        );
        IntegerGraph g = new IntegerGraph(adjacencyList);

        assertEquals(List.of(0, 2, 4, 1, 3), g.dfs());
    }
}