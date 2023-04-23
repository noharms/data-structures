package list.nested;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class NestedListNode {

    @Nullable
    private NestedListNode next;

    @Nullable
    public NestedListNode getNext() {
        return next;
    }

    public void setNext(@Nullable NestedListNode next) {
        this.next = next;
    }

    public abstract int countNodesTraversal();

    public abstract int countValuesTraversal();

    public abstract void valuesInOrder(List<Integer> result);

}
