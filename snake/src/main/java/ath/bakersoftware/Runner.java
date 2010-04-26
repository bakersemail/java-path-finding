package ath.bakersoftware;

import java.util.concurrent.TimeUnit;

public class Runner implements Runnable {

	private final Drawer drawer;
	private final Rules rules;
	private final Ai ai;
	
	private boolean running;

	public Runner(Drawer drawer, Rules rules, Ai ai) {
		this.drawer = drawer;
		this.rules = rules;
		this.ai = ai;		
	}
	
	@Override
	public void run() {
		running = true;
		while (running) {
			drawer.repaint();
			try {
				TimeUnit.MILLISECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ai.step();
			drawer.repaint();
			
			running = !rules.isGameOver();
		}
	}
}
