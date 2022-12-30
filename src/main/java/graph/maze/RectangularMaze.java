package graph.maze;

import graph.UnweightedGraph;

/**
 * Converts a rectangular integer maze to a graph of cells, where a cell is a pair of row-index, column-index.
 * <br>
 * We assume that the integer maze consists of empty slots (0) and blocked slots (any other digit than 0).
 * So, in the graph representation of the maze each node may at maximum have four edges. One to its
 *   top,
 *   bottom,
 *   left
 *   right
 * cell. If an adjacent slot is blocked (i.e. non-zero), we consider the two as not connected in its graph
 * representation (so they are no neighbors in the graph, even though they are in the maze).
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
        int nCols = maze[0].length;
        return col < nCols - 1 && maze[row][col + 1] == FREE_SLOT;
    }

    private static boolean canConnectTop(int[][] maze, int row, int col) {
        return row > 0 && maze[row - 1][col] == FREE_SLOT;
    }

    private static boolean canConnectBottom(int[][] maze, int row, int col) {
        int nRows = maze.length;
        return row < nRows - 1 && maze[row + 1][col] == FREE_SLOT;
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
