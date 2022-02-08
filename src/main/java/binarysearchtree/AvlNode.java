package binarysearchtree;

public class AvlNode<K extends Comparable<K>, V> extends Node<K, V> {

    private int height;

    @Override
    public AvlNode<K, V> left() {
        return (AvlNode<K, V>) left;
    }

    @Override
    public AvlNode<K, V> right() {
        return (AvlNode<K, V>) right;
    }

    private AvlNode(K key, V value) {
        super(new KeyValuePair<>(key, value));
    }

    @Override
    int computeHeight() {
        return height;
    }

    private void resetHeight() {
        int heightLeft = left() != null ? left().height : 0;
        int heightRight = right() != null ? right().height : 0;
        height = 1 + Math.max(heightLeft, heightRight);
    }

    private int computeBalanceFactor() {
        return (right != null ? right.computeHeight() : 0) - (left != null ? left.computeHeight() : 0);
    }

    // TODO: this rotates the subtree starting at this node - however, since the root of the subtree may be
    //       another one after the rotation, we need the parent reference from above to be reset to the new root
    private void rebalance() {
        int balanceFactor = computeBalanceFactor();
        // Left-heavy?
        if (balanceFactor < -1) {
            if (left().computeBalanceFactor() <= 0) {    // Case 1
                // Rotate right
                rotateRight();
            } else {                                // Case 2
                // Rotate left-right
                left = left().rotateLeft();
                rotateRight();
            }
        }
        // Right-heavy?
        if (balanceFactor > 1) {
            if (right().computeBalanceFactor() >= 0) {    // Case 3
                // Rotate left
                rotateLeft();
            } else {                                 // Case 4
                // Rotate right-left
                right = right().rotateRight();
                rotateLeft();
            }
        }
    }

    private AvlNode<K, V> rotateRight() {
        AvlNode<K, V> pivot = left();
        AvlNode<K, V> rightChildPivot = pivot.right();

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
        AvlNode<K, V> pivot = right();
        AvlNode<K, V> leftChildPivot = pivot.left();

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
