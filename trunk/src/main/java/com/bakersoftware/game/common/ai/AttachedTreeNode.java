package com.bakersoftware.game.common.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttachedTreeNode<T> implements Comparable<AttachedTreeNode<T>> {
	private final int index;
	private final Set<AttachedTreeNode<T>> adjacentNodes;
	private final T attached;

	private AttachedTreeNode<T> parent;
	private int weight;

	public AttachedTreeNode(T attached, int index) {
		this.index = index;
		this.adjacentNodes = new HashSet<AttachedTreeNode<T>>();
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
		return Integer.valueOf(this.weight).compareTo(o.getWeight());
	}

	public int getIndex() {
		return this.index;
	}

	public void detach() {
		for (AttachedTreeNode<T> adjNode : getAdjacentNodes()) {
			adjNode.getAdjacentNodes().remove(this);
		}
	}

	public void reset() {
		this.weight = Integer.MAX_VALUE;
		setParent(null);
	}

	public List<AttachedTreeNode<T>> removeAllIncomingConnections() {
		List<AttachedTreeNode<T>> oldAdjNodes = new ArrayList<AttachedTreeNode<T>>(
				adjacentNodes);
		for (AttachedTreeNode<T> adjNode : adjacentNodes) {
			adjNode.getAdjacentNodes().remove(this);
		}
		return oldAdjNodes;
	}
}
