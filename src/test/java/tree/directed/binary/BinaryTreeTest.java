package tree.directed.binary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryTreeTest {

    @Test
    void single_node_has_height_0() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);

        assertEquals(0, new BinaryTree<>(node).height());
    }

    @Test
    void root_with_left_child_has_height_1() {
        /*
                1
               /
              2
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        n1.left = n2;

        assertEquals(1, new BinaryTree<>(n1).height());
    }

    @Test
    void root_with_right_child_has_height_1() {
        /*
                1
                 \
                  2
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        n1.right = n2;

        assertEquals(1, new BinaryTree<>(n1).height());
    }

    @Test
    void root_with_left_and_right_child_has_height_1() {
        /*
                1
               / \
              2   3
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        n1.left = n2;
        n1.right = n3;

        assertEquals(1, new BinaryTree<>(n1).height());
    }

    @Test
    void degenerate_linked_list() {
        /*
                       1
                      /
                     2
                    /
                   3
                  /
                 4
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4);
        n1.left = n2;
        n2.left = n3;
        n3.left = n4;

        assertEquals(3, new BinaryTree<>(n1).height());
    }

    @Test
    void perfect_tree_with_2_levels_has_height_2() {
        /*
                            1
                          /   \
                         2     3
                        / \   / \
                       4   5 6   7
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> n5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> n6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> n7 = new BinaryTreeNode<>(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;

        assertEquals(2, new BinaryTree<>(n1).height());
    }

    @Test
    void complete_tree_with_2_levels_has_height_2() {
        /*
                            1
                          /   \
                         2     3
                        / \   /
                       4   5 6
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> n5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> n6 = new BinaryTreeNode<>(6);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;

        assertEquals(2, new BinaryTree<>(n1).height());
    }

    @Test
    void single_node_has_depth_0() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);

        assertEquals(0, new BinaryTree<>(node).depth(node));
    }

    @Test
    void left_child_of_root_has_depth_1() {
        /*
                1
               /
              2
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        n1.left = n2;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(0, bt.depth(n1));
        assertEquals(1, bt.depth(n2));
    }

    @Test
    void right_child_of_root_has_depth_1() {
        /*
                1
                 \
                  2
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        n1.right = n2;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(0, bt.depth(n1));
        assertEquals(1, bt.depth(n2));
    }

    @Test
    void root_with_left_and_right_child_depths() {
        /*
                1
               / \
              2   3
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        n1.left = n2;
        n1.right = n3;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(0, bt.depth(n1));
        assertEquals(1, bt.depth(n2));
        assertEquals(1, bt.depth(n3));
    }

    @Test
    void node_which_is_not_contained_has_depth_minus_1() {
        /*
                1
               / \
              2   3
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        n1.left = n2;
        n1.right = n3;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(-1, bt.depth(new BinaryTreeNode<>(4)));
    }

    @Test
    void degenerate_linked_list_depths() {
        /*
                       1
                      /
                     2
                    /
                   3
                  /
                 4
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4);
        n1.left = n2;
        n2.left = n3;
        n3.left = n4;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(0, bt.depth(n1));
        assertEquals(1, bt.depth(n2));
        assertEquals(2, bt.depth(n3));
        assertEquals(3, bt.depth(n4));
    }

    @Test
    void perfect_tree_with_2_levels_depths() {
        /*
                            1
                          /   \
                         2     3
                        / \   / \
                       4   5 6   7
         */
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3);
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4);
        BinaryTreeNode<Integer> n5 = new BinaryTreeNode<>(5);
        BinaryTreeNode<Integer> n6 = new BinaryTreeNode<>(6);
        BinaryTreeNode<Integer> n7 = new BinaryTreeNode<>(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;

        BinaryTree<Integer> bt = new BinaryTree<>(n1);

        assertEquals(0, bt.depth(n1));
        assertEquals(1, bt.depth(n2));
        assertEquals(1, bt.depth(n3));
        assertEquals(2, bt.depth(n4));
        assertEquals(2, bt.depth(n5));
        assertEquals(2, bt.depth(n6));
        assertEquals(2, bt.depth(n7));
    }
}