package binarysearchtree;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public class AvlTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    @Override
    public void add(K key, V value) {
        super.addNode(new AvlNode<>(key, value));

        List<AvlNode<K, V>> path = computePathTo(key).stream()
                                                     .map(node -> (AvlNode<K, V>) node)
                                                     .collect(Collectors.toList());
        Collections.reverse(path); // now root is the last one in the path
        root = rebalance(path);
    }

    private static <U extends Comparable<U>, W> AvlNode<U, W> rebalance(List<AvlNode<U, W>> path) {
        for (int i = 0; i <= path.size() - 2; ++i) {
            AvlNode<U, W> parent = path.get(i + 1);
            AvlNode<U, W> current = path.get(i);
            boolean isLeftChild = parent.left == current;

            AvlNode<U, W> newSubtreeRoot = current.rebalance();
            if (isLeftChild) {
                parent.left = newSubtreeRoot;
            } else {
                parent.right = newSubtreeRoot;
            }
        }
        return path.get(path.size() - 1).rebalance();
    }

    @Override
    public void remove(K key) {
        AvlNode<K, V> replacement = (AvlNode<K, V>) replace(key);

        if (replacement != null) {
            List<AvlNode<K, V>> path = computePathTo(replacement.key()).stream()
                                                                       .map(node -> (AvlNode<K, V>) node)
                                                                       .collect(Collectors.toList());
            Collections.reverse(path); // now root is the last one in the path
            rebalance(path);
        }
    }

    private static <U extends Comparable<U>, W> int computeHeight(AvlNode<U, W> node) {
        return node == null ? -1 : node.height;
    }

    static <U extends Comparable<U>, W> void updateHeight(AvlNode<U, W> node) {
        int leftChildHeight = computeHeight(node.left());
        int rightChildHeight = computeHeight(node.right());
        node.height = 1 + Math.max(leftChildHeight, rightChildHeight);
    }

}
