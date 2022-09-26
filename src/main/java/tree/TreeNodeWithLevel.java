package tree;

import java.util.List;
import java.util.Objects;

/**
 * A package private class intended for internal use only: when trying to find the nodes of a level in a rooted tree,
 * we create intermediate objects of this type to keep track of the levels of a node.
 * <br>
 * The level 0 is the root. The level 1 are the children of the root. And so on.
 */
class TreeNodeWithLevel<T> {

    private final TreeNode<T> node;
    private final int level;

    TreeNodeWithLevel(TreeNode<T> node, int level) {
        this.node = node;
        this.level = level;
    }

    public TreeNode<T> getNode() {
        return node;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TreeNodeWithLevel<?> that = (TreeNodeWithLevel<?>) o;
        return level == that.level && Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, level);
    }

    public static <V> List<TreeNodeWithLevel<V>> decorateWithLevel(List<TreeNode<V>> nodes, int level) {
        return nodes.stream().map(child -> new TreeNodeWithLevel<>(child, level)).toList();
    }
}
