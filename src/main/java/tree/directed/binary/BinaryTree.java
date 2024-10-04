package tree.directed.binary;

import org.jetbrains.annotations.Nullable;

public class BinaryTree<T> {

    private final BinaryTreeNode<T> root;

    public BinaryTree(BinaryTreeNode<T> root) {
        this.root = root;
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(@Nullable BinaryTreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        final int heightLeft = 1 + heightRecursive(node.left);
        final int heightRight = 1 + heightRecursive(node.right);
        return Math.max(heightLeft, heightRight);
    }

    public int depth(BinaryTreeNode<T> node) {
        return depthRecursive(root, node);
    }

    private int depthRecursive(BinaryTreeNode<T> current, BinaryTreeNode<T> target) {
        if (current == null) {
            return -1;
        } else if (current.equals(target)) {
            return 0;
        }
        final int leftSearchResult = depthRecursive(current.left, target);
        if (leftSearchResult != -1) {
            return 1 + leftSearchResult;
        }
        final int rightSearchResult = depthRecursive(current.right, target);
        if (rightSearchResult != -1) {
            return 1 + rightSearchResult;
        }
        return -1;
    }
}
