package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeightedGraphTopologicalSortTest {


    @Test
    void three_nodes_and_two_simple_dependencies() {
        /*
                Cori -1-> Enno -2-> Max
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Cori", "Enno", 1);
        g.addDirectedEdge("Enno", "Max", 2);

        assertEquals(List.of("Cori", "Enno", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_two_simple_dependencies_with_one_unconnected_node() {
        /*
                Cori -2-> Enno -1-> Max, Jonas
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas"); // -> unconnected
        g.addDirectedEdge("Cori", "Enno", 2);
        g.addDirectedEdge("Enno", "Max", 1);

        // Jonas could appear anywhere, having the node as the first is just currently what the implementation gives
        assertEquals(List.of("Cori", "Jonas", "Enno", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_one_node_with_dependencies_on_both_others() {
        /*
                Cori <-1- Enno -2-> Max
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Enno", "Cori", 1);
        g.addDirectedEdge("Enno", "Max", 2);

        assertEquals(List.of("Enno", "Cori", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_mutual_dependencies() {
        /*
                Cori <-2-- Enno -3-> Max
                  |                  ^
                  |                  |
                   --------1--------
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Enno", "Cori", 2);
        g.addDirectedEdge("Enno", "Max", 3);
        g.addDirectedEdge("Cori", "Max", 1);

        assertEquals(List.of("Enno", "Cori", "Max"), g.topologicalSort());
    }

    @Test
    void topological_sort_throws_if_cyclic_dependency_through_an_undirected_edge() {
        /*
                Cori <-42-> Enno
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori", 42);

        assertThrows(IllegalStateException.class, g::topologicalSort);
    }

    @Test
    void multiple_nodes_and_mutual_dependencies() {
        /*

               Moritz <-42- Jonas    Niclas
                  |           |        |
                 42          42        42
                  v           v        v
                Cori  <-1- Enno  -2-> Max
                  |                    ^
                  |                    |
                   ----------3---------
         */
        WeightedGraph<String> g = new WeightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addDirectedEdge("Enno", "Cori", 1);
        g.addDirectedEdge("Enno", "Max", 2);
        g.addDirectedEdge("Cori", "Max", 3);
        g.addDirectedEdge("Moritz", "Cori", 42);
        g.addDirectedEdge("Jonas", "Enno", 42);
        g.addDirectedEdge("Jonas", "Moritz", 42);
        g.addDirectedEdge("Niclas", "Max", 42);

        assertEquals(List.of("Niclas", "Jonas", "Moritz", "Enno", "Cori", "Max"), g.topologicalSort());
    }

}
