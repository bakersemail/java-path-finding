package com.bakersoftware.game.common.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PathBuilder<T> {
	private static final int MINIMUM_WEIGHT = 1;

	private final List<AttachedTreeNode<T>> nodes;

	private final Comparator<T> comparator;

	public PathBuilder(Comparator<T> comparator) {
		this.comparator = comparator;
		this.nodes = new ArrayList<AttachedTreeNode<T>>();
	}

	public PathBuilder<T> withVertex(T source, T destination) {
		return withVertex(source, destination, MINIMUM_WEIGHT);
	}

	public PathBuilder<T> withVertex(T source, T destination, int weight) {
		if (weight < MINIMUM_WEIGHT) {
			throw new IllegalArgumentException("Minimum weight is "
					+ MINIMUM_WEIGHT);
		}

		AttachedTreeNode<T> node = getNewOrExistingNodeForAttached(source);
		AttachedTreeNode<T> destinationNode = getNewOrExistingNodeForAttached(destination);
		node.addAjacentNode(destinationNode);
		return this;
	}

	public PathBuilder<T> withBiDirectionalVertex(T source, T destination) {
		AttachedTreeNode<T> node = getNewOrExistingNodeForAttached(source);
		AttachedTreeNode<T> destinationNode = getNewOrExistingNodeForAttached(destination);
		node.addAjacentNode(destinationNode);
		destinationNode.addAjacentNode(node);
		return this;
	}

	public List<AttachedTreeNode<T>> list() {
		return nodes;
	}

	private AttachedTreeNode<T> getNewOrExistingNodeForAttached(T attached) {
		AttachedTreeNode<T> node = findNodeWithAttached(attached);
		if (node == null) {
			node = addNodeWithAttached(attached);
		}
		return node;
	}

	private AttachedTreeNode<T> addNodeWithAttached(T attached) {
		AttachedTreeNode<T> node = new AttachedTreeNode<T>(attached, nodes
				.size());
		nodes.add(node);
		return node;
	}

	public AttachedTreeNode<T> findNodeWithAttached(T attached) {
		for (AttachedTreeNode<T> node : nodes) {
			if (comparator.compare(node.getAttached(), attached) == 0) {
				return node;
			}
		}
		return null;
	}
}
