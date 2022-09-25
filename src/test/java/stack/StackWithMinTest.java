package stack;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StackWithMinTest {

    @Test
    public void emptyStack_peek_throws() {
        assertThrows(EmptyStackException.class, new StackWithMin<>()::peek);
    }

    @Test
    public void emptyStack_pop_throws() {
        assertThrows(EmptyStackException.class, new StackWithMin<>()::pop);
    }

    @Test
    public void push_one_element_then_peek_pop() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
    }

    @Test
    public void push_two_elements_then_peek_pop() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
    }

    @Test
    public void push_multiple_elements_then_peek_pop() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.peek());
        assertEquals(2, stack.pop());
        stack.push(42);
        assertEquals(42, stack.peek());
        assertEquals(42, stack.pop());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
    }

    @Test
    public void works_with_strings() {
        StackWithMin<String> stack = new StackWithMin<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        assertEquals("3", stack.peek());
        assertEquals("3", stack.pop());
        assertEquals("2", stack.peek());
        assertEquals("2", stack.pop());
        stack.push("42");
        assertEquals("42", stack.peek());
        assertEquals("42", stack.pop());
        assertEquals("1", stack.peek());
        assertEquals("1", stack.pop());
    }

    @Test
    void empty_stack_min_throws() {
        assertThrows(EmptyStackException.class, () -> new StackWithMin<>().min());
    }

    @Test
    void one_element_stack_min_gives_element() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(42);
        assertEquals(42, stack.min());
    }

    @Test
    void one_element_stack_min_after_pop_throws() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(42);
        assertEquals(42, stack.min());
        stack.pop();
        assertThrows(EmptyStackException.class, stack::min);
    }

    @Test
    void multiple_elements_stack_min_works() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        stack.push(2);
        stack.push(1);
        stack.push(3);
        assertEquals(1, stack.min());
        stack.push(0);
        assertEquals(0, stack.min());
        stack.pop(); // pop the 0
        assertEquals(1, stack.min());
        stack.pop(); // pop the 3
        assertEquals(1, stack.min());
        stack.pop(); // pop the 1
        assertEquals(2, stack.min());
    }
}