# Data Structures
## Data Structures you should know

## How to represent some data structures

### Graphs
Many coding problems can be abstracted as a graph so be aware of how you can do so!

Map-based graphs:
- Pros
  - Most dynamic and flexible approach
  - Can simplify code or complex if statement logic
- Cons
  - More work to manage values properly
  - Might need to initially set values beforehand
```java
//Example: Character -> Character (distance / weight / status code)
Map<Character, Map<Character, Integer>> dfa;

Map<String, Map<String, Double>> conversionRates;

Map<Integer, List<Integer>> graph;
Map<Integer, Map<Integer, Integer>> weightedGraph;
```

List of Lists
- Pros
  - Can save some memory if the vertices are sparse
- Cons
  - Not flexible if you need to remove a vertex
```java
//Example: 
// vertices.get(0), get all outgoing edges of that vertex
List<List<Integer>> vertices;
List<List<WeightedEdge>> weightedGraph;
```

Another approach is using matrix to represent a graph
but need to know up front how many total vertices and can somehow represent the vertices as a number.
```java
//Each row represents a vertex, each column represents an outgoing vertex
//If graph[sourceVertex][outgoingVertex] != 0 then there is an edge, can also represent a weighted edge
int[][] graph; 
```

### 