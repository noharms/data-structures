import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoublyLinkedListIntegerTest {

    @Test
    void peekFront_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<Integer>()::peekFront);
    }

    @Test
    void peekBack_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<Integer>()::peekBack);
    }

    @Test
    void popFront_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<Integer>()::popFront);
    }

    @Test
    void popBack_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<Integer>()::popBack);
    }

    @Test
    void addFront_one_element_then_peek_pop() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFront(42);
        assertEquals(42, list.peekFront());
        assertEquals(42, list.peekBack());
        assertEquals(42, list.popFront());
        assertThrows(IllegalStateException.class, list::peekFront);
        assertThrows(IllegalStateException.class, list::peekBack);
    }

    @Test
    void addFront_two_elements_then_peek_pop() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFront(42);
        list.addFront(1);
        assertEquals(1, list.peekFront());
        assertEquals(42, list.peekBack());
        assertEquals(1, list.popFront());
        assertEquals(42, list.peekFront());
        assertEquals(42, list.peekBack());
        assertEquals(42, list.popBack());
    }

    @Test
    void addFront_addBack_multiple_elements_then_peek_pop() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFront(42);
        list.addFront(1);
        list.addBack(2);
        list.addFront(0);
        list.addBack(3);

        assertEquals(0, list.peekFront());
        assertEquals(3, list.peekBack());
        assertEquals(0, list.popFront());
        assertEquals(1, list.peekFront());
        assertEquals(3, list.peekBack());
        assertEquals(3, list.popBack());
        assertEquals(2, list.popBack());
        assertEquals(42, list.popBack());
    }

    @Test
    void reverse_changes_nothing_on_empty_list() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.reverse();

        assertEquals(new DoublyLinkedList<Integer>(), list);
    }

    @Test
    void reverse_changes_nothing_on_one_element_list() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addFront(42);
        DoublyLinkedList<Integer> copy = new DoublyLinkedList<>();
        copy.addFront(42);

        list.reverse();

        assertEquals(copy, list);
    }

    @Test
    void reverse_on_multiple_element_list_works_as_expected() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addBack(3);
        list.addBack(2);
        list.addBack(1);

        list.reverse();

        assertEquals(1, list.popFront());
        assertEquals(2, list.popFront());
        assertEquals(3, list.popFront());
    }
}