package com.google.code.java_path_finding

class AttachedTreeNode implements Comparable<AttachedTreeNode> {
	int index
	def attached
	Set<AttachedTreeNode> adjacentNodes = [] as Set

	AttachedTreeNode parent
	int weight = Integer.MAX_VALUE

	int compareTo(AttachedTreeNode o) {
		weight - o.weight
	}

	void reset() {
		weight = Integer.MAX_VALUE
		parent = null
	}

	void removeAllIncomingConnections() {
		adjacentNodes.each {
			it.adjacentNodes.remove this
		}
	}
	
	void restoreAllConnectionsToSelf() {
		adjacentNodes.each {
			it.adjacentNodes << this
		}
	}
}
