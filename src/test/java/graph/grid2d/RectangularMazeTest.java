package graph.grid2d;

import graph.UnweightedGraph;
import graph.grid2d.Cell;
import graph.grid2d.RectangularMaze;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class RectangularMazeTest {

    @Test
    public void toGraph_1x1Maze() {
        int[][] maze = {
            {1}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(1, graph.size());

        assertTrue(graph.contains(new Cell(0, 0)));
        assertFalse(graph.contains(new Cell(1, 0)));
        assertFalse(graph.contains(new Cell(0, 1)));
        assertFalse(graph.contains(new Cell(1, 1)));

        assertEquals(emptySet(), graph.allNeighbors(new Cell(0, 0)));
    }

    @Test
    public void toGraph_2x2MazeAllFree() {
        int[][] maze = {
            {0, 0},
            {0, 0}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(4, graph.size());

        assertTrue(graph.contains(new Cell(0, 0)));
        assertTrue(graph.contains(new Cell(1, 0)));
        assertTrue(graph.contains(new Cell(0, 1)));
        assertTrue(graph.contains(new Cell(1, 1)));

        assertEquals(Set.of(new Cell(0, 1),
                            new Cell(1, 0)),
                     graph.allNeighbors(new Cell(0, 0)));
        assertEquals(Set.of(new Cell(0, 0),
                            new Cell(1, 1)),
                     graph.allNeighbors(new Cell(0, 1)));
        assertEquals(Set.of(new Cell(0, 0),
                            new Cell(1, 1)),
                     graph.allNeighbors(new Cell(1, 0)));
        assertEquals(Set.of(new Cell(0, 1),
                            new Cell(1, 0)),
                     graph.allNeighbors(new Cell(1, 1)));

        assertEquals(3, graph.shortestPath(new Cell(0, 0), new Cell(1, 1)).size());
        assertEquals(1, graph.shortestPath(new Cell(0, 0), new Cell(0, 0)).size());
        assertEquals(2, graph.shortestPath(new Cell(0, 0), new Cell(0, 1)).size());
        assertEquals(2, graph.shortestPath(new Cell(0, 0), new Cell(1, 0)).size());
    }

    @Test
    public void toGraph_2x2MazeAllBlocked() {
        int[][] maze = {
            {1, 1},
            {1, 1}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(4, graph.size());

        assertTrue(graph.contains(new Cell(0, 0)));
        assertTrue(graph.contains(new Cell(1, 0)));
        assertTrue(graph.contains(new Cell(0, 1)));
        assertTrue(graph.contains(new Cell(1, 1)));

        assertEquals(emptySet(), graph.allNeighbors(new Cell(0, 0)));
        assertEquals(emptySet(), graph.allNeighbors(new Cell(0, 1)));
        assertEquals(emptySet(), graph.allNeighbors(new Cell(1, 0)));
        assertEquals(emptySet(), graph.allNeighbors(new Cell(1, 1)));

        assertEquals(0, graph.shortestPath(new Cell(0, 0), new Cell(1, 1)).size());
        assertEquals(1, graph.shortestPath(new Cell(0, 0), new Cell(0, 0)).size());
        assertEquals(0, graph.shortestPath(new Cell(0, 0), new Cell(0, 1)).size());
        assertEquals(0, graph.shortestPath(new Cell(0, 0), new Cell(1, 0)).size());
    }


    @Test
    public void toGraph_2x6() {
        int[][] maze = {
            {0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 1, 0}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(12, graph.size());

        assertEquals(9, graph.shortestPath(new Cell(0, 0), new Cell(1, 5)).size());
    }


    @Test
    public void toGraph_3x3MazeAllFree() {
        int[][] maze = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(9, graph.size());

        assertEquals(Set.of(new Cell(0, 1),
                            new Cell(1, 0)),
                     graph.allNeighbors(new Cell(0, 0)));
        assertEquals(Set.of(new Cell(0, 0),
                            new Cell(1, 1),
                            new Cell(0, 2)),
                     graph.allNeighbors(new Cell(0, 1)));
        assertEquals(Set.of(new Cell(0, 0),
                            new Cell(1, 1),
                            new Cell(2, 0)),
                     graph.allNeighbors(new Cell(1, 0)));
        assertEquals(Set.of(new Cell(0, 1),
                            new Cell(1, 0),
                            new Cell(1, 2),
                            new Cell(2, 1)),
                     graph.allNeighbors(new Cell(1, 1)));

        assertEquals(5, graph.shortestPath(new Cell(0, 0), new Cell(2, 2)).size());
        assertEquals(4, graph.shortestPath(new Cell(0, 0), new Cell(2, 1)).size());
        assertEquals(3, graph.shortestPath(new Cell(0, 0), new Cell(2, 0)).size());
    }

    @Test
    public void toGraph_3x3MazeWithBlock() {
        int[][] maze = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 0, 0}
        };
        UnweightedGraph<Cell> graph = RectangularMaze.createGraph(maze);

        assertEquals(9, graph.size());

        assertEquals(Set.of(new Cell(1, 0)), graph.allNeighbors(new Cell(0, 0)));
        assertEquals(emptySet(), graph.allNeighbors(new Cell(0, 1)));
        assertEquals(Set.of(new Cell(0, 0),
                            new Cell(2, 0)),
                     graph.allNeighbors(new Cell(1, 0)));
        assertEquals(emptySet(),
                     graph.allNeighbors(new Cell(1, 1)));

        assertEquals(7, graph.shortestPath(new Cell(0, 0), new Cell(0, 2)).size());
    }
}