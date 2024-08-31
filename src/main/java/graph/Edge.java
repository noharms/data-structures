package graph;

public interface Edge<T> {

    T from();

    T to();

    Edge<T> opposite();

    record WeightedEdge<T>(T from, T to, int weight) implements Edge<T> {
        @Override
        public WeightedEdge<T> opposite() {
            return new WeightedEdge<>(to, from, weight);
        }
    }

    record UnweightedEdge<T>(T from, T to) implements Edge<T> {
        @Override
        public UnweightedEdge<T> opposite() {
            return new UnweightedEdge<>(to, from);
        }
    }

}
