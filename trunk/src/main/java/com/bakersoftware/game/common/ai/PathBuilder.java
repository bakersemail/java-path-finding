package com.bakersoftware.game.common.ai;

import java.util.ArrayList;
import java.util.List;

public class PathBuilder<T> {
	private final List<AttachedTreeNode<T>> nodes;

	private int index;

	public PathBuilder() {
		nodes = new ArrayList<AttachedTreeNode<T>>();
	}

	public void addConnection(T source, T destination) {
		addConnection(source, destination, 1);
	}

	public void addConnection(T source, T destination, int weight) {
		for (AttachedTreeNode<T> node : nodes) {
			if (node.getAttached().equals(source)) {
				node.addAjacentNode(new AttachedTreeNode<T>(destination, index));
				index++;
				return;
			}
		}

		AttachedTreeNode<T> newNode = new AttachedTreeNode<T>(source, index);
		newNode.addAjacentNode(new AttachedTreeNode<T>(destination, index));
		nodes.add(newNode);
		index++;
	}

	public List<AttachedTreeNode<T>> getNodes() {
		return nodes;
	}

	public AttachedTreeNode<T> getNodeForAttached(T attached) {
		for (AttachedTreeNode<T> node : nodes) {
			if (node.getAttached().equals(attached)) {
				return node;
			}
		}
		return null;
	}
}
