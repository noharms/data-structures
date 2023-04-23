package list.nested;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ContainerNode extends NestedListNode {

    @Nullable
    private NestedListNode sublistHead;

    public void setSublistHead(@Nullable NestedListNode sublistHead) {
        this.sublistHead = sublistHead;
    }

    @Override
    public int countNodesTraversal() {
        NestedListNode next = getNext();
        int subListCount = sublistHead != null ? sublistHead.countNodesTraversal() : 0;
        int nextCount = next != null ? next.countNodesTraversal() : 0;
        return 1 + subListCount + nextCount;
    }

    @Override
    public int countValuesTraversal() {
        NestedListNode next = getNext();
        int subListCount = sublistHead != null ? sublistHead.countValuesTraversal() : 0;
        int nextCount = next != null ? next.countValuesTraversal() : 0;
        return subListCount + nextCount;
    }

    @Override
    public void valuesInOrder(List<Integer> result) {
        if (sublistHead != null) {
            sublistHead.valuesInOrder(result);
        }
        if (getNext() != null) {
            getNext().valuesInOrder(result);
        }
    }

}
