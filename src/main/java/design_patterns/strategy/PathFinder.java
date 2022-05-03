package designpatterns.strategy;

import java.util.List;

public interface PathFinder {
	public List<Node> findPath(Node starting, Node end, DistanceMapper mapper);
}
