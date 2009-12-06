package com.bakersoftware.game.common.ai;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class ShortestPathCalculatorTest {
	private TreeNode[] nodes;
	private ShortestPathCalculator calculator;

	@Before
	public void setUp() {
		calculator = new ShortestPathCalculator();

		nodes = new TreeNode[10];
		for (int i = 0; i < 10; i++) {
			nodes[i] = new AttachedTreeNode<Object>(i, i);
		}
	}

	@Test
	public void shouldFindShortestPath() {
		TreeNode source = nodes[0];
		TreeNode destination = nodes[9];

		source.addAjacentNode(nodes[1]);
		nodes[1].addAjacentNode(nodes[2]);
		nodes[2].addAjacentNode(destination);

		// Some other long path which should be avoided
		nodes[1].addAjacentNode(nodes[3]);
		nodes[3].addAjacentNode(nodes[4]);
		nodes[4].addAjacentNode(nodes[5]);
		nodes[5].addAjacentNode(nodes[6]);
		nodes[6].addAjacentNode(destination);

		Stack<TreeNode> path = calculator.calculatePath(source, destination, 100);

		assertTrue(path.pop().equals(nodes[1]));
		assertTrue(path.pop().equals(nodes[2]));
		assertTrue(path.pop().equals(destination));

		assertNull(source.getParent());
		assertTrue(path.size() == 0);
	}
}
