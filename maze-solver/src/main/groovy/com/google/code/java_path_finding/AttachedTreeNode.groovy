package com.google.code.java_path_finding

class AttachedTreeNode<T> implements Comparable<AttachedTreeNode<T>> {
	int index
	T attached
	Set<AttachedTreeNode<T>> adjacentNodes = [] as Set

	AttachedTreeNode<T> parent
	int weight = Integer.MAX_VALUE

	int compareTo(AttachedTreeNode<T> o) {
		weight - o.getWeight()
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
			it.adjacentNodes.add this
		}
	}
}
