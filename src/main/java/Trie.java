import java.util.ArrayList;
import java.util.List;

/**
 * A prefix search tree based on the lower case latin alphabet 'a-z'
 * (upper case and non-alphabetic letters are ignored).
 *
 * <ul>
 *     <li>{@code void add(String validWord} in O(1)</li>
 *     <li>{@code List<String> autoComplete(String prefix)} in O(m + 26^(ALPHABET_SIZE - m)) </li>
 *     <li>{@code boolean isValid(String word)}</li>
 * </ul>
 */
public class Trie {

    private static final int ALPHABET_SIZE = 26; // a-z

    private static class TreeNode {

        private TreeNode[] children = new TreeNode[ALPHABET_SIZE];

        private boolean isValidWord;

    }

    private final TreeNode root;

    public Trie() {
        root = new TreeNode();
    }

    public void add(String validWord) {
        TreeNode current = root;
        for (char c : validWord.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new TreeNode();
                }
                current = current.children[index];
            }
        }
        current.isValidWord = true;
    }

    public boolean isValid(String word) {
        TreeNode current = root;
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
        TreeNode current = root;
        for (char c : prefix.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                if (current.children[index] == null) {
                    return null;
                } else {
                    current = current.children[index];
                }
            }
        }
        List<String> valids = new ArrayList<>();
        findValidDescendants(current, new StringBuilder(prefix), valids);
        return valids;
    }

    private void findValidDescendants(TreeNode current, StringBuilder candidate, List<String> valids) {
        if (current == null) {
            return;
        } else if (current.isValidWord) {
            valids.add(candidate.toString());
        }
        for (int i = 0; i < ALPHABET_SIZE; ++i) {
            char c = (char) ('a' + i);
            TreeNode child = current.children[i];
            findValidDescendants(child, candidate.append(c), valids);
            candidate.deleteCharAt(candidate.length() - 1);
        }
    }

}
