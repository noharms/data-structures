package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTreeTest {

    @Test
    void empty_graph_is_not_a_tree() {
        UnweightedGraph<Object> g = new UnweightedGraph<>();
        assertFalse(g.isTree());
    }

    @Test
    void single_node_is_a_tree() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);

        assertTrue(g.isTree());
    }

    @Test
    void two_unconnected_nodes_are_not_a_tree() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);

        assertFalse(g.isTree());
    }

    @Test
    void two_nodes_with_directed_edge_are_a_tree() {
        /*
         *           1 -> 2
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addDirectedEdge(1, 2);

        assertTrue(g.isTree());
    }

    @Test
    void two_nodes_with_undirected_edge_are_a_tree() {
        /*
         *           1 - 2
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addUndirectedEdge(1, 2);

        assertTrue(g.isTree());
    }

    @Test
    void three_nodes_with_two_having_undirected_edge_but_one_unconnected_are_not_a_tree() {
        /*
         *           1 - 2
         *
         *           3
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);

        assertFalse(g.isTree());
    }

    @Test
    void three_nodes_all_connected_by_linear_path_are_a_tree() {
        /*
         *             1
         *           /
         *          2 --- 3
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);

        assertTrue(g.isTree());
    }

    @Test
    void three_nodes_all_connected_by_cycle_are_not_a_tree() {
        /*
         *             1
         *           /   \
         *          2 --- 3
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        g.addUndirectedEdge(3, 1);

        assertFalse(g.isTree());
    }

    @Test
    void four_nodes_with_three_connected_in_linear_path_but_one_unconnected_are_not_a_tree() {
        /*
         *             1
         *           /
         *          2 --- 3
         *
         *             4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);

        assertFalse(g.isTree());
    }

    @Test
    void four_nodes_all_connected_in_linear_path_are_a_tree() {
        /*
         *             1
         *           /
         *          2 --- 3
         *                |
         *                4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        g.addUndirectedEdge(3, 4);

        assertTrue(g.isTree());
    }

    @Test
    void four_nodes_all_connected_but_containing_a_cycle_are_not_a_tree() {
        /*
         *             1
         *           /
         *          2 --- 3
         *            \   |
         *             \  |
         *               4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        g.addUndirectedEdge(3, 4);
        g.addUndirectedEdge(2, 4);

        assertFalse(g.isTree());
    }

    @Test
    void four_nodes_with_one_connecting_two_all_others_are_a_tree() {
        /*
         *               1
         *            /  |  \
         *          2    3   4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(1, 3);
        g.addUndirectedEdge(1, 4);

        assertTrue(g.isTree());
    }

    @Test
    void tree_with_sub_trees() {
        /*
         *                1
         *             /  | \
         *            /   |  \
         *          2     3   4
         *        / | \   |   | \
         *       5  6  7  8   9  10
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.addNode(10);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(1, 3);
        g.addUndirectedEdge(1, 4);
        g.addUndirectedEdge(2, 5);
        g.addUndirectedEdge(2, 6);
        g.addUndirectedEdge(2, 7);
        g.addUndirectedEdge(3, 8);
        g.addUndirectedEdge(4, 9);
        g.addUndirectedEdge(4, 10);

        assertTrue(g.isTree());
    }

    @Test
    void almost_a_tree_but_one_small_cycle_violates_definition() {
        /*
         *                1
         *             /  | \
         *            /   |  \
         *          2     3   4
         *        / | \   |   | \
         *       5  6  7  8   9  10
         *       |                |
         *       -----------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addNode(8);
        g.addNode(9);
        g.addNode(10);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(1, 3);
        g.addUndirectedEdge(1, 4);
        g.addUndirectedEdge(2, 5);
        g.addUndirectedEdge(2, 6);
        g.addUndirectedEdge(2, 7);
        g.addUndirectedEdge(3, 8);
        g.addUndirectedEdge(4, 9);
        g.addUndirectedEdge(4, 10);
        g.addUndirectedEdge(5, 10);

        assertFalse(g.isTree());
    }

    @Test
    void two_nodes_with_a_directed_edge_are_a_tree() {

        /*
         *               1
         *               |
         *               v
         *               2
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addDirectedEdge(1, 2);

        assertTrue(g.isTree());
    }

    @Test
    void three_nodes_with_directed_edges_are_a_tree() {

        /*
         *                 1
         *               /   \
         *              v     v
         *              2     3
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);

        assertTrue(g.isTree());
    }

    @Test
    void four_nodes_with_directed_edges_from_one_to_all_others_are_a_tree() {

        /*
         *               1
         *            /  |  \
         *           /   |   \
         *          v    v    v
         *          2    3    4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(1, 4);

        assertTrue(g.isTree());
    }

    @Test
    void if_the_graph_has_at_least_one_directed_edge_any_undirected_edge_will_violate_the_tree_definition() {

        /*
         *               1
         *            /  ^  \
         *           /   |   \
         *          v    v    v
         *          2    3    4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(3, 1);
        g.addDirectedEdge(1, 4);

        assertFalse(g.isTree());
    }

    @Test
    void directed_graph_whose_undirected_equivalent_is_not_a_tree_is_not_considered_a_tree() {

        /*
         *               1
         *            /  |  \
         *           /   |   \
         *          v    v    v
         *          2    3 -> 4
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(1, 4);
        g.addDirectedEdge(3, 4);

        assertFalse(g.isTree());
    }
}
