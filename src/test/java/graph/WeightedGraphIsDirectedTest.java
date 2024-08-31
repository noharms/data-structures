package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedGraphIsDirectedTest {

    @Test
    void is_directed_check_on_g_with_only_undirected_edges() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max
                  |                                                      |
                  -------------------------- 10 -------------------------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Niclas");
        g.addNode("Moritz");
        g.addNode("Max");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 2);
        g.addUndirectedEdge("Niclas", "Moritz", 3);
        g.addUndirectedEdge("Moritz", "Max", 3);
        g.addUndirectedEdge("Cori", "Max", 10);

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }

    @Test
    void is_directed_check_on_g_with_directed_edges_that_are_effectively_undirected() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max
                  |                                                      |
                  -------------------------- 10 -------------------------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Niclas");
        g.addNode("Moritz");
        g.addNode("Max");
        g.addDirectedEdge("Cori", "Enno", 1);
        g.addDirectedEdge("Enno", "Cori", 1);
        g.addDirectedEdge("Enno", "Niclas", 2);
        g.addDirectedEdge("Niclas", "Enno", 2);
        g.addDirectedEdge("Niclas", "Moritz", 3);
        g.addDirectedEdge("Moritz", "Niclas", 3);
        g.addDirectedEdge("Moritz", "Max", 3);
        g.addDirectedEdge("Max", "Moritz", 3);
        g.addDirectedEdge("Cori", "Max", 10);
        g.addDirectedEdge("Max", "Cori", 10);

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }

    @Test
    void is_directed_check_on_g_with_directed_edges_that_are_not_fully_symmetric() {
        /*
                Cori <-- 1 --> Enno <-- 2 -- Niclas <-- 3 --> Moritz <-- 3 --> Max
                  ^                                                            ^
                  |                                                            |
                  -------------------------- 10 -------------------------------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Niclas");
        g.addNode("Moritz");
        g.addNode("Max");
        g.addDirectedEdge("Cori", "Enno", 1);
        g.addDirectedEdge("Enno", "Cori", 1);
        // g.addDirectedEdge("Enno", "Niclas", 2); // -> without this edge the g has a direction!
        g.addDirectedEdge("Niclas", "Enno", 2);
        g.addDirectedEdge("Niclas", "Moritz", 3);
        g.addDirectedEdge("Moritz", "Niclas", 3);
        g.addDirectedEdge("Moritz", "Max", 3);
        g.addDirectedEdge("Max", "Moritz", 3);
        g.addDirectedEdge("Cori", "Max", 10);
        g.addDirectedEdge("Max", "Cori", 10);

        assertTrue(g.isDirected());
        assertFalse(g.isUndirected());
    }

    @Test
    void is_directed_check_on_g_considers_the_weights_and_if_to_from_edge_has_another_weight_than_from_to_we_have_a_direction() {
        /*
                Cori <-- 1 --> Enno <-- 2 -- Niclas <-- 3 --> Moritz <-- 3 --> Max
                  ^                  -- 3 -->                                  ^
                  |                                                            |
                  -------------------------- 10 -------------------------------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Niclas");
        g.addNode("Moritz");
        g.addNode("Max");
        g.addDirectedEdge("Cori", "Enno", 1);
        g.addDirectedEdge("Enno", "Cori", 1);
        g.addDirectedEdge("Enno", "Niclas", 3);  // -> same nodes but different weight
        g.addDirectedEdge("Niclas", "Enno", 2);  // -> same nodes but different weight
        g.addDirectedEdge("Niclas", "Moritz", 3);
        g.addDirectedEdge("Moritz", "Niclas", 3);
        g.addDirectedEdge("Moritz", "Max", 3);
        g.addDirectedEdge("Max", "Moritz", 3);
        g.addDirectedEdge("Cori", "Max", 10);
        g.addDirectedEdge("Max", "Cori", 10);

        assertTrue(g.isDirected());
        assertFalse(g.isUndirected());
    }

    @Test
    void an_undirected_g_can_contain_unconnected_nodes_which_do_not_affect_the_is_directed_property() {
          /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max  ,  Buggi, Chris (unconnected)
                  |                                                      |
                  -------------------------- 10 -------------------------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Niclas");
        g.addNode("Moritz");
        g.addNode("Max");
        g.addNode("Buggi");
        g.addNode("Chris");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 2);
        g.addUndirectedEdge("Niclas", "Moritz", 3);
        g.addUndirectedEdge("Moritz", "Max", 3);
        g.addUndirectedEdge("Cori", "Max", 10);

        assertFalse(g.isDirected());
        assertTrue(g.isUndirected());
    }
}
