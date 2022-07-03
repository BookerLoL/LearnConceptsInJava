package interviewprep.datastructures;

import java.util.*;
import java.util.stream.IntStream;

/**
 * The different types of graph representations to be aware of for interviews
 * that you may need to recall in order to represent a graph for a problem.
 *
 * Many coding problems are an abstraction of a graph and knowing how to represent the graph will be helpful
 * for solving.
 *
 */
public class Graph {
    /**
     * Map Based Graphs are good when you cannot guarantee the amount of vertices and edges and useful for problems that may
     * remove edges.
     *
     * I would suggest using this type of graph for interviews coding problems
     * where representing vertices is difficult such as characters / strings / other objects.
     *
     * This is just the most dynamic approach but does require a bit more code to get certain information such as indegrees
     * and updating the values.
     *
     * @param <T>
     */
    public static class MapBasedGraph<T> {
        public Map<T, List<T>> verticesWithEdges;
        public Map<T, Map<T, Integer>>  verticesWithWeightedEdges;
    }


    /**
     * List based graphs tend to represent graphs where vertices start from value 0 -  X and source vertices do not get deleted
     * but outgoing vertices can be deleted.
     *
     * This approach is not as often used in coding problems but does allow for some flexibility such as using
     * {@code List<List<WeightedEdge>> vertices;} or {@code List<List<Integer>> vertices; }
     * <br/>
     * <br/>
     * {@code vertices.get(0); //Get vertex 0's outgoing vertices, 0 -> {1, 2, 4}}
     * <br/>
     * {@code vertices.get0); //0 -> {}, means no outgoing vertices}
     *
     * @param <T>
     */
    public static class ListBasedGraph<T> {
        public List<List<T>> vertices;

        public ListBasedGraph(int numVertices) {
            vertices = new ArrayList<>(numVertices);
            IntStream.range(0, numVertices).forEach(i -> vertices.add(new LinkedList<>()));
        }
    }

    /**
     * Probably one of the more common implementation of a realistic graph and quite flexible.
     *
     * Every row index represents a source vertex, and every column represents the outgoing vertex, and
     * the {@code graph[sourceVertex][outgoingVertex]} can represent there is an edge or a weighted edge.
     *
     * This approach can be good for interviews when the vertices are numbers and preferably from 0-X.
     * <br/>
     * <br/>
     * Standard assumptions:
     * <br/>
     * {@code graph[sourceVertex][outgoingVertex] == 0 // No directed edge from source to outgoing }
     * <br/>
     * {@code graph[outgoingVertex][sourceVertex] != 0 // Directed edge from outgoing to source}
     */
    public static class MatrixBasedGraph {
        //Generally no edge is represented as 0, but you could have a different value to represent it
        public static final int NO_EDGE = 0;
        int[][] graph;
    }
}
