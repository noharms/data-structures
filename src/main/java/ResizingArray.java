import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A wrapper for a generic array that automatically doubles its capacity when {@code add} would raise the number
 * of elements beyond the current capacity.
 *
 * <br>
 *
 * <ul>
 *     <li>get(int index) in O(1)</li>
 *     <li>set(int index, T value) in O(1)</li>
 *     <li>contains(T value) in O(n)</li>
 * </ul>
 */
public class ResizingArray<T> {

    private static final int INITIAL_CAPACITY = 2;  // made small for testing

    private final Class<? extends T> elementClazz;
    private T[] memory;
    private int nElements;

    @SuppressWarnings("unchecked")
    public ResizingArray(Class<? extends T> elementClazz) {
        this.elementClazz = elementClazz;
        this.memory = (T[]) Array.newInstance(elementClazz, INITIAL_CAPACITY);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nElements;
    }

    public boolean contains(T value) {
        return find(value) != -1;
    }

    /**
     * Returns the index of the first element matching the given value, or, if none matches, returns -1.
     */
    public final int find(T value) {
        for (int i = 0; i < size(); ++i) {
            T current = get(i);
            if (current.equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Throws IndexOutOfBoundsException if the index is less than 0 or beyond the current size.
     */
    public void set(int index, T value) {
        if (index < 0 || index >= nElements) {
            throw new IndexOutOfBoundsException("Index %s is beyond the current size %s.".formatted(index, size()));
        }
        memory[index] = value;
    }

    public T get(int index) {
        if (index >= 0 && index < size()) {
            return memory[index];
        } else {
            throw new IndexOutOfBoundsException("Index %s is out of bounds for length %s.".formatted(index, size()));
        }
    }

    public void add(T element) {
        if (isMemoryFull()) {
            resizeMemory();
        }
        memory[nElements++] = element;
    }

    @SuppressWarnings("unchecked")
    private void resizeMemory() {
        T[] newMemory = (T[]) Array.newInstance(elementClazz, memory.length * 2);
        System.arraycopy(memory, 0, newMemory, 0, memory.length);
        memory = newMemory;
    }

    private boolean isMemoryFull() {
        return size() == memory.length;
    }

    /**
     * If the index is less than 0 or beyond the range of elements in the List, this method does nothing.
     */
    public final void removeAt(int index) {
        if (index >= 0 && index < size()) {
            System.arraycopy(memory, index + 1, memory, index, size() - index - 1);
            --nElements;
        }
    }

    /**
     * Removes the first occurrence of the value, if present in the array, and otherwise does nothing.
     */
    public void removeFirst(T value) {
        int index = find(value);
        if (index >= 0) {
            removeAt(index);
        }
    }


}
