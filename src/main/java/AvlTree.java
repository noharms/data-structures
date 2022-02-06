import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A Maximovich-Velsky-Landis binary search tree that has a guaranteed height of O(log(n)). Duplicate keys
 * are not allowed.
 *
 * <ul>
 *     <li>V search(K key) in O(log n)</li>
 *     <li>void add(K key, V value) in O(log n)</li>
 *     <li>V remove (K key) in O(log n)</li>
 * </ul>
 *
 * @param <K> generic type parameter for the type of the keys
 * @param <V> generic type parameter for the type of the values
 */
public class AvlTree<K extends Comparable<K>, V> {

    private static record KeyValuePair<U extends Comparable<U>, W>(U key, W value) {
    }

    private static class Node<U extends Comparable<U>, W> {
        private KeyValuePair<U, W> keyValuePair;
        private Node<U, W> left;
        private Node<U, W> right;
        private int height; // is 0, if both children are null

        private Node(KeyValuePair<U, W> keyValuePair) {
            this.keyValuePair = keyValuePair;
        }

        private U key() {
            return keyValuePair.key;
        }

        private void recomputeHeight() {
            height = Math.max(getHeight(left), getHeight(right)) + 1;
        }

        private int computeBalanceFactor() {
            return getHeight(right) - getHeight(left);
        }

        private Node<U, W> rotateRight() {
            Node<U, W> pivot = this.left;
            Node<U, W> rightChildPivot = pivot.right;

            // make pivot parent of this node
            pivot.right = this;

            // relocate old child of pivot
            this.left = rightChildPivot;

            // recompute heights (caveat: order is important, because pivot is above this node now)
            recomputeHeight();
            pivot.recomputeHeight();

            return pivot;
        }

        private Node<U, W> rotateLeft() {
            Node<U, W> pivot = this.right;
            Node<U, W> leftChildPivot = pivot.left;

            // make pivot parent of this node
            pivot.right = this;

            // relocate old child of pivot
            this.right = leftChildPivot;

            // recompute heights (caveat: order is important, because pivot is above this node now)
            recomputeHeight();
            pivot.recomputeHeight();

            return pivot;
        }

        private @Nullable Node<U, W> findInOrderSuccessor() {
            return findMinimum(right);
        }

        public int nChildren() {
            if (left == null && right == null) {
                return 0;
            } else if (left != null && right != null) {
                return 2;
            } else {
                return 1;
            }

        }

    }

    private int nElements;
    private Node<K, V> root;

    public int size() {
        return nElements;
    }

    public boolean isEmpty() {
        return nElements == 0;
    }

    private static <U extends Comparable<U>, W> Node<U, W> findMinimum(Node<U, W> node) {
        if (node == null) {
            return null;
        }
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

    private static <U extends Comparable<U>, W> int getHeight(Node<U, W> node) {
        return node != null ? node.height : -1;
    }

    public Optional<V> search(K searchKey) {
        Node<K, V> result = searchNode(searchKey, root);
        return result == null ? Optional.empty() : Optional.of(result.keyValuePair.value);
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
        Node<K, V> parentForInsert = searchParentForInsert(key, root);
        Node<K, V> newNode = new Node<>(new KeyValuePair<>(key, value));
        if (newNode.key().compareTo(parentForInsert.key()) < 0) {
            parentForInsert.left = newNode;
        } else {
            parentForInsert.right = newNode;
        }
        ++nElements;
    }

    private Node<K, V> searchParentForInsert(K searchKey, Node<K, V> current) {
        K currentKey = current.key();
        int compareResult = currentKey.compareTo(searchKey);
        if (compareResult == 0) {
            throw new IllegalArgumentException("Duplicate keys are not allowed. Remove key " + searchKey + "first.");
        } else if (compareResult < 0) {
            return current.right == null ? current : searchNode(searchKey, current.right);
        } else {
            return current.left == null ? current : searchNode(searchKey, current.left);
        }
    }

    public Optional<V> remove(K key) {
        Node<K, V> parentOfMatch = searchParentOfMatch(key, root);
        if (parentOfMatch == null) {
            if (root.key().compareTo(key) != 0) {
                return Optional.empty();
            } else {
                // TODO remove root
                return root;
            }
        }
        boolean isMatchLeft = parentOfMatch.left != null && parentOfMatch.left.key().compareTo(key) == 0;
        Node<K, V> match = isMatchLeft ? removeLeftChild(parentOfMatch) : removeRightChild(parentOfMatch);
        if (match.nChildren() < 2) {
            if (isMatchLeft) {
                removeLeftChild(parentOfMatch);
            } else {
                removeRightChild(parentOfMatch);
            }
        } else {
            removeChildWith2GrandChildren(parentOfMatch);
        }
        --nElements;
        return Optional.of(match.keyValuePair.value);
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

    // returns null if current is the match, or if no match in current's subtree
    private Node<K, V> searchParentOfMatch(K searchKey, Node<K, V> current) {
        K currentKey = current.key();
        int compareResult = currentKey.compareTo(searchKey);
        if (compareResult == 0) {
            return null; // this can only happen if root is the match
        } else if (compareResult < 0) {
            if (current.right == null) {
                return null;
            } else {
                boolean rightChildIsMatch = current.right.key().compareTo(searchKey) == 0;
                return rightChildIsMatch ? current : searchParentOfMatch(searchKey, current.right);
            }
        } else {
            if (current.left == null) {
                return null;
            } else {
                boolean leftChildIsMatch = current.left.key().compareTo(searchKey) == 0;
                return leftChildIsMatch ? current : searchParentOfMatch(searchKey, current.left);
            }
        }
    }


}
