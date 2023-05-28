package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void bfs_gives_shortest_path() {
        /*
                Cori ---- Enno ---- Niclas ---- Moritz ---- Max
                  |                                         |
                  ------------- Buggi ----------------------

         */
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Cori");
        graph.addNode("Enno");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Buggi");
        graph.addUndirectedEdge("Cori", "Enno");
        graph.addUndirectedEdge("Enno", "Niclas");
        graph.addUndirectedEdge("Niclas", "Moritz");
        graph.addUndirectedEdge("Moritz", "Max");
        graph.addUndirectedEdge("Cori", "Buggi");
        graph.addUndirectedEdge("Buggi", "Max");

        assertEquals(List.of("Cori", "Buggi", "Max"), graph.bfsPath("Cori", "Max"));
        assertEquals(List.of("Cori", "Buggi", "Max"), graph.shortestPath("Cori", "Max"));
    }

    @Test
    void dfs_same_node_is_connected() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        assertTrue(graph.dfsIsConnected("Enno", "Enno"));
    }

    @Test
    void dfs_empty_graph_throws() {
        assertThrows(IllegalArgumentException.class, () -> new UnweightedGraph<String>().dfsIsConnected("X", "Y"));
    }

    @Test
    void dfs_unconnected_nodes() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Max");
        assertFalse(graph.dfsIsConnected("Enno", "Max"));
        assertFalse(graph.dfsIsConnected("Max", "Enno"));
    }

    @Test
    void dfs_undirected_connection_neighboring() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addUndirectedEdge("Enno", "Cori");
        assertTrue(graph.dfsIsConnected("Enno", "Cori"));
        assertTrue(graph.dfsIsConnected("Enno", "Cori"));
    }

    @Test
    void dfs_directed_connection_neighboring() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addDirectedEdge("Enno", "Cori");
        assertTrue(graph.dfsIsConnected("Enno", "Cori"));
        assertFalse(graph.dfsIsConnected("Cori", "Enno"));
    }

    @Test
    void dfs_undirected_connection_over_multiple_nodes() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Aaron");
        graph.addUndirectedEdge("Enno", "Cori");
        graph.addUndirectedEdge("Enno", "Niclas");
        graph.addUndirectedEdge("Cori", "Niclas");
        graph.addUndirectedEdge("Cori", "Max");
        graph.addUndirectedEdge("Max", "Moritz");
        graph.addUndirectedEdge("Moritz", "Aaron");

        assertTrue(graph.dfsIsConnected("Enno", "Aaron"));
        assertTrue(graph.dfsIsConnected("Aaron", "Enno"));
    }

    @Test
    void dfs_directed_connection_over_multiple_nodes() {
        UnweightedGraph<String> graph = new UnweightedGraph<>();
        graph.addNode("Enno");
        graph.addNode("Cori");
        graph.addNode("Max");
        graph.addNode("Moritz");
        graph.addNode("Niclas");
        graph.addNode("Aaron");
        graph.addDirectedEdge("Enno", "Cori");
        graph.addDirectedEdge("Enno", "Niclas");
        graph.addDirectedEdge("Cori", "Niclas");
        graph.addDirectedEdge("Cori", "Max");
        graph.addDirectedEdge("Max", "Moritz");
        graph.addDirectedEdge("Moritz", "Aaron");

        assertTrue(graph.dfsIsConnected("Enno", "Aaron"));
        assertFalse(graph.dfsIsConnected("Aaron", "Enno"));
    }

    @Test
    void findComponents_on_empty_graph_gives_empty_set() {
        assertEquals(emptySet(), new UnweightedGraph<>().findComponents());
    }

    @Test
    void findComponents_one_node_in_graph_gives_just_one_component() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");

        assertEquals(Set.of(Set.of("Enno")), unweightedGraph.findComponents());
    }

    @Test
    void findComponents_two_unconnected_nodes_in_graph_gives_two_components_with_each_one_element() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");

        assertEquals(Set.of(Set.of("Enno"), Set.of("Cori")), unweightedGraph.findComponents());
    }

    @Test
    void findComponents_two_connected_nodes_in_graph_gives_one_component_with_two_elements() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");

        assertEquals(Set.of(Set.of("Enno", "Cori")), unweightedGraph.findComponents());
    }

    @Test
    void findComponents_two_components_with_each_two_elements() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");
        unweightedGraph.addNode("Moritz");
        unweightedGraph.addNode("Niclas");
        unweightedGraph.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of(Set.of("Enno", "Cori"), Set.of("Moritz", "Niclas")), unweightedGraph.findComponents());
    }

    @Test
    void findComponents_one_chain_of_nodes_gives_one_big_component() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addNode("Moritz");
        unweightedGraph.addNode("Niclas");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");
        unweightedGraph.addUndirectedEdge("Cori", "Moritz");
        unweightedGraph.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of(Set.of("Enno", "Cori", "Moritz", "Niclas")), unweightedGraph.findComponents());
    }

    @Test
    void findAllConnected_node_not_contained() {
        assertEquals(emptySet(), new UnweightedGraph<>().findAllConnected("Enno"));
    }

    @Test
    void findAllConnected_single_node() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");

        assertEquals(Set.of("Enno"), unweightedGraph.findAllConnected("Enno"));
    }

    @Test
    void findAllConnected_two_unconnected_nodes_in_graph_gives_two_components_with_each_one_element() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");

        assertEquals(Set.of("Enno"), unweightedGraph.findAllConnected("Enno"));
        assertEquals(Set.of("Cori"), unweightedGraph.findAllConnected("Cori"));
    }

    @Test
    void findAllConnected_two_connected_nodes_in_graph_gives_both_symmetrically() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");

        assertEquals(Set.of("Enno", "Cori"), unweightedGraph.findAllConnected("Enno"));
        assertEquals(Set.of("Enno", "Cori"), unweightedGraph.findAllConnected("Cori"));
    }

    @Test
    void findAllConnected_two_components_with_each_two_elements() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");
        unweightedGraph.addNode("Moritz");
        unweightedGraph.addNode("Niclas");
        unweightedGraph.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of("Enno", "Cori"), unweightedGraph.findAllConnected("Enno"));
        assertEquals(Set.of("Enno", "Cori"), unweightedGraph.findAllConnected("Cori"));
        assertEquals(Set.of("Moritz", "Niclas"), unweightedGraph.findAllConnected("Niclas"));
        assertEquals(Set.of("Moritz", "Niclas"), unweightedGraph.findAllConnected("Moritz"));
    }

    @Test
    void findAllConnected_one_chain_of_nodes_gives_one_big_component() {
        UnweightedGraph<String> unweightedGraph = new UnweightedGraph<>();
        unweightedGraph.addNode("Enno");
        unweightedGraph.addNode("Cori");
        unweightedGraph.addNode("Moritz");
        unweightedGraph.addNode("Niclas");
        unweightedGraph.addNode("NotConnected");
        unweightedGraph.addUndirectedEdge("Enno", "Cori");
        unweightedGraph.addUndirectedEdge("Cori", "Moritz");
        unweightedGraph.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of("Enno", "Cori", "Moritz", "Niclas"), unweightedGraph.findAllConnected("Enno"));
        assertEquals(Set.of("Enno", "Cori", "Moritz", "Niclas"), unweightedGraph.findAllConnected("Cori"));
        assertEquals(Set.of("Enno", "Cori", "Moritz", "Niclas"), unweightedGraph.findAllConnected("Niclas"));
        assertEquals(Set.of("Enno", "Cori", "Moritz", "Niclas"), unweightedGraph.findAllConnected("Moritz"));
        assertEquals(Set.of("NotConnected"), unweightedGraph.findAllConnected("NotConnected"));
    }
}