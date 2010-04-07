package ath.bakersoftware;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bakersoftware.game.common.ai.AttachedTreeNode;
import com.bakersoftware.game.common.ai.PathBuilder;
import com.bakersoftware.game.common.ai.ShortestPathCalculator;

public class SnakeBoard extends JPanel implements Runnable, SnakeEventHandler,
		PartDrawProvider {
	private static final long serialVersionUID = 1L;

	private static final int BOARD_WIDTH = 30;
	private static final int BOARD_HEIGHT = 30;
	private static final int STARTING_FOOD = 10;

	private final int width;
	private final int height;
	private final List<FoodPart> food;
	private final AtomicInteger foodEaten = new AtomicInteger();

	private volatile boolean running;

	private SnakePart snake;
	private int level;

	private int drawWidth;
	private int drawHeight;

	private List<AttachedTreeNode<Part>> joins;
	private PathBuilder<Part> builder;
	private Stack<AttachedTreeNode<Part>> path;

	private final Map<Part, AttachedTreeNode<Part>> nodesToRejoin = new HashMap<Part, AttachedTreeNode<Part>>();

	public SnakeBoard(int width, int height, Window parent) {
		this.width = width;
		this.height = height;

		drawWidth = parent.getWidth();
		drawHeight = parent.getHeight();
		this.food = new ArrayList<FoodPart>(STARTING_FOOD);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(drawWidth, drawHeight));
		initPaths();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (FoodPart part : food) {
			part.draw(g);
		}
		snake.draw(g);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + foodEaten, drawWidth - getPartDrawWidth() * 2,
				10);
		g.drawString("Level: " + level, drawWidth - getPartDrawHeight() * 2
				- 50, 10);

		drawJoints(g);
	}

	private void drawJoints(Graphics g) {
		g.setColor(Color.WHITE);
		for (AttachedTreeNode<Part> joint : builder.list()) {
			Part jointAttached = joint.getAttached();

			for (AttachedTreeNode<Part> joinedTo : joint.getAdjacentNodes()) {
				Part joinedToAttached = joinedTo.getAttached();
				g.drawLine(getPartDrawWidth() * jointAttached.getPositionX(),
						getPartDrawHeight() * jointAttached.getPositionY(),
						getPartDrawWidth() * joinedToAttached.getPositionX(),
						getPartDrawHeight() * joinedToAttached.getPositionY());
			}
		}
	}

	public void run() {
		while (running) {
			repaint();
			try {
				int delay = 180 - 20 * level;
				if (delay < 0) {
					delay = 0;
				}
				Thread.sleep(delay + 20);
			} catch (InterruptedException e) {
				running = false;
			}
			animate();
			repaint();
		}
	}

	private synchronized void animate() {
		if (hitWall(snake)) {
			running = false;
		}
		if (path == null || path.isEmpty()) {
			path = findClosestFoodPath(snake);
		}

		snake.move();
		if (path != null && !path.isEmpty()) {
			AttachedTreeNode<Part> next = path.pop();
			Part point = next.getAttached();
			snake.setDirectionX(point.getPositionX() - snake.getPositionX());
			snake.setDirectionY(point.getPositionY() - snake.getPositionY());
		}

		Map<Part, AttachedTreeNode<Part>> toAddBack = removeTouched();
		if (toAddBack != null) {
			recycleNowUntouched(toAddBack);
		}
	}

	private void recycleNowUntouched(Map<Part, AttachedTreeNode<Part>> toAddBack) {
		for (Part part : toAddBack.keySet()) {
			AttachedTreeNode<Part> node = toAddBack.get(part);
			System.out.println("adding path back from " + part + " to "
					+ node.getAttached());
			builder.withBiDirectionalVertex(part, node.getAttached());
		}

		joins = builder.list();
	}

	private Map<Part, AttachedTreeNode<Part>> removeTouched() {
		Map<Part, AttachedTreeNode<Part>> toAddBack = new HashMap<Part, AttachedTreeNode<Part>>();
		AttachedTreeNode<Part> removedNode = builder
				.findNodeWithAttached(snake);
		if (removedNode == null) {
			return toAddBack;
		}
		removedNode.removeAllIncomingConnections();
		nodesToRejoin.put(snake, removedNode);

		SnakePart snakePart = snake;
		for (Part part : nodesToRejoin.keySet()) {
			AttachedTreeNode<Part> node = nodesToRejoin.get(part);
			Part attached = node.getAttached();
			boolean addBack = true;
			while (snakePart != null) {
				if (isSamePosition(snakePart, attached)) {
					addBack = false;
				} else {
					break;
				}
				snakePart = snakePart.getNextPart();
			}

			if (addBack) {
				toAddBack.put(part, node);
			}
		}
		return toAddBack;
	}

	private boolean isSamePosition(SnakePart snakePart, Part attached) {
		return attached.getPositionX() == snakePart.getPositionX()
				&& attached.getPositionY() == snakePart.getPositionY();
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

	private boolean hitSelf(SnakePart snake) {
		SnakePart aPart = snake.getNextPart();
		while (aPart != null) {
			if (isSamePosition(aPart, snake)) {
				return true;
			}
			aPart = aPart.getNextPart();
		}
		return false;
	}

	private boolean hitWall(SnakePart snake) {
		if (snake.getDirectionX() > 0 && snake.getPositionX() >= width - 1) {
			return true;
		}
		if (snake.getDirectionX() < 0 && snake.getPositionX() <= 0) {
			return true;
		}
		if (snake.getDirectionY() > 0 && snake.getPositionY() >= height - 1) {
			return true;
		}
		if (snake.getDirectionY() < 0 && snake.getPositionY() <= 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame("Snake");
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setBackground(Color.WHITE);
		final SnakeBoard snakeBoard = new SnakeBoard(BOARD_WIDTH, BOARD_HEIGHT,
				frame);
		for (int i = 0; i < STARTING_FOOD; i++) {
			snakeBoard.addRandomFood();
		}

		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				snakeBoard.setDrawWidth(frame.getWidth());
				snakeBoard.setDrawHeight(frame.getHeight());
			}

		});
		SnakePart snake = new SnakePart(snakeBoard, 10, 10, Color.RED,
				snakeBoard);
		SnakePart middle = new SnakePart(null, 11, 10, Color.YELLOW, snakeBoard);
		SnakePart tail = new SnakePart(null, 12, 10, Color.YELLOW, snakeBoard);
		snakeBoard.setSnake(snake);

		snake.setNextPart(middle);
		middle.setNextPart(tail);
		frame.setContentPane(snakeBoard);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new SnakeKeyHandler(snakeBoard, snake));
		frame.setLocationRelativeTo(null);

		snakeBoard.animate();
		snakeBoard.setRunning(true);
		while (!snakeBoard.running) {
			try {
				Thread.yield();
			} catch (Exception e) {
			}
		}
		Thread thread = new Thread(snakeBoard);
		thread.start();
		thread.join();
	}

	private void initPaths() {
		Part[][] board = new Part[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = new Part(i, j);
			}
		}

		builder = new PathBuilder<Part>(new PartLocationComparator());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height - 1; j++) {
				builder.withBiDirectionalVertex(board[i][j], board[i][j + 1]);
			}
		}

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width - 1; i++) {
				builder.withBiDirectionalVertex(board[i][j], board[i + 1][j]);
			}
		}
		joins = builder.list();
	}

	protected void setDrawWidth(int drawWidth) {
		this.drawWidth = drawWidth;
	}

	protected void setDrawHeight(int drawHeight) {
		this.drawHeight = drawHeight;
	}

	private void setSnake(SnakePart snake) {
		this.snake = snake;
	}

	@Override
	public void snakeMoved(SnakePart snake) {
		if (food != null) {
			FoodPart eaten = null;
			for (FoodPart part : food) {
				if (part.getPositionX() == snake.getPositionX()
						&& part.getPositionY() == snake.getPositionY()) {
					snake.grow();
					eaten = part;
					foodEaten.incrementAndGet();
					if (foodEaten.get() % 5 == 0) {
						level++;
					}
				}
			}
			if (eaten != null) {
				food.remove(eaten);
				addRandomFood();

				resetJoins();
				path = findClosestFoodPath(snake);
			}
		}
	}

	private Stack<AttachedTreeNode<Part>> findClosestFoodPath(SnakePart snake) {
		AttachedTreeNode<Part> headNode = builder.findNodeWithAttached(snake);
		ShortestPathCalculator<Part> pathCalculator = new ShortestPathCalculator<Part>();

		List<Stack<AttachedTreeNode<Part>>> paths = new ArrayList<Stack<AttachedTreeNode<Part>>>();
		for (FoodPart food : this.food) {
			AttachedTreeNode<Part> foodNode = builder
					.findNodeWithAttached(food);
			paths.add(pathCalculator.calculatePath(headNode, foodNode, width
					* height));
		}
		return getShorestPath(paths);
	}

	private void resetJoins() {
		for (AttachedTreeNode<Part> node : joins) {
			node.reset();
		}
	}

	public void addRandomFood() {
		int positionX = (int) (Math.random() * width);
		int positionY = (int) (Math.random() * height);
		food.add(new FoodPart(positionX, positionY, this));
	}

	@Override
	public int getPartDrawWidth() {
		return drawWidth / (width + 1);
	}

	@Override
	public int getPartDrawHeight() {
		return drawHeight / (height + 2);
	}
}
