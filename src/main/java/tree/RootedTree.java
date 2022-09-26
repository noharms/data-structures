package tree;

import com.google.common.collect.Sets;
import queue.QueueDLLBased;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;
import static tree.TreeNodeWithLevel.decorateWithLevel;

/**
 * A rooted tree is a graph with a root node to which all nodes are connected and which does not have cycles.
 * <br>
 * In a rooted tree we naturally obtain levels of nodes, which are all the same number of edges away from the root.
 * Besides construction APIs, the main functionality that we expect from such a tree is to tell us which nodes
 * are on a given level i.
 */
public class RootedTree<T> {

    private static final int LEVEL_NOT_FOUND = -1;

    private final TreeNode<T> root;

    public RootedTree(TreeNode<T> root) {
        this.root = root;
    }

    public void add(TreeNode<T> parent, T newValue) {
        throwIfNotFound(parent);
        parent.add(new TreeNode<>(newValue));
    }

    private void throwIfNotFound(TreeNode<T> node) {
        if (!contains(node)) {
            throw new NoSuchElementException("Node with value %s was not found in the tree.".formatted(node.value()));
        }
    }

    public boolean contains(TreeNode<T> node) {
        return contains(root, node);
    }

    private static <V> boolean contains(TreeNode<V> root, TreeNode<V> node) {
        return root.equals(node) || root.children().stream().anyMatch(child -> contains(child, node));
    }

    public int findLevel(T value) {
        int level = findLevelRecursion(value, root, 0);
        if (level != LEVEL_NOT_FOUND) {
            return level;
        } else {
            throw new NoSuchElementException("The value %s was not found in the tree.".formatted(value));
        }
    }

    /* depth first search for the target value; the recursion level keeps track of the tree level */
    private static <V> int findLevelRecursion(V targetValue, TreeNode<V> node, int currentLevel) {
        if (node.value().equals(targetValue)) {
            return currentLevel;
        } else {
            for (TreeNode<V> child : node.children()) {
                int result = findLevelRecursion(targetValue, child, currentLevel + 1);
                if (result != LEVEL_NOT_FOUND) {
                    return result;
                }
            }
        }
        return LEVEL_NOT_FOUND;
    }

    public Map<Integer, List<TreeNode<T>>> computeLevelToNodesWithQueue() {
        Map<Integer, List<TreeNode<T>>> levelToNodes = new HashMap<>();

        QueueDLLBased<TreeNodeWithLevel<T>> queue = new QueueDLLBased<>();
        queue.enqueue(new TreeNodeWithLevel<>(root, 0));

        while (!queue.isEmpty()) {
            TreeNodeWithLevel<T> currentNode = queue.dequeue();
            int currentLevel = currentNode.getLevel();
            levelToNodes.computeIfAbsent(currentLevel, level -> new ArrayList<>())
                        .add(currentNode.getNode());
            List<TreeNodeWithLevel<T>> childrenWithLevel = decorateWithLevel(currentNode.getNode().children(),
                                                                             currentLevel + 1);
            queue.enqueueAll(childrenWithLevel);
        }

        return levelToNodes;
    }

    public Map<Integer, List<TreeNode<T>>> computeLevelToNodesWithTraversal() {
        Map<Integer, List<TreeNode<T>>> levelToNodes = new HashMap<>();
        traverseAndFill(levelToNodes, root, 0);
        return levelToNodes;
    }

    private void traverseAndFill(Map<Integer, List<TreeNode<T>>> levelToNodes,
                                 TreeNode<T> currentNode,
                                 int currentLevel
    ) {
        levelToNodes.computeIfAbsent(currentLevel, level -> new ArrayList<>()).add(currentNode);
        for (var child : currentNode.children()) {
            traverseAndFill(levelToNodes, child, currentLevel + 1);
        }
    }

    /**
     * Computes the nodes on one given level and can thus be preferable over
     * {@link RootedTree#computeLevelToNodesWithQueue()}
     * in terms of memory costs.
     */
    public List<TreeNode<T>> findAllNodesOnLevel(int level) {
        throwIfNegative(level);
        List<TreeNode<T>> nodes = new ArrayList<>();

        QueueDLLBased<TreeNodeWithLevel<T>> queue = new QueueDLLBased<>();
        queue.enqueue(new TreeNodeWithLevel<>(root, 0));

        while (!queue.isEmpty()) {
            TreeNodeWithLevel<T> currentNode = queue.dequeue();
            int currentLevel = currentNode.getLevel();
            if (currentLevel > level) {
                break;
            } else if (currentLevel == level) {
                nodes.add(currentNode.getNode());
            }
            List<TreeNodeWithLevel<T>> childrenWithLevel = decorateWithLevel(currentNode.getNode().children(),
                                                                             currentLevel + 1);
            queue.enqueueAll(childrenWithLevel);
        }

        return nodes;
    }

    private void throwIfNegative(int level) {
        if (level < 0) {
            String msg = "The number of nodes in the tree is undefined for level %d".formatted(level);
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Takes a map from the designated values to their child values and constructs nodes from it. The created root
     * node is referenced by the returned tree data structure.
     * <br>
     * Note: we only require to specify those values as keys of the input map that actually have children. The leaf
     * nodes can but are not required to appear as keys with an empty-children-list.
     */
    public static <V> RootedTree<V> from(Map<V, List<V>> parentToChildValues, V root) {
        Map<V, TreeNode<V>> valueToNode = createNodes(parentToChildValues);
        for (var entry : parentToChildValues.entrySet()) {
            V value = entry.getKey();
            List<V> childValues = entry.getValue() != null ? entry.getValue() : emptyList();

            TreeNode<V> currentNode = valueToNode.get(value);
            List<TreeNode<V>> childNodes = childValues.stream().map(valueToNode::get).toList();

            currentNode.addAll(childNodes);
        }
        TreeNode<V> rootNode = valueToNode.get(root);
        return new RootedTree<>(rootNode);
    }

    private static <V> Map<V, TreeNode<V>> createNodes(Map<V, List<V>> valueToChildValues) {
        Set<V> allParentValues = valueToChildValues.keySet();
        Set<V> allChildValues = valueToChildValues.values().stream().flatMap(List::stream).collect(Collectors.toSet());
        Set<V> allValues = Sets.union(allParentValues, allChildValues);
        return allValues.stream().collect(toMap(value -> value, TreeNode<V>::new));
    }

}
