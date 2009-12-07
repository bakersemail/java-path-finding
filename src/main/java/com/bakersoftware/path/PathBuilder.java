package com.bakersoftware.path;

import java.util.ArrayList;
import java.util.List;

public class PathBuilder<T> {
	private static final int MINIMUM_WEIGHT = 1;

	private final List<AttachedTreeNode<T>> nodes;

	public PathBuilder() {
		nodes = new ArrayList<AttachedTreeNode<T>>();
	}

	public PathBuilder<T> withVertex(T source, T destination) {
		return withVertex(source, destination, MINIMUM_WEIGHT);
	}

	private PathBuilder<T> withVertex(T source, T destination, int weight) {
		if (weight < MINIMUM_WEIGHT) {
			throw new IllegalArgumentException("Minimum weight is " + MINIMUM_WEIGHT);
		}

		AttachedTreeNode<T> node = getNewOrExistingNodeForAttached(source);
		node.addAjacentNode(getNewOrExistingNodeForAttached(destination));
		return this;
	}

	public List<AttachedTreeNode<T>> list() {
		return nodes;
	}

	public AttachedTreeNode<T> findNodeWithAttached(T attached) {
		for (AttachedTreeNode<T> node : nodes) {
			if (node.getAttached().equals(attached)) {
				return node;
			}
		}
		return null;
	}

	private AttachedTreeNode<T> getNewOrExistingNodeForAttached(T attached) {
		AttachedTreeNode<T> node = findNodeWithAttached(attached);
		if (node == null) {
			node = addNodeWithAttached(attached);
		}
		return node;
	}

	private AttachedTreeNode<T> addNodeWithAttached(T attached) {
		AttachedTreeNode<T> node = new AttachedTreeNode<T>(attached);
		nodes.add(node);
		return node;
	}
}
