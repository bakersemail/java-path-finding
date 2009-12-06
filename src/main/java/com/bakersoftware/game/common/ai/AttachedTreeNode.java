package com.bakersoftware.game.common.ai;

import java.util.ArrayList;
import java.util.List;

import com.bakersoftware.game.common.ai.TreeNode;

public class AttachedTreeNode<T> implements TreeNode {
	private final int index;
	private final List<TreeNode> adjacentNodes;

	private T attached;
	private TreeNode parent;
	private int weight;

	public AttachedTreeNode(T attached, int index) {
		this.index = index;
		this.adjacentNodes = new ArrayList<TreeNode>();
		this.weight = Integer.MAX_VALUE;
		this.attached = attached;
	}

	public List<TreeNode> getAdjacentNodes() {
		return this.adjacentNodes;
	}

	public T getAttached() {
		return this.attached;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void addAjacentNode(TreeNode treeNode) {
		this.adjacentNodes.add(treeNode);
	}

	public int compareTo(TreeNode o) {
		return Integer.valueOf(this.weight).compareTo(o.getWeight());
	}

	public int getIndex() {
		return this.index;
	}

	public void detach() {
		for (TreeNode adjNode : getAdjacentNodes()) {
			adjNode.getAdjacentNodes().remove(this);
		}
	}
}
