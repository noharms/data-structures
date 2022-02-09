package binarysearchtree;

import static binarysearchtree.AvlTree.updateHeight;

class AvlNode<K extends Comparable<K>, V> extends Node<K, V> {

    int height;

    @Override
    public AvlNode<K, V> left() {
        return (AvlNode<K, V>) left;
    }

    @Override
    public AvlNode<K, V> right() {
        return (AvlNode<K, V>) right;
    }

    AvlNode(K key, V value) {
        super(new KeyValuePair<>(key, value));
    }

    private int computeBalanceFactor() {
        return (right != null ? computeHeight(right()) : 0) - (left != null ? computeHeight(left()) : 0);
    }

    // TODO: this rotates the subtree starting at this node - however, since the root of the subtree may be
    //       another one after the rotation, we need the parent reference from above to be reset to the new root
    AvlNode<K, V> rebalance() {
        int balanceFactor = computeBalanceFactor();
        AvlNode<K, V> newSubtreeRoot = this;
        if (balanceFactor < -1) {
            if (left().computeBalanceFactor() > 0) {
                left = left().rotateLeft();
            }
            newSubtreeRoot = rotateRight();
        } else if (balanceFactor > 1) {
            if (right().computeBalanceFactor() < 0) {
                right = right().rotateRight();
            }
            newSubtreeRoot = rotateLeft();
        }
        return newSubtreeRoot;
    }

    private AvlNode<K, V> rotateRight() {
        AvlNode<K, V> oldRootSubtree = this;
        AvlNode<K, V> pivot = left();
        AvlNode<K, V> rightChildPivot = pivot.right();

        // make pivot parent of this node
        pivot.right = oldRootSubtree;

        // relocate old child of pivot
        oldRootSubtree.left = rightChildPivot;

        // recompute heights (caveat: order is important, because pivot is above this node now)
        updateHeight(oldRootSubtree);
        updateHeight(pivot);

        return pivot;
    }

    private AvlNode<K, V> rotateLeft() {
        AvlNode<K, V> oldRootSubtree = this;
        AvlNode<K, V> pivot = right();
        AvlNode<K, V> leftChildPivot = pivot.left();

        // make pivot parent of this node
        pivot.left = oldRootSubtree;

        // relocate old child of pivot
        oldRootSubtree.right = leftChildPivot;

        // recompute heights (caveat: order is important, because pivot is above this node now)
        updateHeight(oldRootSubtree);
        updateHeight(pivot);

        return pivot;
    }

}
