package interviewprep.algorithms;

import java.util.*;
import java.util.function.Supplier;

/**
 * Topological Sort is used specifically for directed acyclic graph (DAG) to generate an ordering such that every edge uv, where vertex u will appear before v.
 * <br/>
 * <br/>
 * <p>
 * Applications: Finding a cycle in a graph, course scheduling, sentence ordering, execution ordering, dependency resolution, deadlock detection
 * </p>
 * <br>
 * Key things to note:
 * <ul>
 *     <li>See if a problem can be represented as a graph and is directed</li>
 *     <ul>
 *         <li>If so then remember how to represent a graph for interviews!</li>
 *         <li>Every DAG has at least 1 topological ordering!</li>
 *     </ul>
 *     </li>
 *     <li>Need at least 1 vertex that has no indegrees (dependencies)</li>
 * </ul>
 *
 * @see <a href="https://iq.opengenus.org/applications-of-topological-sort/">Applications Explained</a>
 * @since 6/29/2022
 */
public class TopologicalSort {

    /**
     * BFS / Kahns Algorithm approach which is a bit more setup work than DFS approach
     *
     *
     * @implNote Runtime: O(V+E), Space: O(V)
     * @implSpec Starts with the first vertex with indegrees 0, so can change logic so it's randomized if needed.
     * @param graph DAG graph using matrix representation of adjacency list, where graph[u][v] == 0 represents no edge and == 1 represents edge between the two nodes.
     * @return A topological sorted ordering of the given graph
     */
    public static List<Integer> bfs(int[][] graph) {
        int[] indegrees = calculateIndegrees(graph);
        Queue<Integer> queue = addVertciesWithNoIndegrees(LinkedList::new, indegrees);

        List<Integer> topologicalOrder = new ArrayList<>();
        //From U vertex -> visit v Vertex then decrement the indegrees, and only add if indegrees is 0 to ensure no more dependencies
        while(!queue.isEmpty()) {
            int uVertex = queue.remove();
            topologicalOrder.add(uVertex);

            for (int vVertex = 0; vVertex < indegrees.length; vVertex++) {
                boolean hasEdge = graph[uVertex][vVertex] != 0;
                if (hasEdge) {
                    indegrees[vVertex]--;
                    if (indegrees[vVertex] == 0) {
                        queue.add(vVertex);
                    }
                }
            }
        }

        return topologicalOrder;
    }

    /**
     * DFS approach which is often easier to remember for interviews
     *
     * @implNote Runtime: O(V+E), Space: O(V)
     * @param graph DAG graph using matrix representation of adjacency list, where graph[u][v] == 0 represents no edge and == 1 represents edge between the two nodes.
     * @return A topological sorted ordering of the given graph
     */
    public static List<Integer> dfs(int[][] graph) {
        Stack<Integer> processedVertices = new Stack<>();
        boolean[] visited = new boolean[graph.length];

        for (int vertex = 0; vertex < visited.length; vertex++) {
            if (!visited[vertex]) {
                dfsHelper(vertex, visited, processedVertices, graph);
            }
        }

        List<Integer> topologicalOrdering = new ArrayList<>();
        while (!processedVertices.isEmpty()) {
            topologicalOrdering.add(processedVertices.pop());
        }
        return topologicalOrdering;
    }
    private static void dfsHelper(int vertex, boolean[] visited, Stack<Integer> processed, int[][] graph) {
        visited[vertex] = true;

        for (int outVertex : getOutVertices(graph[vertex])) {
            if (!visited[outVertex]) {
                dfsHelper(outVertex, visited, processed, graph);
            }
        }

        processed.add(vertex);
    }

    private static <T extends Collection<Integer>> T addVertciesWithNoIndegrees(Supplier<T> collectionSupplier, int[] indegrees) {
        T collection = collectionSupplier.get();
        for (int vertex = 0; vertex < indegrees.length; vertex++) {
            if (indegrees[vertex] == 0) {
                collection.add(vertex);
            }
        }
        return collection;
    }
    private static int[] calculateIndegrees(int[][] graph) {
        final int totalVertices = graph.length;
        int[] indegrees = new int[totalVertices];

        for (int uVertex = 0; uVertex < totalVertices; uVertex++) {
            for (int vVertex = 0; vVertex < totalVertices; vVertex++) {
                boolean hasEdge = graph[uVertex][vVertex] != 0;
                if (hasEdge) {
                    indegrees[vVertex]++;
                }
            }
        }
        return indegrees;
    }

    private static List<Integer> getOutVertices(int[] vertexEdges) {
        List<Integer> neighbors = new ArrayList<>();

        for (int outVertex = 0; outVertex < vertexEdges.length; outVertex++) {
            if (vertexEdges[outVertex] != 0) {
                neighbors.add(outVertex);
            }
        }

        return neighbors;
    }

    public static void main(String[] args) {
        //Vertex 0 through 5
        int[][] graph = {
                {0, 1, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0},
        };

        System.out.println(dfs(graph));
    }
}
