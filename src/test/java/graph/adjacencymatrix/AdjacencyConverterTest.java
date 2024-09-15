package graph.adjacencymatrix;

import graph.WeightedGraph;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyConverterTest {

    @Test
    void non_quadratic_matrix_throws() {
        int[][] adjacency = new int[][]{{1, 2}, {3}};

        assertThrows(IllegalArgumentException.class, () -> new AdjacencyConverter(adjacency));
    }

    @Test
    void non_zero_self_connection_throws() {
        int[][] adjacency = new int[][]{{1, 2}, {3, 0}};

        assertThrows(IllegalArgumentException.class, () -> new AdjacencyConverter(adjacency));
    }

    @Test
    void single_element_graph() {
        int[][] adjacency = new int[][]{{0}};

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectAll();

        assertEquals(Set.of(0), graph.nodes());
    }

    @Test
    void empty_adjacency_gives_empty_graph() {
        int[][] adjacency = new int[0][0];

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectAll();

        assertTrue(graph.nodes().isEmpty());
    }

    @Test
    void two_x_two_matrix_represents_graph_of_two_nodes() {
        int[][] adjacency = new int[][]{{0, 4}, {2, 0}};

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectAll();

        assertEquals(Set.of(0, 1), graph.nodes());
    }

    @Test
    void three_x_three_matrix_represents_graph_of_three_nodes() {
        int[][] adjacency = new int[][]{{0, 4, 1}, {2, 0, 1}, {1, 1, 0}};

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectAll();

        assertEquals(Set.of(0, 1, 2), graph.nodes());
    }

    @Test
    void slots_in_adjacency_become_weights_of_edges() {
        int[][] adjacency = new int[][]{{0, 42, 43}, {44, 0, 45}, {46, 47, 0}};

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectAll();

        assertEquals(Map.of(1, 42, 2, 43), graph.allEdges(0));
        assertEquals(Map.of(0, 44, 2, 45), graph.allEdges(1));
        assertEquals(Map.of(0, 46, 1, 47), graph.allEdges(2));
    }

    @Test
    void slots_in_adjacency_become_weights_of_edges_unless_excluded() {
        int[][] adjacency = new int[][]{{0, 0, 43}, {0, 0, 45}, {46, 0, 0}};

        WeightedGraph<Integer> graph = new AdjacencyConverter(adjacency).connectExcept(0);

        assertEquals(Map.of(2, 43), graph.allEdges(0));
        assertEquals(Map.of(2, 45), graph.allEdges(1));
        assertEquals(Map.of(0, 46), graph.allEdges(2));
    }
}