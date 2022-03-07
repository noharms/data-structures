import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnweightedGraphTest {

    @Test
    void empty_graph_shortest_path_throws() {
        assertThrows(IllegalArgumentException.class, () -> new UnweightedGraph<>().shortestPath("Enno", "Cori"));
    }

    @Test
    void add_duplicate_throws() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        assertThrows(IllegalArgumentException.class, () -> graph.addNode("Enno"));
    }

    @Test
    void shortest_path_from_to_same_node() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");

        assertEquals(List.of("Enno"), graph.shortestPath("Enno", "Enno"));
    }

    @Test
    void shortest_path_not_contained_nodes_throws() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");

        assertThrows(IllegalArgumentException.class, () -> graph.shortestPath("Enno", "Cori"));
    }

    @Test
    void shortest_path_unconnected_nodes_is_empty() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        assertEquals(emptyList(), graph.shortestPath("Enno", "Cori"));
    }

    @Test
    void undirected_connection_works_in_both_directions() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addUndirectedEdge("Enno", "Cori");

        assertEquals(List.of("Enno", "Cori"), graph.shortestPath("Enno", "Cori"));
        assertEquals(List.of("Cori", "Enno"), graph.shortestPath("Cori", "Enno"));
    }

    @Test
    void directed_connection_works_in_one_directions() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addDirectedEdge("Enno", "Cori");

        assertEquals(List.of("Enno", "Cori"), graph.shortestPath("Enno", "Cori"));
        assertEquals(emptyList(), graph.shortestPath("Cori", "Enno"));
    }

    @Test
    void two_distantly_connected_nodes_odd_distance() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addUndirectedEdge("Cori", "Enno");
        graph.addUndirectedEdge("Enno", "Niclas");
        graph.addUndirectedEdge("Niclas", "Moritz");
        graph.addUndirectedEdge("Moritz", "Max");

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz", "Max"), graph.shortestPath("Cori", "Max"));
    }

    @Test
    void two_distantly_connected_nodes_even_distance() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addUndirectedEdge("Cori", "Enno");
        graph.addUndirectedEdge("Enno", "Niclas");
        graph.addUndirectedEdge("Niclas", "Moritz");

        assertEquals(List.of("Cori", "Enno", "Niclas", "Moritz"), graph.shortestPath("Cori", "Moritz"));
    }

}