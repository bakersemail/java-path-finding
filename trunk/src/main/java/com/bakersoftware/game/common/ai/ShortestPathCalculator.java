package com.bakersoftware.game.common.ai;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ShortestPathCalculator {
	public Stack<TreeNode> calculatePath(final TreeNode source, final TreeNode destination,
			int numNodes) {
		Queue<TreeNode> unsettled = new PriorityQueue<TreeNode>();
		boolean[] settled = new boolean[numNodes];

		source.setWeight(0);
		unsettled.add(source);

		while (unsettled.size() > 0) {
			TreeNode head = unsettled.poll();
			settled[head.getIndex()] = true;
			relaxNeighbors(head, unsettled, settled);
		}

		Stack<TreeNode> path = new Stack<TreeNode>();
		if (destination.getParent() != null) {
			path.push(destination);
			TreeNode reverse = destination.getParent();
			while (reverse != source) {
				path.push(reverse);
				reverse = reverse.getParent();
			}
		}

		return path;
	}

	private void relaxNeighbors(TreeNode node, Queue<TreeNode> unsettled, boolean[] settled) {
		for (TreeNode adjNode : node.getAdjacentNodes()) {
			if (!settled[adjNode.getIndex()] && adjNode.getWeight() > (node.getWeight() + 1)) {
				adjNode.setWeight(node.getWeight() + 1);
				adjNode.setParent(node);
				unsettled.add(adjNode);
			}
		}
	}
}
