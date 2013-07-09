package ath.bakersoftware;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SnakeManager implements EventHandler {

	private final Drawer drawer;
	private final int stepWait;
	private final List<Runner> runners;
	private final SnakeBoard board;
	private final List<SnakePart> snakes;
	
	public SnakeManager(SnakeBoard board, Drawer drawer, int stepWait) {
		this.board = board;
		this.drawer = drawer;
		this.stepWait = stepWait;
		this.runners = new ArrayList<Runner>();
		this.snakes = new ArrayList<SnakePart>();
	}
	
	public Runner addSnake(int startX, int startY) {
		SnakePart snake = SnakePart.newSnake(startX, startY);
		
		Ai ai = new Ai(this, board, snake);
		ai.step();
		Runner runner = new Runner(this, ai);
		runners.add(runner);
		snakes.add(snake);
		drawer.setSnakes(snakes);
		return runner;
	}

	@Override
	public void redraw() {
		drawer.repaint();
	}

	@Override
	public void ateFood(SnakePart snake, FoodPart eaten) {
		board.replaceFood(eaten);
		for (Runner runner : runners) {
			runner.getAi().resetPath();
		}
	}

	public void run() {
		while (!runners.isEmpty()) {
            long start = System.currentTimeMillis();

            Iterator<Runner> i = runners.iterator();
            while (i.hasNext()) {
                boolean okay = i.next().runStep();
                if (!okay) {
                    System.out.println("Snake died");
                    i.remove();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(start + stepWait - System.currentTimeMillis());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        System.out.println("All the snakes are dead");
	}

}
