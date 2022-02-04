/**
 * A node (with left and right child nodes) based heap data structure.
 * <br>
 *
 * <ul>
 *     <li>T peek() in O(1)</li>
 *     <li>T pop() in O(1)</li>
 *     <li>void add(T value) in O(log n)</li>
 * </ul>
 *
 *
 */
public class Heap<T> {

    private static class Node<T> {
        private T value;
        private Node<T> top;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }

    private static class HeapUnderflowException extends RuntimeException {
        public HeapUnderflowException(String s) {
            super(s);
        }
    }

    private Node<T> root;
    private int nElements;

    public int size() {
        return nElements;
    }

    private boolean isEmpty() {
        return nElements == 0;
    }

    public T peek() {
        if (isEmpty()) {
            throw new HeapUnderflowException("Heap is empty - cannot peek.");
        }
        return root.value;
    }

    public T pop() {
        return null; // TODO
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);

    }

}
