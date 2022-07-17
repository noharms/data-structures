import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HashMapArrayBasedTest {

    @Test
    public void emptyMap_get_returnsEmptyOptional() {
        assertEquals(Optional.empty(), new HashMapArrayBased<>().get(1));
        assertEquals(Optional.empty(), new HashMapArrayBased<>().get(false));
        assertEquals(Optional.empty(), new HashMapArrayBased<>().get('1'));
        assertEquals(Optional.empty(), new HashMapArrayBased<>().get("1"));
    }

    @Test
    public void stringToInteger_add_and_get() {
        HashMapArrayBased<String, Integer> stringToInteger = new HashMapArrayBased<>();
        stringToInteger.add("Enno", 1);
        stringToInteger.add("Max", 2);
        stringToInteger.add("Moritz", 3);

        assertEquals(1, stringToInteger.get("Enno").orElse(0));
        assertEquals(2, stringToInteger.get("Max").orElse(0));
        assertEquals(3, stringToInteger.get("Moritz").orElse(0));
        assertEquals(Optional.empty(), stringToInteger.get("Enn"));
    }

    @Test
    public void stringToInteger_add_duplicatedKey_replaceOldValue() {
        HashMapArrayBased<String, Integer> stringToInteger = new HashMapArrayBased<>();
        stringToInteger.add("Enno", 1);
        stringToInteger.add("Max", 2);
        stringToInteger.add("Enno", 4);
        stringToInteger.add("Max", 4);

        assertEquals(4, stringToInteger.get("Enno").orElse(0));
        assertEquals(4, stringToInteger.get("Max").orElse(0));
        assertEquals(Optional.empty(), stringToInteger.get("Moritz"));
    }

    @Test
    public void charToString_add_and_get() {
        HashMapArrayBased<Character, String> charToString = new HashMapArrayBased<>();
        charToString.add('E', "Enno");
        charToString.add('M', "Max");
        charToString.add('M', "Moritz");
        charToString.add('M', "Markus");

        assertEquals("Enno", charToString.get('E').orElse("0"));
        assertEquals("Markus", charToString.get('M').orElse("0"));
        assertEquals(Optional.empty(), charToString.get('N'));
    }


    @Test
    public void add_remove_contains() {
        HashMapArrayBased<String, Integer> stringToInteger = new HashMapArrayBased<>();
        stringToInteger.add("Enno", 1);
        stringToInteger.add("Max", 2);
        stringToInteger.add("Moritz", 3);

        assertTrue(stringToInteger.contains("Enno"));
        assertTrue(stringToInteger.contains("Max"));
        assertTrue(stringToInteger.contains("Moritz"));
        assertFalse(stringToInteger.contains("Enn"));

        stringToInteger.remove("Enno");
        stringToInteger.remove("Max");
        stringToInteger.remove("Moritz");

        assertFalse(stringToInteger.contains("Enno"));
        assertFalse(stringToInteger.contains("Max"));
        assertFalse(stringToInteger.contains("Moritz"));
        assertFalse(stringToInteger.contains("Morit"));
    }

    @Test
    public void add_remove_get() {
        HashMapArrayBased<String, Integer> stringToInteger = new HashMapArrayBased<>();
        stringToInteger.add("Enno", 1);
        stringToInteger.add("Max", 2);
        stringToInteger.add("Moritz", 3);

        assertEquals(1, stringToInteger.get("Enno").orElse(0));
        assertEquals(2, stringToInteger.get("Max").orElse(0));
        assertEquals(3, stringToInteger.get("Moritz").orElse(0));

        stringToInteger.remove("Enno");
        stringToInteger.remove("Max");
        stringToInteger.remove("Moritz");

        assertEquals(Optional.empty(), stringToInteger.get("Enno"));
        assertEquals(Optional.empty(), stringToInteger.get("Max"));
        assertEquals(Optional.empty(), stringToInteger.get("Moritz"));
    }

}