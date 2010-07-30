package com.google.code.java_path_finding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class AttachedTreeNodeTest {
	@Test
	public void shouldRemoveSelfFromAllAdjacentNodesWhenRemovingAllIncomingConnections() {
		AttachedTreeNode<String> node = new AttachedTreeNode<String>(null, 0);
		AttachedTreeNode<String> adjNode = new AttachedTreeNode<String>(null, 0);
		node.addAjacentNode(adjNode);
		adjNode.addAjacentNode(node);
		
		assertTrue(node.getAdjacentNodes().contains(adjNode));
		adjNode.removeAllIncomingConnections();
		assertFalse(node.getAdjacentNodes().contains(adjNode));
	}
	
	@Test
	public void shouldRestoreAllIncomingConnectionsToAdjNodesUponRestore() {
		AttachedTreeNode<String> node = new AttachedTreeNode<String>(null, 0);
		AttachedTreeNode<String> adjNode = new AttachedTreeNode<String>(null, 0);
		node.addAjacentNode(adjNode);
		
		assertFalse(adjNode.getAdjacentNodes().contains(node));
		node.restoreAllConnectionsToSelf();
		assertTrue(adjNode.getAdjacentNodes().contains(node));
	}
}
