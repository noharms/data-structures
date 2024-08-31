package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnweightedGraphIsDirectedTest {

    @Test
    void is_directed_check_on_graph_with_only_undirected_edges() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Moritz");
        g.addUndirectedEdge("Moritz", "Niclas");

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }

    @Test
    void is_directed_check_on_graph_with_directed_edges_that_are_effectively_undirected() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addDirectedEdge("Enno", "Cori");
        g.addDirectedEdge("Cori", "Enno");
        g.addDirectedEdge("Cori", "Moritz");
        g.addDirectedEdge("Moritz", "Cori");
        g.addDirectedEdge("Moritz", "Niclas");
        g.addDirectedEdge("Niclas", "Moritz");

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }

    @Test
    void is_directed_check_on_g_with_directed_edges_that_are_not_fully_symmetric() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addDirectedEdge("Enno", "Cori");
        g.addDirectedEdge("Cori", "Enno");
//        g.addDirectedEdge("Cori", "Moritz"); // --> without this edge there is an asymmetry, so the graph is directed
        g.addDirectedEdge("Moritz", "Cori");
        g.addDirectedEdge("Moritz", "Niclas");
        g.addDirectedEdge("Niclas", "Moritz");

        assertTrue(g.isDirected());
        assertFalse(g.isUndirected());
    }

    @Test
    void an_undirected_g_can_contain_unconnected_nodes_which_do_not_affect_the_is_directed_property() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addNode("NotConnected");  // --> notice this node for which we do not enter an edge below
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Moritz");
        g.addUndirectedEdge("Moritz", "Niclas");

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }
}
