import org.jetbrains.annotations.Nullable;

/**
 * A double-ended doubly-linked list, holding elements of the generic type parameter T. The provided API is
 *
 * <ul>
 *     <li>addFront(T value) in O(1) </li>
 *     <li>addBack(T value) in O(1) </li>
 *     <li>popFront() in O(1) </li>
 *     <li>popBack() in O(1) </li>
 *     <li>peekFront() in O(1) </li>
 *     <li>peekBack() in O(1) </li>
 * </ul>
 */
public class DoublyLinkedList<T> {

    private static class Node<T> {
        @Nullable
        private Node<T> prev;
        @Nullable
        private Node<T> next;
        private T value;

        private Node(@Nullable Node<T> prev, @Nullable Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Nullable
    private Node<T> head;
    @Nullable
    private Node<T> tail;
    private int nElements;

    public int size() {
        return nElements;
    }

    public void addFront(T value) {
        head = new Node<>(null, head, value);
        nElements++;
        if (tail == null) {  // list was empty before
            tail = head;
        }
    }

    public T popFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty linked list.");
        }
        T valueToPop = head.value;
        head = head.next;
        removePrevReference(head);
        nElements--;
        return valueToPop;
    }

    public T peekFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty linked list.");
        }
        return head.value;
    }

    public void addBack(T value) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<>(oldTail, null, value);
        if (oldTail != null) {
            oldTail.next = newNode;
        }
        tail = newNode;
        nElements++;
        if (head == null) { // list was empty before
            head = tail;
        }
    }

    public T popBack() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty linked list.");
        }
        Node<T> oldTail = tail;
        T valueToPop = oldTail.value;
        tail = oldTail.prev; // if list contained only oldTail, list is empty after pop, so tail is set null here
        removePrevReference(oldTail);
        removeNextReference(tail);
        nElements--;
        if (isEmpty()) {
            head = null;
        }
        return valueToPop;
    }

    public T peekBack() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty linked list.");
        }
        return tail.value;
    }

    private boolean isEmpty() {
        return nElements == 0;
    }

    private void removePrevReference(Node<T> node) {
        if (node == null) {
            return;
        }
        node.prev = null;
    }

    private void removeNextReference(Node<T> node) {
        if (node == null) {
            return;
        }
        node.next = null;
    }

}
