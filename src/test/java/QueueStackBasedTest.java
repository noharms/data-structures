import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueStackBasedTest {

    @Test
    public void dequeue_emptyQueueStackBased_throws() {
        assertThrows(IllegalStateException.class, new QueueStackBased<>()::dequeue);
    }

    @Test
    public void getFirst_emptyQueueStackBased_throws() {
        assertThrows(IllegalStateException.class, new QueueStackBased<>()::getFirst);
    }

    @Test
    public void getLast_emptyQueueStackBased_throws() {
        assertThrows(IllegalStateException.class, new QueueStackBased<>()::getLast);
    }

    @Test
    public void enqueue_one_element_then_getFirst_getLast_dequeue() {
        QueueStackBased<Integer> queue = new QueueStackBased<>();
        queue.enqueue(1);
        assertEquals(1, queue.getFirst());
        assertEquals(1, queue.getLast());
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void enqueue_two_elements_then_getFirst_getLast_dequeue() {
        QueueStackBased<Integer> queue = new QueueStackBased<>();
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
        QueueStackBased<Integer> queue = new QueueStackBased<>();
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
        Queue<String> queue = new Queue<>();
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