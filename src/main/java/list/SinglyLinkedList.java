package list;

import java.util.LinkedList;
import java.util.List;

/**
 * A singly linked list is defined by the following API and complexities
 * <ul>
 *     <li>getFirst in O(1)</li>
 *     <li>getLast in O(1)</li>
 *     <li>addFirst in O(1)</li>
 *     <li>addLast in O(1)</li>
 *     <li>removeFirst in O(1)</li>
 *     <li>removeLast in O(n)</li>
 * </ul>
 */
public class SinglyLinkedList<T> {

    private int nElements = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    public static class Node<V> {
        private final V value;
        private Node<V> next;

        public Node(V value, Node<V> next) {
            this.value = value;
            this.next = next;
        }
    }

    public boolean isEmpty() {
        return nElements == 0;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get an element from an empty list.");
        }
        return head.value;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot get an element from an empty list.");
        }
        return tail.value;
    }

    public List<T> getAll() {
        List<T> result = new LinkedList<>();
        Node<T> curr = head;
        while (curr != null) {
            result.add(curr.value);
            curr = curr.next;
        }
        return result;
    }

    public void addFirst(T newValue) {
        Node<T> newNode = new Node<>(newValue, head);
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        ++nElements;
    }

    public void addLast(T newValue) {
        Node<T> newNode = new Node<>(newValue, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> oldTail = tail;
            oldTail.next = newNode;
        }
        tail = newNode;
        ++nElements;
    }

    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("Cannot remove first element from empty list.");
        }
        T result = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        --nElements;
        return result;
    }

    /**
     * O(n).
     */
    public T removeLast() {
        if (tail == null) {
            throw new IllegalStateException("Cannot remove last element from empty list.");
        }
        T result = tail.value;
        if (nElements == 1) {
            head = null;
            tail = null;
        } else { // nElements > 1
            tail = findLastButOneNode();
            tail.next = null;
        }
        --nElements;
        return result;
    }

    private Node<T> findLastButOneNode() {
        if (nElements <= 1) {
            throw new IllegalArgumentException("List requires at least two elements to find last but one node.");
        }
        Node<T> curr = head;
        while (curr.next.next != null) {
            curr = curr.next;
        }
        return curr;
    }

    public void reverse() {
        if (nElements <= 1) {
            return;
        }
        Node<T> left = head;
        Node<T> curr = head.next;
        while (curr != null) {
            Node<T> right = curr.next;
            curr.next = left;

            left = curr;
            curr = right;
        }

        head.next = null;
        Node<T> oldTail = tail;
        tail = head;
        head = oldTail;
    }

    /**
     * Zips this list with the given other list such that the returned list is ordered alternatingly with nodes from
     * this list and from the other list. If one of the two lists is longer than the other, its remaining nodes are
     * just appended.
     *
     * <br><br>
     * <bf>Caveat:</bf> this method works in place, i.e. the nodes in BOTH this list and the given other list will be
     * re-wired to point to different other nodes.
     *
     * <br><br>
     * For example,
     * <pre>
     * 1 -> 2 -> 3
     * 4 -> 5 -> 6 -> 7 -> 8
     * returns
     * 1 -> 4 -> 2 -> 5 -> 3 -> 6 -> 7 -> 8
     * </pre>
     *
     * <br>
     *
     * Or for example,
     * <pre>
     * 1 -> 2 -> 3 -> 7 -> 8
     * 4 -> 5 -> 6
     * returns
     * 1 -> 4 -> 2 -> 5 -> 3 -> 6 -> 7 -> 8
     * </pre>
     *
     */
    public void zipInPlace(SinglyLinkedList<T> other) {
        if (this.isEmpty()) {
            this.head = other.head;
            return;
        }
        Node<T> current1 = this.head;
        Node<T> current2 = other.head;
        while (current1 != null && current2 != null) {
            // remember next nodes
            Node<T> next1 = current1.next;
            Node<T> next2 = current2.next;
            // re-wire the links
            current1.next = current2;
            current2.next = next1 != null ? next1 : current2.next;
            // prepare for next round
            current1 = next1;
            current2 = next2;
        }
    }
}
