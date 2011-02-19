package ath.bakersoftware;

import java.util.concurrent.TimeUnit;

public class Runner implements Runnable {

	private final Rules rules;
	private final Ai ai;
	private final int stepWait;
	private final SnakePart snake;
	private final EventHandler eventHandler;
	
	private boolean running;

	public Runner(EventHandler eventHandler, SnakePart snake, Rules rules, Ai ai, int stepWait) {
		this.eventHandler = eventHandler;
		this.snake = snake;
		this.rules = rules;
		this.ai = ai;
		this.stepWait = stepWait;		
	}
	
	@Override
	public void run() {
		running = true;
		while (running) {
			eventHandler.redraw();
			if (stepWait > 0) {
				delay();
			}
			
			ai.step();
			eventHandler.redraw();
			
			running = !rules.isGameOver(snake);
		}
	}
	
	public Ai getAi() {
		return ai;
	}

	private void delay() {
		try {
			TimeUnit.MILLISECONDS.sleep(stepWait);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
