package binarysearchtree;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AvlTreeTest {

    @Test
    public void emptyTree_size_is_0() {
        assertEquals(0, new AvlTree<>().size());
        assertTrue(new AvlTree<>().isEmpty());
    }

    @Test
    public void emptyTree_height_is_0() {
        assertEquals(0, new AvlTree<>().computeHeight());
    }

    @Test
    public void emptyTree_search_gives_emptyOptionals() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        assertEquals(Optional.empty(), bst.search(0));
        assertEquals(Optional.empty(), bst.search(-1));
        assertEquals(Optional.empty(), bst.search(1));
        assertEquals(Optional.empty(), bst.search(2));
        assertEquals(Optional.empty(), bst.search(-2));
    }

    @Test
    public void add_element_isNotEmpty() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 41);
        assertEquals(1, bst.size());
        assertFalse(bst.isEmpty());
    }

    @Test
    public void add_remove_gives_emptyBst() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 41);
        bst.remove(1);
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
    }

    @Test
    public void add_duplicate_key_throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
            bst.add(1, 1);
            bst.add(1, 2);
        });
    }


    @Test
    public void add_1_to_5_height_is_5() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 1);
        bst.add(2, 2);
        bst.add(3, 3);
        bst.add(4, 4);
        bst.add(5, 5);
        assertEquals(3, bst.computeHeight());
    }

    @Test
    public void add_3_2_1_4_5_height_is_3() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(3, 3);
        bst.add(2, 2);
        bst.add(1, 1);
        bst.add(4, 4);
        bst.add(5, 5);
        assertEquals(3, bst.computeHeight());
    }

    @Test
    public void add_1_2_3_4_5_6_height_is_6() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 1);
        bst.add(2, 2);
        bst.add(3, 3);
        bst.add(4, 4);
        bst.add(5, 5);
        bst.add(6, 6);
        assertEquals(3, bst.computeHeight());
    }

    @Test
    public void add_4_2_3_1_6_5_height_is_3() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(4, 4);
        bst.add(2, 2);
        bst.add(3, 3);
        bst.add(1, 1);
        bst.add(6, 6);
        bst.add(5, 5);
        assertEquals(3, bst.computeHeight());
    }

    @Test
    public void add_1_to_256_height_is_256() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        for (int i = 0; i < 256; ++i) {
            bst.add(i, i);
        }
        assertEquals(9, bst.computeHeight());
    }


    @Test
    public void add_1_search_1_finds_associated_value() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 42);
        assertEquals(42, bst.search(1).orElse(0));
        assertEquals(Optional.empty(), bst.search(2));
        assertEquals(Optional.empty(), bst.search(42));
        assertEquals(Optional.empty(), bst.search(-1));
    }

    @Test
    public void add_1_2_3_4_5_search_finds_associated_values() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();
        bst.add(1, 41);
        bst.add(2, 42);
        bst.add(3, 43);
        bst.add(4, 44);
        bst.add(5, 45);
        assertEquals(41, bst.search(1).orElse(0));
        assertEquals(42, bst.search(2).orElse(0));
        assertEquals(43, bst.search(3).orElse(0));
        assertEquals(44, bst.search(4).orElse(0));
        assertEquals(45, bst.search(5).orElse(0));
        assertEquals(Optional.empty(), bst.search(0));
        assertEquals(Optional.empty(), bst.search(42));
        assertEquals(Optional.empty(), bst.search(-1));
    }

    @Test
    public void add_remove_search_works_together() {
        BinarySearchTree<Integer, Integer> bst = new AvlTree<>();

        bst.add(1, 41);
        bst.remove(1);
        bst.add(2, 42);
        bst.add(3, 43);
        assertEquals(Optional.empty(), bst.search(1));
        assertEquals(42, bst.search(2).orElse(0));
        assertEquals(43, bst.search(3).orElse(0));

        bst.add(5, 45);
        bst.add(1, 41);
        bst.add(4, 44);
        assertEquals(41, bst.search(1).orElse(0));
        assertEquals(42, bst.search(2).orElse(0));
        assertEquals(43, bst.search(3).orElse(0));
        assertEquals(44, bst.search(4).orElse(0));
        assertEquals(45, bst.search(5).orElse(0));

        bst.remove(2);
        bst.remove(3);
        bst.remove(4);
        assertEquals(41, bst.search(1).orElse(0));
        assertEquals(Optional.empty(), bst.search(2));
        assertEquals(Optional.empty(), bst.search(3));
        assertEquals(Optional.empty(), bst.search(4));
        assertEquals(45, bst.search(5).orElse(0));
    }

}