import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void emptyTrie_isValid_givesFalse() {
        Trie trie = new Trie();
        assertFalse(trie.isValid("a"));
        assertFalse(trie.isValid("A"));
        assertFalse(trie.isValid("$"));
        assertFalse(trie.isValid(" "));
        assertFalse(trie.isValid("abcdef"));
    }

    @Test
    void emptyTree_autoComplete_givesEmptyList() {
        Trie trie = new Trie();
        assertEquals(emptyList(), trie.autoComplete(""));
        assertEquals(emptyList(), trie.autoComplete("a"));
        assertEquals(emptyList(), trie.autoComplete("A"));
        assertEquals(emptyList(), trie.autoComplete("abcdef"));
    }

    @Test
    void autocomplete_non_alphabetic_characters_throws() {
        Trie trie = new Trie();
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete("$"));
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete(" "));
    }

    @Test
    void isValid_emptyPrefixIsAlwaysFalse() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("world");
        assertFalse(trie.isValid(""));
        assertFalse(new Trie().isValid(""));
    }

    @Test
    void isValid_isCaseInsensitive() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("world");
        assertTrue(trie.isValid("Hello"));
        assertTrue(trie.isValid("HELLO"));
        assertTrue(trie.isValid("HeLlO"));
        assertTrue(trie.isValid("hello"));
        assertTrue(trie.isValid("%§??!#<^^hello%§??!#<^^"));
        assertTrue(trie.isValid("he%§ll??!#<^^o"));
        assertTrue(trie.isValid("World"));
        assertTrue(trie.isValid("WORLD"));
        assertTrue(trie.isValid("wOrLd"));
        assertTrue(trie.isValid("world"));
        assertFalse(trie.isValid("other"));
    }

    @Test
    void isValid_ignoresNonAlphabetic() {
        Trie trie = new Trie();
        trie.add("hello");
        assertTrue(trie.isValid("0h1e2l3l4o5"));
        assertTrue(trie.isValid("%§??!#<^^hello%§??!#<^^"));
        assertTrue(trie.isValid("he%§ll??!#<^^o"));
    }

    @Test
    void autoComplete_emptyPrefixGivesAllValidWords() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("world");
        trie.add("how");
        trie.add("are");
        trie.add("u?");

        assertEquals(List.of("are", "hello", "how", "u", "world"), trie.autoComplete(""));
    }

    @Test
    void autoComplete_non_alphabetic_characters_throws() {
        Trie trie = new Trie();
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete(" "));
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete("1"));
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete("!"));
        assertThrows(IllegalArgumentException.class, () -> trie.autoComplete("="));
    }


    @Test
    void autoComplete_works() {
        Trie trie = new Trie();
        trie.add("slow");
        trie.add("song");
        trie.add("stock");
        trie.add("steam");
        trie.add("star");
        trie.add("string");
        trie.add("strap");
        trie.add("stroll");
        trie.add("stream");
        trie.add("others");

        assertEquals(List.of("slow", "song", "star", "steam", "stock", "strap", "stream", "string", "stroll"),
                trie.autoComplete("s"));
        assertEquals(List.of("slow"), trie.autoComplete("sl"));
        assertEquals(List.of("star", "steam", "stock", "strap", "stream", "string", "stroll"),
                trie.autoComplete("st"));
        assertEquals(List.of("strap", "stream", "string", "stroll"), trie.autoComplete("str"));
    }
}