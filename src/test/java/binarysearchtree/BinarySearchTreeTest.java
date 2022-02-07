package binarysearchtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BinarySearchTreeTest {

    @Test
    public void add_duplicate_key_throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
            bst.add(1, 1);
            bst.add(1, 2);
        });
    }

    @Test
    public void emptyTree_height_is_0() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        assertEquals(0, bst.computeHeight());
    }

    @Test
    public void add_1_2_3_height_is_3() {
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        bst.add(1, 1);
        bst.add(2, 2);
        bst.add(3, 3);
        assertEquals(3, bst.computeHeight());
    }

}