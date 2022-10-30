package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LongestPathComputerTest {

    @Test
    void longest_path_from_to_same_node() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addDirectedEdge("Enno", "Cori", 1);
        LongestPathComputer<String> longestPathComputer = new LongestPathComputer<>(graph);

        assertEquals(List.of("Enno"), longestPathComputer.longestPath("Enno", "Enno"));
    }

    @Test
    void longest_path_no_edges_throws() {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");

        assertThrows(IllegalArgumentException.class, () -> new LongestPathComputer<>(graph));
    }

    @Test
    void two_linearly_connected_nodes() {
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
        LongestPathComputer<String> longestPathComputer = new LongestPathComputer<>(graph);

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), longestPathComputer.longestPath("Cori", "Max"
        ));
    }

    @Test
    void if_same_weights_path_with_more_nodes_is_longest_path() {
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
        LongestPathComputer<String> longestPathComputer = new LongestPathComputer<>(graph);

        assertEquals(
                List.of("Cori", "Enno", "Niclas", "Moritz", "Max"),
                longestPathComputer.longestPath("Cori", "Max")
        );
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
        LongestPathComputer<String> longestPathComputer = new LongestPathComputer<>(graph);

        assertEquals(List.of("Cori", "Buggi", "Moritz", "Max"), longestPathComputer.longestPath("Cori", "Max"));
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
        LongestPathComputer<String> longestPathComputer = new LongestPathComputer<>(graph);

        assertEquals(List.of("Cori", "Max"), longestPathComputer.longestPath("Cori", "Max"));
    }

}