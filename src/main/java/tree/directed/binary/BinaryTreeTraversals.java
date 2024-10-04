package tree.directed.binary;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BinaryTreeTraversals {

    static <V> void inOrderRecursive(@Nullable BinaryTreeNode<V> node, List<V> result) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left, result);
        result.add(node.value);
        inOrderRecursive(node.right, result);
    }

    static <V> void preOrderRecursive(@Nullable BinaryTreeNode<V> node, List<V> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        preOrderRecursive(node.left, result);
        preOrderRecursive(node.right, result);
    }

    static <V> void postOrderRecursive(@Nullable BinaryTreeNode<V> node, List<V> result) {
        if (node == null) {
            return;
        }
        postOrderRecursive(node.left, result);
        postOrderRecursive(node.right, result);
        result.add(node.value);
    }
}
