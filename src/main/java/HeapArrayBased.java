import java.util.ArrayList;

public class HeapArrayBased<T extends Comparable<T>> {

    static class HeapUnderflowException extends RuntimeException {
        private HeapUnderflowException(String msg) {
            super(msg);
        }
    }

    private final ArrayList<T> memory;

    public HeapArrayBased() {
        this.memory = new ArrayList<>();
    }

    public int size() {
        return memory.size();
    }

    public boolean isEmpty() {
        return memory.isEmpty();
    }

    public T peek() {
        if (isEmpty()) {
            throw new HeapUnderflowException("Cannot peek on empty heap.");
        }
        return memory.get(0);
    }

    public T pop() {
        if (isEmpty()) {
            throw new HeapUnderflowException("Cannot pop on empty heap.");
        }
        T popValue = memory.get(0);
        T temporarilyRemovedValue = memory.remove(size() - 1);
        if (size() > 0) {
            memory.set(0, temporarilyRemovedValue);
            bubbleDown();
        }
        return popValue;
    }

    private void bubbleDown() {
        int index = 0;
        while (hasGreaterChild(index)) {
            int leftChildIndex = computeLeftChildIndex(index);
            int rightChildIndex = computeRightChildIndex(index);
            int indexLargestChild = leftChildIndex;
            if (rightChildIndex < size() && memory.get(leftChildIndex).compareTo(memory.get(rightChildIndex)) < 0) {
                indexLargestChild = rightChildIndex;
            }
            swapValuesAt(index, indexLargestChild);
            index = indexLargestChild;
        }
    }

    private void swapValuesAt(int index, int indexLargestChild) {
        T temp = memory.get(index);
        memory.set(index, memory.get(indexLargestChild));
        memory.set(indexLargestChild, temp);
    }

    private boolean hasGreaterChild(int index) {
        int leftChildIndex = computeLeftChildIndex(index);
        int rightChildIndex = computeRightChildIndex(index);
        T value = memory.get(index);
        return (rightChildIndex < size() && memory.get(rightChildIndex).compareTo(value) > 0)
            || (leftChildIndex < size() && memory.get(leftChildIndex).compareTo(value) > 0);
    }

    private int computeRightChildIndex(int index) {
        return (index + 1) * 2;
    }

    private int computeLeftChildIndex(int index) {
        return computeRightChildIndex(index) - 1;
    }

    public void add(T value) {
        memory.add(value);
        bubbleUp();
    }

    private void bubbleUp() {
        int index = size() - 1;
        while (hasLesserParent(index)) {
            int parentIndex = computeParentIndex(index);
            swapValuesAt(index, parentIndex);
            index = parentIndex;
        }
    }

    private boolean hasLesserParent(int index) {
        if (index == 0) {
            return false;
        }
        int parentIndex = computeParentIndex(index);
        return memory.get(parentIndex).compareTo(memory.get(index)) < 0;
    }

    private int computeParentIndex(int index) {
        return (index - 1) / 2;
    }

}
