package com.bakersoftware.path;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Stack;

import org.junit.Test;

public class PathFindingIntegrationTest {
	@Test
	public void shouldReturnShortestPathFromBuilder() {
		PathBuilder<String> builder = new PathBuilder<String>();
		ShortestPathCalculator<String> calculator = new ShortestPathCalculator<String>();

		builder.withVertex("a", "b");
		builder.withVertex("a", "c");
		builder.withVertex("a", "d");

		builder.withVertex("b", "h");

		builder.withVertex("c", "b");
		builder.withVertex("c", "d");
		builder.withVertex("c", "f");

		builder.withVertex("d", "e");
		builder.withVertex("d", "i");

		builder.withVertex("e", "c");
		builder.withVertex("e", "f");
		builder.withVertex("e", "g");
		builder.withVertex("e", "i");

		builder.withVertex("f", "b");
		builder.withVertex("f", "h");

		builder.withVertex("g", "h");
		builder.withVertex("g", "i");

		builder.withVertex("i", "h");

		List<AttachedTreeNode<String>> nodes = builder.list();
		assertEquals(9, nodes.size());

		AttachedTreeNode<String> source = builder.findNodeWithAttached("a");
		AttachedTreeNode<String> destination = builder.findNodeWithAttached("i");
		Stack<AttachedTreeNode<String>> path = calculator.calculatePath(source, destination, 9);
		assertEquals(path.size(), 2);
		assertEquals(path.pop().getAttached(), "d");
		assertEquals(path.pop().getAttached(), "i");
	}
}
