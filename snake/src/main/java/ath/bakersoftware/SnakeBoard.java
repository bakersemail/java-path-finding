package ath.bakersoftware;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JFrame;

public class SnakeBoard {

	private static final int STEP_WAIT = 30;
	private static final int BOARD_WIDTH = 80;
	private static final int BOARD_HEIGHT = 80;
	private static final int STARTING_FOOD = 20;

	private final int width;
	private final int height;
	private final Collection<DrawablePart> parts;
	private final Collection<FoodPart> food;
	private final Collection<DrawablePart> walls;

	public SnakeBoard(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.food = new HashSet<FoodPart>(STARTING_FOOD);
		this.parts = new HashSet<DrawablePart>(STARTING_FOOD);
		this.walls = new HashSet<DrawablePart>();
		addWalls();
		for (int i = 0; i < STARTING_FOOD; i++) {
			addRandomFood();
		}
	}

	private void addWalls() {
		for (int x=0; x < width; x++) {
			walls.add(new WallPart(x, 0));
		}
		
		for (int x=0; x < width; x++) {
			walls.add(new WallPart(x, height - 1));
		}
		
		for (int y=0; y < height; y++) {
			walls.add(new WallPart(0, y));
		}
		
		for (int y=0; y < height; y++) {
			walls.add(new WallPart(width - 1, y));
		}
		parts.addAll(walls);
	}

	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame("Snake");
		frame.setPreferredSize(new Dimension(488, 514));
		frame.setSize(488, 514);
		SnakeBoard snakeBoard = new SnakeBoard(BOARD_WIDTH, BOARD_HEIGHT);
		
		Drawer drawer = new Drawer(snakeBoard, frame);
		prepareFrame(frame, drawer);

		SnakePart snake = SnakePart.newSnake();
		drawer.setSnake(snake);
		
		Ai ai = new Ai(snakeBoard, snake);
		ai.step();
		Rules rules = new Rules(snake, BOARD_WIDTH, BOARD_HEIGHT);
		Thread thread = new Thread(new Runner(drawer, rules, ai, STEP_WAIT));
		thread.start();
		thread.join();
	}

	private static void prepareFrame(final JFrame frame, final Drawer drawer) {
		frame.setContentPane(drawer);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				drawer.setDrawWidth(frame.getWidth());
				drawer.setDrawHeight(frame.getHeight());
			}
		});
	}

	public void addRandomFood() {
		int positionX = (int) (Math.random() * (width - 2)) + 1;
		int positionY = (int) (Math.random() * (height - 2)) + 1;
		FoodPart newFood = new FoodPart(positionX, positionY);
		food.add(newFood);
		parts.add(newFood);
	}
	
	public Collection<DrawablePart> getParts() {
		return parts;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Collection<DrawablePart> getStaticParts() {
		return walls;
	}

	public Collection<FoodPart> getFood() {
		return food;
	}

	public void removeFood(FoodPart eaten) {
		parts.remove(eaten);
		food.remove(eaten);
	}
}
