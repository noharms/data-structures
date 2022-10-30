package graph;

/**
 * Computes the maximum flow that arrives from a given source node to a given sink node which both are connected in
 * a weighted graph of nodes, where the weight between node i and node j represent the capacity of flow that can pass
 * from i to j.
 * <br>
 * Example from Wikipedia
 *
 *  <pre>
 *     --------------------> node1 -----------------
 *     |       10              |          5        |
 *     |                       |                   v
 *    source                   | 15               sink
 *     |                       |                   ^
 *     |       5               v         10        |
 *     --------------------> node2 ----------------
 *  </pre>
 *
 *  In this flow system, the maximum flow of 15 can be achieved by the following flow
 *   *
 *  <pre>
 *     --------------------> node1 -----------------
 *     |      10/10            |          5/5      |
 *     |                       |                   v
 *    source                   | 5/15             sink
 *     |                       |                   ^
 *     |      5/5              v      10/10        |
 *     --------------------> node2 ----------------
 *  </pre>
 *
 *
 */
public class MaximumFlow {


}
