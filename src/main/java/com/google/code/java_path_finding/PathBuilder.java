package com.google.code.java_path_finding;

import java.util.HashMap;
import java.util.Map;

public class PathBuilder<T> {
	private static final int MINIMUM_WEIGHT = 1;

	private final Map<T, AttachedTreeNode<T>> nodes;

	public PathBuilder() {
		this.nodes = new HashMap<T, AttachedTreeNode<T>>();
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

	public PathBuilder<T> addBiDirectionalVertex(T source, T destination) {
		AttachedTreeNode<T> node = getNewOrExistingNodeForAttached(source);
		AttachedTreeNode<T> destinationNode = getNewOrExistingNodeForAttached(destination);
		node.addAjacentNode(destinationNode);
		destinationNode.addAjacentNode(node);
		return this;
	}

	public Map<T, AttachedTreeNode<T>> list() {
		return nodes;
	}

	public AttachedTreeNode<T> findNodeWithAttached(T attached) {
		return nodes.get(attached);
	}

	public void resetNodes() {
		for (AttachedTreeNode<T> node : nodes.values()) {
			node.reset();
		}
	}
	
	private AttachedTreeNode<T> getNewOrExistingNodeForAttached(T attached) {
		AttachedTreeNode<T> node = findNodeWithAttached(attached);
		if (node == null) {
			node = addNodeWithAttached(attached);
		}
		return node;
	}

	private AttachedTreeNode<T> addNodeWithAttached(T attached) {
		AttachedTreeNode<T> node = new AttachedTreeNode<T>(attached, nodes.size());
		nodes.put(attached, node);
		return node;
	}
}
