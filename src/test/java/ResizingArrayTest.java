import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResizingArrayTest {

    @Test
    void emptyArray_isEmpty() {
        assertTrue(new ResizingArray<>(Object.class).isEmpty());
    }

    @Test
    void emptyArray_hasZeroSize() {
        assertEquals(0, new ResizingArray<>(Object.class).size());
    }

    @Test
    void emptyArray_get_throws() {
        assertThrows(IndexOutOfBoundsException.class, () -> new ResizingArray<>(Object.class).get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> new ResizingArray<>(Object.class).get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> new ResizingArray<>(Object.class).get(-1));
    }

    @Test
    void emptyArray_contains_gives_false() {
        assertFalse(new ResizingArray<>(Object.class).contains(0));
        assertFalse(new ResizingArray<>(Object.class).contains(-1));
        assertFalse(new ResizingArray<>(Object.class).contains(1));
    }

    @Test
    void contains_element_after_add() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);

        assertTrue(numbers.contains(42));
    }

    @Test
    void set_throws_if_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> new ResizingArray<>(Integer.class).set(42, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> new ResizingArray<>(Integer.class).set(42, 0));
    }

    @Test
    void set_throws_if_index_is_illegal() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(1);
        numbers.add(2);

        assertThrows(IndexOutOfBoundsException.class, () -> numbers.set(42, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> numbers.set(42, 2));
    }

    @Test
    void add_duplicates_is_possible() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(42);
        numbers.add(42);

        assertTrue(numbers.contains(42));
    }

    @Test
    void remove_duplicate_once_other_remains() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(42);

        numbers.removeFirst(42);

        assertTrue(numbers.contains(42));
    }

    @Test
    void remove_duplicate_twice_all_are_removed() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(42);

        numbers.removeFirst(42);
        numbers.removeFirst(42);

        assertFalse(numbers.contains(42));
    }

    @Test
    void size_is_1_after_add() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);

        assertEquals(1, numbers.size());
    }

    @Test
    void size_is_2_after_twice_add() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(-42);

        assertEquals(2, numbers.size());
    }

    @Test
    void size_is_0_after_twice_add_twice_remove(){
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(-42);
        numbers.removeAt(0);
        numbers.removeAt(0);

        assertEquals(0, numbers.size());
    }

    @Test
    void removeAt_illegal_index_does_nothing() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(-42);
        numbers.removeAt(-1);
        numbers.removeAt(2);

        assertEquals(2, numbers.size());
    }

    @Test
    void removeFirst_non_contained_value_does_nothing() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(42);
        numbers.add(-42);
        numbers.removeFirst(41);
        numbers.removeFirst(0);

        assertEquals(2, numbers.size());
    }

    @Test
    void contains_string_element_after_add() {
        ResizingArray<String> numbers = new ResizingArray<>(String.class);
        numbers.add("42");

        assertTrue(numbers.contains("42"));
    }

    @Test
    void add_keeps_insertion_order() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(3);
        numbers.add(5);
        numbers.add(-1);
        numbers.add(0);

        assertEquals(3, numbers.get(0));
        assertEquals(5, numbers.get(1));
        assertEquals(-1, numbers.get(2));
        assertEquals(0, numbers.get(3));
    }

    @Test
    void add_works_when_resizing_is_needed() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        for (int i = 0; i < 1000; ++i) {
            numbers.add(i);
        }

        assertEquals(999, numbers.get(999));
        assertEquals(1000, numbers.size());
    }

    @Test
    void set_replaces_value_at_given_index() {
        ResizingArray<Integer> numbers = new ResizingArray<>(Integer.class);
        numbers.add(3);
        numbers.add(5);
        numbers.add(-1);
        numbers.set(0, 42);
        numbers.set(2, 42);

        assertEquals(42, numbers.get(0));
        assertEquals(5, numbers.get(1));
        assertEquals(42, numbers.get(2));
    }
}