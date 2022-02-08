package binarysearchtree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An unbalanced binary search tree. Duplicate keys are not allowed.
 *
 * <ul>
 *     <li>V search(K key) in O(n)</li>
 *     <li>void add(K key, V value) in O(n)</li>
 *     <li>V remove (K key) in O(n)</li>
 * </ul>
 *
 * @param <K> generic type parameter for the type of the keys
 * @param <V> generic type parameter for the type of the values
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    private int nElements;
    private Node<K, V> root;

    public int size() {
        return nElements;
    }

    public boolean isEmpty() {
        return nElements == 0;
    }

    public int computeHeight() {
        return isEmpty() ? 0 : root.computeHeight();
    }

    static <U extends Comparable<U>, W> Node<U, W> findMinimum(Node<U, W> node) {
        Objects.requireNonNull(node);
        Node<U, W> minimum = node;
        while (minimum.left != null) {
            minimum = minimum.left;
        }
        return minimum;
    }

    public List<Node<K, V>> inOrder() {
        List<Node<K, V>> result = new LinkedList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node<K, V> node, List<Node<K, V>> result) {
        if (node.left != null) {
            inOrder(node, result);
        }
        result.add(node);
        if (node.right != null) {
            inOrder(node, result);
        }
    }

    public Optional<V> search(K searchKey) {
        Node<K, V> result = searchNode(searchKey, root);
        return result == null ? Optional.empty() : Optional.of(result.value());
    }

    private Node<K, V> searchNode(K searchKey, Node<K, V> current) {
        if (current == null) {
            return null;
        }
        K currentKey = current.key();
        int compareResult = currentKey.compareTo(searchKey);
        if (compareResult == 0) {
            return current;
        } else if (compareResult < 0) {
            return searchNode(searchKey, current.right);
        } else {
            return searchNode(searchKey, current.left);
        }
    }

    public void add(K key, V value) {
        Node<K, V> newNode = Node.of(key, value);
        if (isEmpty()) {
            root = newNode;
        } else {
            Node<K, V> parentForInsert = searchParentForInsert(key, root);
            if (newNode.key().compareTo(parentForInsert.key()) < 0) {
                parentForInsert.left = newNode;
            } else {
                parentForInsert.right = newNode;
            }
        }
        ++nElements;
    }

    private Node<K, V> searchParentForInsert(K searchKey, Node<K, V> current) {
        K currentKey = current.key();
        int compareResult = currentKey.compareTo(searchKey);
        if (compareResult == 0) {
            throw new IllegalArgumentException("Duplicate keys are not allowed. Remove key " + searchKey + "first.");
        } else if (compareResult < 0) {
            return current.right == null ? current : searchParentForInsert(searchKey, current.right);
        } else {
            return current.left == null ? current : searchParentForInsert(searchKey, current.left);
        }
    }

    public Optional<V> remove(K key) {
        if (isEmpty()) {
            return Optional.empty();
        }
        Node<K, V> parentOfMatch = root.findParentNode(new Node<>(new Node.KeyValuePair<>(key, null)));
        if (parentOfMatch == null) {
            if (root.key().compareTo(key) != 0) {
                return Optional.empty();
            } else {
                // root itself is the match
                V value = root.value();
                if (root.nChildren() == 0) {
                    root = null;
                } else if (root.nChildren() == 1) {
                    root = root.left != null ? root.left : root.right;
                } else {
                    replaceMatchWith2Children(root);
                }
                --nElements;
                return Optional.of(value);
            }
        } else {
            boolean isMatchLeft = parentOfMatch.left != null && parentOfMatch.left.key().compareTo(key) == 0;
            Node<K, V> match = isMatchLeft ? parentOfMatch.left : parentOfMatch.right;
            V removedValue = match.value();
            if (match.nChildren() < 2) {
                if (isMatchLeft) {
                    removeLeftChild(parentOfMatch);
                } else {
                    removeRightChild(parentOfMatch);
                }
            } else {
                replaceMatchWith2Children(match);
            }
            --nElements;
            return Optional.of(removedValue);
        }
    }

    private void replaceMatchWith2Children(Node<K, V> match) {
        Node<K, V> successor = findMinimum(match.right);
        Node.KeyValuePair<K, V> keyValueSuccessor = successor.keyValuePair;
        Node<K, V> parentOfSuccessor = match.findParentNode(successor);
        if (parentOfSuccessor.left == successor) {
            removeLeftChild(parentOfSuccessor);
        } else {
            removeRightChild(parentOfSuccessor);
        }
        // replace data content to effectively replace the node
        match.keyValuePair = keyValueSuccessor;
    }

    private void removeLeftChild(Node<K, V> node) {
        Node<K, V> toBeRemoved = node.left;
        Node<K, V> replacement = null;
        if (toBeRemoved.nChildren() == 1) {
            replacement = toBeRemoved.left != null ? toBeRemoved.left : toBeRemoved.right;
        } else if (toBeRemoved.nChildren() > 1) {
            throw new IllegalArgumentException("Accepting only nodes with 0 or 1 child.");
        }
        node.left = replacement;
    }

    private void removeRightChild(Node<K, V> node) {
        Node<K, V> toBeRemoved = node.right;
        Node<K, V> replacement = null;
        if (toBeRemoved.nChildren() == 1) {
            replacement = toBeRemoved.left != null ? toBeRemoved.left : toBeRemoved.right;
        } else if (toBeRemoved.nChildren() > 1) {
            throw new IllegalArgumentException("Accepting only nodes with 0 or 1 child.");
        }
        node.right = replacement;
    }

}
