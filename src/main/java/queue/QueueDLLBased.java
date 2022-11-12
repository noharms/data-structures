package queue;

import list.DoublyLinkedList;

import java.util.List;

/**
 * A Queue data structure that is based on a doubly linked list.
 * <br>
 * <ul>
 *     <li>enqueue(T value) in O(1)</li>
 *     <li>dequeue() in O(1)</li>
 *     <li>getFirst() in O(1)</li>
 *     <li>getLast() in O(1)</li>
 * </ul>
 */
public class QueueDLLBased<T> {

    private final DoublyLinkedList<T> memory;

    public QueueDLLBased() {
        memory = new DoublyLinkedList<>();
    }

    public void enqueue(T value) {
        memory.addLast(value);
    }

    public void enqueueAll(List<T> values) {
        for (T value : values) {
            memory.addLast(value);
        }
    }

    public T dequeue() {
        return memory.removeFirst();
    }

    public T getFirst() {
        return memory.getFirst();
    }

    public T getLast() {
        return memory.getLast();
    }

    public boolean isEmpty() {
        return memory.isEmpty();
    }
}
