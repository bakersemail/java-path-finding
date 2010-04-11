package ath.bakersoftware;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bakersoftware.game.common.ai.AttachedTreeNode;
import com.bakersoftware.game.common.ai.PathBuilder;
import com.bakersoftware.game.common.ai.ShortestPathCalculator;

@SuppressWarnings("serial")
public class SnakeBoard extends JPanel implements Runnable, SnakeEventHandler,
		PartDrawProvider {

	private static final int BOARD_WIDTH = 80;
	private static final int BOARD_HEIGHT = 80;
	private static final int STARTING_FOOD = 15;

	private final int width;
	private final int height;
	private final List<FoodPart> food;
	private final PathBuilder<Part> builder;

	private volatile boolean running;

	private SnakePart snake;

	private int drawWidth;
	private int drawHeight;

	private Stack<AttachedTreeNode<Part>> path;

	public SnakeBoard(int width, int height, Window parent) {
		this.width = width;
		this.height = height;

		drawWidth = parent.getWidth();
		drawHeight = parent.getHeight();
		this.food = new ArrayList<FoodPart>(STARTING_FOOD);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(drawWidth, drawHeight));
		this.builder = new PathBuilder<Part>();
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
	}

	public void run() {
		while (running) {
			repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			animate();
			repaint();
		}
	}

	private void animate() {
		synchronized (builder) {
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
		
			AttachedTreeNode<Part> snakePart = builder.findNodeWithAttached(snake);
			if (snakePart == null) {
				return;
			}
			snakePart.removeAllIncomingConnections();
			SnakePart tail = snake.getTail();
			builder.findNodeWithAttached(tail).restoreAllConnectionsToSelf();
			
			if (hitSelf()) {
				resetJoins();
				path = findClosestFoodPath(snake);
			}
		}
	}

	private boolean hitSelf() {
		SnakePart part = snake.getNextPart();
		while (part != null) {
			if (snake.equals(part)) {
				return true;
			}
			part = part.getNextPart();
		}
		return false;
	}

	private Stack<AttachedTreeNode<Part>> getShorestPath(List<Stack<AttachedTreeNode<Part>>> paths) {
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
		final SnakeBoard snakeBoard = new SnakeBoard(BOARD_WIDTH, BOARD_HEIGHT, frame);
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
				TimeUnit.SECONDS.sleep(3);
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
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height - 1; j++) {
				builder.addBiDirectionalVertex(board[i][j], board[i][j + 1]);
			}
		}

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width - 1; i++) {
				builder.addBiDirectionalVertex(board[i][j], board[i + 1][j]);
			}
		}
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
				if (part.equals(snake)) {
					snake.grow();
					eaten = part;
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
			AttachedTreeNode<Part> foodNode = builder.findNodeWithAttached(food);
			paths.add(pathCalculator.calculatePath(headNode, foodNode, width * height));
		}
		return getShorestPath(paths);
	}

	private void resetJoins() {
		builder.resetNodes();
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
