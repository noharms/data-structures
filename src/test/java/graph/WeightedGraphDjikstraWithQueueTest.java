package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeightedGraphDjikstraWithQueueTest {

    @Test
    void empty_graph_shortest_path_throws() {
        assertThrows(IllegalArgumentException.class, () -> new WeightedGraph<>().shortestPathUsingMinQueue("Enno",
                                                                                                           "Cori"));
    }

    @Test
    void add_duplicate_throws() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        assertThrows(IllegalArgumentException.class, () -> graph.addNode("Enno"));
    }

    @Test
    void shortest_path_from_to_same_node() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");

        assertEquals(List.of("Enno"), graph.shortestPathUsingMinQueue("Enno", "Enno"));
    }

    @Test
    void shortest_path_not_contained_nodes_throws() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");

        assertThrows(IllegalArgumentException.class, () -> graph.shortestPathUsingMinQueue("Enno", "Cori"));
    }

    @Test
    void shortest_path_unconnected_nodes_is_empty() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        assertEquals(emptyList(), graph.shortestPathUsingMinQueue("Enno", "Cori"));
    }

    @Test
    void undirected_connection_works_in_both_directions() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addUndirectedEdge("Enno", "Cori", 1000);

        assertEquals(List.of("Enno", "Cori"), graph.shortestPathUsingMinQueue("Enno", "Cori"));
        assertEquals(List.of("Cori", "Enno"), graph.shortestPathUsingMinQueue("Cori", "Enno"));
    }

    @Test
    void directed_connection_works_in_one_directions() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addDirectedEdge("Enno", "Cori", 1000);

        assertEquals(List.of("Enno", "Cori"), graph.shortestPathUsingMinQueue("Enno", "Cori"));
        assertEquals(emptyList(), graph.shortestPathUsingMinQueue("Cori", "Enno"));
    }

    @Test
    void two_distantly_connected_nodes_odd_distance() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addUndirectedEdge("Cori", "Enno", 1);
        graph.addUndirectedEdge("Enno", "Niclas", 1);
        graph.addUndirectedEdge("Niclas", "Moritz", 1);
        graph.addUndirectedEdge("Moritz", "Max", 1);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), graph.shortestPathUsingMinQueue("Cori", "Max"
        ));
    }

    @Test
    void two_distantly_connected_nodes_even_distance() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addUndirectedEdge("Cori", "Enno", 1);
        graph.addUndirectedEdge("Enno", "Niclas", 1);
        graph.addUndirectedEdge("Niclas", "Moritz", 1);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz"), graph.shortestPathUsingMinQueue("Cori", "Moritz"));
    }

    @Test
    void same_weights_path_with_least_nodes_is_shortest_path() {
        /*
                Cori -- 1 -- Enno -- 1 -- Niclas -- 1 -- Moritz -- 1 -- Max
                  |                                        |
                  ------- 1 ------ Buggi ----  1  ----------

         */
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Buggi");
        graph.addUndirectedEdge("Cori", "Enno", 1);
        graph.addUndirectedEdge("Enno", "Niclas", 1);
        graph.addUndirectedEdge("Niclas", "Moritz", 1);
        graph.addUndirectedEdge("Moritz", "Max", 1);
        graph.addUndirectedEdge("Cori", "Buggi", 1);
        graph.addUndirectedEdge("Buggi", "Moritz", 1);

        assertEquals(List.of("Cori", "Buggi", "Moritz", "Max"), graph.shortestPathUsingMinQueue("Cori", "Max"));
    }

    @Test
    void higher_weights_on_path_with_less_nodes() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 1 -- Max
                  |                                        |
                  ------- 3 ------ Buggi ----  4  ----------

         */
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Buggi");
        graph.addUndirectedEdge("Cori", "Enno", 1);
        graph.addUndirectedEdge("Enno", "Niclas", 2);
        graph.addUndirectedEdge("Niclas", "Moritz", 3);
        graph.addUndirectedEdge("Moritz", "Max", 1);
        graph.addUndirectedEdge("Cori", "Buggi", 3);
        graph.addUndirectedEdge("Buggi", "Moritz", 4);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), graph.shortestPathUsingMinQueue("Cori", "Max"
        ));
    }

    @Test
    void costly_direct_neighbor() {
        /*
                Cori -- 1 -- Enno -- 2 -- Niclas -- 3 -- Moritz -- 3 -- Max
                  |                                                      |
                  -------------------------- 10 -------------------------

         */
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Buggi");
        graph.addUndirectedEdge("Cori", "Enno", 1);
        graph.addUndirectedEdge("Enno", "Niclas", 2);
        graph.addUndirectedEdge("Niclas", "Moritz", 3);
        graph.addUndirectedEdge("Moritz", "Max", 3);
        graph.addUndirectedEdge("Cori", "Max", 10);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), graph.shortestPathUsingMinQueue("Cori", "Max"
        ));
    }

}