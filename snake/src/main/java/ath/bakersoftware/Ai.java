package ath.bakersoftware;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.code.java_path_finding.AttachedTreeNode;
import com.google.code.java_path_finding.PathBuilder;
import com.google.code.java_path_finding.ShortestPathCalculator;

public class Ai {
	private final PathBuilder<Part> builder;
	private final SnakeBoard board;
	private final SnakePart snake;
	private final EventHandler eventHandler;

	private Stack<AttachedTreeNode<Part>> path;

	public Ai(EventHandler eventHandler, SnakeBoard board, SnakePart snake) {
		this.eventHandler = eventHandler;
		this.board = board;
		this.snake = snake;
		this.builder = new PathBuilder<Part>();
		initPaths();
		addStatics();
	}

	private void initPaths() {
		Part[][] parts = new Part[board.getWidth()][board.getHeight()];
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				parts[i][j] = new Part(i, j);
			}
		}

		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight() - 1; j++) {
				builder.addBiDirectionalVertex(parts[i][j], parts[i][j + 1]);
			}
		}

		for (int j = 0; j < board.getHeight(); j++) {
			for (int i = 0; i < board.getWidth() - 1; i++) {
				builder.addBiDirectionalVertex(parts[i][j], parts[i + 1][j]);
			}
		}
	}

	public void step() {
		if (path == null || path.isEmpty()) {
			path = findClosestFoodPath(snake);
		}

		snake.move();
		checkFood();

		AttachedTreeNode<Part> snakePart = builder.findNodeWithAttached(snake);
		if (snakePart == null) {
			return;
		}
		snakePart.removeAllIncomingConnections();
		SnakePart tail = snake.getTail();
		builder.findNodeWithAttached(tail).restoreAllConnectionsToSelf();
		if (path != null && !path.isEmpty()) {
			AttachedTreeNode<Part> next = path.pop();
			Part point = next.getAttached();
			snake.setDirectionX(point.getPositionX() - snake.getPositionX());
			snake.setDirectionY(point.getPositionY() - snake.getPositionY());
		}
	}
	
	public void resetPath() {
		resetJoins();
		path = findClosestFoodPath(snake);
	}

	private Stack<AttachedTreeNode<Part>> findClosestFoodPath(SnakePart snake) {
		AttachedTreeNode<Part> headNode = builder.findNodeWithAttached(snake);
		ShortestPathCalculator<Part> pathCalculator = new ShortestPathCalculator<Part>();

		List<Stack<AttachedTreeNode<Part>>> paths = new LinkedList<Stack<AttachedTreeNode<Part>>>();
		for (DrawablePart food : board.getFood()) {
			AttachedTreeNode<Part> foodNode = builder.findNodeWithAttached(food);
			paths.add(pathCalculator.calculatePath(headNode, foodNode, board.getWidth()	* board.getHeight()));
		}
		return getShorestPath(paths);
	}

	private void resetJoins() {
		builder.resetNodes();
	}

	private void checkFood() {
		FoodPart eaten = null;
		for (FoodPart part : board.getFood()) {
			if (part.equals(snake)) {
				snake.grow();
				eaten = part;
			}
		}
		if (eaten != null) {
			eventHandler.ateFood(snake, eaten);
		}
	}

	private Stack<AttachedTreeNode<Part>> getShorestPath(
			List<Stack<AttachedTreeNode<Part>>> paths) {
		int shorestDistance = Integer.MAX_VALUE;
		Stack<AttachedTreeNode<Part>> shorestPath = null;

		for (Stack<AttachedTreeNode<Part>> path : paths) {
			if (path.size() < shorestDistance && path.size() > 1) {
				shorestPath = path;
				shorestDistance = shorestPath.size();
			}
		}
		return shorestPath;
	}

	private void addStatics() {
		for (DrawablePart part : board.getStaticParts()) {
			builder.findNodeWithAttached(part).removeAllIncomingConnections();
		}
	}
}
