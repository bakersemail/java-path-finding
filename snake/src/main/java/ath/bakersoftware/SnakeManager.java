package ath.bakersoftware;

import java.util.ArrayList;
import java.util.List;

public class SnakeManager implements EventHandler {

	private final Drawer drawer;
	private final int stepWait;
	private final List<Runner> runners;
	private final SnakeBoard board;
	
	public SnakeManager(SnakeBoard board, Drawer drawer, int stepWait) {
		this.board = board;
		this.drawer = drawer;
		this.stepWait = stepWait;
		this.runners = new ArrayList<Runner>();
	}
	
	public Runner addSnake() {
		SnakePart snake = SnakePart.newSnake();
		drawer.setSnake(snake);
		
		Ai ai = new Ai(this, board, snake);
		ai.step();
		Rules rules = new Rules(board.getWidth(), board.getHeight());
		Runner runner = new Runner(this, snake, rules, ai, stepWait);
		runners.add(runner);
		return runner;
	}

	@Override
	public void redraw() {
		drawer.repaint();
	}

	@Override
	public void ateFood(SnakePart snake, FoodPart eaten) {
		board.removeFood(eaten);
		board.addRandomFood();
		for (Runner runner : runners) {
			runner.getAi().resetPath();
		}
	}

}
