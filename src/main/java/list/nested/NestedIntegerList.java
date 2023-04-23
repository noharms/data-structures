package list.nested;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A list of nodes which can have one of two possible types:
 * either a value node (i.e. they have an integer value)
 * or sub-list nodes/elements (i.e. they do not have a value but a reference to the head of a subList;
 * note that the head of the sub-list can be a value node or a sub-list node).
 * <br>
 * All nodes have a reference to the next node, which is null if they are the last node in the sub-list.
 * <br>
 * If we visualise the value nodes by x and the container nodes by o, we can enumerate the nodes of a list like
 * <p>
 * [1, 2, [11, [21, 22], 12], 3, [13], [], [14, 15]]
 * <p>
 * in the following way
 * <p>
 * x:1   ->   x:2   ->    o   ->      x:3    ->      o   ->   o    ->   o
 * |                          |        |         |
 * V                          V        V         v
 * x:11 -> o -> x:12          x:13     null       x:14 -> x:15
 * |
 * v
 * x:21 -> x:22
 * <p>
 * One could say that we have:
 * <p>
 * 7 nodes on the first level
 * 6 nodes on the second level
 * 2 nodes on the third level
 * <p>
 * However, if we would only count actual value elements, the numbers would be
 * <p>
 * 3 value elements on the first level
 * 5 value elements on the second level
 * 2 value elements on the third level
 */
public class NestedIntegerList {

    public static final char START_OF_LIST = '[';
    private static final char END_OF_LIST = ']';
    private static final char DELIMITER = ',';

    @Nullable
    private final NestedListNode head;

    private NestedIntegerList(@Nullable NestedListNode head) {
        this.head = head;
    }

    public static NestedIntegerList emptyList() {
        return new NestedIntegerList(null);
    }

    public static NestedIntegerList from(String listAsString) {
        if (listAsString.isBlank()) {
            return emptyList();
        }
        validate(listAsString);
        listAsString = prepareForParsing(listAsString);

        // holds the current last node on each level; with the topmost node being the current, i.e. deepest level
        LinkedList<NestedListNode> predecessorStack = new LinkedList<>();
        ContainerNode topLevelContainerNode = new ContainerNode(); // this is the overall topLevelContainerNode
        predecessorStack.addLast(topLevelContainerNode);

        NestedListNode currentPredecessor = null;
        int i = 0;
        while (i < listAsString.length()) {
            char c = listAsString.charAt(i);
            if (c == START_OF_LIST) {
                // every new list means we need a new dummy topLevelContainerNode node
                NestedListNode newNode = new ContainerNode();
                integrateWithPredecessorOrParent(predecessorStack, currentPredecessor, newNode);
                currentPredecessor = newNode;
                predecessorStack.addLast(newNode);
            } else if (Character.isDigit(c)) {
                String nextValueString = getNextNumber(listAsString, i);
                int value = Integer.parseInt(nextValueString);
                NestedListNode newNode = new ValueNode(value);
                integrateWithPredecessorOrParent(predecessorStack, currentPredecessor, newNode);
                currentPredecessor = newNode;
                i += nextValueString.length();
            } else if (c == END_OF_LIST) {
                currentPredecessor = predecessorStack.removeLast();
            } else if (c == DELIMITER) {
                i++;
                continue;
            } else {
                throw new IllegalStateException("Illegal character in list representation: " + c);
            }
            i++;
        }

        return new NestedIntegerList(topLevelContainerNode);
    }

    // TODO: check further syntax
    private static void validate(String listAsString) {
        if (listAsString.charAt(0) != START_OF_LIST) {
            throw new IllegalStateException("Wrong syntax. String representation must start with " + START_OF_LIST);
        } else if (listAsString.charAt(listAsString.length() - 1) != END_OF_LIST) {
            throw new IllegalStateException("Wrong syntax. String representation must end with " + END_OF_LIST);
        }
    }

    private static String prepareForParsing(String listAsString) {
        listAsString = listAsString.replaceAll("\\s+", "");
        // remove top-level brackets in order to create the topLevelContainerNode container node separately (avoids
        // case distinctions)
        listAsString = listAsString.substring(1, listAsString.length() - 1);
        return listAsString;
    }

    private static String getNextNumber(String listAsString, int firstChar) {
        int j = firstChar;
        do {
            ++j;
        } while (j < listAsString.length() && Character.isDigit(listAsString.charAt(j)));
        return listAsString.substring(firstChar, j);
    }

    private static void integrateWithPredecessorOrParent(
        LinkedList<NestedListNode> predecessorStack,
        @Nullable NestedListNode currentPredecessor,
        NestedListNode newNode
    ) {
        if (currentPredecessor != null) {
            currentPredecessor.setNext(newNode);
        } else {
            // if there is no predecessor, this new node is the first element of a new sublist and the last
            // node in the previous level must have been a container node
            ContainerNode parentNode = (ContainerNode) predecessorStack.getLast();
            parentNode.setSublistHead(newNode);
        }
    }

    public int countNodes() {
        return head == null ? 0 : head.countNodesTraversal();
    }

    public int countValues() {
        return head == null ? 0 : head.countValuesTraversal();
    }

    /**
     * In order means to print the values of a sublist before proceeding with the next value. Another alternative would
     * be to traverse the recursive list by levels.
     */
    public List<Integer> valuesInOrder() {
        List<Integer> result = new ArrayList<>();
        if (head != null) {
            head.valuesInOrder(result);
        }
        return result;
    }

    /**
     * Zero based index of a value for an in-order traversal of the recursive list.
     * E.g. for [1, [[21, 22], 11] the method would return
     * getValue(0) = 1
     * getValue(1) = 21
     * getValue(2) = 22
     * getValue(3) = 11
     * and throw a {@link NoSuchElementException} otherwise.
     **/
    public int getValue(int atInorderIndex) {
        if (atInorderIndex > countValues() - 1) {
            throw new NoSuchElementException();
        }
        return valuesInOrder().get(atInorderIndex);
    }
}
