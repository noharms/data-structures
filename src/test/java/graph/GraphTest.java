package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void has_cycles_false_on_empty_graph() {
        assertFalse(new UnweightedGraph<>().hasCycle());
    }

    @Test
    void has_cycles_false_on_one_node_graph() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);

        assertFalse(g.hasCycle());
    }

    @Test
    void has_cycles_false_on_multiple_nodes_no_edges() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        assertFalse(g.hasCycle());
    }

    @Test
    void has_cycles_false_on_multiple_nodes_all_connected_by_directed_edge_no_cycles() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);

        assertFalse(g.hasCycle());
    }

    @Test
    void has_cycles_false_on_multiple_nodes_some_connected_by_directed_edge_some_unconnected_no_cycles() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);

        assertFalse(g.hasCycle());
    }

    @Test
    void has_cycles_true_on_two_nodes_with_undirected_edge() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);

        assertTrue(g.hasCycle());
    }

    @Test
    void has_cycles_true_on_three_nodes_with_directed_cyclic_connection() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(2, 3);
        g.addDirectedEdge(3, 1);

        assertTrue(g.hasCycle());
    }

    @Test
    void has_cycle_false_on_three_nodes_forming_a_diamond_connection() {
        /*
                 1
               /   \
              v     v
              2     3
              |     |
              -> 4 <-
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(2, 4);
        g.addDirectedEdge(3, 4);

        assertFalse(g.hasCycle());
    }

    @Test
    void has_cycles_true_on_multiple_nodes_with_one_small_cycle() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(5, 4);
        g.addDirectedEdge(4, 6);
        g.addDirectedEdge(6, 7);
        g.addDirectedEdge(7, 4);

        assertTrue(g.hasCycle());
    }

    @Test
    void has_non_trivial_cycle_false_on_empty_graph() {
        assertFalse(new UnweightedGraph<>().hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_on_one_node_graph() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);

        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_on_multiple_nodes_no_edges() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_on_multiple_nodes_all_connected_by_directed_edge_no_cycles() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);

        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_on_multiple_nodes_some_connected_by_directed_edge_some_unconnected_no_cycles() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);

        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_on_two_nodes_with_undirected_edge() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);

        assertTrue(g.hasCycle());
        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_true_on_three_nodes_with_directed_cyclic_connection() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(2, 3);
        g.addDirectedEdge(3, 1);

        assertTrue(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_true_on_multiple_nodes_with_one_small_cycle() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(5, 4);
        g.addDirectedEdge(4, 6);
        g.addDirectedEdge(6, 7);
        g.addDirectedEdge(7, 4);

        assertTrue(g.hasNonTrivialCycle());
    }

    @Test
    void has_nontrivial_cycle_false_if_only_one_undirected_edge_but_otherwise_no_cycle() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.addNode(6);
        g.addNode(7);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(5, 4);
        g.addUndirectedEdge(4, 6);
        g.addDirectedEdge(6, 7);

        assertFalse(g.hasNonTrivialCycle());
    }

    @Test
    void is_connected_is_false_on_empty_graph() {
        assertTrue(new UnweightedGraph<>().isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_one_node_graph() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);

        assertTrue(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_multiple_node_graph() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3));

        assertFalse(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_true_on_two_node_graph_with_undirected_edge() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2));
        g.addUndirectedEdge(1, 2);

        assertTrue(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_two_node_graph_with_directed_edge() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2));
        g.addDirectedEdge(1, 2);

        assertFalse(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_two_node_graph_with_directed_edge_other_direction() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2));
        g.addDirectedEdge(2, 1);

        assertFalse(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_multiple_node_graph_with_only_partial_connections() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3));
        g.addUndirectedEdge(1, 2);

        assertFalse(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_true_on_one_big_directed_cycle() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7));
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(2, 3);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(4, 5);
        g.addDirectedEdge(5, 6);
        g.addDirectedEdge(6, 7);
        g.addDirectedEdge(7, 1);

        assertTrue(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_false_on_one_big_directed_cycle_plus_separate_node() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 42, 4, 5, 6, 7));
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(2, 3);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(4, 5);
        g.addDirectedEdge(5, 6);
        g.addDirectedEdge(6, 7);
        g.addDirectedEdge(7, 1);

        assertFalse(g.isStronglyConnected());
    }

    @Test
    void is_connected_is_true_on_one_big_directed_cycle_plus_undirected_branch_to_second_cycle() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(2, 3);
        g.addDirectedEdge(3, 4);
        g.addDirectedEdge(4, 5);
        g.addDirectedEdge(5, 6);
        g.addDirectedEdge(6, 7);
        g.addDirectedEdge(7, 1);
        g.addUndirectedEdge(7, 8);
        g.addDirectedEdge(8, 9);
        g.addDirectedEdge(9, 10);
        g.addDirectedEdge(10, 8);

        assertTrue(g.isStronglyConnected());
    }


    @Test
    void transpose_empty_graph_gives_empty() {
        WeightedGraph<Integer> g = new WeightedGraph<>();
        assertEquals(g, g.transpose());
    }

    @Test
    void transpose_graph_without_edges_gives_same_graph() {
        WeightedGraph<Integer> g = new WeightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4));
        assertEquals(g, g.transpose());
    }

    @Test
    void transpose_graph_with_one_edge_gives_opposite_edge() {
        WeightedGraph<Integer> g = new WeightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4));
        Edge.WeightedEdge<Integer> from1To2 = new Edge.WeightedEdge<>(1, 2, 42);
        g.addDirectedEdges(Set.of(from1To2));

        assertEquals(g.nodes(), g.transpose().nodes());
        assertEquals(Set.of(from1To2.opposite()), g.transpose().edges());
    }

    @Test
    void transpose_graph_with_multiple_edge_gives_opposite_edges() {
        WeightedGraph<Integer> g = new WeightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4));
        Edge.WeightedEdge<Integer> from1To2 = new Edge.WeightedEdge<>(1, 2, 42);
        Edge.WeightedEdge<Integer> from1To3 = new Edge.WeightedEdge<>(1, 3, 43);
        Edge.WeightedEdge<Integer> from2To4 = new Edge.WeightedEdge<>(2, 4, 44);
        g.addDirectedEdges(Set.of(from1To2, from1To3, from2To4));

        assertEquals(g.nodes(), g.transpose().nodes());
        assertEquals(Set.of(from1To2.opposite(), from1To3.opposite(), from2To4.opposite()), g.transpose().edges());
    }

    @Test
    void transpose_of_transpose_gives_back_original() {
        WeightedGraph<Integer> g = new WeightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4));
        Edge.WeightedEdge<Integer> from1To2 = new Edge.WeightedEdge<>(1, 2, 42);
        Edge.WeightedEdge<Integer> from1To3 = new Edge.WeightedEdge<>(1, 3, 43);
        Edge.WeightedEdge<Integer> from2To4 = new Edge.WeightedEdge<>(2, 4, 44);
        g.addDirectedEdges(Set.of(from1To2, from1To3, from2To4));

        assertEquals(g, g.transpose().transpose());
    }


    @Test
    void weakly_connected_components_of_empty_graph() {
        assertEquals(emptySet(), new UnweightedGraph<String>().weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_without_edges() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        assertEquals(Set.of(Set.of(1), Set.of(2), Set.of(3)), g.weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_with_all_nodes_connected() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        assertEquals(Set.of(Set.of(1, 2, 3)), g.weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_with_all_nodes_connected_also_works_for_strings() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori");

        assertEquals(Set.of(Set.of("Enno", "Cori")), g.weaklyConnectedComponents());
    }


    @Test
    void weakly_connected_components_of_graph_with_two_nodes_connected_and_third_not() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        assertEquals(Set.of(Set.of(1, 2), Set.of(3)), g.weaklyConnectedComponents());
    }

    @Test
    void multiple_nodes_all_connected() {
        /*
                Moritz -- Jonas    Niclas
                  |          |        |
                  |          |        |
                Cori  --- Enno       Max
                  |                   |
                   -------------------
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Max");
        g.addUndirectedEdge("Moritz", "Cori");
        g.addUndirectedEdge("Jonas", "Enno");
        g.addUndirectedEdge("Jonas", "Moritz");
        g.addUndirectedEdge("Niclas", "Max");

        assertEquals(Set.of(Set.of("Niclas", "Jonas", "Moritz", "Enno", "Cori", "Max")), g.weaklyConnectedComponents());
    }

    @Test
    void multiple_nodes_partially_connected() {
        /*
                Moritz -- Jonas    Niclas

                Cori  --- Enno ---  Max
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Max");
        g.addUndirectedEdge("Jonas", "Moritz");

        assertEquals(
            Set.of(Set.of("Niclas"), Set.of("Jonas", "Moritz"), Set.of("Enno", "Cori", "Max")),
            g.weaklyConnectedComponents()
        );
    }

    @Test
    void weakly_connected_components_of_one_chain_of_nodes_gives_one_big_component() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Moritz");
        g.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of(Set.of("Enno", "Cori", "Moritz", "Niclas")), g.weaklyConnectedComponents());
    }

    @Test
    void strongly_connected_components_empty_graph() {
        assertEquals(emptySet(), new UnweightedGraph<String>().stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_without_edges() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        assertEquals(Set.of(Set.of(1), Set.of(2), Set.of(3)), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_all_nodes_connected() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        assertEquals(Set.of(Set.of(1, 2, 3)), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_all_nodes_connected_also_works_for_strings() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori");

        assertEquals(Set.of(Set.of("Enno", "Cori")), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_three_scss() {
        /*
                  1 <----->  2 <---> 3
                  |
                  |
                  v
                  4  ----->  5 ---->  6  --> 7 <---> 8 <---> 9
                  ^                   |
                  |                   |
                   -------------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        g.addDirectedEdges(List.of(
            new Edge.UnweightedEdge<>(1, 2),
            new Edge.UnweightedEdge<>(2, 1),
            new Edge.UnweightedEdge<>(2, 3),
            new Edge.UnweightedEdge<>(3, 2),
            new Edge.UnweightedEdge<>(1, 4),
            new Edge.UnweightedEdge<>(4, 5),
            new Edge.UnweightedEdge<>(5, 6),
            new Edge.UnweightedEdge<>(6, 4),
            new Edge.UnweightedEdge<>(6, 7),
            new Edge.UnweightedEdge<>(7, 8),
            new Edge.UnweightedEdge<>(8, 7),
            new Edge.UnweightedEdge<>(8, 9),
            new Edge.UnweightedEdge<>(9, 8)
        ));
        assertEquals(Set.of(Set.of(1, 2, 3), Set.of(4, 5, 6), Set.of(7, 8, 9)), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_three_scss_more_edges() {
        /*
                  1 <----->  2 <--->  3  ----
                  |          |        |      |
                  |          |        |      |
                  v          v        v      v
                  4  ----->  5 ---->  6  --> 7 <---> 8 <---> 9
                  ^                   |
                  |                   |
                   -------------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        g.addDirectedEdges(List.of(
            new Edge.UnweightedEdge<>(1, 2),
            new Edge.UnweightedEdge<>(2, 1),
            new Edge.UnweightedEdge<>(2, 3),
            new Edge.UnweightedEdge<>(3, 2),
            new Edge.UnweightedEdge<>(1, 4),
            new Edge.UnweightedEdge<>(4, 5),
            new Edge.UnweightedEdge<>(5, 6),
            new Edge.UnweightedEdge<>(6, 4),
            new Edge.UnweightedEdge<>(6, 7),
            new Edge.UnweightedEdge<>(7, 8),
            new Edge.UnweightedEdge<>(8, 7),
            new Edge.UnweightedEdge<>(8, 9),
            new Edge.UnweightedEdge<>(9, 8),
            new Edge.UnweightedEdge<>(2, 5),
            new Edge.UnweightedEdge<>(3, 6),
            new Edge.UnweightedEdge<>(3, 7)
        ));
        assertEquals(Set.of(Set.of(1, 2, 3), Set.of(4, 5, 6), Set.of(7, 8, 9)), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_two_scss() {
        /*
                  1 <----->  2 <--->  3  ----
                  |          |        ^      |
                  |          |        |      |
                  v          v        v      v
                  4  ----->  5 ---->  6  --> 7 <---> 8 <---> 9
                  ^                   |
                  |                   |
                   -------------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        g.addDirectedEdges(List.of(
            new Edge.UnweightedEdge<>(1, 2),
            new Edge.UnweightedEdge<>(2, 1),
            new Edge.UnweightedEdge<>(2, 3),
            new Edge.UnweightedEdge<>(3, 2),
            new Edge.UnweightedEdge<>(1, 4),
            new Edge.UnweightedEdge<>(4, 5),
            new Edge.UnweightedEdge<>(5, 6),
            new Edge.UnweightedEdge<>(6, 4),
            new Edge.UnweightedEdge<>(6, 7),
            new Edge.UnweightedEdge<>(7, 8),
            new Edge.UnweightedEdge<>(8, 7),
            new Edge.UnweightedEdge<>(8, 9),
            new Edge.UnweightedEdge<>(9, 8),
            new Edge.UnweightedEdge<>(2, 5),
            new Edge.UnweightedEdge<>(3, 6),
            new Edge.UnweightedEdge<>(3, 7),
            new Edge.UnweightedEdge<>(6, 3) // this connects two otherwise independent SCCs
        ));
        assertEquals(Set.of(Set.of(1, 2, 3, 4, 5, 6), Set.of(7, 8, 9)), g.stronglyConnectedComponents());
    }

    @Test
    void strongly_connected_components_of_graph_with_three_scss_and_some_sattelites() {
         /*
                  1 <----->  2 <--->  3  ----
                  |          |        |      |             13
                  |          |        |      |              |
                  v          v        v      v              v
            10 -> 4  ----->  5 ---->  6  --> 7 <---> 8 <---> 9 <--- 11 <--12
                  ^                   |
                  |                   |
                   -------------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNodes(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        g.addDirectedEdges(List.of(
            new Edge.UnweightedEdge<>(1, 2),
            new Edge.UnweightedEdge<>(2, 1),
            new Edge.UnweightedEdge<>(2, 3),
            new Edge.UnweightedEdge<>(3, 2),
            new Edge.UnweightedEdge<>(1, 4),
            new Edge.UnweightedEdge<>(4, 5),
            new Edge.UnweightedEdge<>(5, 6),
            new Edge.UnweightedEdge<>(6, 4),
            new Edge.UnweightedEdge<>(6, 7),
            new Edge.UnweightedEdge<>(7, 8),
            new Edge.UnweightedEdge<>(8, 7),
            new Edge.UnweightedEdge<>(8, 9),
            new Edge.UnweightedEdge<>(9, 8),
            new Edge.UnweightedEdge<>(2, 5),
            new Edge.UnweightedEdge<>(3, 6),
            new Edge.UnweightedEdge<>(3, 7),
            new Edge.UnweightedEdge<>(10, 4),
            new Edge.UnweightedEdge<>(11, 9),
            new Edge.UnweightedEdge<>(12, 11),
            new Edge.UnweightedEdge<>(13, 9)
        ));
        assertEquals(
            Set.of(Set.of(1, 2, 3), Set.of(4, 5, 6), Set.of(7, 8, 9), Set.of(10), Set.of(11), Set.of(12), Set.of(13)),
            g.stronglyConnectedComponents()
        );
    }

}