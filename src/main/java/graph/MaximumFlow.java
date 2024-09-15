package graph;

import java.util.List;

/**
 * Computes the maximum flow according to the Ford-Fulkerson algorithm.
 * <br>
 * Given a weighted graph which contains a "source" and a "sink" node which are connected,
 * and assuming the weight between node i and node j represents the capacity of flow that can pass from i to j,
 * the maximum flow is that stationary flow that can maximally arrive at the sink.
 * <br>
 * Example from Wikipedia
 *
 * <pre>
 *     --------------------> node1 -----------------
 *     |       10              |          5        |
 *     |                       |                   v
 *    source                   | 15               sink
 *     |                       |                   ^
 *     |       5               v         10        |
 *     --------------------> node2 ----------------
 *  </pre>
 * <p>
 * In this flow system, the maximum flow of 15 can be achieved by the following flow
 * *
 * <pre>
 *     --------------------> node1 -----------------
 *     |      10/10            |          5/5      |
 *     |                       |                   v
 *    source                   | 5/15             sink
 *     |                       |                   ^
 *     |      5/5              v      10/10        |
 *     --------------------> node2 ----------------
 *  </pre>
 */
public class MaximumFlow {

    private final WeightedGraph<Integer> graph;

    public MaximumFlow(WeightedGraph<Integer> graph) {
        this.graph = graph;
    }

    public int computeMaxFlow(int sourceId, int sinkId) {
        WeightedGraph<Integer> residualGraph = (WeightedGraph<Integer>) graph.copy();
        int maxFlow = 0;
        List<Integer> pathSourceToSink = residualGraph.bfsPath(sourceId, sinkId);
        while (!pathSourceToSink.isEmpty()) {
            int pathFlow = findFlowAlongPath(pathSourceToSink, residualGraph);
            maxFlow += pathFlow;
            updateResidualGraphWeights(residualGraph, pathSourceToSink, pathFlow);
            pathSourceToSink = residualGraph.bfsPath(sourceId, sinkId);
        }
        return maxFlow;
    }

    private static void updateResidualGraphWeights(WeightedGraph<Integer> residualGraph,
                                                   List<Integer> pathSourceToSink,
                                                   int pathFlow
    ) {
        for (int i = 0; i < pathSourceToSink.size() - 1; i++) {
            int fromNodeId = pathSourceToSink.get(i);
            int toNodeId = pathSourceToSink.get(i + 1);
            int oldWeightForward = residualGraph.getEdge(fromNodeId, toNodeId);
            if (oldWeightForward - pathFlow == 0) {
                residualGraph.removeEdgeIfExisting(fromNodeId, toNodeId);
            } else {
                residualGraph.addDirectedEdge(fromNodeId, toNodeId, oldWeightForward - pathFlow);
            }
            // Caveat: we basically assume that adding a flow of x in one direction, increases the capacity
            //         for a flow in the opposite direction by x
            //         e.g.  if we have
            //                                 node0 -- 1 --> node1
            //                                  |               |
            //                                  <------- 3 -----
            //         and apply a flow of 1 from node0 to node 1, the updated residual graph
            //         will have a connection
            //
            //                                 node0          node1
            //                                  |               |
            //                                  <------- 4 -----
            // --> note: this also means, if there was no connection before, adding a flow can create a direction in the
            //           opposite direction
            int oldWeightBackward = hasBackwardEdge(residualGraph, fromNodeId, toNodeId)
                ? residualGraph.getEdge(toNodeId, fromNodeId)
                : 0;
            residualGraph.addDirectedEdge(toNodeId, fromNodeId, oldWeightBackward + pathFlow);

        }

    }

    private static boolean hasBackwardEdge(WeightedGraph<Integer> residualGraph, int fromNodeId, int toNodeId) {
        return residualGraph.allEdges(toNodeId).containsKey(fromNodeId);
    }

    private static int findFlowAlongPath(List<Integer> pathSourceToSink, WeightedGraph<Integer> residualGraph) {
        if (pathSourceToSink.isEmpty()) {
            return 0;
        }
        int pathFlow = Integer.MAX_VALUE;
        for (int i = 0; i < pathSourceToSink.size() - 1; i++) {
            int fromNodeId = pathSourceToSink.get(i);
            int toNodeId = pathSourceToSink.get(i + 1);
            int weight = residualGraph.getEdge(fromNodeId, toNodeId);
            pathFlow = Math.min(pathFlow, weight);
        }
        return pathFlow;
    }

}
