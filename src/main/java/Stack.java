import java.util.EmptyStackException;

/**
 * A stack data structure based on a double-linked list. No capacity limit is imposed (except physical memory).
 * <br>
 * <ul>
 *     <li>void push(T value) in O(1)</li>
 *     <li>T peek() in O(1)</li>
 *     <li>T pop() in O(1)</li>
 * </ul>
 * <p>
 * Throws EmptyStackException if pop/peek is called on empty stack.
 */
public class Stack<T> {

    private final DoublyLinkedList<T> memory;

    public Stack() {
        memory = new DoublyLinkedList<>();
    }

    public void push(T value) {
        memory.addFront(value);
    }

    public T peek() {
        if (size() > 0) {
            return memory.peekFront();
        } else {
            throw new EmptyStackException();
        }
    }

    public T pop() {
        if (size() > 0) {
            return memory.popFront();
        } else {
            throw new EmptyStackException();
        }
    }

    public int size() {
        return memory.size();
    }
}
