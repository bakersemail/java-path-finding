package com.bakersoftware.path;

import java.util.ArrayList;
import java.util.List;

public class AttachedTreeNode<T> implements Comparable<AttachedTreeNode<T>> {
	private final List<AttachedTreeNode<T>> adjacentNodes;

	private T attached;
	private AttachedTreeNode<T> parent;
	private int weight;
	private boolean settled;

	public AttachedTreeNode(T attached) {
		this.adjacentNodes = new ArrayList<AttachedTreeNode<T>>();
		this.attached = attached;
		reset();
	}

	public List<AttachedTreeNode<T>> getAdjacentNodes() {
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

	public void detach() {
		for (AttachedTreeNode<T> adjNode : getAdjacentNodes()) {
			adjNode.getAdjacentNodes().remove(this);
		}
	}

	public void reset() {
		this.weight = Integer.MAX_VALUE;
		this.settled = false;
		setParent(null);
	}

	public void settle() {
		this.settled = true;
	}

	public boolean isNotSettled() {
		return !settled;
	}
}
