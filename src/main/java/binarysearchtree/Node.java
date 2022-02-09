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

    public Node<K, V> left() {
        return left;
    }

    public Node<K, V> right() {
        return right;
    }

    int computeHeight() {
        return 1 + Math.max(computeHeight(left), computeHeight(right));
    }

    static <U extends Comparable<U>, W> int computeHeight(Node<U, W> node) {
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

    // returns null if candidate is not the parent of the match but the match or if no match in candidate's can be found
    Node<K, V> findParentNode(Node<K, V> target) {
        K searchKey = target.key();
        int compareResult = key().compareTo(searchKey);
        if (compareResult == 0) {
            return null; // this can only happen if in the first-non recursive call target is the match
        } else if (compareResult < 0) {
            if (right == null) {
                return null;
            } else {
                boolean rightChildIsMatch = right.key().compareTo(searchKey) == 0;
                return rightChildIsMatch ? this : right.findParentNode(target);
            }
        } else {
            if (left == null) {
                return null;
            } else {
                boolean leftChildIsMatch = left.key().compareTo(searchKey) == 0;
                return leftChildIsMatch ? this : left.findParentNode(target);
            }
        }
    }

}
