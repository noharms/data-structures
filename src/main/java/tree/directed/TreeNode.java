package tree.directed;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TreeNode<T> {
    private final T value;
    private final List<TreeNode<T>> children = new ArrayList<>();

    public TreeNode(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    /**
     * Caveat: this gives a defensive copy of the list of children to avoid external manipulation of the list.
     * It is the main reason (as of now) that this class cannnot be a record.
     *
     * @return a copy of the list of children
     */
    public List<TreeNode<T>> children() {
        return new ArrayList<>(children);
    }

    /**
     * Caveat: adding children to a node is a technical task and therefore package private. Clients should use
     * {@link RootedTree#add(TreeNode, Object)}.
     */
    void add(TreeNode<T> child) {
        children.add(child);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (TreeNode) obj;
        return Objects.equals(this.value, that.value) &&
            Objects.equals(this.children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, children);
    }

    @Override
    public String toString() {
        return "TreeNode[" +
            "value=" + value + ", " +
            "children=" + children + ']';
    }

    public void addAll(List<TreeNode<T>> additionalchildren) {
        children.addAll(additionalchildren);
    }
}
