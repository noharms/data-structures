/**
 * A node (with left and right child nodes) based heap data structure.
 * <br>
 *
 * <ul>
 *     <li>T peek() in O(1)</li>
 *     <li>T pop() in O(log n)</li>
 *     <li>void add(T value) in O(log n)</li>
 * </ul>
 */
public class HeapNodeBased<T extends Comparable<T>> {

    // in a complete binary tree we can describe the slots by their level and the offset from the leftmost slot in level
    private static record LevelIndices(int level, int offset) {

        private static LevelIndices from(int linearSlotIndex) {
            int level = (int) (Math.log(linearSlotIndex + 1) / Math.log(2));
            int firstSlotInLevel = (int) (Math.pow(2, level) - 1);
            int offSetToFirstSlot = linearSlotIndex - firstSlotInLevel;
            return new LevelIndices(level, offSetToFirstSlot);
        }

        private int toLinearSlotIndex() {
            return (int) Math.pow(2, level) - 1 + offset;
        }
    }

    private static class Node<U extends Comparable<U>> implements Comparable<Node<U>> {
        private U value;
        private Node<U> top;
        private Node<U> left;
        private Node<U> right;

        public Node(U value) {
            this.value = value;
        }

        @Override
        public int compareTo(Node<U> o) {
            return this.value.compareTo(o.value);
        }

        public boolean hasLargerChildren() {
            return (left != null && left.compareTo(this) > 0) || (right != null && right.compareTo(this) > 0);
        }

        /**
         * Call {@link Node#hasLargerChildren()} before to make sure it exists, otherwise throws an error.
         * If left.compareTo(right) == 0, right is returned.
         */
        public Node<U> getLargestChild() {
            if (!hasLargerChildren()) {
                throw new IllegalStateException("No larger children exist.");
            }
            if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            } else {
                return left.compareTo(right) > 0 ? left : right;
            }
        }
    }

    static class HeapUnderflowException extends RuntimeException {
        public HeapUnderflowException(String s) {
            super(s);
        }
    }

    private Node<T> root;
    private int nElements;

    public int size() {
        return nElements;
    }

    private boolean isEmpty() {
        return nElements == 0;
    }

    public T peek() {
        if (isEmpty()) {
            throw new HeapUnderflowException("Heap is empty - cannot peek.");
        }
        return root.value;
    }

    public T pop() {
        if (isEmpty()) {
            throw new HeapUnderflowException("Heap is empty - cannot pop.");
        } else if (size() == 1) {
            T value = root.value;
            root = null;
            nElements = 0;
            return value;
        }

        T value = root.value;

        // remove last node and insert it as new root
        Node<T> removed = removeBottomRightNode();
        root.value = removed.value;
        --nElements;

        // reinstall heap order (note this involves upwards swaps, that reset root if necessary)
        bubbleDown(root);

        return value;
    }

    private void bubbleDown(Node<T> node) {
        while (node.hasLargerChildren()) {
            Node<T> largestChild = node.getLargestChild();
            swapUpwards(largestChild);
        }
    }

    private Node<T> removeBottomRightNode() {
        int lastOccupiedIndex = nElements - 1;
        Node<T> lastNode = findNodeAtIndex(lastOccupiedIndex);
        Node<T> parent = lastNode.top;
        if (lastNode == parent.left) {
            parent.left = null;
        } else {
            parent.right = null;
        }
        return lastNode;
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        addNodeAtBottomRight(newNode);
        ++nElements;
        bubbleUp(newNode);
    }

    private void bubbleUp(Node<T> node) {
        while (node.top != null && node.top.compareTo(node) < 0) {
            swapUpwards(node);
        }
    }

    private void swapUpwards(Node<T> node) {
        if (node != null && node.top != null) {
            T valueTop = node.top.value;
            node.top.value = node.value;
            node.value = valueTop;
        }
    }

    private void addNodeAtBottomRight(Node<T> newNode) {
        if (isEmpty()) {
            root = newNode;
        } else {
            int bottomRightSlotIndex = nElements;
            int parentIndex = findParentIndex(bottomRightSlotIndex);
            Node<T> parent = findNodeAtIndex(parentIndex);
            newNode.top = parent;
            if (parent.left == null) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
    }

    // since a heap is a complete binary tree, we can index every slot by going from left to right through the levels
    //                       slot indices
    // level 0:                  0
    // level 1:             1        2
    // level 2:         3     4   5     6
    // ...
    // Note how the first index in each level is 2^level - 1
    // --> for all indices i in a level:  2^level - 1 <= index < 2^(level+1) - 1
    //                              ==>   level <= log2(index + 1) < level + 1
    private static int findParentIndex(int slotIndex) {
        if (slotIndex == 0) {
            throw new IllegalArgumentException("For index 0 there is no parent.");
        }
        LevelIndices levelIndices = LevelIndices.from(slotIndex);
        int levelParent = levelIndices.level - 1;
        int firstSlotInLevelParent = (int) (Math.pow(2, levelParent) - 1);
        int offSetToFirstSlotParent = firstSlotInLevelParent / 2;
        LevelIndices levelIndicesParent = new LevelIndices(levelParent, offSetToFirstSlotParent);
        return levelIndicesParent.toLinearSlotIndex();
    }

    // the offsets in each level define the path to a slot: looking at their big-endian binary form, we can interpret
    // each 0 as a going to left child, and a 1 as going to the right child
    // level 0:                  0                       0
    // level 1:             0        1               0       1
    // level 2:         0     1   2     3         00  01  10   11
    // ...
    private Node<T> findNodeAtIndex(int linearSlotIndex) {
        LevelIndices levelIndices = LevelIndices.from(linearSlotIndex);
        String bigEndianBinary = convertToBigEndianBinary(levelIndices.offset, levelIndices.level);
        Node<T> node = root;
        for (char c : bigEndianBinary.toCharArray()) {
            node = c == '0' ? node.left : node.right;
        }
        return node;
    }

    private static String convertToBigEndianBinary(int n, int digitsInResult) {
        StringBuilder sb = new StringBuilder();
        while (digitsInResult-- > 0) {
            int modulo = n % 2;
            n = n / 2;
            sb.append(modulo == 0 ? "0" : "1");
        }
        return sb.reverse().toString();
    }

}
