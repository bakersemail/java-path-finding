package com.bakersoftware.game.common.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PathBuilderTest {
	private static final Integer NODE_1 = Integer.valueOf(1);
	private static final Integer NODE_2 = Integer.valueOf(2);
	private static final Integer NODE_3 = Integer.valueOf(3);
	
	private PathBuilder<Integer> builder;
	private Map<Integer, AttachedTreeNode<Integer>> path;

	@Before
	public void setUp() {
		builder = new PathBuilder<Integer>();
		path = builder.withVertex(NODE_1, NODE_2)
				.withVertex(NODE_1, NODE_3).withVertex(NODE_2, NODE_3).list();
	}
	
	@Test
	public void shouldBuildPath() {
		assertEquals(3, path.size());
		assertEquals(NODE_1, path.get(NODE_1).getAttached());
		assertEquals(NODE_2, path.get(NODE_2).getAttached());
		assertEquals(NODE_3, path.get(NODE_3).getAttached());

		assertEquals(2, path.get(1).getAdjacentNodes().size());
		assertEquals(1, path.get(2).getAdjacentNodes().size());
		assertEquals(0, path.get(3).getAdjacentNodes().size());

		assertTrue(containsAdjNode(path.get(NODE_1), NODE_2));
		assertTrue(containsAdjNode(path.get(NODE_1), NODE_3));
		
		assertTrue(containsAdjNode(path.get(NODE_2), NODE_3));
		assertFalse(containsAdjNode(path.get(NODE_2), NODE_1));
		assertFalse(containsAdjNode(path.get(NODE_3), NODE_1));
		assertFalse(containsAdjNode(path.get(NODE_3), NODE_2));
		assertFalse(containsAdjNode(path.get(NODE_3), NODE_3));
	}
	
	@Test
	public void shouldFindNodeWithAttached() {
		assertEquals(NODE_1, builder.findNodeWithAttached(NODE_1).getAttached());
		assertEquals(NODE_2, builder.findNodeWithAttached(NODE_2).getAttached());
		assertEquals(NODE_3, builder.findNodeWithAttached(NODE_3).getAttached());
	}
	
	@Test
	public void shouldResetNodes() {
		AttachedTreeNode<Integer> node = builder.findNodeWithAttached(NODE_1);
		int weight = node.getWeight();
		
		node.setWeight(weight + 1);
		assertFalse(weight == node.getWeight());
		builder.resetNodes();
		assertEquals(weight, node.getWeight());
	}
	
	@Test
	public void shouldAddBiDirectionalVertex() {
		PathBuilder<Integer> builder = new PathBuilder<Integer>();
		builder.addBiDirectionalVertex(NODE_1, NODE_2);
		
		assertEquals(2, builder.list().size());		
		assertEquals(1, builder.list().get(NODE_1).getAdjacentNodes().size());
		assertEquals(1, builder.list().get(NODE_2).getAdjacentNodes().size());
		assertTrue(containsAdjNode(builder.findNodeWithAttached(NODE_1), NODE_2));
		assertTrue(containsAdjNode(builder.findNodeWithAttached(NODE_2), NODE_1));
	}
	
	private boolean containsAdjNode(AttachedTreeNode<Integer> attachedTreeNode, Integer node) {
		for (AttachedTreeNode<Integer> aNode : attachedTreeNode.getAdjacentNodes()) {
			if (aNode.getAttached().equals(node)) {
				return true;
			}
		}
		return false;
	}
}
