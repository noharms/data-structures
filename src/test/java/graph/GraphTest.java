package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest {

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
    void findComponents_one_chain_of_nodes_gives_one_big_component() {
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

}