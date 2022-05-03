package designpatterns.strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSPathFinder implements PathFinder {

	@Override
	public List<Node> findPath(Node starting, Node end, DistanceMapper mapper) {
		List<Node> path = new LinkedList<>();

		Queue<Node> nodesToProcess = new LinkedList<>();
		nodesToProcess.add(starting);

		while (!nodesToProcess.isEmpty()) {
			Node currentNode = nodesToProcess.remove();

			if (!path.contains(currentNode)) {
				path.add(currentNode);
			}

			if (currentNode.equals(end)) {
				break;
			}

			nodesToProcess.addAll(mapper.getConnections(starting));
		}

		return path;
	}

}
