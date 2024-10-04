package tree.directed.binary;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BinaryTreeNode<T> {

    final T value;
    @Nullable BinaryTreeNode<T> left;
    @Nullable BinaryTreeNode<T> right;

    public BinaryTreeNode(T value) {
        this(value, null, null);
    }

    public BinaryTreeNode(T value, @Nullable BinaryTreeNode<T> left, @Nullable BinaryTreeNode<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryTreeNode<?> that = (BinaryTreeNode<?>) o;
        return Objects.equals(value, that.value)
            && Objects.equals(left, that.left)
            && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }
}
