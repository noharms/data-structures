import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

/**
 * An array based hash map, using the {@link Object#hashCode()} method to generate the hash. Duplicates are not allowed.
 * <br>
 *
 * <ul>
 *     <li>V get(K key) in O(1)</li>
 *     <li>boolean contains(K key) in O(1)</li>
 *     <li>void add(K key, V value) in O(1)</li>
 *     <li>V remove(K key)</li>
 * </ul>
 */
public class HashMap<K, V> {

    private static record KeyValuePair<K, V> (K key, V value) {}

    private static final double LOAD_THRESHOLD = 0.5;
    private static final int INITIAL_CAPACITY = 2; // using a low value for testing - change if used productively

    private ArrayList<LinkedList<KeyValuePair<K, V>>> memory;
    private int capacity;
    private int nElements;

    public HashMap() {
        this.capacity = INITIAL_CAPACITY;
        this.memory = allocateNewMemory(capacity);
    }

    public int size() {
        return nElements;
    }

    private int computeHashCode(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    private static <K, V> ArrayList<LinkedList<KeyValuePair<K, V>>> allocateNewMemory(int size) {
        ArrayList<LinkedList<KeyValuePair<K, V>>> newMemory = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            newMemory.add(new LinkedList<>());
        }
        return newMemory;
    }

    /**
     * if the key was previously assigned to another value, that entry is removed (no duplicates allowed in this map)
     */
    public void add(K key, V value) {
        remove(key);
        int hashCode = computeHashCode(key);
        LinkedList<KeyValuePair<K, V>> existingElements = memory.get(hashCode);
        existingElements.add(new KeyValuePair<>(key, value));
        ++nElements;
        if (resizeNeeded()) {
            resizeMemory();
        }
    }

    private boolean resizeNeeded() {
        return Double.compare((double) size() / capacity, LOAD_THRESHOLD) > 0;
    }

    private void resizeMemory() {
        int oldCapacity = capacity;
        capacity *= 2;
        ArrayList<LinkedList<KeyValuePair<K, V>>> newMemory = allocateNewMemory(capacity);
        for (int i = 0; i < oldCapacity; ++i) {
            LinkedList<KeyValuePair<K, V>> fromElements = memory.get(i);
            for (var element : fromElements) {
                int newHashCode = computeHashCode(element.key);
                LinkedList<KeyValuePair<K, V>> toElements = newMemory.get(newHashCode);
                toElements.add(element);
            }
        }
        memory = newMemory;
    }

    private Optional<KeyValuePair<K, V>> findElementWithKey(K key) {
        int hashCode = computeHashCode(key);
        LinkedList<KeyValuePair<K, V>> existingElements = memory.get(hashCode);
        return existingElements.stream().filter(e -> e.key.equals(key)).findFirst();
    }

    /**
     * @param key if this hash map contains the key, the value is returned  inside an Optional;
     *           if not, an empty Optional is returned; if the key is null, an empty Optional is returned
     */
    public Optional<V> get(K key) {
        return contains(key) ? Optional.of(findElementWithKey(key).orElseThrow().value()) : Optional.empty();
    }

    public boolean contains(K key) {
        if (key == null) {
            return false;
        }
        return findElementWithKey(key).isPresent();
    }

    /**
     * if the key is null or not contained in the hash map, nothing is done
     */
    public void remove(K key) {
        if (contains(key)) {
            int hashCode = computeHashCode(key);
            LinkedList<KeyValuePair<K, V>> existingElements = memory.get(hashCode);
            existingElements.remove(findElementWithKey(key).orElseThrow());
            --nElements;
        }
    }

}
