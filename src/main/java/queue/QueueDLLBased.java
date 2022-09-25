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
        memory.addBack(value);
    }

    public void enqueueAll(List<T> values) {
        for (T value : values) {
            memory.addBack(value);
        }
    }

    public T dequeue() {
        return memory.popFront();
    }

    public T getFirst() {
        return memory.peekFront();
    }

    public T getLast() {
        return memory.peekBack();
    }

    public boolean isEmpty() {
        return memory.isEmpty();
    }
}
