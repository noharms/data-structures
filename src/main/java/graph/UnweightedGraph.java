package graph;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

/**
 * An unweighted graph that uses a map for storing the nodes of the graph as keys, and the edges of the graph
 * as values for each node.
 * <br>
 * The graph is built iteratively by the client by adding nodes and edges.
 * <br>
 * The graph can be directed or undirected. The client simply has to use the separate APIs when constructing the
 * {@link UnweightedGraph#addDirectedEdge(Object, Object)}
 * {@link UnweightedGraph#addUndirectedEdge(Object, Object)}.
 */
public class UnweightedGraph<T> extends Graph<T> {

    private final Map<T, Set<T>> nodesToEdges = new HashMap<>();

    @Override
    public int size() {
        return nodesToEdges.keySet().size();
    }

    @Override
    public Set<T> nodes() {
        return nodesToEdges.keySet();
    }

    @Override
    public boolean contains(T value) {
        return nodesToEdges.containsKey(value);
    }

    @Override
    public void addNode(T value) {
        throwIfFound(value);
        nodesToEdges.put(value, new HashSet<>());
    }

    /**
     * If the edge already exists, this method will not complain.
     */
    public void addDirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
    }

    /**
     * If the edge already exists, this method will not complain.
     */
    public void addUndirectedEdge(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        nodesToEdges.get(from).add(to);
        nodesToEdges.get(to).add(from);
    }

    public Set<Edge<T>> allEdges() {
        return nodesToEdges.entrySet()
            .stream()
            .flatMap(
                nodeToNeighbors -> nodeToNeighbors
                    .getValue()
                    .stream()
                    .map(neighbor -> new Edge.UnweightedEdge<>(nodeToNeighbors.getKey(), neighbor))
            )
            .collect(Collectors.toSet());
    }

    @Override
    public Set<T> allNeighbors(T value) {
        throwIfNotFound(value);
        return nodesToEdges.get(value);
    }

    @Override
    public Set<T> allUpstreamNeighbors(T value) {
        return nodesToEdges
            .entrySet()
            .stream()
            .filter(e -> e.getValue().contains(value))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    @Override
    public List<T> shortestPath(T from, T to) {
        throwIfNotFound(from);
        throwIfNotFound(to);
        return bfsPath(from, to);
    }

    /**
     * Returns the component that the given node belongs to.
     */
    public Set<T> findAllConnected(T node) {
        if (!contains(node)) {
            return emptySet();
        }
        Set<T> connectedNodes = new HashSet<>();
        connectedNodes.add(node);
        findAllConnected(node, new HashSet<>(), connectedNodes);
        return connectedNodes;
    }

    private void findAllConnected(T node, Set<T> visited, Set<T> result) {
        for (T neighbor : nodesToEdges.get(node)) {
            if (!visited.contains(neighbor)) {
                result.add(neighbor);
                visited.add(neighbor);
                findAllConnected(neighbor, visited, result);
            }
        }
    }

    /**
     * Finds all cliques that the given node is part of. Note that also the one-element set that contains only the node
     * is a clique (a "1-clique").
     * <br><br>
     * The algorithm builds the cliques in order of ascending number of elements: starting from the 1-clique that only
     * contains the node itself; we build 2-cliques by joining the node with all its neighbors; then 3-cliques by
     * going through all 2-cliques and for each finding the common neighbors that are not yet included in the current
     * clique, and the for each of these disintegrated common neighbors adding them to form a new 3-clique; whenever
     * a new clique is created it is added to a queue of candidates that might potentially be enlarged even more;
     * once this queue is empty, we stop.
     * <br><br>
     * Caveat: for graphs in which all nodes are mutual neighbors, finding all sub-cliques is equivalent to finding all
     * subsets (because each subset is a clique, except that we do not have the empty set here); this means we will have
     * O(2^n) cliques to create.
     */
    public Set<Set<T>> findAllCliques(T node) {
        if (!contains(node)) {
            return emptySet();
        }
        Set<T> oneClique = Set.of(node);
        Set<Set<T>> result = new HashSet<>();
        result.add(oneClique);
        Queue<Set<T>> candidateCliquesForEnlargement = new ArrayDeque<>();
        candidateCliquesForEnlargement.add(oneClique);
        while (!candidateCliquesForEnlargement.isEmpty()) {
            Set<T> clique = candidateCliquesForEnlargement.remove();
            Set<T> disintegratedCommonNeighbors = findDisintegratedCommonNeighbors(clique);
            for (T commonNeighbor : disintegratedCommonNeighbors) {
                Set<T> oneLargerClique = new HashSet<>(clique);
                oneLargerClique.add(commonNeighbor);
                result.add(oneLargerClique);
                candidateCliquesForEnlargement.add(oneLargerClique);
            }
        }
        return result;
    }

    private Set<T> findDisintegratedCommonNeighbors(Set<T> clique) {
        Set<Set<T>> memberNeighborhoods = clique.stream()
            .map(this::allNeighbors)
            .map(neighborhood -> excise(neighborhood, clique))
            .collect(Collectors.toSet());
        Set<T> smallestMemberNeighborhood = memberNeighborhoods.stream()
            .min(Comparator.comparingInt(Set::size))
            .orElse(emptySet());
        return smallestMemberNeighborhood.stream()
            .filter(node -> memberNeighborhoods.stream().allMatch(neighborhood -> neighborhood.contains(node)))
            .collect(Collectors.toSet());
    }

    private Set<T> excise(Set<T> set, Set<T> filterOut) {
        return set.stream().filter(node -> !filterOut.contains(node)).collect(Collectors.toSet());
    }

}
