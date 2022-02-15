import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void emptyTrie_search_gives_false() {
        Trie trie = new Trie();
        assertFalse(trie.isValid(""));
        assertFalse(trie.isValid("a"));
        assertFalse(trie.isValid("A"));
        assertFalse(trie.isValid("$"));
        assertFalse(trie.isValid(" "));
        assertFalse(trie.isValid("abcdef"));
    }

}