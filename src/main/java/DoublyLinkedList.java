import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(prev, node.prev) && Objects.equals(next, node.next) && Objects.equals(value,
                                                                                                        node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prev, next, value);
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
        if (isEmpty()) {
            Node<T> newNode = new Node<>(null, null, value);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> newNode = new Node<>(null, head, value);
            head.prev = newNode;
            head = newNode;
        }
        nElements++;
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

    public void reverse() {
        if (nElements <= 1) {
            return;
        }

        Node<T> old2ndNode = head.next;
        Node<T> old2ndLastNode = tail.prev;

        // reverse is easy for all "inner" nodes, that is except the head and the tail
        Node<T> current = head.next;
        while (current != null && current != tail) {
            Node<T> oldNext = current.next;
            current.next = current.prev;
            current.prev = oldNext;
            current = oldNext;
        }

        // care for head and tail
        Node<T> newHead = tail;
        newHead.prev = null;
        newHead.next = old2ndLastNode;

        Node<T> newTail = head;
        newTail.prev = old2ndNode;
        newTail.next = null;

        head = newHead;
        tail = newTail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DoublyLinkedList<?> that = (DoublyLinkedList<?>) o;
        return nElements == that.nElements && Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail, nElements);
    }
}
