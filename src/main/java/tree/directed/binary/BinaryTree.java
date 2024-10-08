package tree.directed.binary;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static tree.directed.binary.BinaryTreeTraversals.*;

public class BinaryTree<T> {

    private final BinaryTreeNode<T> root;

    public BinaryTree(BinaryTreeNode<T> root) {
        this.root = root;
    }

    public List<T> inOrder() {
        final List<T> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    public List<T> preOrder() {
        final List<T> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    public List<T> postOrder() {
        final List<T> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    public List<T> topToBottomLeftToRight() {
        final List<T> result = new ArrayList<>();
        if (root != null) {
            traverseFromTopFromLeft(root, result);
        }
        return result;
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(@Nullable BinaryTreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        final int heightLeft = heightRecursive(node.left);
        final int heightRight = heightRecursive(node.right);
        return 1 + Math.max(heightLeft, heightRight);
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
