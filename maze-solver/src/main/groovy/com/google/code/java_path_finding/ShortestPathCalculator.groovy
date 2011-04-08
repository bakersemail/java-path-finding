package com.google.code.java_path_finding

class ShortestPathCalculator {
	Stack<AttachedTreeNode> calculatePath(AttachedTreeNode source, AttachedTreeNode destination, int numNodes) {
		def unsettled = [] as PriorityQueue
		boolean[] settled = new boolean[numNodes]

		source.weight = 0
		unsettled << source

		while (!unsettled.isEmpty()) {
			AttachedTreeNode head = unsettled.poll()
			settled[head.getIndex()] = true
			relaxNeighbors(head, unsettled, settled)
		}

		def path = [] as Stack
		if (destination.parent) {
			path << destination
			def node = destination.parent
			while (node && node != source) {
				path << node
				node = node.parent
			}
		}
		path
	}

	private def relaxNeighbors(AttachedTreeNode node, def unsettled, boolean[] settled) {
		node.adjacentNodes.each {
			if (!settled[it.index] && it.weight > node.weight + 1) {
				it.weight = node.weight + 1
				it.parent = node
				unsettled << it
			}
		}
	}
}
