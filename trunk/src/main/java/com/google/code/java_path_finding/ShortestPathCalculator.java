package com.google.code.java_path_finding;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ShortestPathCalculator<T> {
    public Stack<AttachedTreeNode<T>> calculatePath(AttachedTreeNode<T> source,
			AttachedTreeNode<T> destination, int numNodes, long maxPathLength) {
		Queue<AttachedTreeNode<T>> unsettled = new PriorityQueue<AttachedTreeNode<T>>();
		boolean[] settled = new boolean[numNodes];

		source.setWeight(0);
		unsettled.add(source);

		while (unsettled.size() > 0) {
			AttachedTreeNode<T> head = unsettled.poll();
			settled[head.getIndex()] = true;
			relaxNeighbors(head, unsettled, settled);
		}

		Stack<AttachedTreeNode<T>> path = new Stack<AttachedTreeNode<T>>();
		if (destination.getParent() != null) {
			path.push(destination);
			AttachedTreeNode<T> reverse = destination.getParent();
			while (reverse != source && reverse != null) {
                if (path.size() >= maxPathLength) {
                    //No path within length
                    return null;
                }

				path.push(reverse);
				reverse = reverse.getParent();
			}
		}

		return path;
	}

	private void relaxNeighbors(AttachedTreeNode<T> node, Queue<AttachedTreeNode<T>> unsettled, 
			boolean[] settled) {
		for (AttachedTreeNode<T> adjNode : node.getAdjacentNodes()) {
			if (!settled[adjNode.getIndex()]
					&& adjNode.getWeight() > (node.getWeight() + 1)) {
				adjNode.setWeight(node.getWeight() + 1);
				adjNode.setParent(node);
				unsettled.add(adjNode);
			}
		}
	}
}
