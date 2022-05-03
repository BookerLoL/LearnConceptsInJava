package design_patterns.strategy;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DistanceMapper {
	private Map<Node, Integer> EMPTY_DISTANCE_MAP = Collections.emptyMap();
	private Map<Node, Map<Node, Integer>> nodeDistanceMap;

	public DistanceMapper() {
		nodeDistanceMap = new HashMap<>();
	}

	public void setDistance(Node from, Node to, Integer distance) {
		Map<Node, Integer> distanceMap = nodeDistanceMap.compute(from, (k, v) -> v == null ? new HashMap<>() : v);
		distanceMap.put(to, distance);
	}

	public void removeDistance(Node from, Node to) {
		nodeDistanceMap.getOrDefault(from, EMPTY_DISTANCE_MAP).remove(to);
	}

	public boolean hasPath(Node from, Node to) {
		return nodeDistanceMap.getOrDefault(from, EMPTY_DISTANCE_MAP).containsKey(to);
	}

	public Optional<Integer> getDistance(Node from, Node to) {
		return Optional.ofNullable(nodeDistanceMap.getOrDefault(from, EMPTY_DISTANCE_MAP).get(to));
	}

	public Collection<Node> getConnections(Node node) {
		return nodeDistanceMap.getOrDefault(node, EMPTY_DISTANCE_MAP).keySet();
	}
}
