package heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapArrayBasedTest {

    @Test
    public void emptyHeap_peek_throws() {
        assertThrows(HeapArrayBased.HeapUnderflowException.class, () -> new HeapArrayBased<>().peek());
    }

    @Test
    public void emptyHeap_pop_throws() {
        assertThrows(HeapArrayBased.HeapUnderflowException.class, () -> new HeapArrayBased<>().pop());
    }

    @Test
    public void addOneElement_peek_pop() {
        HeapArrayBased<Integer> heap = new HeapArrayBased<>();
        heap.add(1);
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
    }

    @Test
    public void addMultipleElements_peek_pop() {
        HeapArrayBased<Integer> heap = new HeapArrayBased<>();
        heap.add(1);
        heap.add(3);
        heap.add(2);
        assertEquals(3, heap.peek());
        assertEquals(3, heap.pop());
        assertEquals(2, heap.peek());
        assertEquals(2, heap.pop());
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
        assertThrows(HeapArrayBased.HeapUnderflowException.class, heap::pop);
    }

    @Test
    public void addSameElements_peek_pop() {
        HeapArrayBased<Integer> heap = new HeapArrayBased<>();
        heap.add(1);
        heap.add(1);
        heap.add(1);
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
        assertThrows(HeapArrayBased.HeapUnderflowException.class, heap::pop);
    }

    @Test
    public void addMultipleElements_peek_pop_addAgain() {
        HeapArrayBased<Integer> heap = new HeapArrayBased<>();
        heap.add(1);
        heap.add(6);
        heap.add(5);
        assertEquals(6, heap.peek());
        assertEquals(6, heap.pop());
        heap.add(2);
        heap.add(4);
        heap.add(3);
        assertEquals(5, heap.peek());
        assertEquals(5, heap.pop());
        assertEquals(4, heap.peek());
        assertEquals(4, heap.pop());
        assertEquals(3, heap.peek());
        assertEquals(3, heap.pop());
        assertEquals(2, heap.peek());
        assertEquals(2, heap.pop());
        assertEquals(1, heap.peek());
        assertEquals(1, heap.pop());
        assertThrows(HeapArrayBased.HeapUnderflowException.class, heap::pop);
    }

}