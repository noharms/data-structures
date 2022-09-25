package queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueDLLBasedTest {

    @Test
    public void dequeue_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueDLLBased<>()::dequeue);
    }

    @Test
    public void getFirst_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueDLLBased<>()::getFirst);
    }

    @Test
    public void getLast_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueDLLBased<>()::getLast);
    }

    @Test
    public void enqueue_one_element_then_getFirst_getLast_dequeue() {
        QueueDLLBased<Integer> queue = new QueueDLLBased<>();
        queue.enqueue(1);
        assertEquals(1, queue.getFirst());
        assertEquals(1, queue.getLast());
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void enqueue_two_elements_then_getFirst_getLast_dequeue() {
        QueueDLLBased<Integer> queue = new QueueDLLBased<>();
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
        QueueDLLBased<Integer> queue = new QueueDLLBased<>();
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

    @Test
    public void works_with_strings() {
        QueueDLLBased<String> queue = new QueueDLLBased<>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        assertEquals("1", queue.getFirst());
        assertEquals("3", queue.getLast());
        assertEquals("1", queue.dequeue());
        assertEquals("2", queue.getFirst());
        assertEquals("3", queue.getLast());
        assertEquals("2", queue.dequeue());
        assertEquals("3", queue.getFirst());
        assertEquals("3", queue.getLast());
        assertEquals("3", queue.dequeue());
    }
}