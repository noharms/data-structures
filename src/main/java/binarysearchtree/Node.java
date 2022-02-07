package binarysearchtree;

class Node<K extends Comparable<K>, V> {

    static record KeyValuePair<K extends Comparable<K>, V>(K key, V value) { }

    KeyValuePair<K, V> keyValuePair;
    Node<K, V> left;
    Node<K, V> right;

    Node(KeyValuePair<K, V> keyValuePair) {
        this.keyValuePair = keyValuePair;
    }
    
    static <U extends Comparable<U>, W> Node<U, W> of(U key, W value) {
        return new Node<>(new KeyValuePair<>(key, value));
    }

    K key() {
        return keyValuePair.key;
    }
    
    V value() {
        return keyValuePair.value; 
    }

    int computeHeight() {
        return 1 + Math.max(computeHeight(left), computeHeight(right));
    }

    private static <U extends Comparable<U>, W> int computeHeight(Node<U, W> node) {
        return node != null ? node.computeHeight() : 0;
    }

    int nChildren() {
        if (left == null && right == null) {
            return 0;
        } else if (left != null && right != null) {
            return 2;
        } else {
            return 1;
        }
    }

}
