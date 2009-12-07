package com.bakersoftware.path;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class PathBuilderTest {
	@Test
	public void shouldBuildPath() {
		PathBuilder<Integer> builder = new PathBuilder<Integer>();
		List<AttachedTreeNode<Integer>> path = builder.withVertex(1, 2).withVertex(1, 3)
				.withVertex(2, 3).list();

		assertEquals(3, path.size());
		assertEquals(path.get(0).getAttached(), Integer.valueOf(1));
		assertEquals(path.get(1).getAttached(), Integer.valueOf(2));
		assertEquals(path.get(2).getAttached(), Integer.valueOf(3));

		assertEquals(path.get(0).getAdjacentNodes().size(), 2);
		assertEquals(path.get(0).getAdjacentNodes().get(0).getAttached(), Integer.valueOf(2));
		assertEquals(path.get(0).getAdjacentNodes().get(1).getAttached(), Integer.valueOf(3));

		assertEquals(path.get(1).getAdjacentNodes().size(), 1);
		assertEquals(path.get(1).getAdjacentNodes().get(0).getAttached(), Integer.valueOf(3));

		assertEquals(path.get(2).getAdjacentNodes().size(), 0);
	}
}
