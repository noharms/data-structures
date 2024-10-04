package tree.directed.binary;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tree.directed.binary.BinaryTreeTraversals.*;

class BinaryTreeTraversalsTest {

    @Test
    void in_order_on_null_gives_unchanged_list() {
        List<?> result = new ArrayList<>();
        inOrderRecursive(null, result);

        assertEquals(emptyList(), result);
    }

    @Test
    void pre_order_on_null_gives_unchanged_list() {
        List<?> result = new ArrayList<>();
        preOrderRecursive(null, result);

        assertEquals(emptyList(), result);
    }

    @Test
    void post_order_on_null_gives_unchanged_list() {
        List<?> result = new ArrayList<>();
        postOrderRecursive(null, result);

        assertEquals(emptyList(), result);
    }

    @Test
    void in_order_on_one_node_gives_just_one_node() {
        List<Integer> result = new ArrayList<>();
        inOrderRecursive(new BinaryTreeNode<>(1), result);

        assertEquals(List.of(1), result);
    }

    @Test
    void pre_order_on_one_node_gives_just_one_node() {
        List<Integer> result = new ArrayList<>();
        preOrderRecursive(new BinaryTreeNode<>(1), result);

        assertEquals(List.of(1), result);
    }

    @Test
    void post_order_on_one_node_gives_just_one_node() {
        List<Integer> result = new ArrayList<>();
        postOrderRecursive(new BinaryTreeNode<>(1), result);

        assertEquals(List.of(1), result);
    }

    @Test
    void in_order_on_perfect_tree_with_2_levels() {
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
        List<Integer> result = new ArrayList<>();

        inOrderRecursive(n1, result);

        assertEquals(List.of(4, 2, 5, 1, 6, 3, 7), result);
    }

    @Test
    void pre_order_on_perfect_tree_with_2_levels() {
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
        List<Integer> result = new ArrayList<>();

        preOrderRecursive(n1, result);

        assertEquals(List.of(1, 2, 4, 5, 3, 6, 7), result);
    }

    @Test
    void post_order_on_perfect_tree_with_2_levels() {
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
        List<Integer> result = new ArrayList<>();

        postOrderRecursive(n1, result);

        assertEquals(List.of(4, 5, 2, 6, 7, 3, 1), result);
    }

    @Test
    void in_order_on_degenerate_tree_that_is_linked_list() {
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
        List<Integer> result = new ArrayList<>();

        inOrderRecursive(n1, result);

        assertEquals(List.of(4, 3, 2, 1), result);
    }

    @Test
    void pre_order_on_degenerate_tree_that_is_linked_list() {
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
        List<Integer> result = new ArrayList<>();

        preOrderRecursive(n1, result);

        assertEquals(List.of(1, 2, 3, 4), result);
    }

    @Test
    void post_order_on_degenerate_tree_that_is_linked_list() {
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
        List<Integer> result = new ArrayList<>();

        postOrderRecursive(n1, result);

        assertEquals(List.of(4, 3, 2, 1), result);
    }
}