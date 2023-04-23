package list.nested;

import java.util.List;

public class ValueNode extends NestedListNode {

    private final int value;

    public ValueNode(int value) {
        this.value = value;
    }

    @Override
    public int countNodesTraversal() {
        NestedListNode next = getNext();
        int countNext = next != null ? next.countNodesTraversal() : 0;
        return 1 + countNext;
    }

    @Override
    public int countValuesTraversal() {
        NestedListNode next = getNext();
        int countNext = next != null ? next.countValuesTraversal() : 0;
        return 1 + countNext;
    }

    @Override
    public void valuesInOrder(List<Integer> result) {
        result.add(value);
        NestedListNode next = getNext();
        if (next != null) {
            next.valuesInOrder(result);
        }
    }
}
