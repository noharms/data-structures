package list.nested;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NestedListTest {

    @Test
    void countNodes_empty_givesEmptyList() {
        NestedIntegerList recursiveList = NestedIntegerList.from("");

        assertEquals(0, recursiveList.countNodes());
    }

    @Test
    void countNodes_emptyList_givesListWithOneContainerNode() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[]");

        assertEquals(1, recursiveList.countNodes());
    }

    @Test
    void countNodes_flatList_numberOfElementsPlus1() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[1, 2, 3]");

        assertEquals(4, recursiveList.countNodes());
    }

    @Test
    void countValues_empty_gives0() {
        NestedIntegerList recursiveList = NestedIntegerList.from("");

        assertEquals(0, recursiveList.countValues());
    }

    @Test
    void countValues_emptyList_gives0() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[]");

        assertEquals(0, recursiveList.countValues());
    }

    @Test
    void countValues_flatList_numberOfElements() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[1, 2, 3]");

        assertEquals(3, recursiveList.countValues());
    }


    @Test
    void getValue_flatListOfLeaves_givesNumbers() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[0, 1, 2, 3, 4, 5]");

        for (int i = 0; i < 6; i++) {
            assertEquals(i, recursiveList.getValue(i));
        }
    }

    @Test
    void getValue_tooHighIndex_givesException() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[42, [1, 2]]");

        assertThrows(NoSuchElementException.class, () -> recursiveList.getValue(3));
    }

    @Test
    void getValue_emptySublistsDoNotCount_givesException() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[[], [], [1, 2]]");

        assertEquals(1, recursiveList.getValue(0));
    }

    @Test
    void valuesInOrder_emptyList_givesEmptyList() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[[], [], [[[]]]]");

        assertEquals(emptyList(), recursiveList.valuesInOrder());
    }

    @Test
    void valuesInOrder_flatList_givesValues() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[1, 2, 3, 4, 5]");

        assertEquals(List.of(1, 2, 3, 4, 5), recursiveList.valuesInOrder());
    }

    @Test
    void valuesInOrder_nestedList_givesValues() {
        NestedIntegerList recursiveList = NestedIntegerList.from("[1, 2, [11, [21, 22], 12], 3, [13], [], [14, 15]]");

        assertEquals(List.of(1, 2, 11, 21, 22, 12, 3, 13, 14, 15), recursiveList.valuesInOrder());
    }
}