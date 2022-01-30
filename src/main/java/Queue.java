/**
 * A Queue data structure that is based on a linked list.
 * <br>
 * <ul>
 *     <li>enqueue(T value) in O(1)</li>
 *     <li>dequeue() in O(1)</li>
 *     <li>getFirst() in O(1)</li>
 *     <li>getLast() in O(1)</li>
 * </ul>
 *
 */
public class Queue<T> {

    private final DoublyLinkedList<T> memory;

    public Queue() {
        memory = new DoublyLinkedList<>();
    }

    public void enqueue(T value) {
        memory.addBack(value);
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
}
