package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void weakly_connected_components_of_empty_graph() {
        assertEquals(emptySet(), new UnweightedGraph<String>().weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_without_edges() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        assertEquals(Set.of(Set.of(1), Set.of(2), Set.of(3)), g.weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_with_all_nodes_connected() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        g.addUndirectedEdge(2, 3);
        assertEquals(Set.of(Set.of(1, 2, 3)), g.weaklyConnectedComponents());
    }

    @Test
    void weakly_connected_components_of_graph_with_all_nodes_connected_also_works_for_strings() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addUndirectedEdge("Enno", "Cori");

        assertEquals(Set.of(Set.of("Enno", "Cori")), g.weaklyConnectedComponents());
    }


    @Test
    void weakly_connected_components_of_graph_with_two_nodes_connected_and_third_not() {
        UnweightedGraph<Integer> g = new UnweightedGraph<>();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addUndirectedEdge(1, 2);
        assertEquals(Set.of(Set.of(1, 2), Set.of(3)), g.weaklyConnectedComponents());
    }

    @Test
    void multiple_nodes_all_connected() {
        /*
                Moritz -- Jonas    Niclas
                  |          |        |
                  |          |        |
                Cori  --- Enno       Max
                  |                   |
                   -------------------
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Max");
        g.addUndirectedEdge("Moritz", "Cori");
        g.addUndirectedEdge("Jonas", "Enno");
        g.addUndirectedEdge("Jonas", "Moritz");
        g.addUndirectedEdge("Niclas", "Max");

        assertEquals(Set.of(Set.of("Niclas", "Jonas", "Moritz", "Enno", "Cori", "Max")), g.weaklyConnectedComponents());
    }

    @Test
    void multiple_nodes_partially_connected() {
        /*
                Moritz -- Jonas    Niclas

                Cori  --- Enno ---  Max
         */
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Max");
        g.addNode("Jonas");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Max");
        g.addUndirectedEdge("Jonas", "Moritz");

        assertEquals(
            Set.of(Set.of("Niclas"), Set.of("Jonas", "Moritz"), Set.of("Enno", "Cori", "Max")),
            g.weaklyConnectedComponents()
        );
    }

    @Test
    void findComponents_one_chain_of_nodes_gives_one_big_component() {
        UnweightedGraph<String> g = new UnweightedGraph<>();
        g.addNode("Enno");
        g.addNode("Cori");
        g.addNode("Moritz");
        g.addNode("Niclas");
        g.addUndirectedEdge("Enno", "Cori");
        g.addUndirectedEdge("Cori", "Moritz");
        g.addUndirectedEdge("Moritz", "Niclas");

        assertEquals(Set.of(Set.of("Enno", "Cori", "Moritz", "Niclas")), g.weaklyConnectedComponents());
    }


}