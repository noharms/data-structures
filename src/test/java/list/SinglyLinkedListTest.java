package list;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SinglyLinkedListTest {

    @Test
    void getFirst_emptyList_throws() {
        assertThrows(IllegalStateException.class, new SinglyLinkedList<Integer>()::getFirst);
    }

    @Test
    void getLast_emptyList_throws() {
        assertThrows(IllegalStateException.class, new SinglyLinkedList<Integer>()::getLast);
    }

    @Test
    void removeFirst_emptyList_throws() {
        assertThrows(IllegalStateException.class, new SinglyLinkedList<Integer>()::removeFirst);
    }

    @Test
    void removeLast_emptyList_throws() {
        assertThrows(IllegalStateException.class, new SinglyLinkedList<Integer>()::removeLast);
    }

    @Test
    void getAll_after_addFirst_gives_list_in_correct_order() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);

        assertEquals(List.of(5, 4, 3, 2, 1), list.getAll());
    }

    @Test
    void getAll_after_addLast_gives_list_in_correct_order() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);

        assertEquals(List.of(1, 2, 3, 4, 5), list.getAll());
    }

    @Test
    void getAll_after_addFirst_addLast_gives_list_in_correct_order() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addFirst(2);
        list.addLast(3);
        list.addFirst(4);
        list.addFirst(5);
        list.addLast(6);

        assertEquals(List.of(5, 4, 2, 1, 3, 6), list.getAll());
    }

    @Test
    void duplicates_works() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(1);
        list.addFirst(1);

        assertEquals(List.of(1, 1, 1), list.getAll());
    }

    @Test
    void addFirst_one_element_then_peek_pop() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(42);
        assertEquals(42, list.getFirst());
        assertEquals(42, list.getLast());
        assertEquals(42, list.removeFirst());
        assertThrows(IllegalStateException.class, list::getFirst);
        assertThrows(IllegalStateException.class, list::getLast);
    }

    @Test
    void addFirst_two_elements_then_peek_pop() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(42);
        list.addFirst(1);
        assertEquals(1, list.getFirst());
        assertEquals(42, list.getLast());
        assertEquals(1, list.removeFirst());
        assertEquals(42, list.getFirst());
        assertEquals(42, list.getLast());
        assertEquals(42, list.removeLast());
    }

    @Test
    void addFirst_addLast_multiple_elements_then_peek_pop() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(42);
        list.addFirst(1);
        list.addLast(2);
        list.addFirst(0);
        list.addLast(3);

        assertEquals(0, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(0, list.removeFirst());
        assertEquals(1, list.getFirst());
        assertEquals(3, list.getLast());
        assertEquals(3, list.removeLast());
        assertEquals(2, list.removeLast());
        assertEquals(42, list.removeLast());
    }

    @Test
    void reverse_changes_nothing_on_empty_list() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.reverse();

        assertEquals(emptyList(), list.getAll());
    }

    @Test
    void reverse_changes_nothing_on_duplicates_list() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(1);
        list.addFirst(1);

        list.reverse();

        assertEquals(List.of(1, 1, 1), list.getAll());
    }

    @Test
    void reverse_changes_nothing_on_one_element_list() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(42);

        list.reverse();

        assertEquals(List.of(42), list.getAll());
    }

    @Test
    void reverse_on_multiple_element_list_works_as_expected() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);

        list.reverse();

        assertEquals(List.of(1, 2, 3), list.getAll());
    }

    @Test
    void zip_empty_list_with_non_empty_other_list_effectively_makes_both_lists_identical() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(1);
        l2.addLast(2);
        l2.addLast(3);

        l1.zipInPlace(l2);

        assertEquals(List.of(1, 2, 3), l1.getAll());
        assertEquals(List.of(1, 2, 3), l2.getAll());
    }

    @Test
    void zip_non_empty_list_with_empty_other_list_does_change_any_of_the_two_lists() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(1);
        l1.addLast(2);
        l1.addLast(3);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();

        l1.zipInPlace(l2);

        assertEquals(List.of(1, 2, 3), l1.getAll());
        assertEquals(List.of(), l2.getAll());
    }

    @Test
    void zip_two_lists_both_size_1() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(42);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(43);

        l1.zipInPlace(l2);

        assertEquals(List.of(42, 43), l1.getAll());
        assertEquals(List.of(43), l2.getAll());
    }

    @Test
    void zip_two_lists_both_size_3() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(1);
        l1.addLast(2);
        l1.addLast(3);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(4);
        l2.addLast(5);
        l2.addLast(6);

        l1.zipInPlace(l2);

        assertEquals(List.of(1, 4, 2, 5, 3, 6), l1.getAll());
        assertEquals(List.of(4, 2, 5, 3, 6), l2.getAll());
    }

    @Test
    void zip_two_lists_where_this_list_is_longer() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(1);
        l1.addLast(2);
        l1.addLast(3);
        l1.addLast(7);
        l1.addLast(8);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(4);
        l2.addLast(5);
        l2.addLast(6);

        l1.zipInPlace(l2);

        assertEquals(List.of(1, 4, 2, 5, 3, 6, 7, 8), l1.getAll());
        assertEquals(List.of(4, 2, 5, 3, 6, 7, 8), l2.getAll());
    }

    @Test
    void zip_two_lists_where_the_other_list_is_longer() {
        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(1);
        l1.addLast(2);
        l1.addLast(3);
        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(4);
        l2.addLast(5);
        l2.addLast(6);
        l2.addLast(7);
        l2.addLast(8);

        l1.zipInPlace(l2);

        assertEquals(List.of(1, 4, 2, 5, 3, 6, 7, 8), l1.getAll());
        assertEquals(List.of(4, 2, 5, 3, 6, 7, 8), l2.getAll());
    }

}