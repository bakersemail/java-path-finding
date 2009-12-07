package com.bakersoftware.path;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ShortestPathCalculator<T> {
	public Stack<AttachedTreeNode<T>> calculatePath(AttachedTreeNode<T> source,
			AttachedTreeNode<T> destination, int numNodes) {
		Queue<AttachedTreeNode<T>> unsettled = new PriorityQueue<AttachedTreeNode<T>>();

		source.setWeight(0);
		unsettled.add(source);

		while (unsettled.size() > 0) {
			AttachedTreeNode<T> head = unsettled.poll();
			head.settle();
			relaxNeighbors(head, unsettled);
		}

		Stack<AttachedTreeNode<T>> path = new Stack<AttachedTreeNode<T>>();
		if (destination.getParent() != null) {
			path.push(destination);
			AttachedTreeNode<T> reverse = destination.getParent();
			while (reverse != source) {
				path.push(reverse);
				reverse = reverse.getParent();
			}
		}

		return path;
	}

	private void relaxNeighbors(AttachedTreeNode<T> node, Queue<AttachedTreeNode<T>> unsettled) {
		for (AttachedTreeNode<T> adjNode : node.getAdjacentNodes()) {
			if (adjNode.isNotSettled() && adjNode.getWeight() > (node.getWeight() + 1)) {
				adjNode.setWeight(node.getWeight() + 1);
				adjNode.setParent(node);
				unsettled.add(adjNode);
			}
		}
	}
}
