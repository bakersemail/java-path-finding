package ath.bakersoftware;

import java.util.concurrent.TimeUnit;

public class Runner implements Runnable {

	private final Drawer drawer;
	private final Rules rules;
	private final Ai ai;
	private final int stepWait;
	
	private boolean running;

	public Runner(Drawer drawer, Rules rules, Ai ai, int stepWait) {
		this.drawer = drawer;
		this.rules = rules;
		this.ai = ai;
		this.stepWait = stepWait;		
	}
	
	@Override
	public void run() {
		running = true;
		while (running) {
			drawer.repaint();
			if (stepWait > 0) {
				delay();
			}
			
			ai.step();
			drawer.repaint();
			
			running = !rules.isGameOver();
		}
	}

	private void delay() {
		try {
			TimeUnit.MILLISECONDS.sleep(stepWait);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
