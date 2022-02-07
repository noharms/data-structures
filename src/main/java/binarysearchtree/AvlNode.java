package binarysearchtree;

public class AvlNode<K extends Comparable<K>, V> extends Node<K, V> {

    private int height;

    private AvlNode(K key, V value) {
        super(new KeyValuePair<>(key, value));
    }

    @Override
    int computeHeight() {
        return height;
    }

    private void resetHeight() {
        int heightLeft = left != null ? ((AvlNode<K, V>) left).height : 0;
        int heightRight = right != null ? ((AvlNode<K, V>) right).height : 0;
        height = 1 + Math.max(heightLeft, heightRight);
    }

    private int computeBalanceFactor() {
        return (right != null ? right.computeHeight() : 0) - (left != null ? left.computeHeight() : 0);
    }

    private AvlNode<K, V> rotateRight() {
        AvlNode<K, V> pivot = (AvlNode<K, V>) this.left;
        AvlNode<K, V> rightChildPivot = (AvlNode<K, V>) pivot.right;

        // make pivot parent of this node
        pivot.right = this;

        // relocate old child of pivot
        this.left = rightChildPivot;

        // recompute heights (caveat: order is important, because pivot is above this node now)
        resetHeight();
        pivot.resetHeight();

        return pivot;
    }

    private AvlNode<K, V> rotateLeft() {
        AvlNode<K, V> pivot = (AvlNode<K, V>) this.right;
        AvlNode<K, V> leftChildPivot = (AvlNode<K, V>) pivot.left;

        // make pivot parent of this node
        pivot.right = this;

        // relocate old child of pivot
        this.right = leftChildPivot;

        // recompute heights (caveat: order is important, because pivot is above this node now)
        resetHeight();
        pivot.resetHeight();

        return pivot;
    }

}
