package designpatterns.strategy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFSPathFinder implements PathFinder {

	@Override
	public List<Node> findPath(Node starting, Node end, DistanceMapper mapper) {
		List<Node> path = new LinkedList<>();

		Set<Node> seen = new HashSet<>();
		Stack<Node> nodesToProcess = new Stack<>();
		nodesToProcess.push(starting);

		while (!nodesToProcess.isEmpty()) {
			Node currentNode = nodesToProcess.pop();

			if (seen.contains(currentNode)) {
				continue;
			}

			if (currentNode.equals(end)) {
				break;
			}

			nodesToProcess.addAll(mapper.getConnections(starting));
		}

		return path;
	}

}
