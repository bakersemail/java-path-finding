package com.google.code.java_path_finding;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class AttachedTreeNode<T> implements Comparable<AttachedTreeNode<T>> {
	private final int index;
	private final T attached;
	private final Set<AttachedTreeNode<T>> adjacentNodes;

	private AttachedTreeNode<T> parent;
	private int weight;

	public AttachedTreeNode(T attached, int index) {
		this.index = index;
		this.adjacentNodes = Collections.newSetFromMap(new WeakHashMap<AttachedTreeNode<T>, Boolean>());
		this.attached = attached;

		reset();
	}

	public Set<AttachedTreeNode<T>> getAdjacentNodes() {
		return this.adjacentNodes;
	}

	public T getAttached() {
		return this.attached;
	}

	public AttachedTreeNode<T> getParent() {
		return this.parent;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setParent(AttachedTreeNode<T> parent) {
		this.parent = parent;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void addAjacentNode(AttachedTreeNode<T> treeNode) {
		this.adjacentNodes.add(treeNode);
	}

	public int compareTo(AttachedTreeNode<T> o) {
		return weight - o.getWeight();
	}

	public int getIndex() {
		return this.index;
	}

	public void reset() {
		this.weight = Integer.MAX_VALUE;
		setParent(null);
	}

	public void removeAllIncomingConnections() {
		for (AttachedTreeNode<T> adjNode : adjacentNodes) {
			adjNode.getAdjacentNodes().remove(this);
		}
	}
	
	public void restoreAllConnectionsToSelf() {
		for (AttachedTreeNode<T> adjNode : adjacentNodes) {
			adjNode.getAdjacentNodes().add(this);
		}
	}
}
