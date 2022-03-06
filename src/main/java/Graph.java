import java.util.List;
import java.util.Set;

public abstract class Graph<T> {

    abstract public boolean contains(T value);

    abstract public List<T> shortestPath(T from, T to);

    abstract Set<T> allNeighbors(T value);

    abstract void addNode(T value);

    void throwIfFound(T value) {
        if (contains(value)) {
            String msg = "Duplicate nodes are not allowed - %s already exists in graph".formatted(value);
            throw new IllegalArgumentException(msg);
        }
    }

    void throwIfNotFound(T value) {
        if (!contains(value)) {
            throw new IllegalArgumentException("Node %s is not found in graph".formatted(value));
        }
    }

}
