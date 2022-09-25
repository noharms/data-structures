package stack;

import java.util.EmptyStackException;
import java.util.LinkedList;

/** Stack which in addition to the normal stack api provides
 * <br>
 * T min() in O(1)
 */
final public class StackWithMin<T extends Comparable<T>> {

    private final LinkedList<T> stack = new LinkedList<>();
    private final LinkedList<T> minStack = new LinkedList<>();

    public int size() {
        return stack.size();
    }

    // Caveat: self-use of size
    public boolean isEmpty() {
        return size() == 0;
    }

    public void push(T value) {
        stack.push(value);
        if (minStack.isEmpty() || value.compareTo(minStack.peek()) < 0) {
            minStack.push(value);
        }
    }

    public T peek() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.peek();
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        T result = stack.remove();
        if (result.compareTo(minStack.peek()) == 0) { // warning can be suppressed: if !stack.isEmpty also !minStack.isEmpty
            minStack.remove();
        }
        return result;
    }

    public T min() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return minStack.peek();
    }

}
