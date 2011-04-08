package com.google.code.java_path_finding

class PathBuilder {
	static final int MINIMUM_WEIGHT = 1
	
	def nodes = [:]
	
	PathBuilder addBiDirectionalVertex(def source, def destination) {
		AttachedTreeNode node = getNewOrExistingNodeForAttached(source)
		AttachedTreeNode destinationNode = getNewOrExistingNodeForAttached(destination)
		node.adjacentNodes << destinationNode
		destinationNode.adjacentNodes << node
		this
	}
	
	private def getNewOrExistingNodeForAttached(def attached) {
		def node = nodes.get(attached)
		node == null ? addNodeWithAttached(attached) : node 
	}
	
	private def addNodeWithAttached(def attached) {
		def node = new AttachedTreeNode(attached: attached, index: nodes.size())
		nodes.put attached, node
		node
	}
}
