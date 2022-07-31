import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StackArrayBasedTest {

    @Test
    public void emptyStack_peek_throws() {
        assertThrows(EmptyStackException.class, new StackArrayBased<>()::peek);
    }

    @Test
    public void emptyStack_pop_throws() {
        assertThrows(EmptyStackException.class, new StackArrayBased<>()::pop);
    }

    @Test
    public void push_one_element_then_peek_pop() {
        StackArrayBased<Integer> stack = new StackArrayBased<>();
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
    }

    @Test
    public void push_two_elements_then_peek_pop() {
        StackArrayBased<Integer> stack = new StackArrayBased<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
    }

    @Test
    public void push_multiple_elements_then_peek_pop() {
        StackArrayBased<Integer> stack = new StackArrayBased<>();
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
        StackArrayBased<String> stack = new StackArrayBased<>();
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

}