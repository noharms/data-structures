package binarysearchtree;

import org.jetbrains.annotations.Nullable;

import java.util.*;

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
    Node<K, V> root;

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
        } else {
            return compareResult < 0 ? searchNode(searchKey, current.right) : searchNode(searchKey, current.left);
        }
    }

    List<Node<K, V>> computePathTo(K searchKey) {
        List<Node<K, V>> path = new ArrayList<>();
        path.add(root);
        computePath(searchKey, root, path);
        return path;
    }

    private void computePath(K searchKey, Node<K, V> current, List<Node<K, V>> path) {
        if (current == null) {
            return;
        }
        K currentKey = current.key();
        int compareResult = currentKey.compareTo(searchKey);
        if (compareResult != 0) {
            Node<K, V> next = compareResult < 0 ? current.right : current.left;
            path.add(next);
            computePath(searchKey, next, path);
        }
    }

    public void add(K key, V value) {
        Node<K, V> newNode = new Node<>(new Node.KeyValuePair<>(key, value));
        addNode(newNode);
    }

    void addNode(Node<K, V> newNode) {
        ++nElements;

        if (root == null) {
            root = newNode;
            return;
        }

        Node<K, V> current = root;
        while (true) {
            if (newNode.key().compareTo(current.key()) < 0) {
                if (current.left != null) {
                    current = current.left;
                } else {
                    current.left = newNode;
                    return;
                }
            } else if (newNode.key().compareTo(current.key()) > 0) {
                if (current.right != null) {
                    current = current.right;
                } else {
                    current.right = newNode;
                    return;
                }
            } else {
                --nElements;
                throw new IllegalArgumentException("Key %s already contained.".formatted(newNode.key()));
            }
        }
    }


    public void remove(K key) {
        replace(key);
    }

    // returns the node that replaces the removed one, or null, if none replaces it
    @Nullable Node<K, V> replace(K key) {
        if (isEmpty()) {
            return null;
        }
        Node<K, V> parentOfMatch = root.findParentNode(new Node<>(new Node.KeyValuePair<>(key, null)));
        if (parentOfMatch == null) {
            if (root.key().compareTo(key) != 0) {
                return null;
            } else {
                // root itself is the match
                if (root.nChildren() == 0) {
                    root = null;
                } else if (root.nChildren() == 1) {
                    root = root.left != null ? root.left : root.right;
                } else {
                    replaceMatchWith2Children(root);
                }
                --nElements;
                return root;
            }
        } else {
            boolean isMatchLeft = parentOfMatch.left != null && parentOfMatch.left.key().compareTo(key) == 0;
            Node<K, V> match = isMatchLeft ? parentOfMatch.left : parentOfMatch.right;
            Node<K, V> replacement = match;
            if (match.nChildren() < 2) {
                if (isMatchLeft) {
                    replacement = removeLeftChild(parentOfMatch);
                } else {
                    replacement = removeRightChild(parentOfMatch);
                }
            } else {
                replaceMatchWith2Children(match);
            }
            --nElements;
            return replacement;
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

    private @Nullable Node<K, V> removeLeftChild(Node<K, V> node) {
        Node<K, V> toBeRemoved = node.left;
        Node<K, V> replacement = null;
        if (toBeRemoved.nChildren() == 1) {
            replacement = toBeRemoved.left != null ? toBeRemoved.left : toBeRemoved.right;
        } else if (toBeRemoved.nChildren() > 1) {
            throw new IllegalArgumentException("Accepting only nodes with 0 or 1 child.");
        }
        node.left = replacement;
        return replacement;
    }

    private @Nullable Node<K, V> removeRightChild(Node<K, V> node) {
        Node<K, V> toBeRemoved = node.right;
        Node<K, V> replacement = null;
        if (toBeRemoved.nChildren() == 1) {
            replacement = toBeRemoved.left != null ? toBeRemoved.left : toBeRemoved.right;
        } else if (toBeRemoved.nChildren() > 1) {
            throw new IllegalArgumentException("Accepting only nodes with 0 or 1 child.");
        }
        node.right = replacement;
        return replacement;
    }

}
