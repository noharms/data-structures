package tree.directed;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RootedTreeTest {

    private static final TreeNode<Integer> NODE_1 = new TreeNode<>(1);
    private static final TreeNode<Integer> NODE_2 = new TreeNode<>(2);
    private static final TreeNode<Integer> NODE_3 = new TreeNode<>(3);
    private static final TreeNode<Integer> NODE_4 = new TreeNode<>(4);
    private static final TreeNode<Integer> NODE_5 = new TreeNode<>(5);
    private static final TreeNode<Integer> NODE_6 = new TreeNode<>(6);
    private static final TreeNode<Integer> NODE_7 = new TreeNode<>(7);
    private static final TreeNode<Integer> NODE_8 = new TreeNode<>(8);
    private static final TreeNode<Integer> NODE_9 = new TreeNode<>(9);
    private static final TreeNode<Integer> NODE_10 = new TreeNode<>(10);
    private static final TreeNode<Integer> NODE_11 = new TreeNode<>(11);
    private static final TreeNode<Integer> NODE_12 = new TreeNode<>(12);
    private static final TreeNode<Integer> NODE_13 = new TreeNode<>(13);

    private static RootedTree<Integer> manuallyConstructedExampleTree() {
        /*                    1
         *        2      3          4       5
         *            6     7                8
         *               9 10 11              12
         *                 13
         */
        NODE_1.add(NODE_2);
        NODE_1.add(NODE_3);
        NODE_1.add(NODE_4);
        NODE_1.add(NODE_5);

        NODE_3.add(NODE_6);
        NODE_3.add(NODE_7);
        NODE_5.add(NODE_8);

        NODE_7.add(NODE_9);
        NODE_7.add(NODE_10);
        NODE_7.add(NODE_11);
        NODE_8.add(NODE_12);

        NODE_10.add(NODE_13);

        return new RootedTree<>(NODE_1);
    }

    private static Map<Integer, List<Integer>> getProgrammaticTreeSpecification() {
        return Map.of(
            1, List.of(2, 3, 4, 5),
            3, List.of(6, 7),
            5, List.of(8),
            7, List.of(9, 10, 11),
            8, List.of(12),
            10, List.of(13)
        );
    }

    @Test
    void findAllNodesOnLevel_exampleTree_givesExpectedCounts() {
        RootedTree<Integer> tree = manuallyConstructedExampleTree();

        assertEquals(List.of(NODE_1), tree.findAllNodesOnLevel(0));
        assertEquals(List.of(NODE_2, NODE_3, NODE_4, NODE_5), tree.findAllNodesOnLevel(1));
        assertEquals(List.of(NODE_6, NODE_7, NODE_8), tree.findAllNodesOnLevel(2));
        assertEquals(List.of(NODE_9, NODE_10, NODE_11, NODE_12), tree.findAllNodesOnLevel(3));
        assertEquals(List.of(NODE_13), tree.findAllNodesOnLevel(4));
        assertEquals(emptyList(), tree.findAllNodesOnLevel(5));
        assertEquals(emptyList(), tree.findAllNodesOnLevel(42));
    }

    @Test
    void findAllNodesOnLevel_negativeLevel_throws() {
        assertThrows(IllegalArgumentException.class, () -> manuallyConstructedExampleTree().findAllNodesOnLevel(-1));
    }

    @Test
    void from_equivalentToExampleTreeSpecification_givesSameResults() {
        RootedTree<Integer> tree = RootedTree.from(getProgrammaticTreeSpecification(), 1);

        assertEquals(List.of(NODE_1), tree.findAllNodesOnLevel(0));
        assertEquals(List.of(NODE_2, NODE_3, NODE_4, NODE_5), tree.findAllNodesOnLevel(1));
        assertEquals(List.of(NODE_6, NODE_7, NODE_8), tree.findAllNodesOnLevel(2));
        assertEquals(List.of(NODE_9, NODE_10, NODE_11, NODE_12), tree.findAllNodesOnLevel(3));
        assertEquals(List.of(NODE_13), tree.findAllNodesOnLevel(4));
        assertEquals(emptyList(), tree.findAllNodesOnLevel(5));
        assertEquals(emptyList(), tree.findAllNodesOnLevel(42));
    }

    @Test
    void computeLevelToNodesWithQueue_exampleTree() {
        RootedTree<Integer> tree = RootedTree.from(getProgrammaticTreeSpecification(), 1);

        Map<Integer, List<TreeNode<Integer>>> expectedLevelToNodes = Map.of(
            0, List.of(NODE_1),
            1, List.of(NODE_2, NODE_3, NODE_4, NODE_5),
            2, List.of(NODE_6, NODE_7, NODE_8),
            3, List.of(NODE_9, NODE_10, NODE_11, NODE_12),
            4, List.of(NODE_13)
        );

        assertEquals(expectedLevelToNodes, tree.computeLevelToNodesWithQueue());
    }

    @Test
    void computeLevelToNodesWithTraversal_exampleTree() {
        RootedTree<Integer> tree = RootedTree.from(getProgrammaticTreeSpecification(), 1);

        Map<Integer, List<TreeNode<Integer>>> expectedLevelToNodes = Map.of(
            0, List.of(NODE_1),
            1, List.of(NODE_2, NODE_3, NODE_4, NODE_5),
            2, List.of(NODE_6, NODE_7, NODE_8),
            3, List.of(NODE_9, NODE_10, NODE_11, NODE_12),
            4, List.of(NODE_13)
        );

        assertEquals(expectedLevelToNodes, tree.computeLevelToNodesWithTraversal());
    }

    @Test
    void findLevel_exampleTree() {
        RootedTree<Integer> tree = RootedTree.from(getProgrammaticTreeSpecification(), 1);

        assertEquals(0, tree.findLevel(1));
        assertEquals(1, tree.findLevel(2));
        assertEquals(1, tree.findLevel(3));
        assertEquals(1, tree.findLevel(4));
        assertEquals(1, tree.findLevel(5));
        assertEquals(2, tree.findLevel(6));
        assertEquals(2, tree.findLevel(7));
        assertEquals(2, tree.findLevel(8));
        assertEquals(3, tree.findLevel(9));
        assertEquals(3, tree.findLevel(10));
        assertEquals(3, tree.findLevel(11));
        assertEquals(3, tree.findLevel(12));
        assertEquals(4, tree.findLevel(13));
    }

    @Test
    void findLevel_notContainedValue_throws() {
        RootedTree<Integer> tree = RootedTree.from(getProgrammaticTreeSpecification(), 1);
        assertThrows(NoSuchElementException.class, () -> tree.findLevel(42));
    }
}
