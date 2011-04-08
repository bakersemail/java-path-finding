package com.google.code.java_path_finding

class PathBuilder<T> {
	static final int MINIMUM_WEIGHT = 1
	
	def nodes = [:]
	
	PathBuilder<T> addBiDirectionalVertex(T source, T destination) {
		AttachedTreeNode<T> node = getNewOrExistingNodeForAttached(source)
		AttachedTreeNode<T> destinationNode = getNewOrExistingNodeForAttached(destination)
		node.adjacentNodes << destinationNode
		destinationNode.adjacentNodes << node
		this
	}
	
	private def getNewOrExistingNodeForAttached(T attached) {
		def node = nodes.get(attached)
		node == null ? addNodeWithAttached(attached) : node 
	}
	
	private def addNodeWithAttached(T attached) {
		def node = new AttachedTreeNode<T>(attached: attached, index: nodes.size())
		nodes.put attached, node
		node
	}
}
