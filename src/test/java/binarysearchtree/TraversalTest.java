package binarysearchtree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TraversalTest {

    @Test
    void inOrder_on_empty_tree_gives_empty_list() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();

        assertEquals(emptyList(), Traversal.inOrder(bst));
    }

    @Test
    void inOrder_on_one_element_tree_gives_list_with_one_element() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(42, 3);

        assertEquals(List.of(42), Traversal.inOrder(bst));
    }

    @Test
    void inOrder_on_multiple_element_tree_gives_sorted_list() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(3, 3);
        bst.add(2, 2);
        bst.add(1, 1);
        bst.add(4, 4);
        bst.add(5, 5);

        assertEquals(List.of(1, 2, 3, 4, 5), Traversal.inOrder(bst));
    }

    @Test
    void inOrderIterative_on_empty_tree_gives_empty_list() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();

        assertEquals(emptyList(), Traversal.inOrderIterative(bst));
    }

    @Test
    void inOrderIterative_on_one_element_tree_gives_list_with_one_element() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(42, 3);

        assertEquals(List.of(42), Traversal.inOrderIterative(bst));
    }

    @Test
    void inOrderIterative_on_multiple_element_tree_gives_sorted_list() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(3, 3);
        bst.add(2, 2);
        bst.add(1, 1);
        bst.add(4, 4);
        bst.add(5, 5);

        assertEquals(List.of(1, 2, 3, 4, 5), Traversal.inOrderIterative(bst));
    }
}