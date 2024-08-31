package graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnweightedGraphTopologicalSortTest {

    @Test
    void three_nodes_and_two_simple_dependencies() {
        /*
                Cori ---> Enno ---> Max
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Cori", "Enno");
        g.addDirectedEdge("Enno", "Max");

        assertEquals(List.of("Cori", "Enno", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_two_simple_dependencies_with_one_unconnected_node() {
        /*
                Cori ---> Enno ---> Max, Jonas
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas"); // -> unconnected
        g.addDirectedEdge("Cori", "Enno");
        g.addDirectedEdge("Enno", "Max");

        // Jonas could appear anywhere, having the node as the first is just currently what the implementation gives
        assertEquals(List.of("Cori", "Jonas", "Enno", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_one_node_with_dependencies_on_both_others() {
        /*
                Cori <--- Enno ---> Max
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Enno", "Cori");
        g.addDirectedEdge("Enno", "Max");

        assertEquals(List.of("Enno", "Cori", "Max"), g.topologicalSort());
    }

    @Test
    void three_nodes_and_mutual_dependencies() {
        /*
                Cori <--- Enno ---> Max
                  |                  ^
                  |                  |
                   ------------------
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addDirectedEdge("Enno", "Cori");
        g.addDirectedEdge("Enno", "Max");
        g.addDirectedEdge("Cori", "Max");

        assertEquals(List.of("Enno", "Cori", "Max"), g.topologicalSort());
    }

    @Test
    void topological_sort_throws_if_cyclic_dependency_through_an_undirected_edge() {
        /*
                Cori <---> Enno
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori");

        assertThrows(IllegalStateException.class, g::topologicalSort);
    }

    @Test
    void multiple_nodes_and_mutual_dependencies() {
        /*

                Moritz <-- Jonas    Niclas
                  |          |        |
                  v          v        v
                Cori  <--- Enno ---> Max
                  |                  ^
                  |                  |
                   ------------------
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addDirectedEdge("Enno", "Cori");
        g.addDirectedEdge("Enno", "Max");
        g.addDirectedEdge("Cori", "Max");
        g.addDirectedEdge("Moritz", "Cori");
        g.addDirectedEdge("Jonas", "Enno");
        g.addDirectedEdge("Jonas", "Moritz");
        g.addDirectedEdge("Niclas", "Max");

        assertEquals(List.of("Niclas", "Jonas", "Moritz", "Enno", "Cori", "Max"), g.topologicalSort());
    }


    @Test
    void integer_nodes_works_as_well() {
        /*
                       --------------
                       |             |
                       v             |
                1 ---> 2 <--- 3 <--- 4
                |             ^
                |             |
                --------------
         */
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 3);
        g.addDirectedEdge(3, 2);
        g.addDirectedEdge(4, 2);
        g.addDirectedEdge(4, 3);

        assertEquals(List.of(1, 4, 3, 2), g.topologicalSort());
    }

}
