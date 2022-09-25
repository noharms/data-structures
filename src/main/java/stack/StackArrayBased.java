package stack;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * A stack is defined by the API with runtimes
 * <ul>
 *     <li>void push(Object newElement) in O(1)</li>
 *     <li>Object pop() in O(1)</li>
 *     <li>Object peek() in O(1)</li>
 *     <li>int size() in O(1)</li>
 * </ul>
 *
 * @param <T>
 */
public class StackArrayBased<T> {

    private final int INITIAL_CAPACITY = 48;

    private int capacity = INITIAL_CAPACITY;
    @SuppressWarnings("unchecked")
    private T[] memory = (T[]) new Object[capacity];

    private int nElements = 0;

    public void push(T newElement) {
        if (nElements == capacity) {
            moveToLargerMemory();
        }
        memory[nElements++] = newElement;
    }

    private void moveToLargerMemory() {
        int newCapacity = capacity * 2;
        memory = Arrays.copyOf(memory, newCapacity);
        capacity = newCapacity;
    }

    public T pop() {
        if (nElements == 0) {
            throw new EmptyStackException();
        }
        T toBePopped = memory[nElements - 1];
        memory[nElements - 1] = null; // free reference for garbage container
        nElements--;
        return toBePopped;
    }

    public T peek() {
        if (nElements == 0) {
            throw new EmptyStackException();
        }
        return memory[nElements - 1];
    }

    public int size() {
        return nElements;
    }

}