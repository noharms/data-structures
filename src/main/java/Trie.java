import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A prefix search tree based on the lower case latin alphabet 'a-z' - upper case letters are converted to lower case
 * and non-alphabetic letters are ignored.
 *
 * <ul>
 *     <li>{@code void add(String validWord} in O(1)</li>
 *     <li>{@code List<String> autoComplete(String prefix)} in O(m + 26^(ALPHABET_SIZE - m)) </li>
 *     <li>{@code boolean isValid(String word)}</li>
 * </ul>
 */
public class Trie {

    private static final int ALPHABET_SIZE = 26; // a-z

    private static class TrieNode {

        private TrieNode[] children = new TrieNode[ALPHABET_SIZE];

        private boolean isValidWord;

    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void add(String validWord) {
        TrieNode current = root;
        for (char c : validWord.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                current = current.children[index];
            }
        }
        current.isValidWord = true;
    }

    public boolean isValid(String word) {
        TrieNode current = root;
        for (char c : word.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                if (current.children[index] == null) {
                    return false;
                } else {
                    current = current.children[index];
                }
            }
        }
        return current.isValidWord;
    }

    public List<String> autoComplete(String prefix) {
        TrieNode current = root;
        StringBuilder cleanedPrefixBuilder = new StringBuilder();
        for (char c : prefix.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current = null;
                    break;
                } else {
                    cleanedPrefixBuilder.append(c);
                    current = current.children[index];
                }
            }
        }
        TrieNode terminalNode = current;
        String cleanedPrefix = cleanedPrefixBuilder.toString();
        return findValidDescendants(terminalNode).stream().map(cleanedPrefix::concat).toList();
    }

    private List<String> findValidDescendants(TrieNode node) {
        if (node == null) {
            return Collections.emptyList();
        } else {
            List<String> valids = new ArrayList<>();
            findValidDescendants(node, new StringBuilder(), valids);
            return valids;
        }
    }

    private void findValidDescendants(TrieNode current, StringBuilder candidate, List<String> valids) {
        if (current == null) {
            return;
        } else if (current.isValidWord) {
            valids.add(candidate.toString());
        }
        for (int i = 0; i < ALPHABET_SIZE; ++i) {
            char c = (char) ('a' + i);
            TrieNode child = current.children[i];
            findValidDescendants(child, candidate.append(c), valids);
            candidate.deleteCharAt(candidate.length() - 1);
        }
    }

}
