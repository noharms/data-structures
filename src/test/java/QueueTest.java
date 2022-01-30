import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    public void dequeue_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new Queue<>()::dequeue);
    }

    @Test
    public void getFirst_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new Queue<>()::getFirst);
    }

    @Test
    public void getLast_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new Queue<>()::getLast);
    }

    @Test
    public void enqueue_one_element_then_getFirst_getLast_dequeue() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        assertEquals(1, queue.getFirst());
        assertEquals(1, queue.getLast());
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void enqueue_two_elements_then_getFirst_getLast_dequeue() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.getFirst());
        assertEquals(2, queue.getLast());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.getFirst());
        assertEquals(2, queue.getLast());
    }

    @Test
    public void enqueue_multiple_elements_then_getFirst_getLast_dequeue() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.getFirst());
        assertEquals(3, queue.getLast());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.getFirst());
        assertEquals(3, queue.getLast());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.getFirst());
        assertEquals(3, queue.getLast());
        assertEquals(3, queue.dequeue());
    }
}