package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class WeightedGraphTest {

    @Test
    void empty_g_shortest_path_throws() {
        assertThrows(IllegalArgumentException.class, () -> new WeightedGraph<>().shortestPath("Enno", "Cori"));
    }

    @Test
    void add_duplicate_throws() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        assertThrows(IllegalArgumentException.class, () -> g.addNode("Enno"));
    }

    @Test
    void shortest_path_from_to_same_node() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");

        assertEquals(List.of("Enno"), g.shortestPath("Enno", "Enno"));
    }

    @Test
    void shortest_path_not_contained_nodes_throws() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");

        assertThrows(IllegalArgumentException.class, () -> g.shortestPath("Enno", "Cori"));
    }

    @Test
    void shortest_path_unconnected_nodes_is_empty() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        assertEquals(emptyList(), g.shortestPath("Enno", "Cori"));
    }

    @Test
    void undirected_connection_works_in_both_directions() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori", 1000);

        assertEquals(List.of("Enno", "Cori"), g.shortestPath("Enno", "Cori"));
        assertEquals(List.of("Cori", "Enno"), g.shortestPath("Cori", "Enno"));
    }

    @Test
    void directed_connection_works_in_one_directions() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addDirectedEdge("Enno", "Cori", 1000);

        assertEquals(List.of("Enno", "Cori"), g.shortestPath("Enno", "Cori"));
        assertEquals(emptyList(), g.shortestPath("Cori", "Enno"));
    }

    @Test
    void two_distantly_connected_nodes_odd_distance() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Max");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 1);
        g.addUndirectedEdge("Niclas", "Moritz", 1);
        g.addUndirectedEdge("Moritz", "Max", 1);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), g.shortestPath("Cori", "Max"));
    }

    @Test
    void two_distantly_connected_nodes_even_distance() {
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 1);
        g.addUndirectedEdge("Niclas", "Moritz", 1);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz"), g.shortestPath("Cori", "Moritz"));
    }

    @Test
    void same_weights_path_with_least_nodes_is_shortest_path() {
        /*
                Cori -- 1 -- Enno -- 1 -- Niclas -- 1 -- Moritz -- 1 -- Max
                  |                                        |
                  ------- 1 ------ Buggi ----  1  ----------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Max");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addNode("Buggi");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 1);
        g.addUndirectedEdge("Niclas", "Moritz", 1);
        g.addUndirectedEdge("Moritz", "Max", 1);
        g.addUndirectedEdge("Cori", "Buggi", 1);
        g.addUndirectedEdge("Buggi", "Moritz", 1);

        assertEquals(List.of("Cori", "Buggi", "Moritz", "Max"), g.shortestPath("Cori", "Max"));
    }

    @Test
    void higher_weights_on_path_with_less_nodes() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 1 -- Max
                  |                                        |
                  ------- 3 ------ Buggi ----  4  ----------

         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Cori");
        g.addNode("Enno");
        g.addNode("Max");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addNode("Buggi");
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 2);
        g.addUndirectedEdge("Niclas", "Moritz", 3);
        g.addUndirectedEdge("Moritz", "Max", 1);
        g.addUndirectedEdge("Cori", "Buggi", 3);
        g.addUndirectedEdge("Buggi", "Moritz", 4);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), g.shortestPath("Cori", "Max"));
    }

    @Test
    void costly_direct_neighbor() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max, Buggi (unconnected)
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
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 2);
        g.addUndirectedEdge("Niclas", "Moritz", 3);
        g.addUndirectedEdge("Moritz", "Max", 3);
        g.addUndirectedEdge("Cori", "Max", 10);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), g.shortestPath("Cori", "Max"));
    }

    @Test
    void bfs_is_not_necessarily_shortest_path() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max, Buggi (unconnected)
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
        g.addUndirectedEdge("Cori", "Enno", 1);
        g.addUndirectedEdge("Enno", "Niclas", 2);
        g.addUndirectedEdge("Niclas", "Moritz", 3);
        g.addUndirectedEdge("Moritz", "Max", 3);
        g.addUndirectedEdge("Cori", "Max", 10);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), g.shortestPath("Cori", "Max"));
        assertEquals(List.of("Cori", "Max"), g.bfsPath("Cori", "Max"));
    }

}