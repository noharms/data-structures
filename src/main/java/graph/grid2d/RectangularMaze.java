package graph.grid2d;

import graph.UnweightedGraph;

/**
 * A representation of a maze that consists of empty slots and blocked slots, where each slot may at maximum
 * have four edges: one to its top, bottom, left and right neighbor. If one of those neighbor slot is blocked,
 * we consider the two as not connected.
 */
public class RectangularMaze {

    public static final int FREE_SLOT = 0;

    public static UnweightedGraph<Cell> createGraph(int[][] maze) {
        validate(maze);

        UnweightedGraph<Cell> mazeGraph = new UnweightedGraph<>();
        addNodes(mazeGraph, maze);
        addEdges(mazeGraph, maze);
        return mazeGraph;
    }

    private static void addNodes(UnweightedGraph<Cell> mazeGraph, int[][] maze) {
        int nRows = maze.length;
        int nCols = maze[0].length;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                mazeGraph.addNode(new Cell(i, j));
            }
        }
    }

    private static void addEdges(UnweightedGraph<Cell> mazeGraph, int[][] maze) {
        int nRows = maze.length;
        int nCols = maze[0].length;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                if (maze[row][col] == FREE_SLOT) {
                    if (canConnectLeft(maze, row, col)) {
                        connectLeftNeighbor(mazeGraph, row, col);
                    }
                    if (canConnectRight(maze, row, col)) {
                        connectRightNeighbor(mazeGraph, row, col);
                    }
                    if (canConnectTop(maze, row, col)) {
                        connectTopNeighbor(mazeGraph, row, col);
                    }
                    if (canConnectBottom(maze, row, col)) {
                        connectBottomNeighbor(mazeGraph, row, col);
                    }
                }
            }
        }
    }

    private static boolean canConnectLeft(int[][] maze, int row, int col) {
        return col > 0 && maze[row][col - 1] == FREE_SLOT;
    }

    private static boolean canConnectRight(int[][] maze, int row, int col) {
        return col < maze.length - 1 && maze[row][col + 1] == FREE_SLOT;
    }

    private static boolean canConnectTop(int[][] maze, int row, int col) {
        return row > 0 && maze[row - 1][col] == FREE_SLOT;
    }

    private static boolean canConnectBottom(int[][] maze, int row, int col) {
        return row < maze.length - 1 && maze[row + 1][col] == FREE_SLOT;
    }

    private static void connectBottomNeighbor(UnweightedGraph<Cell> mazeGraph, int row, int col) {
        Cell current = new Cell(row, col);
        Cell bottom = new Cell(row + 1, col);
        mazeGraph.addUndirectedEdge(current, bottom);
    }

    private static void connectTopNeighbor(UnweightedGraph<Cell> mazeGraph, int row, int col) {
        Cell current = new Cell(row, col);
        Cell top = new Cell(row - 1, col);
        mazeGraph.addUndirectedEdge(current, top);
    }

    private static void connectRightNeighbor(UnweightedGraph<Cell> mazeGraph, int row, int col) {
        Cell current = new Cell(row, col);
        Cell right = new Cell(row, col + 1);
        mazeGraph.addUndirectedEdge(current, right);
    }

    private static void connectLeftNeighbor(UnweightedGraph<Cell> mazeGraph, int row, int col) {
        Cell current = new Cell(row, col);
        Cell left = new Cell(row, col - 1);
        mazeGraph.addUndirectedEdge(current, left);
    }

    private static void validate(int[][] maze) {
        if (maze.length == 0) {
            throw new IllegalArgumentException("Maze cannot be empty.");
        }
        int width = maze[0].length;
        for (int[] row : maze) {
            if (row.length != width) {
                throw new IllegalArgumentException("Each row of the maze must have the same width.");
            }
        }
    }

}
