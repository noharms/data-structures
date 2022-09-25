package queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QueueArrayBasedTest {


    @Test
    public void dequeue_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueArrayBased<>()::dequeue);
    }

    @Test
    public void getFirst_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueArrayBased<>()::getFirst);
    }

    @Test
    public void getLast_emptyQueue_throws() {
        assertThrows(IllegalStateException.class, new QueueArrayBased<>()::getLast);
    }

    @Test
    public void enqueue_one_element_then_getFirst_getLast_dequeue() {
        QueueArrayBased<Integer> queue = new QueueArrayBased<>();
        queue.enqueue(1);
        assertEquals(1, queue.getFirst());
        assertEquals(1, queue.getLast());
        assertEquals(1, queue.dequeue());
    }

    @Test
    public void enqueue_two_elements_then_getFirst_getLast_dequeue() {
        QueueArrayBased<Integer> queue = new QueueArrayBased<>();
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
        QueueArrayBased<Integer> queue = new QueueArrayBased<>();
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
        QueueArrayBased<String> queue = new QueueArrayBased<>();
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

    @Test
    public void works_when_wrapping_last_element_before_first_element_assuming_initial_capacity_is_4() {
        QueueArrayBased<Integer> queue = new QueueArrayBased<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        int firstDequeued = queue.dequeue();
        queue.enqueue(5);

        assertEquals(1, firstDequeued);
        assertEquals(2, queue.getFirst());
        assertEquals(5, queue.getLast());
    }
}