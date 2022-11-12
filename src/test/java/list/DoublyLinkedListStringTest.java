package list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoublyLinkedListStringTest {

    @Test
    void peekFront_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<String>()::getFirst);
    }

    @Test
    void peekBack_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<String>()::getLast);
    }

    @Test
    void popFront_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<String>()::removeFirst);
    }

    @Test
    void popBack_emptyList_throws() {
        assertThrows(IllegalStateException.class, new DoublyLinkedList<String>()::removeLast);
    }

    @Test
    void addFront_one_element_then_peek_pop() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("42");
        assertEquals("42", list.getFirst());
        assertEquals("42", list.getLast());
        assertEquals("42", list.removeFirst());
        assertThrows(IllegalStateException.class, list::getFirst);
        assertThrows(IllegalStateException.class, list::getLast);
    }

    @Test
    void addFront_two_elements_then_peek_pop() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("42");
        list.addFirst("1");
        assertEquals("1", list.getFirst());
        assertEquals("42", list.getLast());
        assertEquals("1", list.removeFirst());
        assertEquals("42", list.getFirst());
        assertEquals("42", list.getLast());
        assertEquals("42", list.removeLast());
    }

    @Test
    void addFront_addBack_multiple_elements_then_peek_pop() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("42");
        list.addFirst("1");
        list.addLast("2");
        list.addFirst("0");
        list.addLast("3");

        assertEquals("0", list.getFirst());
        assertEquals("3", list.getLast());
        assertEquals("0", list.removeFirst());
        assertEquals("1", list.getFirst());
        assertEquals("3", list.getLast());
        assertEquals("3", list.removeLast());
        assertEquals("2", list.removeLast());
        assertEquals("42", list.removeLast());
    }


    @Test
    void reverse_changes_nothing_on_empty_list() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();

        list.reverse();

        assertEquals(new DoublyLinkedList<String>(), list);
    }

    @Test
    void reverse_changes_nothing_on_one_element_list() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("42");
        DoublyLinkedList<String> copy = new DoublyLinkedList<>();
        copy.addFirst("42");

        list.reverse();

        assertEquals(copy, list);
    }

    @Test
    void reverse_on_multiple_element_list_works_as_expected() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("3");
        list.addLast("2");
        list.addLast("1");

        list.reverse();

        assertEquals("1", list.removeFirst());
        assertEquals("2", list.removeFirst());
        assertEquals("3", list.removeFirst());
    }
}