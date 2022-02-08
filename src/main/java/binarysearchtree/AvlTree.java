package binarysearchtree;

import org.jetbrains.annotations.Nullable;

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
public class AvlTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    @Override
    public void add(K key, V value) {
        // TODO
    }

    @Override
    public Optional<V> remove(K key) {
        // TODO
        return null;
    }




}
