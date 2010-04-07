package ath.bakersoftware;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

public class ShortestPathTest {
	@Test
	public void shouldReturnShortestPath() {
		PathNode start = new PathNode();
		PathNode middleDirect = new PathNode();
		PathNode middleIndirect = new PathNode();
		PathNode finish = new PathNode();

		start.addPathTo(middleIndirect);
		start.addPathTo(middleDirect);
		middleIndirect.addPathTo(middleDirect);
		middleDirect.addPathTo(finish);

		ShortestPathService service = new ShortestPathService();
		Stack<PathNode> path = service.findShortestPath(start, finish);
		PathNode node = path.pop();
		assertEquals(start, node);
	}
}
