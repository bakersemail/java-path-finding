package com.bakersoftware.game.common.ai;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class ShortestPathCalculatorTest {
	private List<AttachedTreeNode<Integer>> nodes;
	private ShortestPathCalculator<Integer> calculator;

	@Before
	public void setUp() {
		calculator = new ShortestPathCalculator<Integer>();

		nodes = new ArrayList<AttachedTreeNode<Integer>>();
		for (int i = 0; i < 10; i++) {
			nodes.add(new AttachedTreeNode<Integer>(i, i));
		}
	}

	@Test
	public void shouldFindShortestPath() {
		AttachedTreeNode<Integer> source = nodes.get(0);
		AttachedTreeNode<Integer> destination = nodes.get(9);

		source.addAjacentNode(nodes.get(1));
		nodes.get(1).addAjacentNode(nodes.get(2));
		nodes.get(2).addAjacentNode(destination);

		// Some other long path which should be avoided
		nodes.get(1).addAjacentNode(nodes.get(3));
		nodes.get(3).addAjacentNode(nodes.get(4));
		nodes.get(4).addAjacentNode(nodes.get(5));
		nodes.get(5).addAjacentNode(nodes.get(6));
		nodes.get(6).addAjacentNode(destination);

		Stack<AttachedTreeNode<Integer>> path = calculator.calculatePath(
				source, destination, 100);

		assertTrue(path.pop().equals(nodes.get(1)));
		assertTrue(path.pop().equals(nodes.get(2)));
		assertTrue(path.pop().equals(destination));

		assertNull(source.getParent());
		assertTrue(path.size() == 0);
	}
}
