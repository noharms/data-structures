package binarysearchtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Traversal {

    public static <K extends Comparable<K>> List<K> inOrder(BinarySearchTree<K, ?> tree) {
        List<K> keysInOrder = new ArrayList<>();
        Node<K, ?> root = tree.root;
        inOrderRecursive(root, keysInOrder);
        return keysInOrder;
    }

    private static <K extends Comparable<K>> void inOrderRecursive(Node<K, ?> node, List<K> keysInOrder) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left(), keysInOrder);
        keysInOrder.add(node.key());
        inOrderRecursive(node.right(), keysInOrder);
    }

    /**
     * A traversal without recursion (useful if tree is so large that stack-overflow could appear).
     */
    public static <K extends Comparable<K>> List<K> inOrderIterative(BinarySearchTree<K, ?> tree) {
        if (tree.isEmpty()) {
            return Collections.emptyList();
        }

        LinkedList<Node<K, ?>> stack = new LinkedList<>();
        List<K> result = new ArrayList<>();
        Node<K, ?> current = tree.root;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.addFirst(current);
                current = current.left();
            } else {
                current = stack.removeFirst();
                result.add(current.key());

                current = current.right();
            }
        }
        return result;
    }
}
