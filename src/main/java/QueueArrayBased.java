import java.util.NoSuchElementException;

/**
 * A queue is defined by the following API and runtimes
 *
 * <ul>
 *     <li>void enqueue(T newElement) in O(1)</li>
 *     <li>T dequeue() in O(1)</li>
 *     <li>T getFirst() in O(1)</li>
 *     <li>T getLast() in O(1)</li>
 *     <li>int size() in O(1)</li>
 * </ul>
 */
public class QueueArrayBased<T> {

    private static final int INITIAL_CAPACITY = 4;  // set so low, in order to simplify testing that things work

    private int capacity = INITIAL_CAPACITY;
    @SuppressWarnings("unchecked")
    private T[] memory = (T[]) new Object[capacity];

    private int iFirstIncl = 0;
    private int iLastIncl = -1;
    private int nElements = 0;

    public int size() {
        return nElements;
    }

    public void enqueue(T newElement) {
        if (nElements == capacity) {
            moveToLargerMemory();
        }
        if (iLastIncl == capacity - 1) {
            iLastIncl = -1;
        }
        ++iLastIncl;
        memory[iLastIncl] = newElement;
        ++nElements;
    }

    public T dequeue() {
        throwIfEmpty();
        T toBeDequeued = memory[iFirstIncl];
        memory[iFirstIncl] = null; // free reference for garbage collection
        ++iFirstIncl;
        --nElements;
        return toBeDequeued;
    }

    public T getFirst() {
        throwIfEmpty();
        return memory[iFirstIncl];
    }

    public T getLast() {
        throwIfEmpty();
        return memory[iLastIncl];
    }

    private void moveToLargerMemory() {
        int newCapacity = capacity * 2;
        @SuppressWarnings("unchecked")
        T[] newMemory = (T[]) new Object[newCapacity];
        if (isQueueWrapped()) {
            copyElementsFromWrappingQueue(newMemory);
        } else {
            copyElementsFromNonWrappingQueue(newMemory);
        }
        memory = newMemory;
        capacity = newCapacity;
        iFirstIncl = 0;
        iLastIncl = nElements - 1;
    }

    private boolean isQueueWrapped() {
        return nElements != 0 && iFirstIncl > iLastIncl;
    }

    private void copyElementsFromNonWrappingQueue(T[] newMemory) {
        int newIndex = 0;
        for (int i = iFirstIncl; i <= iLastIncl; ++i) {
            newMemory[newIndex++] = memory[i];
        }
    }

    private void copyElementsFromWrappingQueue(T[] newMemory) {
        int newIndex = 0;
        // copy elements from iFirstIncl to the end of the old memory
        for (int i = iFirstIncl; i < capacity; ++i) {
            newMemory[newIndex++] = memory[i];
        }
        // copy elements from the start of the old memory to iLastExcl
        for (int i = 0; i <= iLastIncl; ++i) {
            newMemory[newIndex++] = memory[i];
        }
    }

    private void throwIfEmpty() {
        if (nElements == 0) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
    }

}